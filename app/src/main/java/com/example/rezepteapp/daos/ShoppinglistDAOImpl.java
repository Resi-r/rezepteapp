package com.example.rezepteapp.daos;

import static com.example.rezepteapp.utils.Constants.DB_TABLE_SHOPPINGLIST;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.ShoppinglistEntity;

import java.util.ArrayList;
import java.util.List;

public class ShoppinglistDAOImpl implements ShoppinglistDAO{
    private RecipeDbOpenHelper dbHelper;
    private SQLiteDatabase db;

    public ShoppinglistDAOImpl(Context context) {
        this.dbHelper = new RecipeDbOpenHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    @Override
    public void saveOrUpdate(ShoppinglistEntity entity) {
        ContentValues values = new ContentValues();
        values.put("amount", entity.getAmount());
        values.put("ingredientId", entity.getIngredientId());
        try {
            db.update(DB_TABLE_SHOPPINGLIST, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
        } catch (Exception e) {
            db.insert(DB_TABLE_SHOPPINGLIST, null, values);
        }
    }

    @Override
    public void delete(ShoppinglistEntity entity) {
        db.delete(DB_TABLE_SHOPPINGLIST, "_id = ?", new String[]{String.valueOf(entity.getId())});
    }

    @Override
    public ShoppinglistEntity getShoppinglistById(int id) {
        ShoppinglistEntity entity = new ShoppinglistEntity();
        try (Cursor cursor = db.query(DB_TABLE_SHOPPINGLIST, null, "_id = ?",
                new String[]{String.valueOf(id)}, null, null, null)){
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex("_id");
                int amountIndex = cursor.getColumnIndex("amount");
                int ingredientIdIndex = cursor.getColumnIndex("ingredientId");

                entity.setId(cursor.getInt(idIndex));
                entity.setAmount(cursor.getString(amountIndex));
                entity.setIngredientId(cursor.getInt(ingredientIdIndex));
            }
        }
        return entity;
    }

    @Override
    public List<ShoppinglistEntity> getAllShoppinglist() {
        List<ShoppinglistEntity> entityList = new ArrayList<>();
        try (Cursor cursor = db.query(DB_TABLE_SHOPPINGLIST, null, null,
                null, null, null, null)){
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndex("_id");
                int amountIndex = cursor.getColumnIndex("amount");
                int ingredientIdIndex = cursor.getColumnIndex("ingredientId");

                entityList.add(new ShoppinglistEntity(cursor.getInt(idIndex), cursor.getString(amountIndex), cursor.getInt(ingredientIdIndex)));
            }
        }
        return entityList;
    }
}
