package com.example.rezepteapp.daos;

import static com.example.rezepteapp.utils.Constants.DB_TABLE_RECIPE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.RecipeEntity;
import com.example.rezepteapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeDAOImpl implements RecipeDAO {

    private final RecipeDbOpenHelper dbHelper;

    public RecipeDAOImpl(Context context) {
        this.dbHelper = RecipeDbOpenHelper.getInstance(context);
    }


    @Override
    public long saveOrUpdate(RecipeEntity entity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
            System.out.println("is open?: " + db.isOpen());


            ContentValues values = new ContentValues();
            values.put("name", entity.getName());
            values.put("kTime", entity.getkTime());
            values.put("vTime", entity.getvTime());
            values.put("servings", entity.getServings());
            values.put("notes", entity.getNotes());
            values.put("steps", entity.getSteps());
            values.put("statusId", entity.getStatusId());
        if (entity.getId() != 0) {
            db.update(DB_TABLE_RECIPE, values, "_id = ?", new String[] {String.valueOf(entity.getId())});
            return entity.getId();
        } else {
            return db.insert(DB_TABLE_RECIPE, null, values);
        }

    }

    @Override
    public long delete(RecipeEntity entity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
            return db.delete(DB_TABLE_RECIPE, "_id = ?", new String[]{String.valueOf(entity.getId())});

    }

    @Override
    public RecipeEntity getRecipeById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
            RecipeEntity entity = new RecipeEntity();

            try (Cursor cursor = db.query(DB_TABLE_RECIPE, null, "_id = ?", new String[]{String.valueOf(id)}, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int nameIndex = cursor.getColumnIndex("name");
                    int kTimeIndex = cursor.getColumnIndex("kTime");
                    int vTimeIndex = cursor.getColumnIndex("vTime");
                    int servingsIndex = cursor.getColumnIndex("servings");
                    int notesIndex = cursor.getColumnIndex("notes");
                    int stepsIndex = cursor.getColumnIndex("steps");
                    int statusIdIndex = cursor.getColumnIndex("statusId");

                    entity.setId(cursor.getInt(idIndex));
                    entity.setName(cursor.getString(nameIndex));
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

    @Override
    public List<RecipeEntity> getRecipesByName(String name) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
            List<RecipeEntity> entities = new ArrayList<>();
            RecipeEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_RECIPE, null, "name LIKE '%?%'", new String[]{name}, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int nameIndex = cursor.getColumnIndex("name");
                    int kTimeIndex = cursor.getColumnIndex("kTime");
                    int vTimeIndex = cursor.getColumnIndex("vTime");
                    int servingsIndex = cursor.getColumnIndex("servings");
                    int notesIndex = cursor.getColumnIndex("notes");
                    int stepsIndex = cursor.getColumnIndex("steps");
                    int statusIdIndex = cursor.getColumnIndex("statusId");

                    entity = new RecipeEntity();

                    entity.setId(cursor.getInt(idIndex));
                    entity.setName(cursor.getString(nameIndex));
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

    @Override
    public List<RecipeEntity> getRecipesByStatusId(int statusId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
            List<RecipeEntity> entities = new ArrayList<>();
            RecipeEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_RECIPE, null, "statusId = ?", new String[]{String.valueOf(statusId)}, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int nameIndex = cursor.getColumnIndex("name");
                    int kTimeIndex = cursor.getColumnIndex("kTime");
                    int vTimeIndex = cursor.getColumnIndex("vTime");
                    int servingsIndex = cursor.getColumnIndex("servings");
                    int notesIndex = cursor.getColumnIndex("notes");
                    int stepsIndex = cursor.getColumnIndex("steps");
                    int statusIdIndex = cursor.getColumnIndex("statusId");

                    entity = new RecipeEntity();

                    entity.setId(cursor.getInt(idIndex));
                    entity.setName(cursor.getString(nameIndex));
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


    @Override
    public List<RecipeEntity> getAllRecipes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
            System.out.println("is open?: " + db.isOpen());

            List<RecipeEntity> entities = new ArrayList<>();
            RecipeEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_RECIPE, null, null, null, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int nameIndex = cursor.getColumnIndex("name");
                    int kTimeIndex = cursor.getColumnIndex("kTime");
                    int vTimeIndex = cursor.getColumnIndex("vTime");
                    int servingsIndex = cursor.getColumnIndex("servings");
                    int notesIndex = cursor.getColumnIndex("notes");
                    int stepsIndex = cursor.getColumnIndex("steps");
                    int statusIdIndex = cursor.getColumnIndex("statusId");

                    entity = new RecipeEntity();

                    entity.setId(cursor.getInt(idIndex));
                    entity.setName(cursor.getString(nameIndex));
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

    @Override
    public List<RecipeEntity> getAllArchivedRecipes() {
        try (SQLiteDatabase db = dbHelper.getReadableDatabase()) {
            List<RecipeEntity> entities = new ArrayList<>();
            RecipeEntity entity;

            try (Cursor cursor = db.query(DB_TABLE_RECIPE, null, "statusId = ?", new String[]{"1"}, null, null, null)) {
                while (cursor.moveToNext()) {
                    int idIndex = cursor.getColumnIndex("_id");
                    int nameIndex = cursor.getColumnIndex("name");
                    int kTimeIndex = cursor.getColumnIndex("kTime");
                    int vTimeIndex = cursor.getColumnIndex("vTime");
                    int servingsIndex = cursor.getColumnIndex("servings");
                    int notesIndex = cursor.getColumnIndex("notes");
                    int stepsIndex = cursor.getColumnIndex("steps");
                    int statusIdIndex = cursor.getColumnIndex("statusId");

                    entity = new RecipeEntity();

                    entity.setId(cursor.getInt(idIndex));
                    entity.setName(cursor.getString(nameIndex));
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
        SQLiteDatabase db = dbHelper.getReadableDatabase();
            try (Cursor cursor = db.query(DB_TABLE_RECIPE, new String[]{"_id"}, "name = ?", new String[]{name}, null, null, null)) {
                if (cursor.moveToFirst()) {
                    return cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                }
            }

        return -1;
    }


}
