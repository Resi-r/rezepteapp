package com.example.rezepteapp.mapper.toentitiy;

import android.content.Context;

import com.example.rezepteapp.daos.IngredientDAO;
import com.example.rezepteapp.daos.IngredientDAOImpl;
import com.example.rezepteapp.entities.IngredientEntity;
import com.example.rezepteapp.model.Ingredient;

public class FromIngredientModelToIngredientEntityMapper {
    private Context context;

    public FromIngredientModelToIngredientEntityMapper(Context context) {
        this.context = context;
    }

    public IngredientEntity map(Ingredient ingredient) {

        IngredientEntity entity = new IngredientEntity();
        if (ingredient.getId() != 0) {
            entity.setId(ingredient.getId());
        }
        entity.setName(ingredient.getName());
        //entity.setId(getIngredientId(ingredient.getName()));
        return entity;
    }

    private int getIngredientId(String name) {
        IngredientDAO dao = new IngredientDAOImpl(context);
        return dao.getId(name);
    }
}
