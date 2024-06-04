package com.example.rezepteapp.model;

import java.util.Arrays;

public enum ShoppinglistEtryStatus {
    TODO(1), DONE(2);

    private int id;

    ShoppinglistEtryStatus(int id){
        this.id = id;
    }

    public static ShoppinglistEtryStatus getStatusById(int id) {
        return Arrays.stream(values()).filter(status -> status.id == id).findFirst().get();
    }
}
