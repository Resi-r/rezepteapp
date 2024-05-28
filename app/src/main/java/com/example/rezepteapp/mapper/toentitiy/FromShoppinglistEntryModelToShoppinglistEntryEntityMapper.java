package com.example.rezepteapp.mapper.toentitiy;

import android.content.Context;

import com.example.rezepteapp.daos.IngredientDAO;
import com.example.rezepteapp.daos.IngredientDAOImpl;
import com.example.rezepteapp.daos.ShoppinglistDAOEntry;
import com.example.rezepteapp.daos.ShoppinglistDAOEntryImpl;
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

        return entity;
    }

    private int getIngredientId(String ingredient) {
        IngredientDAO dao = new IngredientDAOImpl(context);
        return dao.getId(ingredient);
    }

    private int getShoppinglistId(String amount, int ingredientId) {
        ShoppinglistDAOEntry dao = new ShoppinglistDAOEntryImpl(context);
        return dao.getId(amount, ingredientId);
    }
}
