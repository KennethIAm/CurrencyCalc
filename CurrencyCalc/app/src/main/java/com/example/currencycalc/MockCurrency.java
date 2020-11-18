package com.example.currencycalc;

import android.content.Context;
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

    // Creates mock rates
    public ArrayList<Rate> getRates(String base, Context context) {
        ArrayList<Rate> rates = new ArrayList<>();

            rates.add(new Rate("DKK", 1.0));
            rates.add(new Rate("NOK", 2.0));
            rates.add(new Rate("SEK", 3.0));
            rates.add(new Rate("EUR", 4.0));
            rates.add(new Rate("USD", 5.0));

        return rates;
    }

    // Converts rates of the User's selected currency into other currencies.
    public List<String> getCurrencies(String base, Double amount, Context context) {
        ArrayList<Rate> currencies = getRates("", context);
        ArrayList<String> conversionRates = new ArrayList<>();
        Double baseValue = null;

        // Finds the value of the base currency, then makes its value the base value.
        for (int i = 0; i < currencies.size(); i++) {
            if (base == currencies.get(i).getName()) {
                baseValue = currencies.get(i).spotRate;
                currencies.remove(currencies.get(i));
            }
        }

        // Updates the conversionRates ArrayList with name and converted rates.
        for (int i = 0; i < currencies.size(); i++) {
            if (base != currencies.get(i).name)
                conversionRates.add(currencies.get(i).name + "\n" + String.valueOf(amount * (currencies.get(i).getSpotRate() / baseValue)));
        }

        return conversionRates;
    }

    // Returns a list of currencies for the user to choose from.
    @RequiresApi(api = Build.VERSION_CODES.R)
    public ArrayList<String> getCurrencyNames() {
        ArrayList<String> currencyNames = new ArrayList<>();

        currencyNames.add("DKK");
        currencyNames.add("NOK");
        currencyNames.add("SEK");
        currencyNames.add("EUR");
        currencyNames.add("USD");

        return currencyNames;
    }
}
