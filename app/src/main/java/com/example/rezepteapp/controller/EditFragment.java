package com.example.rezepteapp.controller;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rezepteapp.adapter.IngredientsAdapter;
import com.example.rezepteapp.R;
import com.example.rezepteapp.RecipeRepository;
import com.example.rezepteapp.StringListAdapter;
import com.example.rezepteapp.databinding.FragmentEditBinding;
import com.example.rezepteapp.model.Ingredient;
import com.example.rezepteapp.model.Label;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.viewmodel.RecipeModel;
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

    private Recipe recipe;

    public EditFragment() {
    }

    public EditFragment(Recipe recipe) {
        this.recipe = recipe;
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
        model = new RecipeModel(requireContext().getApplicationContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (recipe != null) {
            setRecipe(recipe);
        }

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
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 123, null);
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
            addRecipeToDatabase(recipe);
            getParentFragmentManager().popBackStack();
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

    private boolean addRecipeToDatabase(Recipe recipe) {

        String title = binding.tvTitle.getText().toString();
        Drawable imageTitle = binding.imgTitle.getDrawable();
        String vTime = binding.tvVTime.getText().toString();
        String kTime = binding.tvKTime.getText().toString();

        if (recipe == null) {
            recipe = new Recipe();
        }

        if (binding.tvPeople.getText().toString().matches("^[0-9]*$")) {
            int people = Integer.parseInt(binding.tvPeople.getText().toString());
            recipe.setServings(people);
        }

        recipe.setTitle(title);
        recipe.setImageTitle(drawableToBitmap(imageTitle));
        recipe.setvTime(vTime);
        recipe.setkTime(kTime);
        recipe.setIngridients(ingredients);
        recipe.setSteps(steps);
        recipe.setNotes(notes);
        recipe.setStatus(Status.LIVE);

        model.addRecipeToDatabase(recipe);
        System.out.println("Added Recipe");
        return true;
    }

    public Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            binding.imgTitle.setImageURI(selectedImageUri);
        }
    }

    private void setRecipe(Recipe recipe) {
        binding.tvTitle.setText(recipe.getTitle());
        binding.imgTitle.setImageBitmap(recipe.getImageTitle());

        binding.tvVTime.setText(recipe.getvTime());
        binding.tvKTime.setText(recipe.getkTime());
        binding.tvPeople.setText(String.valueOf(recipe.getServings()));

        ingredients.addAll(recipe.getIngridients());
        labels.addAll(recipe.getLabels());
        steps.addAll(recipe.getSteps());
        notes.addAll(recipe.getNotes());

    }
}