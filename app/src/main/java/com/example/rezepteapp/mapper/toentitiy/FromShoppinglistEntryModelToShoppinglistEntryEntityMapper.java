package com.example.rezepteapp.mapper.toentitiy;

import android.content.Context;

import com.example.rezepteapp.daos.IngredientDAO;
import com.example.rezepteapp.daos.IngredientDAOImpl;
import com.example.rezepteapp.daos.ShoppinglistEntryDAO;
import com.example.rezepteapp.daos.ShoppinglistEntryDAOImpl;
import com.example.rezepteapp.entities.ShoppinglistEntryEntity;
import com.example.rezepteapp.model.ShoppinglistEntry;

public class FromShoppinglistEntryModelToShoppinglistEntryEntityMapper {
    private Context context;

    public FromShoppinglistEntryModelToShoppinglistEntryEntityMapper(Context context) {
        this.context = context;
    }

    public ShoppinglistEntryEntity map(ShoppinglistEntry entry) {
        ShoppinglistEntryEntity entity = new ShoppinglistEntryEntity();
        int ingredientId = getIngredientId(entry.getIngredient());
        entity.setIngredientId(ingredientId);
        entity.setId(getShoppinglistId(entry.getAmount(), ingredientId));
        entity.setAmount(entry.getAmount());
        entity.setStatus(entry.getStatus());

        return entity;
    }

    private int getIngredientId(String ingredient) {
        IngredientDAO dao = new IngredientDAOImpl(context);
        return dao.getId(ingredient);
    }

    private int getShoppinglistId(String amount, int ingredientId) {
        ShoppinglistEntryDAO dao = new ShoppinglistEntryDAOImpl(context);
        return dao.getId(amount, ingredientId);
    }
}
