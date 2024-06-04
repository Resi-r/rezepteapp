package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.LabelEntity;

import java.util.List;

public interface LabelDAO {

    long saveOrUpdate(final LabelEntity entity);
    long delete(final LabelEntity entity);

    LabelEntity getLabelById(int id);
    LabelEntity getLabelByName(String name);
    List<LabelEntity> getAllLabels();
    int getId(String name);
}
