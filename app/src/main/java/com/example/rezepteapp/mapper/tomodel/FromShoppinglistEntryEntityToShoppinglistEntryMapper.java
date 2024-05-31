package com.example.rezepteapp.mapper.tomodel;

import com.example.rezepteapp.entities.IngredientEntity;
import com.example.rezepteapp.entities.ShoppinglistEntryEntity;
import com.example.rezepteapp.model.ShoppinglistEntry;

import java.util.ArrayList;
import java.util.List;

public class FromShoppinglistEntryEntityToShoppinglistEntryMapper {
    public List<ShoppinglistEntry> mapToList(List<ShoppinglistEntryEntity> sle, List<IngredientEntity> ie) {
        List<ShoppinglistEntry> list = new ArrayList<>();
        for (ShoppinglistEntryEntity entity : sle) {
            list.add(mapToEntry(entity, ie));
        }
        return list;
    }

    public ShoppinglistEntry mapToEntry(ShoppinglistEntryEntity sle, List<IngredientEntity> ie) {
        ShoppinglistEntry entry = new ShoppinglistEntry();
        entry.setAmount(sle.getAmount());
        entry.setId(sle.getId());
        entry.setIngredient(getIngredientName(sle.getId(), ie));

        return entry;
    }
    public ShoppinglistEntry mapToEntry(ShoppinglistEntryEntity sle, IngredientEntity ie) {
        ShoppinglistEntry entry = new ShoppinglistEntry();
        entry.setAmount(sle.getAmount());
        entry.setId(sle.getId());
        entry.setIngredient(ie.getName());

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
