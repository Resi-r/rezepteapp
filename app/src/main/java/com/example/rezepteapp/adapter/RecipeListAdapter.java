package com.example.rezepteapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rezepteapp.R;
import com.example.rezepteapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView recipeName;
        public TextView recipeDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.img_recipe_entry);
            recipeName = (TextView) itemView.findViewById(R.id.tv_title_recipe_entry);
            recipeDescription = (TextView) itemView.findViewById(R.id.tv_descr_recipe_entry);
        }
    }

    private final List<Recipe> allRecipesList;
    private final List<Recipe> visibleRecipesList;

    public RecipeListAdapter(List<Recipe> recipeList) {
        this.allRecipesList = recipeList;
        visibleRecipesList = new ArrayList<>(allRecipesList);
    }

    @NonNull
    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View recipeView = inflater.inflate(R.layout.recycl_item_welcome_screen_recipes, parent, false);

        return new ViewHolder(recipeView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.ViewHolder holder, int position) {
        Recipe recipe = visibleRecipesList.get(position);

        holder.recipeName.setText(visibleRecipesList.get(position).getTitle());
        holder.recipeDescription.setText(getDescription(visibleRecipesList.get(position)));
    }

    @Override
    public int getItemCount() {
        return visibleRecipesList.size();
    }

    private String getDescription(Recipe recipe) {
        return "Portionen: " + recipe.getServings() + " || " + "Vorbereitungszeit: " + recipe.getvTime() + " || " + "Kochzeit: " + recipe.getkTime();
    }
}
