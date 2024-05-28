package com.example.rezepteapp.mapper.toentitiy;

import android.content.Context;

import com.example.rezepteapp.daos.IngredientDAO;
import com.example.rezepteapp.daos.IngredientDAOImpl;
import com.example.rezepteapp.daos.ShoppinglistDAO;
import com.example.rezepteapp.daos.ShoppinglistDAOImpl;
import com.example.rezepteapp.entities.ShoppinglistEntity;
import com.example.rezepteapp.model.ShoppinglistEntry;

public class FromShoppinglistEntryModelToShoppinglistEntityMapper {
    private Context context;

    public FromShoppinglistEntryModelToShoppinglistEntityMapper(Context context) {
        this.context = context;
    }

    public ShoppinglistEntity map(ShoppinglistEntry entry) {
        ShoppinglistEntity entity = new ShoppinglistEntity();
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
        ShoppinglistDAO dao = new ShoppinglistDAOImpl(context);
        return dao.getId(amount, ingredientId);
    }
}
