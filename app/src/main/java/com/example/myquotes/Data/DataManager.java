package com.example.myquotes.Data;

import com.example.myquotes.Model.QuotesResponse;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager single_instance = null;
    private List<QuotesResponse> quotes = new ArrayList<>();

    private DataManager() {
    }

    public static DataManager getInstance() {
        if (single_instance == null)
            single_instance = new DataManager();

        return single_instance;
    }

    public void setQuotes(List<QuotesResponse> list) {
        this.quotes = list;
    }

    public List<QuotesResponse> getQuotes() {
        return quotes;
    }

}
