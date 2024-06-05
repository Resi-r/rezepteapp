package com.example.rezepteapp.daos;

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

    private final RecipeDbOpenHelper dbHelper;
    private SQLiteDatabase db;

    public IngredientsRecipesDAOImpl(Context context) {
        this.dbHelper = RecipeDbOpenHelper.getInstance(context);

    }


    @Override
    public long saveOrUpdate(IngredientsRecipesEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            System.out.println("is open?: " + db.isOpen());

            ContentValues values = new ContentValues();
            values.put("ingredientId", entity.getIngredientId());
            values.put("recipeId", entity.getRecipeId());
            if (entity.getId() != 0) {

                return db.update(DB_TABLE_INGREDIENTS_RECIPES, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
            } else  {
                return db.insert(DB_TABLE_INGREDIENTS_RECIPES, null, values);
            }
        }
    }

    @Override
    public long delete(IngredientsRecipesEntity entity) {
        try(SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            return db.delete(DB_TABLE_INGREDIENTS_RECIPES, "_id = ?", new String[]{String.valueOf(entity.getId())});
        }
    }

    @Override
    public IngredientsRecipesEntity getIngredientsRecipesById(int id) {
        try(SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            IngredientsRecipesEntity entity = new IngredientsRecipesEntity();

            try (Cursor cursor = db.query(DB_TABLE_INGREDIENTS_RECIPES, null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null)) {
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
        try(SQLiteDatabase db = dbHelper.getReadableDatabase()) {

            List<IngredientsRecipesEntity> entities = new ArrayList<>();
            IngredientsRecipesEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_INGREDIENTS_RECIPES,
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
        try(SQLiteDatabase db = dbHelper.getReadableDatabase()) {

            List<IngredientsRecipesEntity> entities = new ArrayList<>();
            IngredientsRecipesEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_INGREDIENTS_RECIPES,
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
        try(SQLiteDatabase db = dbHelper.getReadableDatabase()) {

            List<IngredientsRecipesEntity> entities = new ArrayList<>();
            IngredientsRecipesEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_INGREDIENTS_RECIPES,
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
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()){
            try (Cursor cursor = db.query(DB_TABLE_INGREDIENTS_RECIPES, new String[]{"_id"}, null, null, null, null, null)) {
                if (cursor.moveToFirst()) {
                    return cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                }
            }
        }
        return -1;
    }

    @Override
    public String getAmount(int id) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            try (Cursor cursor = db.query(DB_TABLE_INGREDIENTS_RECIPES, new String[]{"amount"}, "_id = ?", new String[]{String.valueOf(id)}, null, null, null)) {
                if (cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndexOrThrow("amount"));
                }
            }
        }
        return null;
    }
}
