package com.example.rezepteapp.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rezepteapp.R;
import com.example.rezepteapp.listener.OnItemCheckedToDoListener;
import com.example.rezepteapp.listener.ShoppinglistToDoActionListener;
import com.example.rezepteapp.model.ShoppinglistEntry;

import java.util.List;

public class ShoppinglistToDoAdapter extends RecyclerView.Adapter<ShoppinglistToDoAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView amountTextView;
        public TextView ingredientTextView;
        public CheckBox checkBox;
        private OnItemCheckedToDoListener itemCheckedToDoListener;
        private final ShoppinglistToDoActionListener shoppinglistToDoActionListener;

        public ViewHolder(View itemView, OnItemCheckedToDoListener itemCheckedToDoListener, ShoppinglistToDoActionListener shoppinglistToDoActionListener) {
            super(itemView);
            this.itemCheckedToDoListener = itemCheckedToDoListener;
            this.shoppinglistToDoActionListener = shoppinglistToDoActionListener;

            itemView.setOnCreateContextMenuListener(this);
            amountTextView = (TextView) itemView.findViewById(R.id.tv_shoppinglist_entry_quantity);
            ingredientTextView = (TextView) itemView.findViewById(R.id.tv_shoppinglist_entry_item);
            checkBox = (CheckBox) itemView.findViewById(R.id.cb_shoppinglist_entry);
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem deleteItem = menu.add(0, v.getId(), 1, "Löschen");

            deleteItem.setOnMenuItemClickListener(item -> {
                if (shoppinglistToDoActionListener != null) {
                    shoppinglistToDoActionListener.deleteEntry((ShoppinglistEntry) itemView.getTag());
                }
                return true;
            });
        }
    }

    private List<ShoppinglistEntry> entries;
    private OnItemCheckedToDoListener itemCheckedToDoListener;
    private ShoppinglistToDoActionListener shoppinglistActionListener;

    public ShoppinglistToDoAdapter(List<ShoppinglistEntry> entries, OnItemCheckedToDoListener itemCheckedToDoListener, ShoppinglistToDoActionListener shoppinglistActionListener) {
        this.entries = entries;
        this.itemCheckedToDoListener = itemCheckedToDoListener;
        this.shoppinglistActionListener = shoppinglistActionListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View entryView = inflater.inflate(R.layout.recycl_item_shoppinglist_entry, parent, false);

        return new ShoppinglistToDoAdapter.ViewHolder(entryView, itemCheckedToDoListener, shoppinglistActionListener);
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
                itemCheckedToDoListener.onItemChecked(entry);
            }
        }));
        holder.itemView.setTag(entry);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public void deleteEntry(ShoppinglistEntry entry) {
        int position = entries.indexOf(entry);
        if (position != -1) {
            entries.remove(position);
            notifyItemRemoved(position);
        }
    }
}