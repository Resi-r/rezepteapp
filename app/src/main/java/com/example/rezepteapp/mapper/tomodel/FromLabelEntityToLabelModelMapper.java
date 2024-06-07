package com.example.rezepteapp.mapper.tomodel;

import com.example.rezepteapp.entities.LabelEntity;
import com.example.rezepteapp.model.Label;

public class FromLabelEntityToLabelModelMapper {

    public FromLabelEntityToLabelModelMapper() {

    }

    public Label map(LabelEntity entity) {
        Label label = new Label();
        label.setId(entity.getId());
        label.setName(entity.getName());
        return label;
    }
}
