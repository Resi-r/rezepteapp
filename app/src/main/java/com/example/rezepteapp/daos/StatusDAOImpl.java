package com.example.rezepteapp.daos;

import static com.example.rezepteapp.utils.Constants.DB_TABLE_STATUS;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.StatusEntity;

import java.util.ArrayList;
import java.util.List;

public class StatusDAOImpl implements StatusDAO {

    private final RecipeDbOpenHelper dbHelper;

    public StatusDAOImpl(Context context) {
        this.dbHelper = RecipeDbOpenHelper.getInstance(context);
    }


    @Override
    public long saveOrUpdate(StatusEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("name", entity.getName());
            if (entity.getId() != 0) {
                return db.update(DB_TABLE_STATUS, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
            } else {
                return db.insert(DB_TABLE_STATUS, null, values);
            }
        }
    }

    @Override
    public long delete(StatusEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            return db.delete(DB_TABLE_STATUS, "_id = ?", new String[]{String.valueOf(entity.getId())});
        }
    }

    @Override
    public StatusEntity getStatusById(int id) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            StatusEntity entity = new StatusEntity();

            try (Cursor cursor = db.query(DB_TABLE_STATUS, null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null)) {
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
    public StatusEntity getStatusByName(String name) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {

            StatusEntity entity = new StatusEntity();

            try (Cursor cursor = db.query(DB_TABLE_STATUS, null, "name LIKE ('?')", new String[]{name}, null, null, null)) {

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
    public List<StatusEntity> getAllStatus() {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {

            List<StatusEntity> entities = new ArrayList<>();
            StatusEntity tmp;

            try (Cursor cursor = db.query(DB_TABLE_STATUS,
                    null, null, null,
                    null, null, null)) {

                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int nameIndex = cursor.getColumnIndex("name");

                    tmp = new StatusEntity();

                    tmp.setId(cursor.getInt(idIndex));
                    tmp.setName(cursor.getString(nameIndex));

                    entities.add(tmp);
                }
                return entities;
            }
        }
    }

    @Override
    public int getId(String name) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            try (Cursor cursor = db.query(DB_TABLE_STATUS, new String[]{"_id"}, "name = ?", new String[]{name}, null, null, null)) {
                if (cursor.moveToFirst()) {
                    return cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                }
            }
        }
        return -1;
    }
}
