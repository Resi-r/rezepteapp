package com.example.rezepteapp.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rezepteapp.ShoppinglistDoneAdapter;
import com.example.rezepteapp.ShoppinglistToDoAdapter;
import com.example.rezepteapp.databinding.FragmentShoppinglistBinding;
import com.example.rezepteapp.viewmodel.RecipeModel;
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


    public ShoppinglistFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        super.onCreateView(inflater, container, saveInstanceState);
        binding = FragmentShoppinglistBinding.inflate(inflater, container, false);
        model = new RecipeModel(requireContext().getApplicationContext());
        toDoList = model.getToDoList();
        doneList = new ArrayList<>();
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


        binding.recycleListEntryToDo.setAdapter(adapterToDo);
        binding.recycleListEntryToDo.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.recycleListEntryDone.setAdapter(adapterDone);
        binding.recycleListEntryDone.setLayoutManager(new LinearLayoutManager(requireContext()));
/*
        binding.btAddNewItemToList.setOnClickListener(v -> {
            String amount = binding.inputQuantityDescription.getText().toString();
            String item = binding.inputItemDescription.getText().toString();
            model.addToTodoList(amount, item);
        });

 */
    }

    @Override
    public void onPause() {
        super.onPause();
        model.updateToDoList(toDoList);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        binding = null;
    }
}
