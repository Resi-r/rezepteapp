package com.example.rezepteapp.model;

public class ShoppinglistEntry {
    private int id;
    private String amount;
    private String ingredient;
    private ShoppinglistEtryStatus status;

    public ShoppinglistEntry(){}

    public ShoppinglistEtryStatus getStatus() {
        return status;
    }

    public void setStatus(ShoppinglistEtryStatus status) {
        this.status = status;
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
