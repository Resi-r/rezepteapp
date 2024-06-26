package com.example.rezepteapp.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rezepteapp.adapter.WelcomeRecipeListAdapter;
import com.example.rezepteapp.databinding.FragmentWelcomeScreenBinding;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.model.Status;
import com.example.rezepteapp.viewmodel.RecipeModel;
import com.example.rezepteapp.weather.Weather;
import com.example.rezepteapp.weather.WeatherCall;
import com.example.rezepteapp.weather.WeatherCallback;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WelcomeScreenFragment extends Fragment implements WeatherCallback {

    private FragmentWelcomeScreenBinding binding;
    private WeatherCall weatherCall;
    private List<Recipe> recipeList;
    private WelcomeRecipeListAdapter welcomeRecipeListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    private RecipeModel model;

    public WelcomeScreenFragment() {
        recipeList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWelcomeScreenBinding.inflate(inflater, container, false);
        model = new RecipeModel(requireContext().getApplicationContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWeather();
        this.recipeList = model.getDailyRecipes();

        welcomeRecipeListAdapter = new WelcomeRecipeListAdapter(randomizeRecipeSuggestions(recipeList));

        binding.recyclView.setAdapter(welcomeRecipeListAdapter);
        binding.recyclView.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.btnReloadRecipes.setOnClickListener(v -> reloadRecipes());

    }

    private void reloadRecipes() {
        welcomeRecipeListAdapter.updateRecipes(randomizeRecipeSuggestions(recipeList));
    }

    public ArrayList<Recipe> randomizeRecipeSuggestions(List<Recipe> list) {

        ArrayList<Recipe> randomizedList = new ArrayList<>();
        Set<Integer> selectedIndices = new HashSet<>();

        int numberOfRecipesToSelect = Math.min(5, list.size());

        while (randomizedList.size() < numberOfRecipesToSelect) {
            int randomIndex = (int) (Math.random() * list.size());
            if (!selectedIndices.contains(randomIndex)) {
                selectedIndices.add(randomIndex);
                randomizedList.add(list.get(randomIndex));
            }
        }

        return randomizedList;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (weatherCall != null) {
            weatherCall.resumeThread();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (weatherCall != null) {
            weatherCall.pauseThread();
        }
    }


    private void init() {
        weatherCall = new WeatherCall(this.requireActivity(), this);
    }

    private void getWeather() {
        weatherCall.startThread();
        System.out.println(weatherCall.getCurrentWeather().getTemp());
    }

    @Override
    public void onWeatherCallback(Weather weather) {
        if (isAdded() && getActivity() != null) {
            getActivity().runOnUiThread(() -> {
                if (binding != null) {
                    binding.tvWeatherDegrees.setText(weather.getTemp());
                    binding.imgWeather.setImageBitmap(weather.getIcon());
                }
            });
        }
    }
}