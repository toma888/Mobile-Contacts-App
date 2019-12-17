package com.example.myapplication.adapter;

import android.content.Context;
import android.view.View;

import com.example.myapplication.domain.Groupable;
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter;

import androidx.recyclerview.widget.DiffUtil;

public class ContactAdapter extends AsyncListDifferDelegationAdapter<Groupable<String>> {

    public ContactAdapter(Context context, ItemClickListener clickListener) {
        super(DIFF_CALLBACK);

        delegatesManager.addDelegate(new ItemAdapterDelegate(context, clickListener))
                .addDelegate(new HeaderAdapterDelegate(context));
    }

    private static final DiffUtil.ItemCallback<Groupable<String>> DIFF_CALLBACK
            = new DiffUtil.ItemCallback<Groupable<String>>() {

        @Override
        public boolean areItemsTheSame(Groupable oldItem, Groupable newItem) {
            return oldItem.getItemId().equals(newItem.getItemId());
        }

        @Override
        public boolean areContentsTheSame(Groupable oldItem, Groupable newItem) {
            return oldItem.getItemHash() == newItem.getItemHash();
        }
    };

    public Groupable getItem(int id) {
        return getItems().get(id);
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}