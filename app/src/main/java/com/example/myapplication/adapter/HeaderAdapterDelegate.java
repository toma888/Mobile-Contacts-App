package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.domain.Groupable;
import com.example.myapplication.domain.Header;
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate;

import java.util.List;

public class HeaderAdapterDelegate extends AdapterDelegate<List<Groupable<String>>> {

    private LayoutInflater inflater;

    public HeaderAdapterDelegate(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public boolean isForViewType(@NonNull List<Groupable<String>> items, int position) {
        return items.get(position).isHeader();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new HeaderViewHolder(inflater.inflate(R.layout.contact_list_header, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<Groupable<String>> items, int position,
                                 @NonNull RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {

        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        Header header = (Header) items.get(position);
        headerViewHolder.firstLetter.setText(String.valueOf(header.getFirstLetter()));
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {

        TextView firstLetter;

        HeaderViewHolder(View itemView) {
            super(itemView);
            firstLetter = itemView.findViewById(R.id.tv_first_letter);
        }
    }
}