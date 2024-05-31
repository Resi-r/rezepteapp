package com.example.rezepteapp.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rezepteapp.R;
import com.example.rezepteapp.adapter.RecipeListAdapter;
import com.example.rezepteapp.databinding.FragmentRecipeListBinding;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.model.RecipeModel;

import java.util.ArrayList;
import java.util.List;


public class RecipeListFragment extends Fragment {

    private FragmentRecipeListBinding binding;
    private RecipeModel model;
    private List<Recipe> recipeList;
    private RecipeListAdapter recipeListAdapter;
    private SharedPreferences sharedPreferences;

    public RecipeListFragment() {
        model = new RecipeModel(getContext());
        recipeList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecipeListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        recipeListAdapter = new RecipeListAdapter(recipeList);

        binding.recyclRecipeList.setAdapter(recipeListAdapter);
        binding.recyclRecipeList.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.fabAddRecipe.setOnClickListener(v ->navigateToAddRecipe());

        binding.btnFilter.setOnClickListener(v -> navigateToFilterOptions());
        
        loadActiveFilters();
    }

    private void loadActiveFilters() {
        LinearLayout filterContainer = (LinearLayout) binding.horizontalScrollView.getChildAt(0);
        filterContainer.removeAllViews();

        int filterCount = sharedPreferences.getInt("filter_count", 0);
        for (int i = 0; i < filterCount; i++) {
            String filterName = sharedPreferences.getString("filterName_" + i, null);
            boolean isActive = sharedPreferences.getBoolean("checkBox_" + i, false);

            if (filterName != null && isActive) {
                TextView filterTag = createFilterTag(filterName, i);
                filterContainer.addView(filterTag);
            }
        }
    }

    private TextView createFilterTag(String filterName, int index) {
        TextView filterTag = new TextView(requireContext());
        filterTag.setText(filterName);
        filterTag.setTextSize(16);
        filterTag.setBackgroundResource(R.drawable.rounded_button);
        filterTag.setPadding(30, 15, 30, 15);
        filterTag.setTextColor(getResources().getColor(android.R.color.white));

        // Setzen der Schriftart auf Serif
        filterTag.setTypeface(Typeface.create("serif", Typeface.ITALIC));

        // LayoutParams mit Margin
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 30, 0); // left, top, right, bottom
        filterTag.setLayoutParams(params);

        // OnClickListener hinzufügen, um den Filter zu deaktivieren
        filterTag.setOnClickListener(v -> {
            // Deaktivieren des entsprechenden Filters
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("checkBox_" + index, false);
            editor.apply();

            // Entfernen des Filter-Tags aus der Ansicht
            ((ViewGroup) filterTag.getParent()).removeView(filterTag);

            // Deaktivieren des Filters in den Filteroptionen
            if (getActivity() != null) {
                FilterFragment filterFragment = (FilterFragment) getActivity().getSupportFragmentManager().findFragmentByTag("FilterFragment");
                if (filterFragment != null) {
                    filterFragment.deactivateFilter(index);
                }
            }
        });

        return filterTag;
    }



    private void navigateToFilterOptions() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new FilterFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToAddRecipe() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new EditFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}