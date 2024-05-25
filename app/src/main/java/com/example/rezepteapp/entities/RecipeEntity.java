package com.example.rezepteapp.entities;

import java.util.Objects;

public class RecipeEntity {

    private int id;
    private String name;
    private String image;
    private String kTime;
    private String vTime;
    private String notes;
    private String steps;
    private int statusId;

    public RecipeEntity(String name, String image, String kTime, String vTime, String notes, String steps, int statusId) {
        this.name = name;
        this.image = image;
        this.kTime = kTime;
        this.vTime = vTime;
        this.notes = notes;
        this.steps = steps;
        this.statusId = statusId;
    }

    public RecipeEntity(int id, String name, String image, String kTime, String vTime, String notes, String steps, int statusId) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.kTime = kTime;
        this.vTime = vTime;
        this.notes = notes;
        this.steps = steps;
        this.statusId = statusId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeEntity that = (RecipeEntity) o;
        return id == that.id && statusId == that.statusId && Objects.equals(name, that.name) && Objects.equals(image, that.image) && Objects.equals(kTime, that.kTime) && Objects.equals(vTime, that.vTime) && Objects.equals(notes, that.notes) && Objects.equals(steps, that.steps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image, kTime, vTime, notes, steps, statusId);
    }
}
