package com.example.rezepteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rezepteapp.databinding.FragmentFilterBinding;
import com.example.rezepteapp.model.FilterOption;
import com.example.rezepteapp.model.Ingredient;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView nameFilter;
        public Button deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.cb_filter_option);
            nameFilter = (TextView) itemView.findViewById(R.id.tv_filter_name);
            deleteButton = (Button) itemView.findViewById(R.id.btn_delete_filter);
        }
    }

    private final List<FilterOption> filterList;

    public FilterAdapter(List<FilterOption> filterList) {
        this.filterList = filterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View filterView = inflater.inflate(R.layout.recycl_item_filter_options, parent, false);

        return new ViewHolder(filterView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilterOption filter = filterList.get(position);

        holder.checkBox.setChecked(false);
        holder.nameFilter.setText(filter.getFilterName());
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

}
