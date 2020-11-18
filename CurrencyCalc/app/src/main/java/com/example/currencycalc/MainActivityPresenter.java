package com.example.currencycalc;

import android.content.Context;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPresenter {
    private CurrencyDAO currency = new MockCurrency();
    private View v;

    MainActivityPresenter(View v, CurrencyDAO cDAO) {
        this.v = v;
        this.currency = cDAO;
    }

    // Gets names of currencies the User can select and convert between.
    @RequiresApi(api = Build.VERSION_CODES.R)
    public ArrayList<String> getCurrencyNames(Context context) {
        return (ArrayList<String>)currency.getCurrencyNames();
    }

    // Gets currencies and their rates.
    @RequiresApi(api = Build.VERSION_CODES.R)
    public List<String> applyRateConversion(String base, Double amount, Context context) {
        return currency.getCurrencies(base, amount, context);
    }

    public interface View{
        public void updateViewList(String base, Double amount, MainActivityPresenter mAPresenter);
    }
}
