package com.example.currencycalc;

public class Rate {
    public String name;
    public Double spotRate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSpotRate() {
        return this.spotRate;
    }

    public void setSpotRate(Double spotRate) {
        this.spotRate = spotRate;
    }

    public Rate(String name, Double spotRate){
        setName(name);
        setSpotRate(spotRate);
    }

}
