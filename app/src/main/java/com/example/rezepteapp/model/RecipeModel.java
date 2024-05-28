package com.example.rezepteapp.model;

import android.content.Context;

import com.example.rezepteapp.daos.IngredientDAOImpl;
import com.example.rezepteapp.daos.ShoppinglistDAOImpl;

import java.util.List;

public class RecipeModel {
    private Recipe recipe;
    private List<ShoppinglistEntry> toDoList;
    private ShoppinglistDAOImpl sldao;
    private IngredientDAOImpl idao;


    public List<ShoppinglistEntry> getToDoList() {
        toDoList.clear();
//        toDoList.addAll(mapper.mapToList(sldao.getAllShoppinglist(), idao.getAllIngredients()));

        return toDoList;
    }

    public RecipeModel(Context context) {

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
