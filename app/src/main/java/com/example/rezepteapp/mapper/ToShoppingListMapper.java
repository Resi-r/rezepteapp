package com.example.rezepteapp.mapper;

import com.example.rezepteapp.entities.IngredientEntity;
import com.example.rezepteapp.entities.ShoppinglistEntity;
import com.example.rezepteapp.model.ShoppinglistEntry;

import java.util.ArrayList;
import java.util.List;

public class ToShoppingListMapper {

    public List<ShoppinglistEntry> mapToList(List<ShoppinglistEntity> sle, List<IngredientEntity> ie) {
        List<ShoppinglistEntry> list = new ArrayList<>();
        for (ShoppinglistEntity entity : sle) {
            list.add(mapToEntry(entity, ie));
        }
        return list;
    }

    public ShoppinglistEntry mapToEntry(ShoppinglistEntity sle, List<IngredientEntity> ie) {
        String ingredientName = "";
        for (IngredientEntity entity : ie) {
            if (entity.getId() == sle.getId()) {
                ingredientName = entity.getName();
            }
        }

        return new ShoppinglistEntry(sle.getAmount(), sle.getId(), ingredientName, sle.getIngredientId());
    }
}
