package com.example.rezepteapp.model;

public class FilterOption {

    private String filterName;
    private boolean active;

    public FilterOption(String filterName, boolean active) {
        this.filterName = filterName;
        this.active = active;
    }

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
