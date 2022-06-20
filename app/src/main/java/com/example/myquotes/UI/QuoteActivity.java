package com.example.myquotes.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquotes.R;

import java.util.Calendar;

public class QuoteActivity extends AppCompatActivity {
    private Intent intent;
    private TextView quote, author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        quote = findViewById(R.id.quote);
        author = findViewById(R.id.author);

        getSupportActionBar().hide();
        intent = getIntent();

        quote.setText(intent.getStringExtra("text"));
        author.setText(intent.getStringExtra("author"));

    }

}