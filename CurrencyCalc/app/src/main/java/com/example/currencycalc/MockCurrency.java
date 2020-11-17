package com.example.currencycalc;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MockCurrency implements CurrencyDAO {

    public ArrayList<Rate> getRates(String base) {
        ArrayList<Rate> rates = new ArrayList<>();

            rates.add(new Rate("DKK", 1.0));
            rates.add(new Rate("NOK", 2.0));
            rates.add(new Rate("SEK", 3.0));
            rates.add(new Rate("EUR", 4.0));
            rates.add(new Rate("USD", 5.0));

        return rates;
    }

    public List<String> mockCurrency(String base, Double amount) {
        ArrayList<Rate> currencies = getRates("");
        ArrayList<String> conversionRates = new ArrayList<>();

        Double baseValue = null;

        for (int i = 0; i < currencies.size(); i++) {
            if (base == currencies.get(i).getName()) {
                baseValue = currencies.get(i).spotRate;
                currencies.remove(currencies.get(i));
            }
        }

        for (int i = 0; i < currencies.size(); i++) {
            if (base != currencies.get(i).name)
                conversionRates.add(currencies.get(i).name + "\n" + String.valueOf(amount * (currencies.get(i).getSpotRate() / baseValue)));
        }

        return conversionRates;
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public ArrayList<String> getCurrencies() {
        ArrayList<Rate> currencies = getRates("");
        ArrayList<String> currencyNames = new ArrayList<>();

        for (int i = 0; i < currencies.size(); i++) {
            currencyNames.add(currencies.get(i).name);
        }

        return currencyNames;
    }
}
