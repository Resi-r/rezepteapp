package com.example.rezepteapp.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rezepteapp.R;
import com.example.rezepteapp.ShoppinglistDoneAdapter;
import com.example.rezepteapp.ShoppinglistToDoAdapter;
import com.example.rezepteapp.databinding.FragmentShoppinglistBinding;
import com.example.rezepteapp.model.RecipeModel;
import com.example.rezepteapp.model.RecipeUnit;
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
        toDoList = new ArrayList<>();
        doneList = new ArrayList<>();
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
        model = new RecipeModel(requireContext());
        this.updateLists();
        adapterToDo = new ShoppinglistToDoAdapter(toDoList, entry -> moveToDoneList(entry), entry -> removeToDoEntry(entry));
        adapterDone = new ShoppinglistDoneAdapter(doneList, entry -> moveToToDoList(entry), entry -> removeDoneEntry(entry));

        binding.ddIngredientsToGet.setOnClickListener(v -> {
            if (isToDoExpanded) {
                binding.recycleListEntryToDo.setVisibility(View.GONE);
                binding.ddIngredientsToGet.setBackgroundResource(R.drawable.baseline_arrow_drop_up_24);
            } else {
                binding.recycleListEntryToDo.setVisibility(View.VISIBLE);
                binding.ddIngredientsToGet.setBackgroundResource(R.drawable.baseline_arrow_drop_down_24);
            }
            isToDoExpanded = !isToDoExpanded;
        });
        binding.ddIngredientsDone.setOnClickListener(v -> {
            if (isDoneExpanded) {
                binding.recycleListEntryDone.setVisibility(View.GONE);
                binding.ddIngredientsDone.setBackgroundResource(R.drawable.baseline_arrow_drop_up_24);
            } else {
                binding.recycleListEntryDone.setVisibility(View.VISIBLE);
                binding.ddIngredientsDone.setBackgroundResource(R.drawable.baseline_arrow_drop_down_24);
            }
            isDoneExpanded = !isDoneExpanded;
        });

        binding.btAddItemToList.setOnClickListener(v -> {
            if (addToToDoListFieldsFilled()) {
                ShoppinglistEntry newEntry = model.addNewShoppinglistEntry(
                        binding.etAddIngredient.getText().toString(),
                        binding.etAddQuantity.getText().toString(),
                        binding.spinnerAddUnit.getSelectedItem().toString()
                );
                if (newEntry != null) {
                    toDoList.add(newEntry);
                    adapterToDo.notifyItemInserted(toDoList.size() - 1);
                }
            } else {
                Toast.makeText(getContext(), "Bitte alle Felder füllen", Toast.LENGTH_SHORT).show();
            }
        });

        binding.recycleListEntryToDo.setAdapter(adapterToDo);
        binding.recycleListEntryToDo.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.recycleListEntryDone.setAdapter(adapterDone);
        binding.recycleListEntryDone.setLayoutManager(new LinearLayoutManager(requireContext()));

        RecipeUnit[] units = RecipeUnit.values();
        ArrayAdapter<RecipeUnit> unitArrayAdapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, units);
        unitArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinnerAddUnit.setAdapter(unitArrayAdapter);

    }

    private boolean addToToDoListFieldsFilled() {
        String ingredient = binding.etAddIngredient.getText().toString().trim();
        String quantity = binding.etAddQuantity.getText().toString().trim();
        Object selectedUnit = binding.spinnerAddUnit.getSelectedItem();

        return !ingredient.isEmpty() && !quantity.isEmpty() && selectedUnit != null;
    }


    private void updateLists() {
        this.toDoList.clear();
        this.toDoList.addAll(this.model.getToDoList());
        this.doneList.clear();
        this.doneList.addAll(this.model.getDoneList());
    }

    private void moveToDoneList(ShoppinglistEntry entry) {
        int position = toDoList.indexOf(entry);
        if (position != -1) {
            toDoList.remove(position);
            adapterToDo.notifyItemRemoved(position);
            doneList.add(entry);
            adapterDone.notifyItemInserted(doneList.size() - 1);
            model.updateDoneList(entry);
        }
    }

    private void moveToToDoList(ShoppinglistEntry entry) {
        int position = doneList.indexOf(entry);
        if (position != -1) {
            doneList.remove(position);
            adapterDone.notifyItemRemoved(position);
            toDoList.add(entry);
            adapterToDo.notifyItemInserted(toDoList.size() - 1);
            model.updateTodoList(entry);
        }
    }
    private void removeDoneEntry(ShoppinglistEntry entry) {
        int position = doneList.indexOf(entry);
        if (position != -1) {
            doneList.remove(position);
            adapterDone.notifyItemRemoved(position);
            model.deleteShoppinglistEntry(entry);
        }
    }
    private void removeToDoEntry(ShoppinglistEntry entry) {
        int position = doneList.indexOf(entry);
        if (position != -1) {
            doneList.remove(position);
            adapterDone.notifyItemRemoved(position);
            model.deleteShoppinglistEntry(entry);
        }
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
