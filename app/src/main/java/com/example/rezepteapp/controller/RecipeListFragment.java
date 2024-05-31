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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}