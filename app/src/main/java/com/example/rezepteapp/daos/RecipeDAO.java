package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.RecipeEntity;
import com.example.rezepteapp.model.Recipe;

import java.util.List;

public interface RecipeDAO {

    long saveOrUpdate(final RecipeEntity entity);
    long delete(final RecipeEntity entity);

    RecipeEntity getRecipeById(int id);
    List<RecipeEntity> getRecipesByName(String name);
    List<RecipeEntity> getRecipesByStatusId(int statusId);

    List<RecipeEntity> getAllRecipes();
    List<RecipeEntity> getAllArchivedRecipes();
//    boolean updateRecipe(RecipeEntity entity);
    int getId(String name);
}
