package com.example.myquotes.Listeners;

import android.app.NotificationManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.myquotes.Data.DataManager;
import com.example.myquotes.Model.QuotesResponse;
import com.example.myquotes.R;

import java.util.List;
import java.util.Random;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        List<QuotesResponse> Quotes = DataManager.getInstance().getQuotes();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "QuoteNotify")
                .setSmallIcon(R.drawable.icon_quote_white)
                .setContentTitle("Today Quote")
                .setAutoCancel(true);

        builder.setStyle(new NotificationCompat.BigTextStyle(builder)
                .bigText("\"" + Quotes.get(new Random().nextInt(Quotes.size())).getText() + "\"")
                .setBigContentTitle("Today Quote")
        );
        notificationManager.notify(100, builder.build());
    }
}
