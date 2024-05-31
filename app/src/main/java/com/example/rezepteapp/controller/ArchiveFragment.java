package com.example.rezepteapp.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.rezepteapp.R;
import com.example.rezepteapp.databinding.FragmentArchiveBinding;
import com.example.rezepteapp.model.RecipeModel;

public class ArchiveFragment extends Fragment {
    private FragmentArchiveBinding binding;
    private RecipeModel model;

    public ArchiveFragment() {
        model = new RecipeModel(getContext());
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

        binding.btnBack.setOnClickListener(v -> navigateToWelcomeScreen());
    }

    private void navigateToWelcomeScreen() {
        getParentFragmentManager().popBackStack();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        binding = null;
    }
}
