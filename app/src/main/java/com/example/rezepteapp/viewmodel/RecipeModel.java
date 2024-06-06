package com.example.rezepteapp.viewmodel;


import android.content.Context;
import com.example.rezepteapp.RecipeRepository;
import com.example.rezepteapp.daos.IngredientDAO;
import com.example.rezepteapp.daos.IngredientDAOImpl;
import com.example.rezepteapp.daos.RecipeDAOImpl;
import com.example.rezepteapp.daos.ShoppinglistEntryDAO;
import com.example.rezepteapp.daos.ShoppinglistEntryDAOImpl;
import com.example.rezepteapp.mapper.tomodel.FromShoppinglistEntryEntityToShoppinglistEntryMapper;
import com.example.rezepteapp.model.Ingredient;
import com.example.rezepteapp.model.Label;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.model.RecipeUnit;
import com.example.rezepteapp.model.ShoppinglistEntry;
import com.example.rezepteapp.model.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeModel {
    private Recipe recipe;
    private final List<ShoppinglistEntry> toDoList;
    private ShoppinglistEntryDAO shoppinglistEntryDAO;
    private IngredientDAO ingredientDAO;


    private FromShoppinglistEntryEntityToShoppinglistEntryMapper fromShoppinglistEntryEntityToShoppinglistEntryMapper;

    private RecipeRepository repository;

    public RecipeModel(Context context) {
        this.repository = new RecipeRepository(context);

        this.shoppinglistEntryDAO = new ShoppinglistEntryDAOImpl(context);
        this.ingredientDAO = new IngredientDAOImpl(context);
        this.fromShoppinglistEntryEntityToShoppinglistEntryMapper = new FromShoppinglistEntryEntityToShoppinglistEntryMapper();

        this.toDoList = new ArrayList<>();
    }


    public void addRecipeToDatabase(Recipe recipe) {
        repository.addRecipe(recipe);
    }

    public List<Recipe> getDailyRecipes() {
        return repository.getDailyRecipes();
    }

    public List<Recipe> getArchivedRecipes() {
        return repository.getArchivedRecipes();
    }

    public Recipe onRecipeClicked(int id) {
        return null;
    }

    public List<Recipe> getAllRecipes() {
        return repository.getAllRecipes();
    }

    public List<Recipe> getFilteredRecepieList(List<Label> labels) {
        return repository.getFilteredRecepieList(labels);
    }

    public List<Recipe> getFilteredRecepieList(String searchString) {
        return repository.getFilteredRecepieList(searchString);
    }

    public void addToTodoList(String amount, String ingredient) {
        repository.addToShoppinglist(new ShoppinglistEntry(amount, ingredient));
    }

    public void updateToDoList(List<ShoppinglistEntry> shoppinglistEntries) {
        toDoList.addAll(shoppinglistEntries);
    }

    public List<ShoppinglistEntry> getToDoList() {
        return repository.getToDoList();
    }

    public List<ShoppinglistEntry> getDoneList() {
        return repository.getDoneList();
    }

    public ShoppinglistEntry addNewShoppinglistEntry(String ingredient, String quantity, String unit) {
        return repository.addNewShoppinglistEntry(ingredient, quantity, unit);
    }

    public void updateTodoList(ShoppinglistEntry entry) {
        repository.updateTodoList(entry);
    }

    public void updateDoneList(ShoppinglistEntry entry) {
        repository.updateDoneList(entry);
    }

    public void deleteRecipe(Recipe recipe) {
        repository.deleteRecipe(recipe);
    }
    public void archiveRecipe(Recipe recipe) {
        repository.archiveRecipe(recipe);
    }
    public void revertArchivation(Recipe recipe) {
        repository.revertArchivation(recipe);
    }

    public void deleteShoppinglistEntry(ShoppinglistEntry entry) {
        repository.deleteShoppinglistEntry(entry);
    }
}