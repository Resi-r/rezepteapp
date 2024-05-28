package com.example.rezepteapp.entities;

import java.util.Objects;

public class ShoppinglistEntryEntity {

    private int id;
    private String amount;

    private int ingredientId;

    public ShoppinglistEntryEntity() {}

    public ShoppinglistEntryEntity(String amount, int ingredientId) {
        this.amount = amount;
        this.ingredientId = ingredientId;
    }

    public ShoppinglistEntryEntity(int id, String amount, int ingredientId) {
        this.id = id;
        this.amount = amount;
        this.ingredientId = ingredientId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppinglistEntryEntity that = (ShoppinglistEntryEntity) o;
        return id == that.id && ingredientId == that.ingredientId && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, ingredientId);
    }
}
