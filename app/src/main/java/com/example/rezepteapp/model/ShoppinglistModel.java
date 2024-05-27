package com.example.rezepteapp.model;

import android.database.Cursor;
import android.util.Log;

import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.ShoppinglistEntity;

import java.util.List;

public class ShoppinglistModel {
    private List<ShoppinglistEntry> toDoList;
    private RecipeDbOpenHelper db;

    public List<ShoppinglistEntry> getToDoList() {
        mapCusorToShoppinglistEntry(db.readAllShoppinglistEntries());
        return toDoList;
    }

    public void updateToDoList(List<ShoppinglistEntry> toDoList) {
        for (ShoppinglistEntry entry : toDoList) {
            db.updateShoppinglist(new ShoppinglistEntity(entry.getId(), entry.getAmount(), entry.getIngredientID()));
        }
    }

    private void mapCusorToShoppinglistEntry(Cursor cursor) {
        toDoList.clear();
        if (cursor != null) {
            try {
            if (cursor.moveToFirst()) {
                do {
                    toDoList.add(new ShoppinglistEntry(
                            cursor.getString(cursor.getColumnIndexOrThrow("sl.amount")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("sl._id")),
                            cursor.getString(cursor.getColumnIndexOrThrow("i.text")),
                            cursor.getInt(cursor.getColumnIndexOrThrow("i._id"))
                    ));
                } while (cursor.moveToNext());
            }
            } catch (IllegalArgumentException e) {
                Log.e("DatabaseError", "Column not found " + e.getMessage());
            } finally {
                cursor.close();
            }
        }
    }
}
