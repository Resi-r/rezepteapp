package com.example.rezepteapp.daos;

import com.example.rezepteapp.entities.LabelEntity;

import java.util.List;

public interface LabelDAO {

    void saveOrUpdate(final LabelEntity entity);
    void delete(final LabelEntity entity);

    LabelEntity getLabelById(int id);
    LabelEntity getLabelByName(String name);
    List<LabelEntity> getAllLabels();
}
