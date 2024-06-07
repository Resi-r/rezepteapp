package com.example.rezepteapp.mapper.toentitiy;

import android.content.Context;

import com.example.rezepteapp.daos.LabelDAO;
import com.example.rezepteapp.daos.LabelDAOImpl;
import com.example.rezepteapp.entities.LabelEntity;
import com.example.rezepteapp.model.Label;

public class FromLabelModelToLabelEntityMapper {
    private Context context;

    public FromLabelModelToLabelEntityMapper(Context context) {
        this.context = context;
    }

    public LabelEntity map(Label label) {
        LabelEntity entity = new LabelEntity();
        if (label.getId() != 0) {
            entity.setId(label.getId());
        }
        entity.setName(label.getName());
        return entity;
    }

    private int getLabelID(String name) {
        LabelDAO dao = new LabelDAOImpl(context);
        return dao.getId(name);
    }
}
