package com.example.rezepteapp.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.rezepteapp.databinding.FragmentShoppinglistBinding;
import com.example.rezepteapp.model.ShoppinglistModel;

public class ShoppinglistFragment extends Fragment {
    private FragmentShoppinglistBinding binding;
    private ShoppinglistModel model;

    public ShoppinglistFragment(){
        model = new ShoppinglistModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        super.onCreateView(inflater, container, saveInstanceState);
        binding = FragmentShoppinglistBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        binding = null;
    }
}
