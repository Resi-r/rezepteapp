package com.example.rezepteapp.model;

import android.content.Context;

import com.example.rezepteapp.daos.IngredientDAOImpl;
import com.example.rezepteapp.daos.RecipeDAOImpl;
import com.example.rezepteapp.daos.ShoppinglistEntryDAOImpl;
import com.example.rezepteapp.entities.IngredientEntity;
import com.example.rezepteapp.entities.ShoppinglistEntryEntity;
import com.example.rezepteapp.mapper.toentitiy.FromShoppinglistEntryModelToShoppinglistEntryEntityMapper;
import com.example.rezepteapp.mapper.tomodel.FromShoppinglistEntryEntityToShoppinglistEntryMapper;
import com.example.rezepteapp.entities.RecipeEntity;
import com.example.rezepteapp.mapper.toentitiy.FromRecipeModelToRecipeEntityMapper;
import com.example.rezepteapp.mapper.tomodel.FromRecipeEntityToRecipeModelMapper;

import java.util.ArrayList;
import java.util.List;

public class RecipeModel {
    private ShoppinglistEntryDAOImpl shoppinglistEntryDAO;
    private IngredientDAOImpl ingredientDAO;
    private Context context;
    private Recipe recipe;
    private List<ShoppinglistEntry> toDoList;
    private ShoppinglistEntryDAOImpl sldao;
    private IngredientDAOImpl idao;
    private RecipeDAOImpl recipeDAO;



    public RecipeModel(Context context) {
        this.context = context;
        this.shoppinglistEntryDAO = new ShoppinglistEntryDAOImpl(this.context);
        this.ingredientDAO = new IngredientDAOImpl(this.context);
        this.context = context;
        this.recipeDAO = new RecipeDAOImpl(context);
    }

    private List<Recipe> getDailyRecipes() {
        return null;
    }

    public List<Recipe> getArchivedRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        FromRecipeEntityToRecipeModelMapper mapper = new FromRecipeEntityToRecipeModelMapper(context);
        List<RecipeEntity> entities = recipeDAO.getAllArchivedRecipes();
        for (RecipeEntity entity : entities) {
            recipes.add(mapper.map(entity));
        }
        return recipes;
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
        List<ShoppinglistEntryEntity> entities = shoppinglistEntryDAO.getAllTodoEntries();
        List<IngredientEntity> ingredientEntities = ingredientDAO.getAllIngredients();
        for (ShoppinglistEntryEntity entity : entities) {
            entries.add(mapper.mapToEntry(entity, ingredientEntities));
        }
        return entries;
    }
    public List<ShoppinglistEntry> getDoneList() {
        List<ShoppinglistEntry> entries = new ArrayList<>();
        FromShoppinglistEntryEntityToShoppinglistEntryMapper mapper = new FromShoppinglistEntryEntityToShoppinglistEntryMapper();
        List<ShoppinglistEntryEntity> entities = shoppinglistEntryDAO.getAllDoneEntries();
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
        entity.setStatus(ShoppinglistEtryStatus.TODO);

        shoppinglistEntryDAO.insert(entity);

        FromShoppinglistEntryEntityToShoppinglistEntryMapper mapper = new FromShoppinglistEntryEntityToShoppinglistEntryMapper();
        return mapper.mapToEntry(
                shoppinglistEntryDAO.getShoppinglistEntryById(shoppinglistEntryDAO.getId(amount, ingredientId)),
                        new IngredientEntity(ingredientId, ingredient));
    }

    public void updateTodoList(ShoppinglistEntry entry) {
        FromShoppinglistEntryModelToShoppinglistEntryEntityMapper mapper = new FromShoppinglistEntryModelToShoppinglistEntryEntityMapper(context);
        ShoppinglistEntryEntity entity = mapper.map(entry);
        entity.setStatus(ShoppinglistEtryStatus.TODO);
        shoppinglistEntryDAO.saveOrUpdate(entity);
    }

    public void updateDoneList(ShoppinglistEntry entry) {
        FromShoppinglistEntryModelToShoppinglistEntryEntityMapper mapper = new FromShoppinglistEntryModelToShoppinglistEntryEntityMapper(context);
        ShoppinglistEntryEntity entity = mapper.map(entry);
        entity.setStatus(ShoppinglistEtryStatus.DONE);
        shoppinglistEntryDAO.saveOrUpdate(entity);
    }

    public void deleteRecipe(Recipe recipe) {
        FromRecipeModelToRecipeEntityMapper mapper = new FromRecipeModelToRecipeEntityMapper(context);
        RecipeEntity entity = mapper.map(recipe);
        recipeDAO.delete(entity);
    }

    public void revertArchivation(Recipe recipe) {
        FromRecipeModelToRecipeEntityMapper mapper = new FromRecipeModelToRecipeEntityMapper(context);
        RecipeEntity entity = mapper.map(recipe);
        entity.setStatusId(2);
        recipeDAO.saveOrDelete(entity);
    }
    public void deleteShoppinglistEntry(ShoppinglistEntry entry) {
        FromShoppinglistEntryModelToShoppinglistEntryEntityMapper mapper = new FromShoppinglistEntryModelToShoppinglistEntryEntityMapper(context);
        ShoppinglistEntryEntity entity = mapper.map(entry);
        shoppinglistEntryDAO.delete(entity);
    }
}