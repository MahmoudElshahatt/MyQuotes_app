package com.example.myquotes.Listeners;

import com.example.myquotes.Model.QuotesResponse;

import java.util.List;

public interface QuotesResponseListener {
    void didFetch(List<QuotesResponse> response, String message);
    void didError(String message);
}
