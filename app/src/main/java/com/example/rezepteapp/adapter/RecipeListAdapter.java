package com.example.rezepteapp.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rezepteapp.R;
import com.example.rezepteapp.controller.RecipeDetailsFragment;
import com.example.rezepteapp.listener.RecipeListActionListener;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.viewmodel.RecipeModel;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private RecipeModel model;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        public ImageView image;
        public TextView recipeName;
        public TextView recipeDescription;
        private RecipeListActionListener listener;

        public ViewHolder(View itemView, List<Recipe> recipeList, Context context, RecipeListActionListener listener) {
            super(itemView);
            this.listener = listener;

            itemView.setOnClickListener(l -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Recipe recipe = recipeList.get(position);
                    AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, new RecipeDetailsFragment(recipe));
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

            itemView.setOnCreateContextMenuListener(this);

            image = (ImageView) itemView.findViewById(R.id.img_recipe_entry);
            recipeName = (TextView) itemView.findViewById(R.id.tv_title_recipe_entry);
            recipeDescription = (TextView) itemView.findViewById(R.id.tv_descr_recipe_entry);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem item = menu.add(0, v.getId(), 1, "Archivieren");

            item.setOnMenuItemClickListener(i -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.archiveRecipe((Recipe) itemView.getTag());
                }
                return true;
            });
        }
    }

    private final List<Recipe> allRecipesList;
    private final List<Recipe> visibleRecipesList;
    private RecipeListActionListener recipeListActionListener;

    public RecipeListAdapter(List<Recipe> recipeList) {
        this.allRecipesList = new ArrayList<>(recipeList);
        this.visibleRecipesList = new ArrayList<>(recipeList);
    }
    public RecipeListAdapter(List<Recipe> recipeList, RecipeListActionListener recipeListActionListener) {
        this.allRecipesList = new ArrayList<>(recipeList);
        this.visibleRecipesList = new ArrayList<>(recipeList);
        this.recipeListActionListener = recipeListActionListener;
    }

    @NonNull
    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View recipeView = inflater.inflate(R.layout.recycl_item_welcome_screen_recipes, parent, false);

        return new RecipeListAdapter.ViewHolder(recipeView, visibleRecipesList, context, recipeListActionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.ViewHolder holder, int position) {
        Recipe recipe = visibleRecipesList.get(position);

        holder.itemView.setTag(recipe);
        holder.recipeName.setText(recipe.getTitle());
        holder.recipeDescription.setText(getDescription(recipe));
    }

    @Override
    public int getItemCount() {
        return visibleRecipesList.size();
    }

    private String getDescription(Recipe recipe) {
        return "Portionen: " + recipe.getServings() +
                " || " + "Vorbereitungszeit: " + recipe.getvTime() +
                " || " + "Kochzeit: " + recipe.getkTime();
    }

    public void updateRecipes(List<Recipe> newRecipes) {
        visibleRecipesList.clear();
        visibleRecipesList.addAll(newRecipes);
        notifyDataSetChanged();
    }

}
