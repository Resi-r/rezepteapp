package com.example.rezepteapp.controller;

import static com.example.rezepteapp.utils.Constants.CHECK_BOX;
import static com.example.rezepteapp.utils.Constants.FILTER_COUNT;
import static com.example.rezepteapp.utils.Constants.FILTER_NAME;
import static com.example.rezepteapp.utils.Constants.MY_PREFERENCES;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rezepteapp.adapter.FilterAdapter;
import com.example.rezepteapp.R;
import com.example.rezepteapp.databinding.FragmentFilterBinding;
import com.example.rezepteapp.model.FilterOption;
import com.example.rezepteapp.model.SharedViewModel;

import java.util.ArrayList;
import java.util.List;


public class FilterFragment extends Fragment {
    private FragmentFilterBinding binding;
    private final List<FilterOption> filterOptionList;
    private FilterAdapter filterAdapter;
    private SharedPreferences sharedPreferences;

    public FilterFragment() {
        filterOptionList = new ArrayList<>();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().findViewById(R.id.navbar_bottom).setVisibility(View.INVISIBLE);

        loadPreferences();

        filterAdapter = new FilterAdapter(filterOptionList, requireContext());

        binding.recyclFilter.setAdapter(filterAdapter);
        binding.recyclFilter.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.btnBackFilter.setOnClickListener(v -> navigateToRecipeList());

        binding.btnAddFilter.setOnClickListener(v -> addToFilterList());
    }

    private void loadPreferences() {
        SharedPreferences sP = requireActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        filterOptionList.clear();

        int filterCount = sP.getInt(FILTER_COUNT, 0);
        for (int i = 0; i < filterCount; i++) {
            String filterName = sP.getString(FILTER_NAME + i, "Test");
            boolean isActive = sP.getBoolean(CHECK_BOX + i, false);
            filterOptionList.add(new FilterOption(filterName, isActive));
        }
    }

    private void addToFilterList() {
        String filterName = binding.tvNewFilter.getText().toString();
        filterOptionList.add(new FilterOption(filterName, false));
        filterAdapter.notifyItemInserted(filterOptionList.size());
        binding.tvNewFilter.setText("");

        savePreferences();
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(FILTER_COUNT, filterOptionList.size());
        for (int i = 0; i < filterOptionList.size(); i++) {
            editor.putString(FILTER_NAME + i, filterOptionList.get(i).getFilterName());
            editor.putBoolean(CHECK_BOX + i, filterOptionList.get(i).isActive());
        }
        editor.apply();
    }

    private void navigateToRecipeList() {
        getParentFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().findViewById(R.id.navbar_bottom).setVisibility(View.VISIBLE);
    }

    public void deactivateFilter(int index) {
        if (index >= 0 && index < filterOptionList.size()) {
            filterOptionList.get(index).setActive(false);
            filterAdapter.notifyDataSetChanged();
        }
    }

    public void removeFilter(int index) {
        if (index >= 0 && index < filterOptionList.size()) {
            filterOptionList.remove(index);
            filterAdapter.notifyItemRemoved(index);
            filterAdapter.notifyItemRangeChanged(index, filterOptionList.size());

            savePreferences();
        }
    }


}