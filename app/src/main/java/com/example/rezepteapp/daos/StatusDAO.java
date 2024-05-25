package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.StatusEntity;

import java.util.List;

public interface StatusDAO {

    void saveOrUpdate(final StatusEntity entity);
    void delete(final StatusEntity entity);

    StatusEntity getStatusById(int id);
    StatusEntity getStatusByName(String name);
    List<StatusEntity> getAllStatus();
}
