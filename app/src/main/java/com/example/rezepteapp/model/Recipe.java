package com.example.rezepteapp.model;

import android.graphics.drawable.Drawable;

import java.util.List;

public class Recipe {
    private String title;
    private Drawable imageTitle;
    private List<String> labels;
    private String vTime;
    private String kTime;
    private int people;
    private List<Ingredient> ingredients;
    private List<String> steps;
    private List<String> notes;

    public Recipe(String title, Drawable imageTitle, List<String> labels, String vTime, String kTime, int people, List<Ingredient> ingredients, List<String> steps, List<String> notes) {
        this.title = title;
        this.imageTitle = imageTitle;
        this.labels = labels;
        this.vTime = vTime;
        this.kTime = kTime;
        this.people = people;
        this.ingredients = ingredients;
        this.steps = steps;
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(Drawable imageTitle) {
        this.imageTitle = imageTitle;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
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

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
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
}
