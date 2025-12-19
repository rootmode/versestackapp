package com.example.versestackapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LibraryViewHolder extends RecyclerView.ViewHolder {

    private final TextView libraryLogViewItem;

    private LibraryViewHolder(@NonNull View itemView) {
        super(itemView);
        libraryLogViewItem = itemView.findViewById(R.id.recyclerItemTextview);
    }

    public void bind(String text) {
        libraryLogViewItem.setText(text);
    }

    public static LibraryViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.library_recycler_item, parent, false);
        return new LibraryViewHolder(view);
    }
}