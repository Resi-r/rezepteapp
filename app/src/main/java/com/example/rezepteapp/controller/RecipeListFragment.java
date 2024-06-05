package com.example.rezepteapp.controller;

import static com.example.rezepteapp.utils.Constants.CHECK_BOX;
import static com.example.rezepteapp.utils.Constants.FILTER_COUNT;
import static com.example.rezepteapp.utils.Constants.FILTER_NAME;
import static com.example.rezepteapp.utils.Constants.MY_PREFERENCES;

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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rezepteapp.R;
import com.example.rezepteapp.adapter.RecipeListAdapter;
import com.example.rezepteapp.databinding.FragmentRecipeListBinding;
import com.example.rezepteapp.model.FilterOption;
import com.example.rezepteapp.model.Label;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.model.Status;
import com.example.rezepteapp.viewmodel.RecipeModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RecipeListFragment extends Fragment {
    private FragmentRecipeListBinding binding;
    private RecipeModel model;
    private List<Recipe> recipeList;
    private RecipeListAdapter recipeListAdapter;
    private SharedPreferences sharedPreferences;

    public RecipeListFragment() {
        recipeList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecipeListBinding.inflate(inflater, container, false);
        model = new RecipeModel(requireContext().getApplicationContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipeList = model.getDailyRecipes();

        sharedPreferences = requireActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        recipeListAdapter = new RecipeListAdapter(recipeList, this::archiveRecipe);

        binding.recyclRecipeList.setAdapter(recipeListAdapter);
        binding.recyclRecipeList.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.fabAddRecipe.setOnClickListener(v ->navigateToAddRecipe());
        binding.btnArchive.setOnClickListener(v -> navigateToArchive());

        binding.btnFilter.setOnClickListener(v -> navigateToFilterOptions());

        binding.searchbarRecipes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                recipeListAdapter.updateRecipes(checkRecipesForCharSequences(recipeList, s));
            }

            @Override
            public void afterTextChanged(Editable s) {
                recipeListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void archiveRecipe(Recipe recipe) {
        int position = recipeList.indexOf(recipe);
        if (position != -1) {
            recipeList.remove(position);
            recipeListAdapter.notifyItemRemoved(position);
            model.archiveRecipe(recipe);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkRecipesForActiveFilters(recipeList, loadActiveFilters());
    }

    private void navigateToArchive() {

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_layout, new ArchiveFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private ArrayList<Recipe> checkRecipesForCharSequences(List<Recipe> list, CharSequence s) {
        ArrayList<Recipe> newList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            CharSequence sLowerCase = s.toString().toLowerCase();
            String titleLowerCase = list.get(i).getTitle().toLowerCase();

            if (titleLowerCase.contains(sLowerCase)) {
                newList.add(list.get(i));
            }
        }
        return newList;
    }

    private List<Recipe> checkRecipesForActiveFilters(List<Recipe> list, ArrayList<FilterOption> activeFilters) {
        if (activeFilters.isEmpty()) {
            recipeListAdapter.updateRecipes(recipeList);
            return recipeList;
        }

        ArrayList<Recipe> newList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getLabels() != null) {
                for (int j = 0; j < list.get(i).getLabels().size(); j++) {
                    for (int n = 0; n < activeFilters.size(); n++) {
                        if (list.get(i).getLabels().get(j).getName().equals(activeFilters.get(n).getFilterName())) {
                            newList.add(list.get(i));
                            break;
                        }
                    }
                }
            }
        }
        recipeListAdapter.updateRecipes(newList);

        return newList;
    }

    private ArrayList<FilterOption> loadActiveFilters() {
        ArrayList<FilterOption> activeFilters = new ArrayList<>();

        LinearLayout filterContainer = (LinearLayout) binding.horizontalScrollView.getChildAt(0);
        filterContainer.removeAllViews();

        int filterCount = sharedPreferences.getInt(FILTER_COUNT, 0);
        for (int i = 0; i < filterCount; i++) {
            String filterName = sharedPreferences.getString(FILTER_NAME + i, null);
            boolean isActive = sharedPreferences.getBoolean(CHECK_BOX + i, false);

            if (filterName != null && isActive) {
                TextView filterTag = createFilterTag(filterName, i);
                filterContainer.addView(filterTag);
                activeFilters.add(new FilterOption(filterName, isActive));
            }
        }
        return activeFilters;
    }

    private TextView createFilterTag(String filterName, int index) {
        TextView filterTag = createFilterTag(filterName);
        addFunctionToTag(index, filterTag);

        return filterTag;
    }

    private void addFunctionToTag(int index, TextView filterTag) {
        filterTag.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(CHECK_BOX + index, false);
            editor.apply();

            ((ViewGroup) filterTag.getParent()).removeView(filterTag);

            if (getActivity() != null) {
                FilterFragment filterFragment = (FilterFragment) getActivity().getSupportFragmentManager().findFragmentByTag("FilterFragment");
                if (filterFragment != null) {
                    filterFragment.deactivateFilter(index);
                }
            }
            checkRecipesForActiveFilters(recipeList, loadActiveFilters());
        });
    }

    @NonNull
    private TextView createFilterTag(String filterName) {
        TextView filterTag = new TextView(requireContext());

        filterTag.setText(filterName);
        filterTag.setTextSize(16);
        filterTag.setBackgroundResource(R.drawable.rounded_button);
        filterTag.setPadding(30, 15, 30, 15);
        filterTag.setTextColor(getResources().getColor(android.R.color.white));
        filterTag.setTypeface(Typeface.create("serif", Typeface.ITALIC));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 30, 0); // left, top, right, bottom
        filterTag.setLayoutParams(params);

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