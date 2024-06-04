package com.example.rezepteapp.controller;

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

import com.example.rezepteapp.R;
import com.example.rezepteapp.RecipeListAdapter;
import com.example.rezepteapp.RecipeRepository;
import com.example.rezepteapp.databinding.FragmentRecipeListBinding;
import com.example.rezepteapp.model.Ingredient;
import com.example.rezepteapp.model.Label;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.model.RecipeUnit;
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

    public RecipeListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecipeListBinding.inflate(inflater, container, false);
        model = new RecipeModel(requireContext().getApplicationContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recipeList = model.getAllRecipes();

        recipeListAdapter = new RecipeListAdapter(recipeList);

        binding.recyclRecipeList.setAdapter(recipeListAdapter);
        binding.recyclRecipeList.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.fabAddRecipe.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new EditFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        binding.btnFilter.setOnClickListener(v -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new FilterFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
/*
        new RecipeRepository(requireActivity().getApplicationContext()).addRecipe(new Recipe(
                "Recipe Test1",
                null, // Replace with actual bitmap
                Arrays.asList(new Label("veggi"), new Label("ölasdkfj"), new Label("woe4f"), new Label("idsf")), // Replace with actual labels
                "10 mins",
                "20 mins",
                4,
                Arrays.asList(new Ingredient("22", RecipeUnit.EL, "Zucker"), new Ingredient("200", RecipeUnit.MILLILITRE, "Wasser"), new Ingredient("22", RecipeUnit.EL, "Mehl")), // Replace with actual ingredients
                Arrays.asList("1. Schritt", "2. Schritt"), // Replace with actual steps
                Arrays.asList("https://www.google.com", "https://www.google.cosskljf"), // Replace with actual notes
                Status.LIVE
        ));

 */
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}