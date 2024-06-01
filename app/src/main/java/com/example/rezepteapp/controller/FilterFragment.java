package com.example.rezepteapp.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

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

    public static final String MY_PREFERENCES = "MyPreferences";
    private FragmentFilterBinding binding;
    private List<FilterOption> filterOptionList;
    private FilterAdapter filterAdapter;
    private SharedPreferences sharedPreferences;

    public FilterFragment() {
        filterOptionList = new ArrayList<>();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
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

        loadPreferences();

        filterAdapter = new FilterAdapter(filterOptionList, requireContext());

        binding.recyclFilter.setAdapter(filterAdapter);
        binding.recyclFilter.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.btnBackFilter.setOnClickListener(v -> navigateToRecipeList());

        binding.btnAddFilter.setOnClickListener(v -> addToFilterList());
    }

    private void loadPreferences() {
        SharedPreferences sP = requireActivity().getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        filterOptionList.clear(); // Sicherstellen, dass die Liste leer ist, bevor sie befüllt wird

        int filterCount = sP.getInt("filter_count", 0);
        for (int i = 0; i < filterCount; i++) {
            String filterName = sP.getString("filterName_" + i, "Test");
            boolean isActive = sP.getBoolean("checkBox_" + i, false);
            filterOptionList.add(new FilterOption(filterName, isActive));
        }
    }

    private void addToFilterList() {
        String filterName = binding.tvNewFilter.getText().toString();
        filterOptionList.add(new FilterOption(filterName, false));
        filterAdapter.notifyItemInserted(filterOptionList.size());
        binding.tvNewFilter.setText("");

        savePreferences();
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("filter_count", filterOptionList.size());
        for (int i = 0; i < filterOptionList.size(); i++) {
            editor.putString("filterName_" + i, filterOptionList.get(i).getFilterName());
            editor.putBoolean("checkBox_" + i, filterOptionList.get(i).isActive());
        }
        editor.apply();
    }

    private void navigateToRecipeList() {
        getParentFragmentManager().popBackStack();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().findViewById(R.id.navbar_bottom).setVisibility(View.VISIBLE);
    }

    public void deactivateFilter(int index) {
        if (index >= 0 && index < filterOptionList.size()) {
            filterOptionList.get(index).setActive(false);
            filterAdapter.notifyDataSetChanged();
        }
    }

    public void removeFilter(int index) {
        if (index >= 0 && index < filterOptionList.size()) {
            filterOptionList.remove(index);
            filterAdapter.notifyItemRemoved(index);
            filterAdapter.notifyItemRangeChanged(index, filterOptionList.size());

            savePreferences();
        }
    }


}