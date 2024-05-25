package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.ShoppinglistEntity;

import java.util.List;

public interface ShoppinglistDAO {

    void saveOrUpdate(final ShoppinglistEntity entity);
    void delete(final ShoppinglistEntity entity);

    ShoppinglistEntity getShoppinglistById(int id);
    List<ShoppinglistEntity> getAllShoppinglist();
}
