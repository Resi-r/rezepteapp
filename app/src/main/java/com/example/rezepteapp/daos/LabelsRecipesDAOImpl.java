package com.example.rezepteapp.daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.LabelEntity;
import com.example.rezepteapp.entities.LabelsRecipesEntity;
import com.example.rezepteapp.entities.RecipeEntity;

import java.util.List;

public class LabelsRecipesDAOImpl implements LabelsRecipesDAO {

    private RecipeDbOpenHelper dbHelper;
    private SQLiteDatabase db;

    public LabelsRecipesDAOImpl(Context context) {
        this.dbHelper = new RecipeDbOpenHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }


    @Override
    public void saveOrUpdate(LabelsRecipesEntity entity) {

    }

    @Override
    public void delete(LabelsRecipesEntity entity) {

    }

    @Override
    public LabelsRecipesEntity getLabelsRecipesById(int id) {
        return null;
    }

    @Override
    public List<RecipeEntity> getRecipesByLabelId(int labelId) {
        return null;
    }

    @Override
    public List<LabelEntity> getLabelsByRecipeId(int recipeId) {
        return null;
    }

    @Override
    public List<LabelsRecipesEntity> getAllLabelsRecipes() {
        return null;
    }
}
