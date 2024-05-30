package com.example.rezepteapp.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rezepteapp.R;
import com.example.rezepteapp.model.Recipe;

import java.util.List;

public class ArchivedRecipeAdapter extends RecyclerView.Adapter<ArchivedRecipeAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView recipeDescriptionTextView;
        public TextView recipeTitleTextView;
        public ImageView recipeEntryImage;

        public ViewHolder(View itemView) {
            super(itemView);
            recipeDescriptionTextView = (TextView) itemView.findViewById(R.id.tv_descr_recipe_entry);
            recipeTitleTextView = (TextView) itemView.findViewById(R.id.tv_title_recipe_entry);
            recipeEntryImage = (ImageView) itemView.findViewById(R.id.img_recipe_entry);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0,v.getId(), 0, "Archivierung rückgängig machen");
            menu.add(0, v.getId(), 0, "löschen");
        }
    }

    public ArchivedRecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    private List<Recipe> recipes;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View entryView = inflater.inflate(R.layout.recycl_item_welcome_screen_recipes, parent, false);

        return new ArchivedRecipeAdapter.ViewHolder(entryView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);

        holder.recipeTitleTextView.setText(recipe.getTitle());
        holder.recipeDescriptionTextView.setText(getDescription(recipe));
        holder.recipeEntryImage.setImageBitmap(recipe.getImageTitle());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void deleteRecipe(Recipe recipe) {
        int position = recipes.indexOf(recipe);
        if (position != -1) {
            recipes.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void revertArchiving(Recipe recipe) {
        int position = recipes.indexOf(recipe);
        if (position != -1) {
            recipes.remove(position);
            notifyItemRemoved(position);
        }
    }

    private String getDescription(Recipe recipe) {
        return "Vorbereitungszeit: " + recipe.getvTime() + "\tKochzeit: " + recipe.getkTime() + "\tPortionen: " + recipe.getServings();
    }
}