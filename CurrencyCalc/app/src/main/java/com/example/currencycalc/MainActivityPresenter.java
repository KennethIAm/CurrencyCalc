package com.example.currencycalc;

import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

public class MainActivityPresenter {
    private MockCurrency mockCurrency = new MockCurrency();
    private View v;

    MainActivityPresenter(View v) {
        this.v = v;
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    public ArrayList<String> getCurrencies() {
        return mockCurrency.getCurrencies();
    }


    @RequiresApi(api = Build.VERSION_CODES.R)
    public List<String> applyRateConversion(String base, Double amount) {
        return mockCurrency.mockCurrency(base, amount);
    }

    public interface View{
        public void updateViewList(String base, Double amount, MainActivityPresenter mAPresenter);
    }
}
