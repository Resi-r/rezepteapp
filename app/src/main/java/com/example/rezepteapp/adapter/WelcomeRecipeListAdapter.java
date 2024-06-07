package com.example.rezepteapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
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

public class WelcomeRecipeListAdapter extends RecyclerView.Adapter<WelcomeRecipeListAdapter.ViewHolder> {
    private RecipeModel model;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView recipeName;
        public TextView recipeDescription;
        private RecipeListActionListener listener;
        private RecipeModel model;

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

            image = (ImageView) itemView.findViewById(R.id.img_recipe_entry);
            recipeName = (TextView) itemView.findViewById(R.id.tv_title_recipe_entry);
            recipeDescription = (TextView) itemView.findViewById(R.id.tv_descr_recipe_entry);
        }

    }

    private final List<Recipe> allRecipesList;
    private final List<Recipe> visibleRecipesList;
    private RecipeListActionListener recipeListActionListener;

    public WelcomeRecipeListAdapter(List<Recipe> recipeList) {
        this.allRecipesList = new ArrayList<>(recipeList);
        this.visibleRecipesList = new ArrayList<>(recipeList);
    }
    public WelcomeRecipeListAdapter(List<Recipe> recipeList, RecipeListActionListener recipeListActionListener) {
        this.allRecipesList = new ArrayList<>(recipeList);
        this.visibleRecipesList = new ArrayList<>(recipeList);
        this.recipeListActionListener = recipeListActionListener;
    }

    @NonNull
    @Override
    public WelcomeRecipeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View recipeView = inflater.inflate(R.layout.recycl_item_welcome_screen_recipes, parent, false);

        return new WelcomeRecipeListAdapter.ViewHolder(recipeView, visibleRecipesList, context, recipeListActionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull WelcomeRecipeListAdapter.ViewHolder holder, int position) {
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

    public void hideItem(int position) {
        visibleRecipesList.remove(position);
        notifyItemRemoved(position);
    }

    public void showItem(int position) {
        Recipe recipe = allRecipesList.get(position);
        visibleRecipesList.add(position, recipe);
        notifyItemInserted(position);
    }

    public void archiveRecipe(Recipe recipe) {
        if (model != null) {
            model.archiveRecipe(recipe);
            int position = allRecipesList.indexOf(recipe);
            if (position != -1) {
                allRecipesList.remove(position);
                notifyItemRemoved(position);
            }
        }
    }
}
