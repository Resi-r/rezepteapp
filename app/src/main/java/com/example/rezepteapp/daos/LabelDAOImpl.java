package com.example.rezepteapp.daos;

import static com.example.rezepteapp.utils.Constants.DB_TABLE_INGREDIENT;
import static com.example.rezepteapp.utils.Constants.DB_TABLE_LABEL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.LabelEntity;

import java.util.List;

public class LabelDAOImpl implements LabelDAO {

    private RecipeDbOpenHelper dbHelper;
    private SQLiteDatabase db;

    public LabelDAOImpl(Context context) {
        this.dbHelper = new RecipeDbOpenHelper(context);
        db = dbHelper.getWritableDatabase();
    }


    @Override
    public void saveOrUpdate(LabelEntity entity) {
        ContentValues values = new ContentValues();
        values.put("name", entity.getName());
        db.insert(DB_TABLE_LABEL, null, values);
    }

    @Override
    public void delete(LabelEntity entity) {
        db.delete(DB_TABLE_LABEL, "_id = ?", new String[] {String.valueOf(entity.getId())});
    }

    @Override
    public LabelEntity getLabelById(int id) {
        return null;
    }

    @Override
    public LabelEntity getLabelByName(String name) {
        return null;
    }

    @Override
    public List<LabelEntity> getAllLabels() {
        return null;
    }

    @Override
    public int getId(String name) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()){
            Cursor cursor = db.query(DB_TABLE_LABEL, new String[]{"_id"}, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            }
        }
        return 0;
    }
}
