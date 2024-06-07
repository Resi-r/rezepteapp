package com.example.rezepteapp.controller;

import static com.example.rezepteapp.utils.Constants.CHECK_BOX;
import static com.example.rezepteapp.utils.Constants.FILTER_COUNT;
import static com.example.rezepteapp.utils.Constants.FILTER_NAME;
import static com.example.rezepteapp.utils.Constants.MY_PREFERENCES;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rezepteapp.adapter.FilterAdapter;
import com.example.rezepteapp.adapter.FilterLabelAdapter;
import com.example.rezepteapp.adapter.IngredientsAdapter;
import com.example.rezepteapp.R;
import com.example.rezepteapp.adapter.StringListAdapter;
import com.example.rezepteapp.databinding.FragmentEditBinding;
import com.example.rezepteapp.model.FilterOption;
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
    private FilterAdapter filterAdapter;
    private FilterLabelAdapter filterLabelAdapter;
    private List<FilterOption> filterOptionList;
    private SharedPreferences sharedPreferences;

    //---
    private List<Label> labels;
    private List<Ingredient> ingredients;
    private List<String> steps;
    private List<String> notes;
    private Recipe recipe;

    public EditFragment() {
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
        filterOptionList = new ArrayList<>();
        labels = new ArrayList<>();
    }

    public EditFragment(Recipe recipe) {
        this();
        this.recipe = recipe;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredients = new ArrayList<>();
        steps = new ArrayList<>();
        notes = new ArrayList<>();
        sharedPreferences = requireActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
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

        loadPreferences();

        filterAdapter = new FilterAdapter(filterOptionList, requireContext());
        filterLabelAdapter = new FilterLabelAdapter(filterOptionList, requireContext());

        binding.recyclIngridients.setAdapter(ingredientsAdapter);
        binding.recyclIngridients.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.recyclSteps.setAdapter(stepsAdapter);
        binding.recyclSteps.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.spinnerUnits.setAdapter(new ArrayAdapter<RecipeUnit>(requireContext(), android.R.layout.simple_spinner_item, RecipeUnit.values()));

        binding.imgTitle.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 123, null);
        });

        binding.btnAddLabel.setOnClickListener(v -> {
            openLabelPopUp();
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

        binding.btnCancel.setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        binding.btnAddRecipe.setOnClickListener(v -> {
            addRecipe();
        });

    }

    private void openLabelPopUp() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_with_checkbox, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.requireActivity());
        builder.setTitle("Verfügbare Labels");
        builder.setView(dialogView);

        RecyclerView recyclerView = dialogView.findViewById(R.id.recycl_filter_labels_edit_recipe);

        recyclerView.setAdapter(filterLabelAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                setLabels(setFiltersAsLabels());
            }
        });

        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // Abbrechen-Taste geklickt
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private List<Label> setFiltersAsLabels() {
        labels.clear();
        for (int i = 0; i < filterOptionList.size(); i++) {
            if (filterOptionList.get(i).isActive()) {
                labels.add(new Label(filterOptionList.get(i).getFilterName()));
            }
        }
        return labels;
    }

    private void loadPreferences() {
        SharedPreferences sP = requireActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        filterOptionList.clear();

        int filterCount = sP.getInt(FILTER_COUNT, 0);
        for (int i = 0; i < filterCount; i++) {
            String filterName = sP.getString(FILTER_NAME + i, "Test");
            boolean isActive = sP.getBoolean(CHECK_BOX + i, false);
            filterOptionList.add(new FilterOption(filterName, isActive));
            System.out.println("LoadPreferences: " + filterOptionList.get(i).getFilterName());
        }
    }

    private void addRecipe() {
        if (ingredientsAdapter.getItemCount() == 0) {
            Toast.makeText(this.requireActivity(), "Bitte gib mind. eine Zutat an!", Toast.LENGTH_LONG).show();
            return;
        }
        addRecipeToDatabase(recipe);
        getParentFragmentManager().popBackStack();
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

        if (binding.tvPeople.getText().toString().matches("^[0-9]*$") && !binding.tvPeople.getText().toString().isEmpty()) {
            int people = Integer.parseInt(binding.tvPeople.getText().toString());
            recipe.setServings(people);
        }

        recipe.setTitle(title);
        recipe.setImageTitle(drawableToBitmap(imageTitle));
        recipe.setLabels(labels);
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
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        System.out.println("drawable: " + drawable);

        if (drawable != null) {
            if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
                bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        }
        return null;
    }

    private void setLabels(List<Label> labels) {
        binding.linearLayoutLabel.removeAllViews();

        for (int i = 0; i < labels.size(); i++) {
            TextView filterTag = new TextView(requireContext());

            filterTag.setText(labels.get(i).getName());
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
        if (!labels.isEmpty()) {
            recipe.setLabels(labels);
        }
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