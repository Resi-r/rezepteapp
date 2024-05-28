package com.example.rezepteapp.daos;

import static com.example.rezepteapp.utils.Constants.DB_TABLE_INGREDIENT;
import static com.example.rezepteapp.utils.Constants.DB_TABLE_INGREDIENTS_RECIPES;
import static com.example.rezepteapp.utils.Constants.DB_TABLE_LABELS_RECIPES;
import static com.example.rezepteapp.utils.Constants.DB_TABLE_STATUS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.IngredientsRecipesEntity;

import java.util.ArrayList;
import java.util.List;

public class IngredientsRecipesDAOImpl implements IngredientsRecipesDAO {

    private final RecipeDbOpenHelper dbOpenHelper;

    public IngredientsRecipesDAOImpl(Context context) {
        this.dbOpenHelper = new RecipeDbOpenHelper(context);
    }


    @Override
    public void saveOrUpdate(IngredientsRecipesEntity entity) {
        try(SQLiteDatabase db = dbOpenHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("labelId", entity.getIngredientId());
            values.put("recipeId", entity.getRecipeId());
            try {
                db.update(DB_TABLE_INGREDIENTS_RECIPES, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
            } catch (Exception e) {
                db.insert(DB_TABLE_INGREDIENTS_RECIPES, null, values);
            }
        }
    }

    @Override
    public void delete(IngredientsRecipesEntity entity) {
        try(SQLiteDatabase db = dbOpenHelper.getWritableDatabase()) {
            db.delete(DB_TABLE_INGREDIENTS_RECIPES, "_id = ?", new String[]{String.valueOf(entity.getId())});
        }
    }

    @Override
    public IngredientsRecipesEntity getIngredientsRecipesById(int id) {
        try(SQLiteDatabase db = dbOpenHelper.getReadableDatabase()) {
            IngredientsRecipesEntity entity = new IngredientsRecipesEntity();

            try (Cursor cursor = db.query(DB_TABLE_LABELS_RECIPES, null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int ingredientIdIndex = cursor.getColumnIndex("ingredientId");
                    int recipeIdIndex = cursor.getColumnIndex("recipeId");

                    entity.setId(cursor.getInt(idIndex));
                    entity.setIngredientId(cursor.getInt(ingredientIdIndex));
                    entity.setRecipeId(cursor.getInt(recipeIdIndex));
                }
                return entity;
            }
        }
    }

    @Override
    public List<IngredientsRecipesEntity> getIngredientsRecipesByIngredientId(int id) {
        try(SQLiteDatabase db = dbOpenHelper.getReadableDatabase()) {

            List<IngredientsRecipesEntity> entities = new ArrayList<>();
            IngredientsRecipesEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_STATUS,
                    null, "ingredientId = ?", new String[]{String.valueOf(id)},
                    null, null, null)) {

                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int ingredientIdIndex = cursor.getColumnIndex("ingredientId");
                    int recipeIdIndex = cursor.getColumnIndex("recipeId");


                    entity = new IngredientsRecipesEntity();

                    entity.setId(cursor.getInt(idIndex));
                    entity.setIngredientId(cursor.getInt(ingredientIdIndex));
                    entity.setRecipeId(cursor.getInt(recipeIdIndex));

                    entities.add(entity);
                }
                return entities;
            }
        }
    }

    @Override
    public List<IngredientsRecipesEntity> getIngredientsRecipesByRecipesId(int id) {
        try(SQLiteDatabase db = dbOpenHelper.getReadableDatabase()) {

            List<IngredientsRecipesEntity> entities = new ArrayList<>();
            IngredientsRecipesEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_STATUS,
                    null, "recipeId = ?", new String[]{String.valueOf(id)},
                    null, null, null)) {

                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int ingredientIdIndex = cursor.getColumnIndex("ingredientId");
                    int recipeIdIndex = cursor.getColumnIndex("recipeId");


                    entity = new IngredientsRecipesEntity();

                    entity.setId(cursor.getInt(idIndex));
                    entity.setIngredientId(cursor.getInt(ingredientIdIndex));
                    entity.setRecipeId(cursor.getInt(recipeIdIndex));

                    entities.add(entity);
                }
                return entities;
            }
        }
    }

    @Override
    public List<IngredientsRecipesEntity> getAllIngredientsRecipes() {
        try(SQLiteDatabase db = dbOpenHelper.getReadableDatabase()) {

            List<IngredientsRecipesEntity> entities = new ArrayList<>();
            IngredientsRecipesEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_STATUS,
                    null, null, null,
                    null, null, null)) {

                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int ingredientIdIndex = cursor.getColumnIndex("ingredientId");
                    int recipeIdIndex = cursor.getColumnIndex("recipeId");


                    entity = new IngredientsRecipesEntity();

                    entity.setId(cursor.getInt(idIndex));
                    entity.setIngredientId(cursor.getInt(ingredientIdIndex));
                    entity.setRecipeId(cursor.getInt(recipeIdIndex));

                    entities.add(entity);
                }
                return entities;
            }
        }
    }

    @Override
    public int getId(String name) {
        try (SQLiteDatabase db = dbOpenHelper.getReadableDatabase()){
            Cursor cursor = db.query(DB_TABLE_INGREDIENTS_RECIPES, new String[]{"_id"}, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            }
        }
        return 0;
    }

    @Override
    public String getAmount(int id) {
        try (SQLiteDatabase db = dbOpenHelper.getReadableDatabase()){
            Cursor cursor = db.query(DB_TABLE_INGREDIENTS_RECIPES, new String[]{"amount"}, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow("amount"));
            }
        }
        return null;
    }
}
