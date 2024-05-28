package com.example.rezepteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rezepteapp.model.ShoppinglistEntry;

import java.util.List;

public class ShoppinglistDoneAdapter extends RecyclerView.Adapter<ShoppinglistDoneAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView amountTextView;
        public TextView ingredientTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            amountTextView = (TextView) itemView.findViewById(R.id.tv_shoppinglist_entry_quantity);
            ingredientTextView = (TextView) itemView.findViewById(R.id.tv_shoppinglist_entry_item);
        }
    }

    private List<ShoppinglistEntry> entries;

    public ShoppinglistDoneAdapter(List<ShoppinglistEntry> entries) { this.entries = entries;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View entryView = inflater.inflate(R.layout.recycl_item_shoppinglist_entry, parent, false);

        return new ShoppinglistDoneAdapter.ViewHolder(entryView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppinglistEntry entry = entries.get(position);

        holder.amountTextView.setText(entry.getAmount());
        holder.ingredientTextView.setText(entry.getIngredient());
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }
}
