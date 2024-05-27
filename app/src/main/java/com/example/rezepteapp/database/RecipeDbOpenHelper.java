package com.example.rezepteapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RecipeDbOpenHelper extends SQLiteOpenHelper {

    public RecipeDbOpenHelper(Context context) {
        super(context, "RecipeDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Ingredient (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("CREATE TABLE Status (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("CREATE TABLE Label (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("CREATE TABLE Recipe (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, image TEXT, servings INTEGER, kTime TEXT, vTime TEXT, notes TEXT, steps TEXT, statusId INTEGER)");
        db.execSQL("CREATE TABLE Shoppinglist (_id INTEGER PRIMARY KEY AUTOINCREMENT, amount TEXT, ingredientId INTEGER)");
        db.execSQL("CREATE TABLE IngredientsRecipes (_id INTEGER PRIMARY KEY AUTOINCREMENT, amount TEXT, ingredientId INTEGER, recipeId INTEGER)");
        db.execSQL("CREATE TABLE LabelsRecipes (_id INTEGER PRIMARY KEY AUTOINCREMENT, labelId INTEGER, recipeId INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(getClass().getSimpleName(), "Upgrades werden nicht unterstützt.");
    }
}
