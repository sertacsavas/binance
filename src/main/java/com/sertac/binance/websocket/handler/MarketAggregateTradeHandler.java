package com.sertac.binance.websocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sertac.binance.currency.util.CurrencyUtil;
import com.sertac.binance.sound.TextToSpeechService;
import com.sertac.binance.websocket.config.ConsoleLogConfig;
import com.sertac.binance.websocket.model.AggregateTradeEvent;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

public class MarketAggregateTradeHandler extends TextWebSocketHandler {
    private BigDecimal minimumQuantityToPrintLog;
    private Integer resetSumDataIntervalMs;

    private BigDecimal minimumQuantityToRead;

    private Date sumDataUpdateDate;

    private BigDecimal sumOfBought = BigDecimal.ZERO;
    private BigDecimal sumOfSold = BigDecimal.ZERO;

    private boolean isSumUpdated;

    public MarketAggregateTradeHandler(BigDecimal minimumQuantityToPrintLog, Integer resetSumDataIntervalMs, BigDecimal minimumQuantityToRead) {

        this.minimumQuantityToPrintLog = minimumQuantityToPrintLog;
        this.resetSumDataIntervalMs = resetSumDataIntervalMs;
        this.minimumQuantityToRead = minimumQuantityToRead;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        AggregateTradeEvent aggregateTradeEvent = new ObjectMapper().readValue(message.getPayload(), AggregateTradeEvent.class);
        handleAggregateTradeEvent(aggregateTradeEvent);
    }

    private void handleAggregateTradeEvent(AggregateTradeEvent aggregateTradeEvent) {
        setSumDataByInterval();
        addToSum(aggregateTradeEvent);
        printLog(aggregateTradeEvent);
        playSound(aggregateTradeEvent);
    }

    private void playSound(AggregateTradeEvent aggregateTradeEvent) {
        if(aggregateTradeEvent.getQuantity().compareTo(minimumQuantityToRead) >= 0) {
            StringBuilder textToRead = new StringBuilder();
            textToRead.append(aggregateTradeEvent.isBuyerMarketMaker() ? "SOLD" : "BOUGHT");
            textToRead.append(", ");
            //textToRead.append(formatBigDecimalForReading(aggregateTradeEvent.getQuantity()));
            textToRead.append(aggregateTradeEvent.getQuantity().divide(new BigDecimal(1000)).intValue());
            textToRead.append("K, ");

            //textToRead.append(formatBigDecimalForReading(aggregateTradeEvent.getPrice()));
            textToRead.append((aggregateTradeEvent.getPrice().subtract(new BigDecimal(aggregateTradeEvent.getPrice().intValue()))).multiply(new BigDecimal(100)).intValue());


            TextToSpeechService.read(textToRead.toString());
        }
    }

    private String formatBigDecimalForReading(BigDecimal bigDecimal) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        return decimalFormat.format(bigDecimal);
    }

    private void setSumDataByInterval() {
        Date now = new Date();
        if (sumDataUpdateDate == null || (now.getTime() - sumDataUpdateDate.getTime() >= resetSumDataIntervalMs)) {
            sumOfBought = BigDecimal.ZERO;
            sumOfSold = BigDecimal.ZERO;
            sumDataUpdateDate = now;
            isSumUpdated = true;
        }

    }

    private void printLog(AggregateTradeEvent aggregateTradeEvent) {
        if (aggregateTradeEvent.getQuantity().compareTo(minimumQuantityToPrintLog) >= 0) {

            handleSumUpdated();
            String printLogString = preparePrintLog(aggregateTradeEvent);
            System.out.print(printLogString);
            String printSumLogString = preparePrintSumLog(aggregateTradeEvent);
            System.out.print(printSumLogString);
            System.out.println();
        }
    }

    private String prepareCurrencyInfoLog(AggregateTradeEvent aggregateTradeEvent) {
        StringBuilder currencyInfoStringBuilder = new StringBuilder();

        BigDecimal currencyRate = aggregateTradeEvent.getPrice().
                divide(CurrencyUtil.getCurrencyRate(
                                Currency.getInstance("USD"),
                                Currency.getInstance("TRY")),
                        5, RoundingMode.HALF_DOWN);
        currencyInfoStringBuilder.append(formatBigDecimal(currencyRate));
        return currencyInfoStringBuilder.toString();
    }

    private String preparePrintLog(AggregateTradeEvent aggregateTradeEvent) {
        StringBuilder textToPrint = new StringBuilder();
        textToPrint.append(aggregateTradeEvent.isBuyerMarketMaker() ? ConsoleLogConfig.ANSI_SOLD + "SOLD" : ConsoleLogConfig.ANSI_BOUGHT + "BOUGHT");
        textToPrint.append("\t");
        textToPrint.append(formatBigDecimal(aggregateTradeEvent.getQuantity()));
        textToPrint.append("\t");
        textToPrint.append("@");
        textToPrint.append(formatBigDecimal(aggregateTradeEvent.getPrice()));
        textToPrint.append(" ( ");
        textToPrint.append(prepareCurrencyInfoLog(aggregateTradeEvent));
        textToPrint.append(" - ");
        textToPrint.append(formatBigDecimal(CurrencyUtil.getCurrencyRate(
                Currency.getInstance("USD"),
                Currency.getInstance("TRY"))));
        textToPrint.append(" ) ");
        textToPrint.append(ConsoleLogConfig.ANSI_RESET);
        return textToPrint.toString();
    }

    private void handleSumUpdated() {
        if (isSumUpdated) {
            System.out.println();
            System.out.println("--------------------------------------------------------------------------------------------------------------------------");
            System.out.println();
            isSumUpdated = false;
        }
    }

    private String preparePrintSumLog(AggregateTradeEvent aggregateTradeEvent) {
        int comparisonResult = sumOfBought.compareTo(sumOfSold);
        StringBuilder textToPrint = new StringBuilder();
        textToPrint.append(" [");
        textToPrint.append(" SUM OF");
        textToPrint.append(" BOUGHT = " + formatBigDecimal(sumOfBought));
        textToPrint.append("\t");
        textToPrint.append(" SOLD = " + formatBigDecimal(sumOfSold));
        textToPrint.append("\t");

        if (comparisonResult == 0) {
            System.out.print(ConsoleLogConfig.ANSI_RESET);
        } else if (comparisonResult < 0) {
            System.out.print(ConsoleLogConfig.ANSI_SOLD);
            textToPrint.append(" SOLD MORE");
        } else if (comparisonResult > 0) {
            System.out.print(ConsoleLogConfig.ANSI_BOUGHT);
            textToPrint.append(" BOUGHT MORE");
        }
        textToPrint.append("\t");
        textToPrint.append(" DIFFERENCE = " + formatBigDecimal(sumOfBought.subtract(sumOfSold).abs()));
        textToPrint.append(" ]");
        textToPrint.append(" SYMBOL = " + aggregateTradeEvent.getSymbol());
        textToPrint.append(" DATE = " + getFormattedDateForConsoleLog(aggregateTradeEvent.getTradeTime()));
        textToPrint.append(" SUM UPDATE DATE = " + getFormattedDateForConsoleLog(sumDataUpdateDate.getTime()));
        textToPrint.append(ConsoleLogConfig.ANSI_RESET);
        return textToPrint.toString();
    }

    private String formatBigDecimal(BigDecimal bigDecimal) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.########");

        return decimalFormat.format(bigDecimal);
    }

    private String getFormattedDateForConsoleLog(long time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        return timeFormat.format(time);
    }

    private void addToSum(AggregateTradeEvent aggregateTradeEvent) {
        if (aggregateTradeEvent.isBuyerMarketMaker()) {
            sumOfSold = sumOfSold.add(aggregateTradeEvent.getQuantity());
        } else {
            sumOfBought = sumOfBought.add(aggregateTradeEvent.getQuantity());
        }
    }
}