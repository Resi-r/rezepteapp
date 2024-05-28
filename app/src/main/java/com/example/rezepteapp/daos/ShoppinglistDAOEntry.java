package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.ShoppinglistEntryEntity;

import java.util.List;

public interface ShoppinglistDAOEntry {

    void saveOrUpdate(final ShoppinglistEntryEntity entity);
    void delete(final ShoppinglistEntryEntity entity);

    ShoppinglistEntryEntity getShoppinglistById(int id);
    List<ShoppinglistEntryEntity> getAllShoppinglist();
    int getId(String amount, int ingredientId);
}
