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

        mAPresenter = new MainActivityPresenter(this, new FixerCurrency());
        EditText amountEditText = findViewById(R.id.amountEditText);
        amountEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        Button goButton = findViewById(R.id.goButton);
        ListView listView = findViewById(R.id.CurrencyListView);
        Spinner currencySpinner = findViewById(R.id.currencySpinner);

        // Gets the currencies shown in the Spinner.
        ArrayList<String> currencies = mAPresenter.getCurrencyNames(this);

        // Runs the API call once, because it never updates to show the first API call.
        rates = (ArrayList<String>)mAPresenter.applyRateConversion("DKK", 1.0, this);

        // Sets the adapters for the Spinner and for the EditText.
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencies);
        currencySpinner.setAdapter(spinAdapter);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rates );
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        // If amountEditText isn't empty, it calls the updateViewList method.
        goButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!amountEditText.getText().toString().isEmpty()){
                    updateViewList(currencySpinner.getSelectedItem().toString(), Double.parseDouble(amountEditText.getText().toString()), mAPresenter);
                }
            }
        });
    }

    // Clears and updates the rates list, then notifies the adapter.
    @RequiresApi(api = Build.VERSION_CODES.R)
    public void updateViewList(String base, Double amount, MainActivityPresenter mAPresenter) {
        rates.clear();
        rates = (ArrayList<String>)mAPresenter.applyRateConversion(base, amount, this);
        adapter.clear();
        for (String r : rates) {
            adapter.add(r);
        }
        adapter.notifyDataSetChanged();
    }
}