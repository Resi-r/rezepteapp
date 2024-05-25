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

    private RecipeDbOpenHelper dbHelper;
    private SQLiteDatabase db;

    public StatusDAOImpl(Context context) {
        dbHelper = new RecipeDbOpenHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    @Override
    public void saveOrUpdate(StatusEntity entity) {
        ContentValues values = new ContentValues();
        values.put("name", entity.getName());
        try {
            db.update(DB_TABLE_STATUS, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
        } catch (Exception e) {
            db.insert(DB_TABLE_STATUS, null, values);
        }
    }

    @Override
    public void delete(StatusEntity entity) {
        db.delete(DB_TABLE_STATUS, "_id = ?", new String[]{String.valueOf(entity.getId())});
    }

    @Override
    public StatusEntity getStatusById(int id) {
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

    @Override
    public StatusEntity getStatusByName(String name) {
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

    @Override
    public List<StatusEntity> getAllStatus() {
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
