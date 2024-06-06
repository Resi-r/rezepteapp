package com.example.rezepteapp.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<List<FilterOption>> filterOptions = new MutableLiveData<>();

    public void setFilterOptions(List<FilterOption> filterOptions) {
        this.filterOptions.setValue(filterOptions);
    }

    public LiveData<List<FilterOption>> getFilterOptions() {
        return filterOptions;
    }
}
