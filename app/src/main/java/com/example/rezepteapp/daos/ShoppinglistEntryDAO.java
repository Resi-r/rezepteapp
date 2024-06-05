package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.ShoppinglistEntryEntity;

import java.util.List;

public interface ShoppinglistEntryDAO {

    void insert(ShoppinglistEntryEntity entity);
    long saveOrUpdate(final ShoppinglistEntryEntity entity);
    long delete(final ShoppinglistEntryEntity entity);

    ShoppinglistEntryEntity getShoppinglistEntryById(int id);
    List<ShoppinglistEntryEntity> getAllShoppinglistEntries();
    List<ShoppinglistEntryEntity> getAllTodoEntries();
    List<ShoppinglistEntryEntity> getAllDoneEntries();
    int getId(String amount, int ingredientId);
}
