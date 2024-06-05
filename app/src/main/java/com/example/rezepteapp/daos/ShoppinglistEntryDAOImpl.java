package com.example.rezepteapp.daos;

import static com.example.rezepteapp.utils.Constants.DB_TABLE_SHOPPINGLIST_ENTRY;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.ShoppinglistEntryEntity;
import com.example.rezepteapp.model.ShoppinglistEtryStatus;

import java.util.ArrayList;
import java.util.List;

public class ShoppinglistEntryDAOImpl implements ShoppinglistEntryDAO {

    private final RecipeDbOpenHelper dbHelper;

    public ShoppinglistEntryDAOImpl(Context context) {
        this.dbHelper = RecipeDbOpenHelper.getInstance(context);
    }


    @Override
    public long saveOrUpdate(ShoppinglistEntryEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("amount", entity.getAmount());
            values.put("ingredientId", entity.getIngredientId());
            values.put("status", entity.getStatus().toString());
            if (entity.getId() != 0) {
                return db.update(DB_TABLE_SHOPPINGLIST_ENTRY, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
            } else {
                return db.insert(DB_TABLE_SHOPPINGLIST_ENTRY, null, values);
            }
        }
    }

    @Override
    public long delete(ShoppinglistEntryEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            return db.delete(DB_TABLE_SHOPPINGLIST_ENTRY, "_id = ?", new String[]{String.valueOf(entity.getId())});
        }
    }

    @Override
    public void insert(ShoppinglistEntryEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()){
            ContentValues values = new ContentValues();
            values.put("amount", entity.getAmount());
            values.put("ingredientId", entity.getIngredientId());
            values.put("status", entity.getStatus().toString());
            db.insert(DB_TABLE_SHOPPINGLIST_ENTRY, null, values);
        }
    }

    @Override
    public ShoppinglistEntryEntity getShoppinglistEntryById(int id) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            ShoppinglistEntryEntity entity = new ShoppinglistEntryEntity();

            try (Cursor cursor = db.query(DB_TABLE_SHOPPINGLIST_ENTRY, null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int amountIndex = cursor.getColumnIndex("amount");
                    int ingredientIdIndex = cursor.getColumnIndex("ingredientId");
                    int statusIndex = cursor.getColumnIndex("status");

                    entity.setId(cursor.getInt(idIndex));
                    entity.setAmount(cursor.getString(amountIndex));
                    entity.setIngredientId(cursor.getInt(ingredientIdIndex));
                    entity.setStatus(ShoppinglistEtryStatus.valueOf(cursor.getString(statusIndex)));
                }
                return entity;
            }
        }
    }

    @Override
    public List<ShoppinglistEntryEntity> getAllShoppinglistEntries() {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            List<ShoppinglistEntryEntity> entities = new ArrayList<>();
            ShoppinglistEntryEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_SHOPPINGLIST_ENTRY, null, null, null, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int amountIndex = cursor.getColumnIndex("amount");
                    int ingredientIdIndex = cursor.getColumnIndex("ingredientId");
                    int statusIndex = cursor.getColumnIndex("status");

                    entity = new ShoppinglistEntryEntity();

                    entity.setId(cursor.getInt(idIndex));
                    entity.setAmount(cursor.getString(amountIndex));
                    entity.setIngredientId(cursor.getInt(ingredientIdIndex));
                    entity.setStatus(ShoppinglistEtryStatus.valueOf(cursor.getString(statusIndex)));

                    entities.add(entity);
                }
                return entities;
            }
        }
    }

    @Override
    public List<ShoppinglistEntryEntity> getAllTodoEntries() {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            List<ShoppinglistEntryEntity> entities = new ArrayList<>();
            ShoppinglistEntryEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_SHOPPINGLIST_ENTRY, null, "status = ?", new String[]{"TODO"}, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int amountIndex = cursor.getColumnIndex("amount");
                    int ingredientIdIndex = cursor.getColumnIndex("ingredientId");
                    int statusIndex = cursor.getColumnIndex("status");

                    entity = new ShoppinglistEntryEntity();

                    entity.setId(cursor.getInt(idIndex));
                    entity.setAmount(cursor.getString(amountIndex));
                    entity.setIngredientId(cursor.getInt(ingredientIdIndex));
                    entity.setStatus(ShoppinglistEtryStatus.valueOf(cursor.getString(statusIndex)));

                    entities.add(entity);
                }
                return entities;
            }
        }
    }

    @Override
    public List<ShoppinglistEntryEntity> getAllDoneEntries() {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            List<ShoppinglistEntryEntity> entities = new ArrayList<>();
            ShoppinglistEntryEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_SHOPPINGLIST_ENTRY, null, "status = ?", new String[]{"DONE"}, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int amountIndex = cursor.getColumnIndex("amount");
                    int ingredientIdIndex = cursor.getColumnIndex("ingredientId");
                    int statusIndex = cursor.getColumnIndex("status");

                    entity = new ShoppinglistEntryEntity();

                    entity.setId(cursor.getInt(idIndex));
                    entity.setAmount(cursor.getString(amountIndex));
                    entity.setIngredientId(cursor.getInt(ingredientIdIndex));
                    entity.setStatus(ShoppinglistEtryStatus.valueOf(cursor.getString(statusIndex)));

                    entities.add(entity);
                }
                return entities;
            }
        }
    }

    @Override
    public int getId(String amount, int ingredientId) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()){
            try (Cursor cursor = db.query(DB_TABLE_SHOPPINGLIST_ENTRY, new String[]{"_id"}, "amount = ? AND ingredientId = ?", new String[]{amount,String.valueOf(ingredientId)}, null, null, null)) {
                if (cursor.moveToFirst()) {
                    return cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                }
            }
        }
        return -1;
    }
}
