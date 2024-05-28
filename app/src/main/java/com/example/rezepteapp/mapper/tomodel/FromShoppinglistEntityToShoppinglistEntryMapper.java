package com.example.rezepteapp.mapper.tomodel;

import android.content.Context;

import com.example.rezepteapp.entities.IngredientEntity;
import com.example.rezepteapp.entities.ShoppinglistEntity;
import com.example.rezepteapp.model.ShoppinglistEntry;

import java.util.ArrayList;
import java.util.List;

public class FromShoppinglistEntityToShoppinglistEntryMapper {
    public List<ShoppinglistEntry> mapToList(List<ShoppinglistEntity> sle, List<IngredientEntity> ie) {
        List<ShoppinglistEntry> list = new ArrayList<>();
        for (ShoppinglistEntity entity : sle) {
            list.add(mapToEntry(entity, ie));
        }
        return list;
    }

    public ShoppinglistEntry mapToEntry(ShoppinglistEntity sle, List<IngredientEntity> ie) {
        ShoppinglistEntry entry = new ShoppinglistEntry();
        entry.setAmount(sle.getAmount());
        entry.setId(sle.getId());
        entry.setIngredient(getIngredientName(sle.getId(), ie));

        return entry;
    }

    private String getIngredientName(int ingredientId, List<IngredientEntity> ingredients) {
        String ingredientName = "";
        for (IngredientEntity entity : ingredients) {
            if (entity.getId() == ingredientId) {
                ingredientName = entity.getName();
            }
        }
        return ingredientName;
    }
}
