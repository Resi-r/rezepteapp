package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.RecipeEntity;

import java.util.List;

public interface RecipeDAO {

    void saveOrDelete(final RecipeEntity entity);
    void delete(final RecipeEntity entity);

    RecipeEntity getRecipeById(int id);
    List<RecipeEntity> getRecipesByName(String name);
    List<RecipeEntity> getRecipesByStatusId(int statusId);
    List<RecipeEntity> getAllRecipes();
}
