package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.ShoppinglistEntryEntity;

import java.util.List;

public interface ShoppinglistEntryDAO {

    long saveOrUpdate(final ShoppinglistEntryEntity entity);
    long delete(final ShoppinglistEntryEntity entity);

    ShoppinglistEntryEntity getShoppinglistById(int id);
    List<ShoppinglistEntryEntity> getAllShoppinglist();
    int getId(String amount, int ingredientId);
}
