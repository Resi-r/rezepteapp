package com.example.rezepteapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rezepteapp.R;
import com.example.rezepteapp.model.ShoppinglistEntry;

import java.util.List;

public class ShoppinglistToDoAdapter extends RecyclerView.Adapter<ShoppinglistToDoAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView amountTextView;
        public TextView ingredientTextView;
        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);

            amountTextView = (TextView) itemView.findViewById(R.id.tv_shoppinglist_entry_quantity);
            ingredientTextView = (TextView) itemView.findViewById(R.id.tv_shoppinglist_entry_item);
            checkBox = (CheckBox) itemView.findViewById(R.id.cb_shoppinglist_entry);
        }
    }
    public interface OnItemCheckedListener {
        void onItemChecked(ShoppinglistEntry entry);
    }

    private List<ShoppinglistEntry> entries;
    private OnItemCheckedListener listener;

    public ShoppinglistToDoAdapter(List<ShoppinglistEntry> entries, OnItemCheckedListener listener) {
        this.entries = entries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View entryView = inflater.inflate(R.layout.recycl_item_shoppinglist_entry, parent, false);

        return new ShoppinglistToDoAdapter.ViewHolder(entryView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShoppinglistEntry entry = entries.get(position);

        holder.amountTextView.setText(entry.getAmount());
        holder.ingredientTextView.setText(entry.getIngredient());
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(false);
        holder.checkBox.setOnCheckedChangeListener(((buttonView, isChecked) -> {
            if (isChecked) {
                listener.onItemChecked(entry);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public void removeEntry(ShoppinglistEntry entry) {
        int position = entries.indexOf(entry);
        if (position != -1) {
            entries.remove(position);
            notifyItemRemoved(position);
        }
    }
}