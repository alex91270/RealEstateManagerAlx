package com.example.realestatemanageralx.service;

public class RealApiService implements ApiService {
    private int currencyRate;

    @Override
    public void setCurrencyRate(int rate){
        currencyRate=rate;
    }

    @Override
    public int getCurrencyRate() {
        return currencyRate;
    }
}
