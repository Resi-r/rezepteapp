package com.example.rezepteapp.controller;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rezepteapp.IngredientsAdapter;
import com.example.rezepteapp.R;
import com.example.rezepteapp.StringListAdapter;
import com.example.rezepteapp.databinding.FragmentEditBinding;
import com.example.rezepteapp.model.Ingredient;
import com.example.rezepteapp.model.Label;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.model.RecipeModel;
import com.example.rezepteapp.model.RecipeUnit;
import com.example.rezepteapp.model.Status;

import java.util.ArrayList;
import java.util.List;


public class EditFragment extends Fragment {

    private FragmentEditBinding binding;

    private RecipeModel model;

    private IngredientsAdapter ingredientsAdapter;
    private StringListAdapter stepsAdapter;
    private StringListAdapter notesAdapter;

    //---
    private List<Label> labels;
    private List<Ingredient> ingredients;
    private List<String> steps;
    private List<String> notes;

    public EditFragment() {
        model = new RecipeModel(getContext());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // auslagern!
        labels = new ArrayList<>();
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
        notes = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().findViewById(R.id.navbar_bottom).setVisibility(View.GONE);

        ingredientsAdapter = new IngredientsAdapter(ingredients);
        stepsAdapter = new StringListAdapter(steps);
        notesAdapter = new StringListAdapter(notes);

        binding.recyclIngridients.setAdapter(ingredientsAdapter);
        binding.recyclIngridients.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.recyclSteps.setAdapter(stepsAdapter);
        binding.recyclSteps.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.recyclNotes.setAdapter(notesAdapter);
        binding.recyclNotes.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.spinnerUnits.setAdapter(new ArrayAdapter<RecipeUnit>(requireContext(), android.R.layout.simple_spinner_item, RecipeUnit.values()));


        binding.imgTitle.setOnClickListener(v -> {
            // load image from gallery or camera (needs permission)
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivity(intent);
        });

        binding.btnAddLabel.setOnClickListener(v -> {
            // open popup with edittext and add button
        });

        binding.btnAddIngridient.setOnClickListener(v -> {
            addToIngredientList();
        });

        binding.editAddIngridientName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addToIngredientList();
                return true;
            }
            return false;
        });

        binding.btnAddStep.setOnClickListener(v -> {
            addToStepList();
        });

        binding.editAddStep.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                addToStepList();
                return true;
            }
            return false;
        });

        binding.imgBtnAddNote.setOnClickListener(v -> {

        });

        binding.btnCancel.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        binding.btnAddRecipe.setOnClickListener(v -> {
            addRecipeToDatabase();
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().findViewById(R.id.navbar_bottom).setVisibility(View.VISIBLE);
    }

    private void addToIngredientList() {
        String amount = binding.editAddIngredientAmount.getText().toString();
        RecipeUnit unit = (RecipeUnit) binding.spinnerUnits.getSelectedItem();
        String name = binding.editAddIngridientName.getText().toString();
        ingredients.add(new Ingredient(amount, unit, name));
        ingredientsAdapter.notifyItemInserted(ingredients.size());
    }

    private void addToStepList() {
        String step = binding.editAddStep.getText().toString();
        steps.add(step);
        stepsAdapter.notifyItemInserted(steps.size());
    }

    private boolean addRecipeToDatabase() {
        String title = binding.tvTitle.getText().toString();
        Drawable imageTitle = binding.imgTitle.getDrawable();
        String vTime = binding.tvVTime.getText().toString();
        String kTime = binding.tvKTime.getText().toString();
        int people = Integer.parseInt(binding.tvPeople.getText().toString());

        Recipe recipe = new Recipe(title, ((BitmapDrawable) imageTitle).getBitmap(), labels, vTime, kTime, people, ingredients, steps, notes, Status.LIVE);

        return true;
    }

    private void setLabels(List<String> labels) {

        labels.forEach(string -> {
            TextView textView = new TextView(requireContext());
            textView.setText(string);
            binding.linearLayoutLabel.addView(textView);
        });
    }

    private boolean isValidStringEntry(String enteredText) {
        return !(enteredText.length() <= 1);
    }
}