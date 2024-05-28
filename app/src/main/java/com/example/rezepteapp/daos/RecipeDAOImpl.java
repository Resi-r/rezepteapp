package com.example.rezepteapp.daos;

import static com.example.rezepteapp.utils.Constants.DB_TABLE_INGREDIENT;
import static com.example.rezepteapp.utils.Constants.DB_TABLE_RECIPE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.RecipeEntity;

import java.util.ArrayList;
import java.util.List;

public class RecipeDAOImpl implements RecipeDAO {

    private RecipeDbOpenHelper dbHelper;

    public RecipeDAOImpl(Context context) {
        this.dbHelper = new RecipeDbOpenHelper(context);
    }


    @Override
    public void saveOrDelete(RecipeEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("name", entity.getName());
            values.put("image", entity.getImage());
            values.put("kTime", entity.getkTime());
            values.put("vTime", entity.getvTime());
            values.put("servings", entity.getServings());
            values.put("notes", entity.getNotes());
            values.put("steps", entity.getSteps());
            values.put("statusId", entity.getStatusId());
            try {
                db.update(DB_TABLE_RECIPE, values, "_id = ?", new String[] {String.valueOf(entity.getId())});
            } catch (Exception e) {
                db.insert(DB_TABLE_RECIPE, null, values);
            }
        }
    }

    @Override
    public void delete(RecipeEntity entity) {
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            db.delete(DB_TABLE_RECIPE, "_id = ?", new String[]{String.valueOf(entity.getId())});
        }
    }

    @Override
    public RecipeEntity getRecipeById(int id) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            RecipeEntity entity = new RecipeEntity();

            try (Cursor cursor = db.query(DB_TABLE_RECIPE, null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int nameIndex = cursor.getColumnIndex("name");
                    int imageIndex = cursor.getColumnIndex("image");
                    int kTimeIndex = cursor.getColumnIndex("kTime");
                    int vTimeIndex = cursor.getColumnIndex("vTime");
                    int servingsIndex = cursor.getColumnIndex("servings");
                    int notesIndex = cursor.getColumnIndex("notes");
                    int stepsIndex = cursor.getColumnIndex("steps");
                    int statusIdIndex = cursor.getColumnIndex("statusId");

                    entity.setId(cursor.getInt(idIndex));
                    entity.setName(cursor.getString(nameIndex));
                    entity.setImage(cursor.getBlob(imageIndex));
                    entity.setkTime(cursor.getString(kTimeIndex));
                    entity.setvTime(cursor.getString(vTimeIndex));
                    entity.setServings(cursor.getInt(servingsIndex));
                    entity.setNotes(cursor.getString(notesIndex));
                    entity.setSteps(cursor.getString(stepsIndex));
                    entity.setStatusId(cursor.getInt(statusIdIndex));
                }
                return entity;
            }
        }
    }

    @Override
    public List<RecipeEntity> getRecipesByName(String name) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            List<RecipeEntity> entities = new ArrayList<>();
            RecipeEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_RECIPE, null, "name = '?'", new String[]{name}, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int nameIndex = cursor.getColumnIndex("name");
                    int imageIndex = cursor.getColumnIndex("image");
                    int kTimeIndex = cursor.getColumnIndex("kTime");
                    int vTimeIndex = cursor.getColumnIndex("vTime");
                    int servingsIndex = cursor.getColumnIndex("servings");
                    int notesIndex = cursor.getColumnIndex("notes");
                    int stepsIndex = cursor.getColumnIndex("steps");
                    int statusIdIndex = cursor.getColumnIndex("statusId");

                    entity = new RecipeEntity();

                    entity.setId(cursor.getInt(idIndex));
                    entity.setName(cursor.getString(nameIndex));
                    entity.setImage(cursor.getBlob(imageIndex));
                    entity.setkTime(cursor.getString(kTimeIndex));
                    entity.setvTime(cursor.getString(vTimeIndex));
                    entity.setServings(cursor.getInt(servingsIndex));
                    entity.setNotes(cursor.getString(notesIndex));
                    entity.setSteps(cursor.getString(stepsIndex));
                    entity.setStatusId(cursor.getInt(statusIdIndex));

                    entities.add(entity);
                }
                return entities;
            }
        }
    }

    @Override
    public List<RecipeEntity> getRecipesByStatusId(int statusId) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            List<RecipeEntity> entities = new ArrayList<>();
            RecipeEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_RECIPE, null, "statusId = ?", new String[]{String.valueOf(statusId)}, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int nameIndex = cursor.getColumnIndex("name");
                    int imageIndex = cursor.getColumnIndex("image");
                    int kTimeIndex = cursor.getColumnIndex("kTime");
                    int vTimeIndex = cursor.getColumnIndex("vTime");
                    int servingsIndex = cursor.getColumnIndex("servings");
                    int notesIndex = cursor.getColumnIndex("notes");
                    int stepsIndex = cursor.getColumnIndex("steps");
                    int statusIdIndex = cursor.getColumnIndex("statusId");

                    entity = new RecipeEntity();

                    entity.setId(cursor.getInt(idIndex));
                    entity.setName(cursor.getString(nameIndex));
                    entity.setImage(cursor.getBlob(imageIndex));
                    entity.setkTime(cursor.getString(kTimeIndex));
                    entity.setvTime(cursor.getString(vTimeIndex));
                    entity.setServings(cursor.getInt(servingsIndex));
                    entity.setNotes(cursor.getString(notesIndex));
                    entity.setSteps(cursor.getString(stepsIndex));
                    entity.setStatusId(cursor.getInt(statusIdIndex));

                    entities.add(entity);
                }
                return entities;
            }
        }
    }

    @Override
    public List<RecipeEntity> getAllRecipes() {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            List<RecipeEntity> entities = new ArrayList<>();
            RecipeEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_RECIPE, null, null, null, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int nameIndex = cursor.getColumnIndex("name");
                    int imageIndex = cursor.getColumnIndex("image");
                    int kTimeIndex = cursor.getColumnIndex("kTime");
                    int vTimeIndex = cursor.getColumnIndex("vTime");
                    int servingsIndex = cursor.getColumnIndex("servings");
                    int notesIndex = cursor.getColumnIndex("notes");
                    int stepsIndex = cursor.getColumnIndex("steps");
                    int statusIdIndex = cursor.getColumnIndex("statusId");

                    entity = new RecipeEntity();

                    entity.setId(cursor.getInt(idIndex));
                    entity.setName(cursor.getString(nameIndex));
                    entity.setImage(cursor.getBlob(imageIndex));
                    entity.setkTime(cursor.getString(kTimeIndex));
                    entity.setvTime(cursor.getString(vTimeIndex));
                    entity.setServings(cursor.getInt(servingsIndex));
                    entity.setNotes(cursor.getString(notesIndex));
                    entity.setSteps(cursor.getString(stepsIndex));
                    entity.setStatusId(cursor.getInt(statusIdIndex));

                    entities.add(entity);
                }
                return entities;
            }
        }
    }

    @Override
    public int getId(String name) {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()){
            Cursor cursor = db.query(DB_TABLE_RECIPE, new String[]{"_id"}, "name = ?", new String[]{name}, null, null, null);
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            }
        }
        return 0;
    }
}
