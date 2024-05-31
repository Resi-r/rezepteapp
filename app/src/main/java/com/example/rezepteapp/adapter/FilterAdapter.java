package com.example.rezepteapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rezepteapp.R;
import com.example.rezepteapp.model.FilterOption;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {

    private final Context context;

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

    public FilterAdapter(List<FilterOption> filterList, Context context) {
        this.filterList = filterList;
        this.context = context;
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

        holder.checkBox.setChecked(filter.isActive());
        holder.nameFilter.setText(filter.getFilterName());

        holder.checkBox.setOnCheckedChangeListener(((buttonView, isActive) -> {
            filter.setActive(isActive);
            savePreferences();
        }));

        holder.deleteButton.setOnClickListener(v -> removeFilter(holder.getAdapterPosition()));
    }


    private void savePreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("filter_count", filterList.size());
        for (int i = 0; i < filterList.size(); i++) {
            editor.putString("filterName_" + i, filterList.get(i).getFilterName());
            editor.putBoolean("checkBox_" + i, filterList.get(i).isActive());
        }
        editor.apply();
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public void removeFilter(int position) {
        if (position >= 0 && position < filterList.size()) {
            filterList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, filterList.size());

            savePreferences();
        }
    }


}
