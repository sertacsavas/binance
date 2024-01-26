package com.sertac.binance.websocket.client;

import com.sertac.binance.currency.util.CurrencyUtil;
import com.sertac.binance.sound.TextToSpeechService;
import com.sertac.binance.websocket.config.BinanceApiConfig;
import com.sertac.binance.websocket.handler.MarketAggregateTradeHandler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableScheduling
public class BinanceClient {

    private final BinanceApiConfig binanceApiConfig;



    public BinanceClient(BinanceApiConfig binanceApiConfig) {
        this.binanceApiConfig = binanceApiConfig;
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            TextToSpeechService.read("Program started");
            CurrencyUtil.updateCurrencyRate(Currency.getInstance("USD"),Currency.getInstance("TRY"));
            //createBinanceStream("usdttry@trade",  new MarketTradeHandler());
            createBinanceStream("usdttry@aggTrade",
                    new MarketAggregateTradeHandler(new BigDecimal(1000), 1000 * 60 * 5,new BigDecimal(10000)));
            //createBinanceStream("btcusdt@aggTrade",new MarketAggregateTradeHandler(new BigDecimal(1), 1000 * 60 * 5,new BigDecimal(1000)));
            //createCombinedBinanceStream(Arrays.asList("usdttry@aggTrade","btcusdt@aggTrade"),  new DefaultHandler());
        };
    }

    private WebSocketSession createBinanceStream(String streamName, WebSocketHandler webSocketHandler) throws ExecutionException, InterruptedException {
        String binanceWebSocketUrl = binanceApiConfig.getBaseWsUrl() + "/ws/" + streamName;
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        return webSocketClient.doHandshake(webSocketHandler, binanceWebSocketUrl).get();
    }

    private WebSocketSession createCombinedBinanceStream(List<String> streamNameList, WebSocketHandler webSocketHandler) throws ExecutionException, InterruptedException {
        String binanceWebSocketUrl = binanceApiConfig.getBaseWsUrl() + "/stream?streams=" + streamNameList.stream().collect(Collectors.joining("/"));
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        return webSocketClient.doHandshake(webSocketHandler, binanceWebSocketUrl).get();
    }
}
