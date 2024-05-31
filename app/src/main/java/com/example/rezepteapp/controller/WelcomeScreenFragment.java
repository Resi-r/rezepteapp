package com.example.rezepteapp.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rezepteapp.adapter.RecipeListAdapter;
import com.example.rezepteapp.databinding.FragmentWelcomeScreenBinding;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.weather.Weather;
import com.example.rezepteapp.weather.WeatherCall;
import com.example.rezepteapp.weather.WeatherCallback;

import com.example.rezepteapp.model.RecipeModel;

import java.util.ArrayList;
import java.util.List;

public class WelcomeScreenFragment extends Fragment implements WeatherCallback {

    private FragmentWelcomeScreenBinding binding;
    private WeatherCall weatherCall;
    private List<Recipe> recipeList;
    private RecipeListAdapter recipeListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    private RecipeModel model;

    public WelcomeScreenFragment() {
        model = new RecipeModel(getContext());
        recipeList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWelcomeScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getWeather();

        recipeListAdapter = new RecipeListAdapter(recipeList);

        binding.recyclView.setAdapter(recipeListAdapter);
        binding.recyclView.setLayoutManager(new LinearLayoutManager(requireContext()));
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