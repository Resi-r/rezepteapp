package com.example.rezepteapp.model;

import android.content.Context;

import com.example.rezepteapp.daos.IngredientDAOImpl;
import com.example.rezepteapp.daos.ShoppinglistEntryDAOImpl;

import java.util.List;

public class RecipeModel {
    private Recipe recipe;
    private List<ShoppinglistEntry> toDoList;
    private ShoppinglistEntryDAOImpl sldao;
    private IngredientDAOImpl idao;


    public List<ShoppinglistEntry> getToDoList() {
        toDoList.clear();

        return toDoList;
    }

    public RecipeModel(Context context) {

    }

    private List<Recipe> getDailyRecipes() {
        return null;
    }

    public List<Recipe> getArchivedRecipes() {
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
