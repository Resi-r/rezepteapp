package com.example.rezepteapp.model;

import java.util.Arrays;

public enum Status {
    IN_ACHIVE(1), LIVE(2);

    private int id;

    Status(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Status getStatusById(int id) {
        return Arrays.stream(values()).filter(status -> status.id == id).findFirst().get();
    }
}
