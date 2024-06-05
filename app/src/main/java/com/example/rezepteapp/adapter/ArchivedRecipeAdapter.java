package com.example.rezepteapp.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rezepteapp.R;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.viewmodel.RecipeModel;

import java.util.List;

public class ArchivedRecipeAdapter extends RecyclerView.Adapter<ArchivedRecipeAdapter.ViewHolder> {
    private RecipeModel model;

    public interface ArchiveActionListener {
        void onDeleteRecipe(Recipe recipe);
        void onRevertArchiving(Recipe recipe);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView recipeDescriptionTextView;
        public TextView recipeTitleTextView;
        public ImageView recipeEntryImage;
        private final ArchiveActionListener listener;

        public ViewHolder(View itemView, ArchiveActionListener listener) {
            super(itemView);
            this.listener = listener;
            recipeDescriptionTextView = (TextView) itemView.findViewById(R.id.tv_descr_recipe_entry);
            recipeTitleTextView = (TextView) itemView.findViewById(R.id.tv_title_recipe_entry);
            recipeEntryImage = (ImageView) itemView.findViewById(R.id.img_recipe_entry);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem revertItem = menu.add(0, v.getId(), 0, "Entarchivieren");
            MenuItem deleteItem = menu.add(0, v.getId(), 1, "Löschen");

            revertItem.setOnMenuItemClickListener(item -> {
                if (listener != null) {
                    listener.onRevertArchiving((Recipe) itemView.getTag());
                }
                return true;
            });

            deleteItem.setOnMenuItemClickListener(item -> {
                if (listener != null) {
                    listener.onDeleteRecipe((Recipe) itemView.getTag());
                }
                return true;
            });
        }

    }
    private List<Recipe> recipes;
    private ArchiveActionListener listener;

    public ArchivedRecipeAdapter(List<Recipe> recipes, ArchiveActionListener listener) {
        this.recipes = recipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View entryView = inflater.inflate(R.layout.recycl_item_welcome_screen_recipes, parent, false);

        return new ArchivedRecipeAdapter.ViewHolder(entryView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.itemView.setTag(recipe);

        holder.recipeTitleTextView.setText(recipe.getTitle());
        holder.recipeDescriptionTextView.setText(getDescription(recipe));
        holder.recipeEntryImage.setImageBitmap(recipe.getImageTitle());
    }

    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }

    public void deleteRecipe(Recipe recipe) {
        model.deleteRecipe(recipe);
        int position = recipes.indexOf(recipe);
        if (position != -1) {
            recipes.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void revertArchiving(Recipe recipe) {
        model.revertArchivation(recipe);
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