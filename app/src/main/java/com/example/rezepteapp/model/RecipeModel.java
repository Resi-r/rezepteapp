package com.example.rezepteapp.model;

import android.content.Context;

import com.example.rezepteapp.daos.IngredientDAOImpl;
import com.example.rezepteapp.daos.ShoppinglistEntryDAOImpl;
import com.example.rezepteapp.entities.IngredientEntity;
import com.example.rezepteapp.entities.ShoppinglistEntryEntity;
import com.example.rezepteapp.mapper.tomodel.FromShoppinglistEntryEntityToShoppinglistEntryMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipeModel {
    private ShoppinglistEntryDAOImpl sldao;
    private IngredientDAOImpl idao;
    private Context context;


    public RecipeModel(Context context) {
        this.context = context;
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
        sldao = new ShoppinglistEntryDAOImpl(this.context);
        idao = new IngredientDAOImpl(this.context);
        FromShoppinglistEntryEntityToShoppinglistEntryMapper mapper = new FromShoppinglistEntryEntityToShoppinglistEntryMapper();
        List<ShoppinglistEntryEntity> entriesEntity = sldao.getAllShoppinglist();
        List<IngredientEntity> ingredientEntities = idao.getAllIngredients();
        return new ArrayList<>(mapper.mapToList(entriesEntity, ingredientEntities));
    }
    public List<ShoppinglistEntry>  getDoneList() {
        sldao = new ShoppinglistEntryDAOImpl(this.context);
        idao = new IngredientDAOImpl(context);
        FromShoppinglistEntryEntityToShoppinglistEntryMapper mapper = new FromShoppinglistEntryEntityToShoppinglistEntryMapper();
        List<ShoppinglistEntryEntity> entriesEntity = sldao.getAllShoppinglist();
        List<IngredientEntity> ingredientEntities = idao.getAllIngredients();
        return new ArrayList<>(mapper.mapToList(entriesEntity, ingredientEntities));
    }

    public void addNewShoppinglistEntry(String ingredient, String quantity, String unit) {
        ShoppinglistEntryEntity entity = new ShoppinglistEntryEntity();
        String amount = String.join(" ", quantity, unit);
        entity.setAmount(amount);
        entity.setIngredientId(idao.getId(ingredient));
        sldao.saveOrUpdate(entity);
    }
}
