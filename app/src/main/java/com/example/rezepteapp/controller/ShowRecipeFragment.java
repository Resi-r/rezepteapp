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

import com.example.rezepteapp.IngredientsAdapter;
import com.example.rezepteapp.R;
import com.example.rezepteapp.StringListAdapter;
import com.example.rezepteapp.databinding.FragmentShowRecipeBinding;
import com.example.rezepteapp.model.Ingredient;
import com.example.rezepteapp.model.Recipe;

import java.util.List;

public class ShowRecipeFragment extends Fragment {

    FragmentShowRecipeBinding binding;
    private Recipe recipe;
    private IngredientsAdapter ingredientsAdapter;
    private StringListAdapter stepsAdapter;
    private StringListAdapter notesAdapter;

    private List<Ingredient> ingredients;
    private List<String> steps;
    private List<String> notes;

    public ShowRecipeFragment() {
        // Required empty public constructor
    }

    public ShowRecipeFragment(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShowRecipeBinding.inflate(inflater, container, false);
        ingredients = recipe.getIngridients();
        steps = recipe.getSteps();
        notes = recipe.getNotes();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ingredientsAdapter = new IngredientsAdapter(ingredients);
        stepsAdapter = new StringListAdapter(steps);
        notesAdapter = new StringListAdapter(notes);

        binding.tvTitleShow.setText(recipe.getTitle());
        binding.imgTitle.setImageBitmap(recipe.getImageTitle());

        binding.tvVTimeShow.setText(recipe.getvTime());
        binding.tvKTime.setText(recipe.getkTime());
        binding.tvPeople.setText(String.valueOf(recipe.getServings()));

        binding.recyclIngridients.setAdapter(ingredientsAdapter);
        binding.recyclIngridients.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.recyclSteps.setAdapter(stepsAdapter);
        binding.recyclSteps.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.recyclNotes.setAdapter(notesAdapter);
        binding.recyclNotes.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.btnCancel.setOnClickListener(l -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        binding.btnEditRecipe.setOnClickListener(l -> {
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, new EditFragment(recipe));
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });
    }
}