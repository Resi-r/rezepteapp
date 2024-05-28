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
        return new LabelEntity(getLabelID(label.getName()), label.getName());
    }

    private int getLabelID(String name) {
        LabelDAO dao = new LabelDAOImpl(context);
        return dao.getId(name);
    }
}
