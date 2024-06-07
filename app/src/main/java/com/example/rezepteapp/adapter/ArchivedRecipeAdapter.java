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
import com.example.rezepteapp.listener.OnDeleteArchivedItemListener;
import com.example.rezepteapp.listener.OnRevertArchivingListener;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.viewmodel.RecipeModel;

import java.util.List;

public class ArchivedRecipeAdapter extends RecyclerView.Adapter<ArchivedRecipeAdapter.ViewHolder> {
    private RecipeModel model;
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView recipeDescriptionTextView;
        public TextView recipeTitleTextView;
        public ImageView recipeEntryImage;
        private OnRevertArchivingListener revertArchivingListener;
        private OnDeleteArchivedItemListener deleteArchivedItemListener;

        public ViewHolder(View itemView, OnRevertArchivingListener revertArchivingListener, OnDeleteArchivedItemListener deleteArchivedItemListener) {
            super(itemView);
            this.deleteArchivedItemListener = deleteArchivedItemListener;
            this.revertArchivingListener = revertArchivingListener;
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
                if (revertArchivingListener != null) {
                    revertArchivingListener.onRevertArchiving((Recipe) itemView.getTag());
                }
                return true;
            });

            deleteItem.setOnMenuItemClickListener(item -> {
                if (deleteArchivedItemListener != null) {
                    deleteArchivedItemListener.onDeleteRecipe((Recipe) itemView.getTag());
                }
                return true;
            });
        }

    }
    private List<Recipe> recipes;
    private OnRevertArchivingListener revertArchivingListener;
    private OnDeleteArchivedItemListener deleteArchivedItemListener;

    public ArchivedRecipeAdapter(List<Recipe> recipes, OnRevertArchivingListener revertArchivingListener, OnDeleteArchivedItemListener deleteArchivedItemListener) {
        this.recipes = recipes;
        this.revertArchivingListener = revertArchivingListener;
        this.deleteArchivedItemListener = deleteArchivedItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View entryView = inflater.inflate(R.layout.recycl_item_welcome_screen_recipes, parent, false);
        this.model = new RecipeModel(context);

        return new ArchivedRecipeAdapter.ViewHolder(entryView, revertArchivingListener, deleteArchivedItemListener);
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


    private String getDescription(Recipe recipe) {
        return "Portionen: " + recipe.getServings() +
                " || " + "Vorbereitungszeit: " + recipe.getvTime() +
                " || " + "Kochzeit: " + recipe.getkTime();
    }
}