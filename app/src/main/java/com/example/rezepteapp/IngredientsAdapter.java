package com.example.rezepteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rezepteapp.model.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView amountTextView;
        public TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            amountTextView = (TextView) itemView.findViewById(R.id.tv_amount);
            nameTextView = (TextView) itemView.findViewById(R.id.tv_ingridient);
        }
    }

    private final List<Ingredient> ingredients;

    public IngredientsAdapter(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ingridientView = inflater.inflate(R.layout.recycl_item_details_ingredient, parent, false);

        return new ViewHolder(ingridientView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.amountTextView.setText(ingredient.getAmount());
        holder.nameTextView.setText(ingredient.getName());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
