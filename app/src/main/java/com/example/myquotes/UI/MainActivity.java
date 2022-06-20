package com.example.myquotes.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myquotes.Listeners.CopyListener;
import com.example.myquotes.Listeners.NotificationReceiver;
import com.example.myquotes.Adapters.QuoteAdapter;
import com.example.myquotes.Listeners.QuoteClicked;
import com.example.myquotes.Model.QuotesResponse;
import com.example.myquotes.Listeners.QuotesResponseListener;
import com.example.myquotes.R;
import com.example.myquotes.ServerClient.RequestManager;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements QuotesResponseListener, CopyListener, QuoteClicked {
    private RecyclerView recyclerView;
    private RequestManager manager;
    private QuoteAdapter quoteAdapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        recyclerView = findViewById(R.id.recycler_home);
        manager = new RequestManager(this);

        manager.getAllQuotes(this);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading...");
        dialog.show();
    }

    @Override
    public void didFetch(List<QuotesResponse> response, String message) {
        showData(response);
        dialog.dismiss();
    }

    private void showData(List<QuotesResponse> response) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        quoteAdapter = new QuoteAdapter(response, MainActivity.this, this::onCopyClicked, this::onQuoteClicked);
        recyclerView.setAdapter(quoteAdapter);
    }

    @Override
    public void didError(String message) {
        dialog.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCopyClicked(String text) {
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("Copies Data", text);
        clipboardManager.setPrimaryClip(data);
        Toast.makeText(this, "Copied successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onQuoteClicked(QuotesResponse quote) {
        Intent intent = new Intent(this, QuoteActivity.class);
        intent.putExtra("text", "\"" + quote.getText() + "\"");
        intent.putExtra("author", quote.getAuthor());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.quote_reminder:
                NotificationSet();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void NotificationSet() {
        Toast.makeText(this, "Reminder is set!!", Toast.LENGTH_SHORT).show();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 13);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 15);

        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext()
                , 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence sequence = "QuotesReminderChannel";
            String description = "Channel for Quotes";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("QuoteNotify", sequence, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}