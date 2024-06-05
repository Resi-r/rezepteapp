package com.example.rezepteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rezepteapp.R;
import com.example.rezepteapp.controller.EditFragment;
import com.example.rezepteapp.controller.ShowRecipeFragment;
import com.example.rezepteapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView recipeName;
        public TextView recipeDescription;

        public ViewHolder(View itemView, List<Recipe> recipeList, Context context) {
            super(itemView);

            itemView.setOnClickListener(l -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Recipe recipe = recipeList.get(position);
                    Toast.makeText(itemView.getContext(), recipe.getTitle(), Toast.LENGTH_SHORT).show();

                    AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, new ShowRecipeFragment(recipe));
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

    public RecipeListAdapter(List<Recipe> recipeList) {
        this.allRecipesList = new ArrayList<>(recipeList);
        this.visibleRecipesList = new ArrayList<>(recipeList);
    }

    @NonNull
    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View recipeView = inflater.inflate(R.layout.recycl_item_welcome_screen_recipes, parent, false);

        return new ViewHolder(recipeView, visibleRecipesList, context);
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
}
