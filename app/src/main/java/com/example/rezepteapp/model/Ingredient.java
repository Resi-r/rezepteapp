package com.example.rezepteapp.model;

public class Ingredient {

    private String amount;

    private RecipeUnit unit;
    private String name;

    public Ingredient(String amount, RecipeUnit unit, String name) {
        this.amount = amount;
        this.unit = unit;
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public RecipeUnit getUnit() {
        return unit;
    }

    public void setUnit(RecipeUnit unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
