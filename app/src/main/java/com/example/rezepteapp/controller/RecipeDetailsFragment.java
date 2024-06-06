package com.example.rezepteapp.controller;

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
import com.example.rezepteapp.adapter.IngredientsAdapter;
import com.example.rezepteapp.adapter.StringListAdapter;
import com.example.rezepteapp.databinding.FragmentRecipeDetailsBinding;
import com.example.rezepteapp.model.Ingredient;
import com.example.rezepteapp.model.Label;
import com.example.rezepteapp.model.Recipe;

import java.util.List;

public class RecipeDetailsFragment extends Fragment {

    FragmentRecipeDetailsBinding binding;
    private Recipe recipe;
    private IngredientsAdapter ingredientsAdapter;
    private StringListAdapter stepsAdapter;
    private StringListAdapter notesAdapter;

    private List<Ingredient> ingredients;
    private List<String> steps;

    public RecipeDetailsFragment() {}

    public RecipeDetailsFragment(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false);
        ingredients = recipe.getIngridients();
        steps = recipe.getSteps();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ingredientsAdapter = new IngredientsAdapter(ingredients);
        stepsAdapter = new StringListAdapter(steps);

        binding.tvTitle.setText(recipe.getTitle());
        binding.imgTitle.setImageBitmap(recipe.getImageTitle());

        binding.tvVTime.setText(recipe.getvTime());
        binding.tvKTime.setText(recipe.getkTime());
        binding.tvPeople.setText(String.valueOf(recipe.getServings()));

        binding.recyclIngridients.setAdapter(ingredientsAdapter);
        binding.recyclIngridients.setLayoutManager(new LinearLayoutManager(requireContext()));

        setLabels(recipe);

        binding.recyclSteps.setAdapter(stepsAdapter);
        binding.recyclSteps.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.btnBack.setOnClickListener(l -> {
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

    private void setLabels(Recipe recipe) {
        for (int i = 0; i < recipe.getLabels().size(); i++) {

            System.out.println("Recipe Label Name: " + recipe.getLabels().get(i).getName());

            TextView filterTag = new TextView(requireContext());

            filterTag.setText(recipe.getLabels().get(i).getName());
            filterTag.setTextSize(16);
            filterTag.setBackgroundResource(R.drawable.rounded_button);
            filterTag.setPadding(30, 15, 30, 15);
            filterTag.setTextColor(getResources().getColor(android.R.color.white));
            filterTag.setTypeface(Typeface.create("serif", Typeface.ITALIC));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 30, 0); // left, top, right, bottom
            filterTag.setLayoutParams(params);

            binding.linearLayoutLabel.addView(filterTag);
        }
    }
}