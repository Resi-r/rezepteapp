package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.StatusEntity;

import java.util.List;

public interface StatusDAO {

    long saveOrUpdate(final StatusEntity entity);
    long delete(final StatusEntity entity);

    StatusEntity getStatusById(int id);
    StatusEntity getStatusByName(String name);
    List<StatusEntity> getAllStatus();
    int getId(String name);
}
