package com.example.rezepteapp.daos;

import static com.example.rezepteapp.utils.Constants.DB_TABLE_LABEL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.LabelEntity;

import java.util.ArrayList;
import java.util.List;

public class LabelDAOImpl implements LabelDAO {

    private final RecipeDbOpenHelper dbHelper;
    public LabelDAOImpl(Context context) {
        this.dbHelper = RecipeDbOpenHelper.getInstance(context);
    }


    @Override
    public long saveOrUpdate(LabelEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            if (entity.getId() != 0) {
                values.put("_id", entity.getId());
            }
            values.put("name", entity.getName());
            if (entity.getId() != 0) {
                db.update(DB_TABLE_LABEL, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
                return entity.getId();
            } else {
                return db.insert(DB_TABLE_LABEL, null, values);
            }
        }
    }

    @Override
    public long delete(LabelEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            return db.delete(DB_TABLE_LABEL, "_id = ?", new String[]{String.valueOf(entity.getId())});
        }
    }

    @Override
    public LabelEntity getLabelById(int id) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            LabelEntity entity = new LabelEntity();

            try (Cursor cursor = db.query(DB_TABLE_LABEL, null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null)) {
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
    public LabelEntity getLabelByName(String name) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            LabelEntity entity = new LabelEntity();

            try (Cursor cursor = db.query(DB_TABLE_LABEL, null, "name LIKE '?'", new String[]{name}, null, null, null)) {
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
    public List<LabelEntity> getAllLabels() {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            List<LabelEntity> entities = new ArrayList<>();
            LabelEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_LABEL, null, null, null, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int nameIndex = cursor.getColumnIndex("name");

                    entity = new LabelEntity();

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
            try (Cursor cursor = db.query(DB_TABLE_LABEL, new String[]{"_id"}, "name = ?", new String[]{name}, null, null, null)) {
                if (cursor.moveToFirst()) {
                    return cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                }
            }
        }
        return -1;
    }
//    public int getId(String name) {
//        SQLiteDatabase db = null;
//        Cursor cursor = null;
//        try {
//            db = dbHelper.getReadableDatabase();
//            cursor = db.query(DB_TABLE_LABEL, new String[]{"_id"}, "name = ?", new String[]{name}, null, null, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                int idColumnIndex = cursor.getColumnIndex("_id");
//                if (idColumnIndex != -1) {
//                    return cursor.getInt(idColumnIndex);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace(); // Optional: Logging the exception might be useful for debugging.
//        } finally {
//            if (cursor != null) {
//                cursor.close();
//            }
//            if (db != null) {
//                db.close();
//            }
//        }
//        return -1;
//    }
}
