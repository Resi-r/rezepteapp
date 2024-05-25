package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.IngredientEntity;

import java.util.List;

public interface IngredientDAO {

    void saveOrUpdate(final IngredientEntity entity);
    void delete(final IngredientEntity entity);

    IngredientEntity getIngredientById(int id);
    IngredientEntity getIngredientByName(String name);
    List<IngredientEntity> getAllIngredients();
}
