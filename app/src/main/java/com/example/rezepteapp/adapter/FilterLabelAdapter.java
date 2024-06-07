package com.example.rezepteapp.adapter;

import static com.example.rezepteapp.utils.Constants.CHECK_BOX;
import static com.example.rezepteapp.utils.Constants.FILTER_COUNT;
import static com.example.rezepteapp.utils.Constants.FILTER_NAME;
import static com.example.rezepteapp.utils.Constants.MY_PREFERENCES;

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

public class FilterLabelAdapter extends RecyclerView.Adapter<FilterLabelAdapter.ViewHolder> {
    private final Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView nameFilter;

        public ViewHolder(View itemView) {
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.cb_filter_option);
            nameFilter = (TextView) itemView.findViewById(R.id.tv_filter_name);
        }
    }

    private final List<FilterOption> filterList;

    public FilterLabelAdapter(List<FilterOption> filterList, Context context) {
        this.filterList = filterList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View filterView = inflater.inflate(R.layout.recycl_item_label_options, parent, false);

        return new ViewHolder(filterView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FilterOption filter = filterList.get(position);

        holder.checkBox.setChecked(filter.isActive());
        holder.nameFilter.setText(filter.getFilterName());

        holder.checkBox.setOnCheckedChangeListener(((buttonView, isActive) -> {
            filter.setActive(isActive);
        }));
    }

    public List<FilterOption> getFilterOptions() {
        return filterList;
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
        }
    }


}
