package com.example.rezepteapp.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.rezepteapp.adapter.ShoppinglistDoneAdapter;
import com.example.rezepteapp.adapter.ShoppinglistToDoAdapter;
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

    public ShoppinglistFragment(){
        model = new RecipeModel(getContext());
        //toDoList = model.getToDoList();
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
        adapterToDo = new ShoppinglistToDoAdapter(toDoList, entry -> {
            toDoList.remove(entry);
            doneList.add(entry);
            adapterToDo.removeEntry(entry);
            adapterDone.notifyItemInserted(doneList.size() - 1);
        });
        adapterDone = new ShoppinglistDoneAdapter(doneList);

        binding.btnAddItem.setOnClickListener(v -> {});
        binding.recycleListEntryToDo.setAdapter(adapterToDo);
        binding.recycleListEntryToDo.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.recycleListEntryDone.setAdapter(adapterDone);
        binding.recycleListEntryDone.setLayoutManager(new LinearLayoutManager(requireContext()));

    }

    @Override
    public void onPause() {
        super.onPause();
//        model.updateToDoList(toDoList);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        binding = null;
    }
}
