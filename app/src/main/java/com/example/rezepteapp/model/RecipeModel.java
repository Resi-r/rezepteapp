package com.example.rezepteapp.model;

import android.content.Context;

import com.example.rezepteapp.database.RecipeDbOpenHelper;

import java.util.List;

public class RecipeModel {
    private Recipe recipe;
    private RecipeDbOpenHelper db;

    public RecipeModel(Context context) {
        db = new RecipeDbOpenHelper(context);
    }

    private List<Recipe> getDailyRecipes() {
        return null;
    }

    private List<Recipe> getArchivedRecipes() {
        return null;
    }

    private Recipe onRecipeClicked(int id) {
        return null;
    }

    private List<Recipe> getAllRecipes() {
        return null;
    }

    private List<Recipe> getFilteredRecepieList(List<Label> labels) {
        return null;
    }

    private List<Recipe> getFilteredRecepieList(String searchString) {
        return null;
    }
}
