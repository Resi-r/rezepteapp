package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.IngredientsRecipesEntity;

import java.util.List;

public interface IngredientsRecipesDAO {

    void saveOrUpdate(final IngredientsRecipesEntity entity);
    void delete(final IngredientsRecipesEntity entity);

    IngredientsRecipesEntity getIngredientsRecipesById(int id);
    List<IngredientsRecipesEntity> getIngredientsRecipesByIngredientId(int ingredienId);
    List<IngredientsRecipesEntity> getIngredientsRecipesByRecipesId(int recipesId);
    List<IngredientsRecipesEntity> getAllIngredientsRecipes();
}
