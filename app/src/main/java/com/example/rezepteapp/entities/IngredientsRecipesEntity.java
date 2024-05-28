package com.example.rezepteapp.entities;

import java.util.Objects;

public class IngredientsRecipesEntity {

    private int id;
    private String amount;
    private int ingredientId;
    private int recipeId;

    public IngredientsRecipesEntity() {}
    public IngredientsRecipesEntity(String amount, int ingredientId, int recipeId) {
        this.amount = amount;
        this.ingredientId = ingredientId;
        this.recipeId = recipeId;
    }

    public IngredientsRecipesEntity(int id, String amount, int ingredientId, int recipeId) {
        this.id = id;
        this.amount = amount;
        this.ingredientId = ingredientId;
        this.recipeId = recipeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
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
        IngredientsRecipesEntity that = (IngredientsRecipesEntity) o;
        return id == that.id && ingredientId == that.ingredientId && recipeId == that.recipeId && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, ingredientId, recipeId);
    }
}
