package com.example.rezepteapp.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rezepteapp.R;
import com.example.rezepteapp.ShoppinglistDoneAdapter;
import com.example.rezepteapp.ShoppinglistToDoAdapter;
import com.example.rezepteapp.databinding.FragmentShoppinglistBinding;
import com.example.rezepteapp.model.RecipeModel;
import com.example.rezepteapp.model.ShoppinglistEntry;

import java.util.ArrayList;
import java.util.List;

public class ShoppinglistFragment extends Fragment {
    private FragmentShoppinglistBinding binding;
    private RecipeModel model;
    private ShoppinglistToDoAdapter adapterToDo;
    private ShoppinglistDoneAdapter adapterDone;
    private List<ShoppinglistEntry> toDoList;
    private List<ShoppinglistEntry> doneList;
    private boolean isToDoExpanded = true;
    private boolean isDoneExpanded = true;

    public ShoppinglistFragment(){
        model = new RecipeModel(getContext());
        toDoList = new ArrayList<>();
        doneList = new ArrayList<>();
        this.updateLists();
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
        adapterToDo = new ShoppinglistToDoAdapter(toDoList, entry -> {
            toDoList.remove(entry);
            doneList.add(entry);
            adapterToDo.removeEntry(entry);
            adapterDone.notifyItemInserted(doneList.size() - 1);
        });
        adapterDone = new ShoppinglistDoneAdapter(doneList);

        binding.ddIngredientsToGet.setOnClickListener(v -> {
            if (isToDoExpanded) {
                binding.recycleListEntryToDo.setVisibility(View.GONE);
                binding.ddIngredientsToGet.setBackgroundResource(R.drawable.baseline_arrow_drop_up_24);
            } else {
                binding.recycleListEntryToDo.setVisibility(View.VISIBLE);
                binding.ddIngredientsToGet.setBackgroundResource(R.drawable.baseline_arrow_drop_down_24);
            }
        });
        binding.ddIngredientsDone.setOnClickListener(v -> {
            if (isDoneExpanded) {
                binding.recycleListEntryDone.setVisibility(View.GONE);
                binding.ddIngredientsDone.setBackgroundResource(R.drawable.baseline_arrow_drop_up_24);
            } else {
                binding.recycleListEntryDone.setVisibility(View.VISIBLE);
                binding.ddIngredientsDone.setBackgroundResource(R.drawable.baseline_arrow_drop_down_24);
            }
        });

        binding.btAddItemToList.setOnClickListener(v -> {
            if (addToToDoListFieldsFilled()) {
                model.addNewShoppinglistEntry(binding.etAddIngredient.getText().toString(), binding.etAddQuantity.getText().toString(), binding.spinnerAddUnit.getSelectedItem().toString());
                this.updateLists();
            } else {
                Toast.makeText(getContext(), "Bitte alle Felder füllen", Toast.LENGTH_SHORT).show();
            }
        });

        binding.recycleListEntryToDo.setAdapter(adapterToDo);
        binding.recycleListEntryToDo.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.recycleListEntryDone.setAdapter(adapterDone);
        binding.recycleListEntryDone.setLayoutManager(new LinearLayoutManager(requireContext()));

    }

    private boolean addToToDoListFieldsFilled() {
        return binding.etAddIngredient.getText() != null && binding.etAddQuantity.getText() != null && binding.spinnerAddUnit.getSelectedItem() != null;
    }

    private void updateLists() {
        this.toDoList.clear();
        this.toDoList.addAll(this.model.getToDoList());
        this.doneList.clear();
        this.doneList.addAll(this.model.getDoneList());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        binding = null;
    }
}
