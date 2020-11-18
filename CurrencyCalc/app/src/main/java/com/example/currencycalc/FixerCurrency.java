package com.example.currencycalc;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FixerCurrency implements CurrencyDAO {

    ArrayList<Rate> currencies = new ArrayList<>();

    //Gets currency rates from the Fixer API
    @Override
    public ArrayList<Rate> getRates(String base, Context context){
        String url = "http://data.fixer.io/api/latest?access_key="
                + "34e9d93775f6ea2075f9104910c27018&symbols=DKK,NOK,SEK,EUR,USD&format=1";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Inserts the data from the API in a JSON object.
                    JSONObject rateJsonObject = response.getJSONObject("rates");
                    Iterator<String> iterator = rateJsonObject.keys();

                    // Gets currencies and their values from the JSON object.
                    for (int i = 0; i < rateJsonObject.length(); i++){
                        String name = iterator.next();
                        currencies.add(new Rate(name, rateJsonObject.getDouble(name)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Json", error.toString());
            }
        });

        Volley.newRequestQueue(context).add(jsonObjectRequest);

        // Creates a temporary array, inserting each string to it from the currencies list one at
        // a time. For some reason, it won't properly return the items of currencies otherwise.
        ArrayList<Rate> tempArray = new ArrayList<>();
        for (int i = 0; i < currencies.size(); i++){
            if (currencies.get(i).getName() != "" && currencies.get(i).getName() != base){
                tempArray.add(currencies.get(i));
            }
        }

        // Clears currencies so that it's empty for the next time the API is called.
        currencies.clear();

        return tempArray;
    }

    // Converts rates of the User's selected currency into other currencies.
    public List<String> getCurrencies(String base, Double amount, Context context){
        ArrayList<Rate> currencies = getRates("", context);
        ArrayList<String> conversionRates = new ArrayList<>();
        Double baseValue = 1.0;

        // Finds the value of the base currency, then makes its value the base value.
        for (int i = 0; i < currencies.size(); i++) {
            if (currencies.get(i).getName().contains(base)) {
                baseValue = currencies.get(i).spotRate;
                currencies.remove(currencies.get(i));
            }
        }

        // Updates the conversionRates ArrayList with name and converted rates.
        for (int i = 0; i < currencies.size(); i++) {
            if (base != currencies.get(i).name)
                conversionRates.add(currencies.get(i).name + "\n" + String.valueOf(
                        amount * (currencies.get(i).getSpotRate() / baseValue)));
        }

        return conversionRates;
    }

    // Returns a list of currencies for the user to choose from.
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
