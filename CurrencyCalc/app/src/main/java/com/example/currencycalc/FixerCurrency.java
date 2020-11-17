package com.example.currencycalc;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FixerCurrency implements CurrencyDAO {
    @Override
    public ArrayList<Rate> getRates(String base) throws IOException {
        URL url = new URL("http://data.fixer.io/api/latest?access_key=34e9d93775f6ea2075f9104910c27018");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        return null;
    }
}
