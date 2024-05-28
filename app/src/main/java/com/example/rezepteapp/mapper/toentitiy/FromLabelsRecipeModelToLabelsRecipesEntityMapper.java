package com.example.rezepteapp.mapper.toentitiy;

import android.content.Context;

import com.example.rezepteapp.daos.RecipeDAO;
import com.example.rezepteapp.daos.RecipeDAOImpl;
import com.example.rezepteapp.entities.LabelsRecipesEntity;
import com.example.rezepteapp.model.Recipe;

public class FromLabelsRecipeModelToLabelsRecipesEntityMapper {
    private Context context;

    public FromLabelsRecipeModelToLabelsRecipesEntityMapper(Context context) {
        this.context = context;
    }

    public LabelsRecipesEntity map(Recipe recipe, int labelId) {
        LabelsRecipesEntity entity = new LabelsRecipesEntity();
        entity.setLabelId(labelId);
        entity.setRecipeId(getRecipeId(recipe.getTitle()));
        return entity;
    }

    private int getRecipeId(String name) {
        RecipeDAO dao = new RecipeDAOImpl(context);
        return dao.getId(name);
    }
}
