package com.example.rezepteapp.model;

import android.content.Context;
import android.view.MenuItem;

import com.example.rezepteapp.daos.IngredientDAOImpl;
import com.example.rezepteapp.daos.RecipeDAOImpl;
import com.example.rezepteapp.daos.ShoppinglistEntryDAOImpl;
import com.example.rezepteapp.entities.RecipeEntity;
import com.example.rezepteapp.mapper.toentitiy.FromRecipeModelToRecipeEntityMapper;
import com.example.rezepteapp.mapper.tomodel.FromRecipeEntityToRecipeModelMapper;

import java.util.ArrayList;
import java.util.List;

public class RecipeModel {
    private Recipe recipe;
    private List<ShoppinglistEntry> toDoList;
    private ShoppinglistEntryDAOImpl sldao;
    private IngredientDAOImpl idao;
    private RecipeDAOImpl recipeDAO;
    private Context context;


    public List<ShoppinglistEntry> getToDoList() {
        toDoList.clear();

        return toDoList;
    }

    public RecipeModel(Context context) {
        this.context = context;
        recipeDAO = new RecipeDAOImpl(context);
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
}
