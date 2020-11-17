package com.example.currencycalc;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {
    MainActivityPresenter mAPresenter;
    ArrayList<String> rates;
    ArrayAdapter<String> adapter;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText amountEditText = findViewById(R.id.amountEditText);
        amountEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        Button goButton = findViewById(R.id.goButton);

        ListView listView = findViewById(R.id.CurrencyListView);
        MockCurrency mockCurrency = new MockCurrency();
        rates = (ArrayList<String>)mockCurrency.mockCurrency("DKK", 1.0);

        mAPresenter = new MainActivityPresenter(this);

        ArrayList<String> currencies = mAPresenter.getCurrencies();

        Spinner currencySpinner = findViewById(R.id.currencySpinner);
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencies);
        currencySpinner.setAdapter(spinAdapter);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                rates );

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        goButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!amountEditText.getText().toString().isEmpty()){
                    updateViewList(currencySpinner.getSelectedItem().toString(), Double.parseDouble(amountEditText.getText().toString()), mAPresenter);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public void updateViewList(String base, Double amount, MainActivityPresenter mAPresenter) {
        rates.clear();
        rates = (ArrayList<String>)mAPresenter.applyRateConversion(base, amount);
        adapter.clear();
        for (String r : rates) {
            adapter.add(r);
        }
        adapter.notifyDataSetChanged();
    }
}