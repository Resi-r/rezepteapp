package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.IngredientEntity;

import java.util.List;

public interface IngredientDAO {

    long saveOrUpdate(final IngredientEntity entity);
    long delete(final IngredientEntity entity);

    IngredientEntity getIngredientById(int id);
    IngredientEntity getIngredientByName(String name);
    List<IngredientEntity> getAllIngredients();
    int getId(String name);
}
