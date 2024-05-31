package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.ShoppinglistEntryEntity;

import java.util.List;

public interface ShoppinglistEntryDAO {

    void saveOrUpdate(final ShoppinglistEntryEntity entity);
    void delete(final ShoppinglistEntryEntity entity);
    void insert(ShoppinglistEntryEntity entity);

    ShoppinglistEntryEntity getShoppinglistEntryById(int id);
    List<ShoppinglistEntryEntity> getAllShoppinglistEntries();
    List<ShoppinglistEntryEntity> getAllTodoEntries();
    List<ShoppinglistEntryEntity> getAllDoneEntries();
    int getId(String amount, int ingredientId);
}
