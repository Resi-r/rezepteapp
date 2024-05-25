package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.LabelsRecipesEntity;

import java.util.List;

public interface LabelsRecipesDAO {

    void saveOrUpdate(final LabelsRecipesEntity entity);
    void delete(final LabelsRecipesEntity entity);

    LabelsRecipesEntity getLabelsRecipesById(int id);
    List<LabelsRecipesEntity> getLabelsRecipesByLabelId(int labelId);
    List<LabelsRecipesEntity> getLabelsRecipesByRecipeId(int recipeId);
    List<LabelsRecipesEntity> getAllLabelsRecipes();
}
