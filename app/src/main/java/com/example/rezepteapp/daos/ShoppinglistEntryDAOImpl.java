package com.example.rezepteapp.daos;

import static com.example.rezepteapp.utils.Constants.DB_TABLE_SHOPPINGLIST_ENTRY;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.ShoppinglistEntryEntity;

import java.util.ArrayList;
import java.util.List;

public class ShoppinglistEntryDAOImpl implements ShoppinglistEntryDAO {

    private RecipeDbOpenHelper dbHelper;

    public ShoppinglistEntryDAOImpl(Context context) {
        this.dbHelper = new RecipeDbOpenHelper(context);
    }


    @Override
    public void saveOrUpdate(ShoppinglistEntryEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("amount", entity.getAmount());
            values.put("ingredientId", entity.getIngredientId());
            try {
                db.update(DB_TABLE_SHOPPINGLIST_ENTRY, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
            } catch (Exception e) {
                db.insert(DB_TABLE_SHOPPINGLIST_ENTRY, null, values);
            }
        }
    }

    @Override
    public void delete(ShoppinglistEntryEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.delete(DB_TABLE_SHOPPINGLIST_ENTRY, "_id = ?", new String[]{String.valueOf(entity.getId())});
        }
    }

    @Override
    public ShoppinglistEntryEntity getShoppinglistById(int id) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            ShoppinglistEntryEntity entity = new ShoppinglistEntryEntity();

            try (Cursor cursor = db.query(DB_TABLE_SHOPPINGLIST_ENTRY, null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int amountIndex = cursor.getColumnIndex("amount");
                    int ingredientIdIndex = cursor.getColumnIndex("ingredientId");

                    entity.setId(cursor.getInt(idIndex));
                    entity.setAmount(cursor.getString(amountIndex));
                    entity.setIngredientId(cursor.getInt(ingredientIdIndex));
                }
                return entity;
            }
        }
    }

    @Override
    public List<ShoppinglistEntryEntity> getAllShoppinglist() {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            List<ShoppinglistEntryEntity> entities = new ArrayList<>();
            ShoppinglistEntryEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_SHOPPINGLIST_ENTRY, null, null, null, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int amountIndex = cursor.getColumnIndex("amount");
                    int ingredientIdIndex = cursor.getColumnIndex("ingredientId");

                    entity = new ShoppinglistEntryEntity();

                    entity.setId(cursor.getInt(idIndex));
                    entity.setAmount(cursor.getString(amountIndex));
                    entity.setIngredientId(cursor.getInt(ingredientIdIndex));

                    entities.add(entity);
                }
                return entities;
            }
        }
    }

    @Override
    public int getId(String amount, int ingredientId) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()){
            Cursor cursor = db.query(DB_TABLE_SHOPPINGLIST_ENTRY, new String[]{"_id"}, "amount = ? AND ingredientId = ?", new String[]{amount,String.valueOf(ingredientId)}, null, null, null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            }
        }
        return 0;
    }
}