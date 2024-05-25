package com.example.rezepteapp.daos;

import android.content.Context;
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

    }

    @Override
    public void delete(LabelEntity entity) {

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
}
