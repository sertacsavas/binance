package com.sertac.binance.websocket.model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TradeEvent {
    @JsonProperty("e")
    private String eventType;

    @JsonProperty("E")
    private long eventTime;

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("t")
    private long tradeId;

    @JsonProperty("p")
    private BigDecimal price;

    @JsonProperty("q")
    private BigDecimal quantity;

    @JsonProperty("b")
    private long buyerOrderId;

    @JsonProperty("a")
    private long sellerOrderId;

    @JsonProperty("T")
    private long tradeTime;

    @JsonProperty("m")
    private boolean isBuyerMarketMaker;

    @JsonProperty("M")
    private boolean ignore;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public long getTradeId() {
        return tradeId;
    }

    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public long getBuyerOrderId() {
        return buyerOrderId;
    }

    public void setBuyerOrderId(long buyerOrderId) {
        this.buyerOrderId = buyerOrderId;
    }

    public long getSellerOrderId() {
        return sellerOrderId;
    }

    public void setSellerOrderId(long sellerOrderId) {
        this.sellerOrderId = sellerOrderId;
    }

    public long getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(long tradeTime) {
        this.tradeTime = tradeTime;
    }

    public boolean isBuyerMarketMaker() {
        return isBuyerMarketMaker;
    }

    public void setBuyerMarketMaker(boolean buyerMarketMaker) {
        isBuyerMarketMaker = buyerMarketMaker;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    @Override
    public String toString() {
        return "TradeEvent{" +
                "eventType='" + eventType + '\'' +
                ", eventTime=" + eventTime +
                ", symbol='" + symbol + '\'' +
                ", tradeId=" + tradeId +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'' +
                ", buyerOrderId=" + buyerOrderId +
                ", sellerOrderId=" + sellerOrderId +
                ", tradeTime=" + tradeTime +
                ", isBuyerMarketMaker=" + isBuyerMarketMaker +
                ", ignore=" + ignore +
                '}';
    }
}