package com.example.rezepteapp.model;

import android.database.Cursor;
import android.util.Log;

import com.example.rezepteapp.daos.IngredientDAOImpl;
import com.example.rezepteapp.daos.ShoppinglistDAOImpl;
import com.example.rezepteapp.database.RecipeDbOpenHelper;
import com.example.rezepteapp.entities.ShoppinglistEntity;
import com.example.rezepteapp.mapper.ToShoppingListMapper;

import java.util.List;

public class ShoppinglistModel {
    private List<ShoppinglistEntry> toDoList;
    private ShoppinglistDAOImpl sldao;
    private IngredientDAOImpl idao;


    public List<ShoppinglistEntry> getToDoList() {
        toDoList.clear();
        ToShoppingListMapper mapper = new ToShoppingListMapper();
        toDoList.addAll(mapper.mapToList(sldao.getAllShoppinglist(), idao.getAllIngredients()));

        return toDoList;
    }


}
