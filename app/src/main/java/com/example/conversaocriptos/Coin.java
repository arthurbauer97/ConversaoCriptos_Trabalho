package com.example.conversaocriptos;

import java.io.Serializable;

public class Coin implements Serializable {

    private String name;
    private Double buy;

    public Coin(String name, Double buy) {
        this.name=name;
        this.buy=buy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBuy() {
        return buy;
    }

    public String  getStringBuy() {
        return buy.toString();
    }
    public void setBuy(double buy) {
        this.buy = buy;
    }
}
