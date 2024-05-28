package com.example.rezepteapp.daos;

import static com.example.rezepteapp.utils.Constants.DB_TABLE_INGREDIENT;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.IngredientEntity;

import java.util.List;

public class IngredientDAOImpl implements IngredientDAO{
    private RecipeDbOpenHelper dbHelper;
    private SQLiteDatabase db;

    public IngredientDAOImpl (Context context) {
        dbHelper = new RecipeDbOpenHelper(context);
        db = dbHelper.getWritableDatabase();
    }
    @Override
    public void saveOrUpdate(IngredientEntity entity) {

    }

    @Override
    public void delete(IngredientEntity entity) {

    }

    @Override
    public IngredientEntity getIngredientById(int id) {
        try (Cursor cursor = db.query(DB_TABLE_INGREDIENT, null,"_id = ?", new String[]{String.valueOf(id)}, null, null, null)){
              int idIndex = cursor.getColumnIndex("_id");
              int nameIndex = cursor.getColumnIndex("name");

              return new IngredientEntity(cursor.getInt(idIndex), cursor.getString(nameIndex));
        }
    }

    @Override
    public IngredientEntity getIngredientByName(String name) {
        try (Cursor cursor = db.query(DB_TABLE_INGREDIENT, null,"name = ?", new String[]{String.valueOf(name)}, null, null, null)){
            int idIndex = cursor.getColumnIndex("_id");
            int nameIndex = cursor.getColumnIndex("name");

            return new IngredientEntity(cursor.getInt(idIndex), cursor.getString(nameIndex));
        }
    }

    @Override
    public List<IngredientEntity> getAllIngredients() {
        return null;
    }
}
