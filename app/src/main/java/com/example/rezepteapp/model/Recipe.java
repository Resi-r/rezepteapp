package com.example.rezepteapp.model;

import android.graphics.Bitmap;

import java.util.List;

public class Recipe {
    private int id;
    private String title;
    private List<Label> labels;
    private String vTime;
    private String kTime;
    private int servings;
    private List<Ingredient> ingredients;
    private List<String> steps;
    private List<String> notes;
    private Status status;

    public Recipe() {}

    public Recipe(String title, List<Label> labels, String vTime, String kTime, int servings, List<Ingredient> ingredients, List<String> steps, List<String> notes, Status status) {
        this.title = title;
        this.labels = labels;
        this.vTime = vTime;
        this.kTime = kTime;
        this.servings = servings;
        this.ingredients = ingredients;
        this.steps = steps;
        this.notes = notes;
        this.status = status;
    }

    public Recipe(int id, String title, List<Label> labels, String vTime, String kTime, int servings, List<Ingredient> ingredients, List<String> steps, List<String> notes, Status status) {
        this.id = id;
        this.title = title;
        this.labels = labels;
        this.vTime = vTime;
        this.kTime = kTime;
        this.servings = servings;
        this.ingredients = ingredients;
        this.steps = steps;
        this.notes = notes;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public String getvTime() {
        return vTime;
    }

    public void setvTime(String vTime) {
        this.vTime = vTime;
    }

    public String getkTime() {
        return kTime;
    }

    public void setkTime(String kTime) {
        this.kTime = kTime;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<Ingredient> getIngridients() {
        return ingredients;
    }

    public void setIngridients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
