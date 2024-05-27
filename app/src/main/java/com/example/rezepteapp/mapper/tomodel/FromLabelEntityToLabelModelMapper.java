package com.example.rezepteapp.mapper.tomodel;

import com.example.rezepteapp.entities.LabelEntity;
import com.example.rezepteapp.model.Label;

public class FromLabelEntityToLabelModelMapper {

    public FromLabelEntityToLabelModelMapper() {

    }

    public Label map(LabelEntity entity) {
        return new Label(entity.getName());
    }
}
