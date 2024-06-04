package com.example.rezepteapp.model;

public class Ingredient {

    private int id;

    private String amount;

    private RecipeUnit unit;
    private String name;

    public Ingredient() {}
    public Ingredient(String amount, RecipeUnit unit, String name) {
        this.amount = amount;
        this.unit = unit;
        this.name = name;
    }

    public Ingredient(int id, String amount, RecipeUnit unit, String name) {
        this.id = id;
        this.amount = amount;
        this.unit = unit;
        this.name = name;
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
