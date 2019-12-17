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
import com.example.myapplication.domain.Contact;
import com.example.myapplication.domain.Groupable;
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate;

import java.util.List;

public class ItemAdapterDelegate extends AdapterDelegate<List<Groupable<String>>> {

    private static ContactAdapter.ItemClickListener clickListener;
    private LayoutInflater inflater;
    private Context context;

    public ItemAdapterDelegate(Context context, ContactAdapter.ItemClickListener clickListener) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        ItemAdapterDelegate.clickListener = clickListener;
    }

    @Override
    public boolean isForViewType(@NonNull List<Groupable<String>> items, int position) {
        return (!items.get(position).isHeader());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        return new ItemViewHolder(inflater.inflate(R.layout.contact_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull List<Groupable<String>> items, int position,
                                 @NonNull RecyclerView.ViewHolder holder, @Nullable List<Object> payloads) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        Contact contact = (Contact) items.get(position);
        itemViewHolder.name.setText(contact.getName());
        if (!contact.getPhone().isEmpty()) {
            itemViewHolder.phone.setText(contact.getPhone().get(0));
        } else {
            itemViewHolder.phone.setText(context.getString(R.string.no_contact_phone));
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView phone;

        ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_contact_name);
            phone = itemView.findViewById(R.id.tv_contact_phone);
            itemView.setOnClickListener(v -> performClickItem(itemView));
        }

        private void performClickItem(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }
}