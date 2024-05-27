package com.example.rezepteapp.daos;

import static com.example.rezepteapp.utils.Constants.DB_TABLE_LABELS_RECIPES;
import static com.example.rezepteapp.utils.Constants.DB_TABLE_STATUS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.contentcapture.ContentCaptureCondition;

import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.LabelEntity;
import com.example.rezepteapp.entities.LabelsRecipesEntity;
import com.example.rezepteapp.entities.RecipeEntity;
import com.example.rezepteapp.entities.StatusEntity;

import java.util.ArrayList;
import java.util.List;

public class LabelsRecipesDAOImpl implements LabelsRecipesDAO {

    private RecipeDbOpenHelper dbHelper;
    private final SQLiteDatabase db;

    public LabelsRecipesDAOImpl(Context context) {
        this.dbHelper = new RecipeDbOpenHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }


    @Override
    public void saveOrUpdate(LabelsRecipesEntity entity) {
        ContentValues values = new ContentValues();
        values.put("labelId", entity.getLabelId());
        values.put("recipeId", entity.getRecipeId());
        try {
            db.update(DB_TABLE_LABELS_RECIPES, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
        } catch (Exception e) {
            db.insert(DB_TABLE_LABELS_RECIPES, null, values);
        }
    }

    @Override
    public void delete(LabelsRecipesEntity entity) {
        db.delete(DB_TABLE_LABELS_RECIPES, "_id = ?", new String[]{String.valueOf(entity.getId())});
    }

    @Override
    public LabelsRecipesEntity getLabelsRecipesById(int id) {
        LabelsRecipesEntity entity = new LabelsRecipesEntity();

        try (Cursor cursor = db.query(DB_TABLE_LABELS_RECIPES, null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null)) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex("_id");
                int labelIdIndex = cursor.getColumnIndex("labelId");
                int recipeIdIndex = cursor.getColumnIndex("recipeId");

                entity.setId(cursor.getInt(idIndex));
                entity.setLabelId(cursor.getInt(labelIdIndex));
                entity.setRecipeId(cursor.getInt(recipeIdIndex));
            }
            return entity;
        }
    }

    @Override
    public List<LabelsRecipesEntity> getLabelsRecipesByLabelId(int id) {
        List<LabelsRecipesEntity> entities = new ArrayList<>();
        LabelsRecipesEntity entity;

        try (Cursor cursor = db.query(DB_TABLE_STATUS,
                null, "labelId = ?", new String[]{String.valueOf(id)},
                null, null, null)) {

            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex("_id");
                int labelIdIndex = cursor.getColumnIndex("labelId");
                int recipeIdIndex = cursor.getColumnIndex("recipeId");


                entity = new LabelsRecipesEntity();

                entity.setId(cursor.getInt(idIndex));
                entity.setLabelId(cursor.getInt(labelIdIndex));
                entity.setRecipeId(cursor.getInt(recipeIdIndex));

                entities.add(entity);
            }
            return entities;
        }
    }

    @Override
    public List<LabelsRecipesEntity> getRecipesLabelsByRecipeId(int id) {
        List<LabelsRecipesEntity> entities = new ArrayList<>();
        LabelsRecipesEntity entity;

        try (Cursor cursor = db.query(DB_TABLE_STATUS,
                null, "recipeId = ?", new String[]{String.valueOf(id)},
                null, null, null)) {

            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex("_id");
                int labelIdIndex = cursor.getColumnIndex("labelId");
                int recipeIdIndex = cursor.getColumnIndex("recipeId");

                entity = new LabelsRecipesEntity();

                entity.setId(cursor.getInt(idIndex));
                entity.setLabelId(cursor.getInt(labelIdIndex));
                entity.setRecipeId(cursor.getInt(recipeIdIndex));

                entities.add(entity);
            }
            return entities;
        }
    }

    @Override
    public List<LabelsRecipesEntity> getAllLabelsRecipes() {
        List<LabelsRecipesEntity> entities = new ArrayList<>();
        LabelsRecipesEntity entity;

        try (Cursor cursor = db.query(DB_TABLE_STATUS,
                null, null, null,
                null, null, null)) {

            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex("_id");
                int labelIdIndex = cursor.getColumnIndex("labelId");
                int recipeIdIndex = cursor.getColumnIndex("recipeId");

                entity = new LabelsRecipesEntity();

                entity.setId(cursor.getInt(idIndex));
                entity.setLabelId(cursor.getInt(labelIdIndex));
                entity.setRecipeId(cursor.getInt(recipeIdIndex));

                entities.add(entity);
            }
            return entities;
        }
    }
}
