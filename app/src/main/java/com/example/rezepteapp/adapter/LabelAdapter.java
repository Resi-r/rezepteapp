package com.example.rezepteapp.adapter;

import static com.example.rezepteapp.utils.Constants.CHECK_BOX;
import static com.example.rezepteapp.utils.Constants.FILTER_COUNT;
import static com.example.rezepteapp.utils.Constants.FILTER_NAME;
import static com.example.rezepteapp.utils.Constants.MY_PREFERENCES;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rezepteapp.R;
import com.example.rezepteapp.controller.FilterFragment;
import com.example.rezepteapp.model.FilterOption;
import com.example.rezepteapp.model.Label;
import com.example.rezepteapp.model.Recipe;
import com.example.rezepteapp.viewmodel.RecipeModel;

import java.util.ArrayList;
import java.util.List;

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.ViewHolder> {
    private final Context context;
    private RecipeModel model;
    ArrayList<FilterOption> filterOptions = new ArrayList<>();
    ArrayList<Label> labels = new ArrayList<>();
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkBox;
        public TextView nameLabel;

        public ViewHolder(View itemView) {
            super(itemView);

            checkBox = (CheckBox) itemView.findViewById(R.id.cb_filter_option);
            nameLabel = (TextView) itemView.findViewById(R.id.tv_filter_name);
        }
    }

    private List<Label> labelList;

    public LabelAdapter(List<Label> labelList, Context context) {
        this.labelList = labelList;
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
        FilterOption filter = filterOptions.get(position);

        holder.checkBox.setChecked(filter.isActive());
        holder.nameLabel.setText(filter.getFilterName());

        holder.checkBox.setOnCheckedChangeListener(((buttonView, isActive) -> {
            filter.setActive(isActive);
            saveLabel(filter.getFilterName());
        }));
    }

    private void saveLabel(String filterName) {
        labelList.add(new Label(filterName));
    }
    public List<Label> getLabelList() {
        return labelList;
    }
    public void setLabelList(List<Label> list) {
        this.labelList = list;
    }

    @Override
    public int getItemCount() {
        return labelList.size();
    }
}
