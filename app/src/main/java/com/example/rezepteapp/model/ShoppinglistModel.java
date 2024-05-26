package com.example.rezepteapp.model;

import com.example.rezepteapp.database.RecipeDbOpenHelper;

import java.util.List;

public class ShoppinglistModel {
    private List<Ingredient> toDoList;
    private List<Ingredient> doneList;
    private RecipeDbOpenHelper db;

    private List<Ingredient> getToDoList() {
        return null;
    }
    private List<Ingredient> getDoneList() {
        return null;
    }
}
