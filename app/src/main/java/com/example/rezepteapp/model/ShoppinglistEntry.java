package com.example.rezepteapp.model;

public class ShoppinglistEntry {
    private int id;
    private String amount;
    private String ingredient;

    public ShoppinglistEntry(){}

    public ShoppinglistEntry(String amount, String ingredient) {
        this.amount = amount;
        this.ingredient = ingredient;
    }

    public ShoppinglistEntry(int id, String amount, String ingredient) {
        this.id = id;
        this.amount = amount;
        this.ingredient = ingredient;
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

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
