package com.example.myquotes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquotes.Listeners.CopyListener;
import com.example.myquotes.Listeners.QuoteClicked;
import com.example.myquotes.Model.QuotesResponse;
import com.example.myquotes.R;

import java.util.List;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder> {
    List<QuotesResponse> quotes;
    Context context;
    CopyListener listener;
    QuoteClicked quoteClicked;

    public QuoteAdapter(List<QuotesResponse> quotes, Context context, CopyListener listener) {
        this.quotes = quotes;
        this.context = context;
        this.listener = listener;
    }

    public QuoteAdapter(List<QuotesResponse> quotes, Context context, CopyListener listener, QuoteClicked quoteClicked) {
        this.quotes = quotes;
        this.context = context;
        this.listener = listener;
        this.quoteClicked = quoteClicked;
    }

    @NonNull
    @Override
    public QuoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuoteViewHolder(LayoutInflater.from(context).inflate(R.layout.list_quote, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuoteViewHolder holder, int position) {
        holder.bindQuote(quotes.get(position));
    }

    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public class QuoteViewHolder extends RecyclerView.ViewHolder {
        TextView txt_quote, txt_author;
        Button button_copy;

        public QuoteViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_quote = itemView.findViewById(R.id.txt_quote);
            txt_author = itemView.findViewById(R.id.txt_author);
            button_copy = itemView.findViewById(R.id.button_copy);

        }

        public void bindQuote(QuotesResponse quote) {
            txt_quote.setText("\"" + quote.getText() + "\"");
            txt_author.setText(quote.getAuthor());
            button_copy.setOnClickListener(view -> {
                listener.onCopyClicked(quotes.get(getAdapterPosition()).getText());
            });
            itemView.setOnClickListener(view ->
                    quoteClicked.onQuoteClicked(quote)
            );
        }
    }

}
