package com.example.rezepteapp.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rezepteapp.R;
import com.example.rezepteapp.adapter.ArchivedRecipeAdapter;
import com.example.rezepteapp.databinding.FragmentArchiveBinding;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.viewmodel.RecipeModel;

import java.util.List;

public class ArchiveFragment extends Fragment {
    private FragmentArchiveBinding binding;
    private RecipeModel model;
    private ArchivedRecipeAdapter adapter;
    private List<Recipe> recipes;

    public ArchiveFragment() {}

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        model = new RecipeModel(getContext());
        recipes = model.getArchivedRecipes();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        super.onCreateView(inflater, container, saveInstanceState);

        binding = FragmentArchiveBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);

        getActivity().findViewById(R.id.navbar_bottom).setVisibility(View.INVISIBLE);

        adapter = new ArchivedRecipeAdapter(recipes, this::onRevertArchiving, this::onDeleteRecipe);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.btnBack.setOnClickListener(v -> navigateToRecipeList());
    }

    private void navigateToRecipeList() {
        getParentFragmentManager().popBackStack();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        binding = null;
    }

    public void onDeleteRecipe(Recipe recipe) {
        int position = recipes.indexOf(recipe);
        if (position != -1) {
            recipes.remove(position);
            adapter.notifyItemRemoved(position);
            model.deleteRecipe(recipe);
        }
    }

    public void onRevertArchiving(Recipe recipe) {
        int position = recipes.indexOf(recipe);
        if (position != -1) {
            recipes.remove(position);
            adapter.notifyItemRemoved(position);
            model.revertArchivation(recipe);
        }
    }
}
