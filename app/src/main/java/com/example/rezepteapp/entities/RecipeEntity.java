package com.example.rezepteapp.entities;

import java.util.Arrays;
import java.util.Objects;

public class RecipeEntity {

    private int id;
    private String name;
    private byte[] image;
    private String kTime;
    private String vTime;
    private int servings;
    private String notes;
    private String steps;
    private int statusId;

    public RecipeEntity(String name, byte[] image, String kTime, String vTime, int servings, String notes, String steps, int statusId) {
        this.name = name;
        this.image = image;
        this.kTime = kTime;
        this.vTime = vTime;
        this.servings = servings;
        this.notes = notes;
        this.steps = steps;
        this.statusId = statusId;
    }

    public RecipeEntity(int id, String name, byte[] image, String kTime, String vTime, int servings, String notes, String steps, int statusId) {
        this.id = id;
        this.name = name;
        this.image = image;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeEntity that = (RecipeEntity) o;
        return id == that.id && servings == that.servings && statusId == that.statusId && Objects.equals(name, that.name) && Arrays.equals(image, that.image) && Objects.equals(kTime, that.kTime) && Objects.equals(vTime, that.vTime) && Objects.equals(notes, that.notes) && Objects.equals(steps, that.steps);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, kTime, vTime, servings, notes, steps, statusId);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}
