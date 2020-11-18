package com.example.currencycalc;

import android.content.Context;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public interface CurrencyDAO {
    public List<Rate> getRates(String base, Context context);
    public List<String> getCurrencyNames();
    public List<String> getCurrencies(String base, Double amount, Context context);
}
