package com.example.currencycalc;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public interface CurrencyDAO {
    public List<Rate> getRates(String base) throws IOException;
}
