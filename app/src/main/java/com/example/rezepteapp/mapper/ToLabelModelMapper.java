package com.example.rezepteapp.mapper;

import com.example.rezepteapp.entities.LabelEntity;
import com.example.rezepteapp.model.Label;

public class ToLabelModelMapper {

    public ToLabelModelMapper() {

    }

    public Label map(LabelEntity entity) {
        return new Label(entity.getName());
    }
}
