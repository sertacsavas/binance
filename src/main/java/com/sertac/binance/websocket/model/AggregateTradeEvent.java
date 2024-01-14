package com.sertac.binance.websocket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class AggregateTradeEvent {
    @JsonProperty("e")
    private String eventType;

    @JsonProperty("E")
    private long eventTime;

    @JsonProperty("s")
    private String symbol;

    @JsonProperty("a")
    private long aggregateTradeId;

    @JsonProperty("p")
    private BigDecimal price;

    @JsonProperty("q")
    private BigDecimal quantity;

    @JsonProperty("f")
    private long firstTradeId;

    @JsonProperty("l")
    private long lastTradeId;

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

    public long getAggregateTradeId() {
        return aggregateTradeId;
    }

    public void setAggregateTradeId(long aggregateTradeId) {
        this.aggregateTradeId = aggregateTradeId;
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

    public long getFirstTradeId() {
        return firstTradeId;
    }

    public void setFirstTradeId(long firstTradeId) {
        this.firstTradeId = firstTradeId;
    }

    public long getLastTradeId() {
        return lastTradeId;
    }

    public void setLastTradeId(long lastTradeId) {
        this.lastTradeId = lastTradeId;
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
        return "AggregateTradeEvent{" +
                "eventType='" + eventType + '\'' +
                ", eventTime=" + eventTime +
                ", symbol='" + symbol + '\'' +
                ", aggregateTradeId=" + aggregateTradeId +
                ", price='" + price + '\'' +
                ", quantity='" + quantity + '\'' +
                ", firstTradeId=" + firstTradeId +
                ", lastTradeId=" + lastTradeId +
                ", tradeTime=" + tradeTime +
                ", isBuyerMarketMaker=" + isBuyerMarketMaker +
                ", ignore=" + ignore +
                '}';
    }
}