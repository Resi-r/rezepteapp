package com.example.rezepteapp.daos;

import static com.example.rezepteapp.utils.Constants.DB_TABLE_LABELS_RECIPES;
import static com.example.rezepteapp.utils.Constants.DB_TABLE_STATUS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.LabelsRecipesEntity;

import java.util.ArrayList;
import java.util.List;

public class LabelsRecipesDAOImpl implements LabelsRecipesDAO {

    private final RecipeDbOpenHelper dbHelper;
    public LabelsRecipesDAOImpl(Context context) {
        this.dbHelper = RecipeDbOpenHelper.getInstance(context);
    }


    @Override
    public long saveOrUpdate(LabelsRecipesEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            if (entity.getId() != 0) {
                values.put("_id", entity.getId());
            }
            values.put("labelId", entity.getLabelId());
            values.put("recipeId", entity.getRecipeId());
            if (entity.getId() != 0) {
                return db.update(DB_TABLE_LABELS_RECIPES, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
            } else {
                return db.insert(DB_TABLE_LABELS_RECIPES, null, values);
            }
        }
    }

    @Override
    public long delete(LabelsRecipesEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            return db.delete(DB_TABLE_LABELS_RECIPES, "_id = ?", new String[]{String.valueOf(entity.getId())});
        }
    }

    @Override
    public LabelsRecipesEntity getLabelsRecipesById(int id) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {

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
    }

    @Override
    public List<LabelsRecipesEntity> getLabelsRecipesByLabelId(int id) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {

            List<LabelsRecipesEntity> entities = new ArrayList<>();
            LabelsRecipesEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_LABELS_RECIPES,
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
    }

    @Override
    public List<LabelsRecipesEntity> getRecipesLabelsByRecipeId(int id) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {

            List<LabelsRecipesEntity> entities = new ArrayList<>();
            LabelsRecipesEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_LABELS_RECIPES,
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
    }

    @Override
    public List<LabelsRecipesEntity> getAllLabelsRecipes() {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {

            List<LabelsRecipesEntity> entities = new ArrayList<>();
            LabelsRecipesEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_LABELS_RECIPES,
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

    @Override
    public int getId(String name) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()){
            try (Cursor cursor = db.query(DB_TABLE_LABELS_RECIPES, new String[]{"_id"}, "name = ?", new String[]{name}, null, null, null)) {
                if (cursor.moveToFirst()) {
                    return cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                }
            }
        }
        return -1;
    }
}
