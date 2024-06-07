package com.example.rezepteapp.entities;

import java.util.Arrays;
import java.util.Objects;

public class RecipeEntity {

    private int id;
    private String name;
    private String kTime;
    private String vTime;
    private int servings;
    private String notes;
    private String steps;
    private int statusId;

    public RecipeEntity(String name, String kTime, String vTime, int servings, String notes, String steps, int statusId) {
        this.name = name;
        this.kTime = kTime;
        this.vTime = vTime;
        this.servings = servings;
        this.notes = notes;
        this.steps = steps;
        this.statusId = statusId;
    }

    public RecipeEntity(int id, String name, String kTime, String vTime, int servings, String notes, String steps, int statusId) {
        this.id = id;
        this.name = name;
        this.kTime = kTime;
        this.vTime = vTime;
        this.servings = servings;
        this.notes = notes;
        this.steps = steps;
        this.statusId = statusId;
    }

    public RecipeEntity() {}

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

    public String getkTime() {
        return kTime;
    }

    public void setkTime(String kTime) {
        this.kTime = kTime;
    }

    public String getvTime() {
        return vTime;
    }

    public void setvTime(String vTime) {
        this.vTime = vTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }
}
