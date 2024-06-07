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
    public static final int DB_VERSION = 2;

    private Context context;

    public static RecipeDbOpenHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RecipeDbOpenHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    public RecipeDbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Ingredient (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("CREATE TABLE Label (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");
        db.execSQL("CREATE TABLE Recipe (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, image TEXT, servings INTEGER, kTime TEXT, vTime TEXT, notes TEXT, steps TEXT, statusId INTEGER)");
        db.execSQL("CREATE TABLE ShoppinglistEntry (_id INTEGER PRIMARY KEY AUTOINCREMENT, amount TEXT, ingredientId INTEGER, status TEXT, FOREIGN KEY (ingredientId) REFERENCES Ingredient(_id))");
        db.execSQL("CREATE TABLE IngredientsRecipes (_id INTEGER PRIMARY KEY AUTOINCREMENT, amount TEXT, ingredientId INTEGER, recipeId INTEGER, FOREIGN KEY (ingredientId) REFERENCES Ingredient(_id), FOREIGN KEY (recipeId) REFERENCES Recipe(_id))");
        db.execSQL("CREATE TABLE LabelsRecipes (_id INTEGER PRIMARY KEY AUTOINCREMENT, labelId INTEGER, recipeId INTEGER, FOREIGN KEY (labelId) REFERENCES Label(_id), FOREIGN KEY (recipeId) REFERENCES Recipe(_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE ShoppinglistEntry ADD COLUMN status TEXT");
        }
        Log.d(getClass().getSimpleName(), "Database upgraded from version " + oldVersion + " to " + newVersion);
    }
}
