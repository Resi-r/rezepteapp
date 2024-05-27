package com.example.rezepteapp.daos;

import static com.example.rezepteapp.utils.Constants.DB_TABLE_INGREDIENT;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.IngredientEntity;

import java.util.ArrayList;
import java.util.List;

public class IngredientDAOImpl implements IngredientDAO {

    private RecipeDbOpenHelper dbHelper;

    public IngredientDAOImpl(Context context) {
        this.dbHelper = new RecipeDbOpenHelper(context);
    }

    @Override
    public void saveOrUpdate(IngredientEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("name", entity.getName());
            try {
                db.update(DB_TABLE_INGREDIENT, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
            } catch (Exception e) {
                db.insert(DB_TABLE_INGREDIENT, null, values);
            }
        }
    }

    @Override
    public void delete(IngredientEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.delete(DB_TABLE_INGREDIENT, "_id = ?", new String[]{String.valueOf(entity.getId())});
        }
    }

    @Override
    public IngredientEntity getIngredientById(int id) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            IngredientEntity entity = new IngredientEntity();

            try (Cursor cursor = db.query(DB_TABLE_INGREDIENT, null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int nameIndex = cursor.getColumnIndex("name");

                    entity.setId(cursor.getInt(idIndex));
                    entity.setName(cursor.getString(nameIndex));
                }
                return entity;
            }
        }
    }

    @Override
    public IngredientEntity getIngredientByName(String name) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            IngredientEntity entity = new IngredientEntity();

            try (Cursor cursor = db.query(DB_TABLE_INGREDIENT, null, "name = ?", new String[]{String.valueOf(name)}, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int nameIndex = cursor.getColumnIndex("name");

                    entity.setId(cursor.getInt(idIndex));
                    entity.setName(cursor.getString(nameIndex));
                }
                return entity;
            }
        }
    }

    @Override
    public List<IngredientEntity> getAllIngredients() {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            List<IngredientEntity> entities = new ArrayList<>();
            IngredientEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_INGREDIENT, null, null, null, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int nameIndex = cursor.getColumnIndex("name");

                    entity = new IngredientEntity();

                    entity.setId(cursor.getInt(idIndex));
                    entity.setName(cursor.getString(nameIndex));

                    entities.add(entity);
                }
                return entities;
            }
        }
    }
    
    @Override
    public int getId(String name) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()){
            Cursor cursor = db.query(DB_TABLE_INGREDIENT, new String[]{"_id"}, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            }
        }
        return 0;
    }
}
