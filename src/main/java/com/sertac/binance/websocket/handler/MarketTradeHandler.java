package com.sertac.binance.websocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sertac.binance.websocket.config.ConsoleLogConfig;
import com.sertac.binance.websocket.model.TradeEvent;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.math.BigDecimal;

public class MarketTradeHandler extends TextWebSocketHandler {
    private Integer quantityScale;
    private Integer priceScale;
    private BigDecimal minimumQuantityToHandle;

    public MarketTradeHandler(Integer quantityScale, Integer priceScale, BigDecimal minimumQuantityToHandle) {
        this.quantityScale = quantityScale;
        this.priceScale = priceScale;
        this.minimumQuantityToHandle = minimumQuantityToHandle;
    }

    public Integer getQuantityScale() {
        return quantityScale;
    }

    public void setQuantityScale(Integer quantityScale) {
        this.quantityScale = quantityScale;
    }

    public Integer getPriceScale() {
        return priceScale;
    }

    public void setPriceScale(Integer priceScale) {
        this.priceScale = priceScale;
    }

    public BigDecimal getMinimumQuantityToHandle() {
        return minimumQuantityToHandle;
    }

    public void setMinimumQuantityToHandle(BigDecimal minimumQuantityToHandle) {
        this.minimumQuantityToHandle = minimumQuantityToHandle;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {

        TradeEvent tradeEvent = new ObjectMapper().readValue(message.getPayload(), TradeEvent.class);

        if(tradeEvent.getQuantity().compareTo(minimumQuantityToHandle) >= 0){

            System.out.print(tradeEvent.isBuyerMarketMaker() ? ConsoleLogConfig.ANSI_SOLD+"SOLD" : ConsoleLogConfig.ANSI_BOUGHT+"BOUGHT");
            System.out.println("\t"+ tradeEvent.getQuantity().setScale(quantityScale) + "\t" + "@"+tradeEvent.getPrice().setScale(priceScale)+ ConsoleLogConfig.ANSI_RESET);

        }
    }
}