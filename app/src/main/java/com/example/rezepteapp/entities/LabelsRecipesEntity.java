package com.example.rezepteapp.entities;

import java.util.Objects;

public class LabelsRecipesEntity {

    private int id;
    private int labelId;
    private int recipeId;

    public LabelsRecipesEntity(int labelId, int recipeId) {
        this.labelId = labelId;
        this.recipeId = recipeId;
    }

    public LabelsRecipesEntity(int id, int labelId, int recipeId) {
        this.id = id;
        this.labelId = labelId;
        this.recipeId = recipeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LabelsRecipesEntity that = (LabelsRecipesEntity) o;
        return id == that.id && labelId == that.labelId && recipeId == that.recipeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, labelId, recipeId);
    }
}
