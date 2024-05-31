package com.example.rezepteapp.model;

import android.content.Context;

import com.example.rezepteapp.daos.IngredientDAOImpl;
import com.example.rezepteapp.daos.ShoppinglistEntryDAOImpl;
import com.example.rezepteapp.entities.IngredientEntity;
import com.example.rezepteapp.entities.ShoppinglistEntryEntity;
import com.example.rezepteapp.mapper.tomodel.FromShoppinglistEntryEntityToShoppinglistEntryMapper;

import java.util.ArrayList;
import java.util.List;

public class RecipeModel {
    private ShoppinglistEntryDAOImpl shoppinglistEntryDAO;
    private IngredientDAOImpl ingredientDAO;
    private Context context;


    public RecipeModel(Context context) {
        this.context = context;
        this.shoppinglistEntryDAO = new ShoppinglistEntryDAOImpl(this.context);
        this.ingredientDAO = new IngredientDAOImpl(this.context);
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

    public List<ShoppinglistEntry> getToDoList() {
        List<ShoppinglistEntry> entries = new ArrayList<>();
        FromShoppinglistEntryEntityToShoppinglistEntryMapper mapper = new FromShoppinglistEntryEntityToShoppinglistEntryMapper();
        List<ShoppinglistEntryEntity> entities = shoppinglistEntryDAO.getAllShoppinglistEntries();
        List<IngredientEntity> ingredientEntities = ingredientDAO.getAllIngredients();
        for (ShoppinglistEntryEntity entity : entities) {
            entries.add(mapper.mapToEntry(entity, ingredientEntities));
        }
        return entries;
    }
    public List<ShoppinglistEntry>  getDoneList() {
        List<ShoppinglistEntry> entries = new ArrayList<>();
        FromShoppinglistEntryEntityToShoppinglistEntryMapper mapper = new FromShoppinglistEntryEntityToShoppinglistEntryMapper();
        List<ShoppinglistEntryEntity> entities = shoppinglistEntryDAO.getAllShoppinglistEntries();
        List<IngredientEntity> ingredientEntities = ingredientDAO.getAllIngredients();
        for (ShoppinglistEntryEntity entity : entities) {
            entries.add(mapper.mapToEntry(entity, ingredientEntities));
        }
        return entries;
    }

    public ShoppinglistEntry addNewShoppinglistEntry(String ingredient, String quantity, String unit) {
        ShoppinglistEntryEntity entity = new ShoppinglistEntryEntity();
        String amount = String.join(" ", quantity, unit);
        entity.setAmount(amount);
        int ingredientId = ingredientDAO.getId(ingredient);
        if (ingredientId == 0) {
            ingredientDAO.saveOrUpdate(new IngredientEntity(ingredient));
            ingredientId = ingredientDAO.getId(ingredient);
        }
        entity.setIngredientId(ingredientId);
        shoppinglistEntryDAO.saveOrUpdate(entity);
        FromShoppinglistEntryEntityToShoppinglistEntryMapper mapper = new FromShoppinglistEntryEntityToShoppinglistEntryMapper();
        ShoppinglistEntry entry = mapper.mapToEntry(shoppinglistEntryDAO.getShoppinglistEntryById(shoppinglistEntryDAO.getId(amount, ingredientId)), new IngredientEntity(ingredientId, ingredient));
        return entry;
    }
}