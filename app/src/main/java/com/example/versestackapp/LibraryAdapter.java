package com.example.versestackapp;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class LibraryAdapter extends ListAdapter<Borrow, LibraryViewHolder> {
    public LibraryAdapter(@NonNull DiffUtil.ItemCallback<Borrow> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return LibraryViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder holder, int position) {
        Borrow current = getItem(position);
        holder.bind("ISBN: " + current.getIsbn() + " (ID: " + current.getBorrowId() + ")");
    }

    public static class BorrowDiff extends DiffUtil.ItemCallback<Borrow> {
        @Override
        public boolean areItemsTheSame(@NonNull Borrow oldItem, @NonNull Borrow newItem) {
            return oldItem.getBorrowId() == newItem.getBorrowId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Borrow oldItem, @NonNull Borrow newItem) {
            return oldItem.getIsbn() == newItem.getIsbn() && oldItem.getUserId() == newItem.getUserId();
        }
    }
}