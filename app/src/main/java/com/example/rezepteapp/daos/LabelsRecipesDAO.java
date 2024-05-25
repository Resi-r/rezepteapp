package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.LabelEntity;
import com.example.rezepteapp.entities.LabelsRecipesEntity;
import com.example.rezepteapp.entities.RecipeEntity;

import java.util.List;

public interface LabelsRecipesDAO {

    void saveOrUpdate(final LabelsRecipesEntity entity);
    void delete(final LabelsRecipesEntity entity);

    LabelsRecipesEntity getLabelsRecipesById(int id);
    List<RecipeEntity> getRecipesByLabelId(int labelId);
    List<LabelEntity> getLabelsByRecipeId(int recipeId);
    List<LabelsRecipesEntity> getAllLabelsRecipes();
}
