package com.example.rezepteapp.model;

public class ShoppinglistEntry {

    private int id;
    private String amount;
    private String ingredient;
    private int ingredientID;

    public ShoppinglistEntry(String amount, int id, String ingredient, int ingredientID) {
        this.amount = amount;
        this.id = id;
        this.ingredient = ingredient;
        this.ingredientID = ingredientID;
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

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }
}
