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

import com.example.rezepteapp.adapter.FilterAdapter;
import com.example.rezepteapp.R;
import com.example.rezepteapp.databinding.FragmentFilterBinding;
import com.example.rezepteapp.model.FilterOption;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FilterFragment extends Fragment {

    private FragmentFilterBinding binding;
    private List<FilterOption> filterOptionList;
    private FilterAdapter filterAdapter;

    public FilterFragment() {
        filterOptionList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFilterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().findViewById(R.id.navbar_bottom).setVisibility(View.INVISIBLE);

        filterAdapter = new FilterAdapter(filterOptionList);

        binding.recyclFilter.setAdapter(filterAdapter);
        binding.recyclFilter.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.btnBackFilter.setOnClickListener(v -> navigateToRecipeList());

        binding.btnAddFilter.setOnClickListener(v -> addToFilterList());
    }

    private void addToFilterList() {
        String filterName = binding.tvNewFilter.getText().toString();
        filterOptionList.add(new FilterOption(filterName, false));
        filterAdapter.notifyItemInserted(filterOptionList.size());
        binding.tvNewFilter.setText("");
    }

    private void navigateToRecipeList() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new RecipeListFragment());
        fragmentManager.popBackStack();
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().findViewById(R.id.navbar_bottom).setVisibility(View.VISIBLE);
    }
}