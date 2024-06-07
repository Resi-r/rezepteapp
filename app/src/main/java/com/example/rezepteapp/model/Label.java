package com.example.rezepteapp.model;

public class Label {
    private int id;
    private String name;

    public Label() {}

    public Label(String name) {
        this.name = name;
    }

    public Label(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
