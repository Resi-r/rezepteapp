package com.example.rezepteapp.database;

import static com.example.rezepteapp.utils.Constants.DB_TABLE_INGREDIENT;
import static com.example.rezepteapp.utils.Constants.DB_TABLE_INGREDIENTS_RECIPES;
import static com.example.rezepteapp.utils.Constants.DB_TABLE_LABEL;
import static com.example.rezepteapp.utils.Constants.DB_TABLE_LABELS_RECIPES;
import static com.example.rezepteapp.utils.Constants.DB_TABLE_RECIPE;
import static com.example.rezepteapp.utils.Constants.DB_TABLE_SHOPPINGLIST_ENTRY;
import static com.example.rezepteapp.utils.Constants.DB_TABLE_STATUS;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.rezepteapp.RecipeRepository;
import com.example.rezepteapp.entities.IngredientEntity;
import com.example.rezepteapp.entities.IngredientsRecipesEntity;
import com.example.rezepteapp.entities.LabelEntity;
import com.example.rezepteapp.entities.LabelsRecipesEntity;
import com.example.rezepteapp.entities.RecipeEntity;
import com.example.rezepteapp.entities.ShoppinglistEntryEntity;
import com.example.rezepteapp.entities.StatusEntity;

public class RecipeDbOpenHelper extends SQLiteOpenHelper {

    private static RecipeDbOpenHelper mInstance = null;

    public static final String DB_NAME = "RecipeDB";
    public static final int DB_VERSION = 1;

    private Context context;

    public static RecipeDbOpenHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RecipeDbOpenHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public RecipeDbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Ingredient (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("CREATE TABLE Status (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("CREATE TABLE Label (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("CREATE TABLE Recipe (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, image TEXT, servings INTEGER, kTime TEXT, vTime TEXT, notes TEXT, steps TEXT, statusId INTEGER)");
        db.execSQL("CREATE TABLE ShoppinglistEntry (_id INTEGER PRIMARY KEY AUTOINCREMENT, amount TEXT, ingredientId INTEGER, FOREIGN KEY (ingredientId) REFERENCES Ingredient(_id))");
        db.execSQL("CREATE TABLE IngredientsRecipes (_id INTEGER PRIMARY KEY AUTOINCREMENT, amount TEXT, ingredientId INTEGER, recipeId INTEGER, FOREIGN KEY (ingredientId) REFERENCES Ingredient(_id), FOREIGN KEY (recipeId) REFERENCES Recipe(_id))");
        db.execSQL("CREATE TABLE LabelsRecipes (_id INTEGER PRIMARY KEY AUTOINCREMENT, labelId INTEGER, recipeId INTEGER, FOREIGN KEY (labelId) REFERENCES Label(_id), FOREIGN KEY (recipeId) REFERENCES Recipe(_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(getClass().getSimpleName(), "Upgrades werden nicht unterstützt.");
    }

    // Insert and Update
    public void insertIngredient(IngredientEntity entity) {
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("name", entity.getName());
            db.insert(DB_TABLE_INGREDIENT, null, values);
        }
    }

    public boolean updateIngredient(IngredientEntity entity) {
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("name", entity.getName());
            int result = db.update(DB_TABLE_INGREDIENT, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
            return result != 0;
        }
    }

    public void insertStatus(StatusEntity entity) {
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("name", entity.getName());
            db.insert(DB_TABLE_STATUS, null, values);
        }
    }

    public boolean updateStatus(StatusEntity entity) {
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("name", entity.getName());
            int result = db.update(DB_TABLE_STATUS, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
            return result != 0;
        }
    }

    public void insertLabel(LabelEntity entity) {
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("name", entity.getName());
            db.insert(DB_TABLE_LABEL, null, values);
        }
    }

    public boolean updateLabel(LabelEntity entity) {
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("name", entity.getName());
            int result = db.update(DB_TABLE_LABEL, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
            return result != 0;
        }
    }

    public void insertRecipe(RecipeEntity entity) {
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("name", entity.getName());
            values.put("image", entity.getImage());
            values.put("kTime", entity.getkTime());
            values.put("vTime", entity.getvTime());
            values.put("notes", entity.getNotes());
            values.put("steps", entity.getSteps());
            values.put("statusId", entity.getStatusId());
            db.insert(DB_TABLE_RECIPE, null, values);
        }
    }

    public boolean updateRecipe(RecipeEntity entity) {
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("name", entity.getName());
            values.put("image", entity.getImage());
            values.put("kTime", entity.getkTime());
            values.put("vTime", entity.getvTime());
            values.put("notes", entity.getNotes());
            values.put("steps", entity.getSteps());
            values.put("statusId", entity.getStatusId());
            int result = db.update(DB_TABLE_RECIPE, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
            return result != 0;
        }
    }

    public void insertShoppinglist(ShoppinglistEntryEntity entity) {
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("amount", entity.getAmount());
            values.put("ingredientId", entity.getIngredientId());
            db.insert(DB_TABLE_SHOPPINGLIST_ENTRY, null, values);
        }
    }

    public boolean updateShoppinglist(ShoppinglistEntryEntity entity) {
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("amount", entity.getAmount());
            values.put("ingredientId", entity.getIngredientId());
            int result = db.update(DB_TABLE_SHOPPINGLIST_ENTRY, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
            return result != 0;
        }
    }

    public void insertIngredientsRecipes(IngredientsRecipesEntity entity) {
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("amount", entity.getAmount());
            values.put("ingredientId", entity.getIngredientId());
            values.put("recipeId", entity.getRecipeId());
            db.insert(DB_TABLE_INGREDIENTS_RECIPES, null, values);
        }
    }

    public boolean updateIngredientsRecipes(IngredientsRecipesEntity entity) {
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("amount", entity.getAmount());
            values.put("ingredientId", entity.getIngredientId());
            values.put("recipeId", entity.getRecipeId());
            int result = db.update(DB_TABLE_INGREDIENTS_RECIPES, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
            return result != 0;
        }
    }

    public void insertLabelsRecipes(LabelsRecipesEntity entity) {
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("labelId", entity.getLabelId());
            values.put("recipeId", entity.getRecipeId());
            db.insert(DB_TABLE_LABELS_RECIPES, null, values);
        }
    }

    public boolean updateLabelsRecipes(LabelsRecipesEntity entity) {
        try(SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put("labelId", entity.getLabelId());
            values.put("recipeId", entity.getRecipeId());
            int result = db.update(DB_TABLE_LABELS_RECIPES, values, "_id = ?", new String[]{String.valueOf(entity.getId())});
            return result != 0;
        }
    }
}
