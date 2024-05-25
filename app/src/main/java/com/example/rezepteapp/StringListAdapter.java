package com.example.rezepteapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StringListAdapter extends RecyclerView.Adapter<StringListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView stepNumberTextView;
        public TextView stepTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            stepNumberTextView = (TextView) itemView.findViewById(R.id.tv_item_number);
            stepTextView = (TextView) itemView.findViewById(R.id.tv_step);
        }
    }

    private final List<String> steps;

    public StringListAdapter(List<String> steps) {
        this.steps = steps;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View ingridientView = inflater.inflate(R.layout.recycl_item_details_vorgehen, parent, false);

        return new ViewHolder(ingridientView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String step = steps.get(position);

        holder.stepNumberTextView.setText(position);
        holder.stepTextView.setText(step);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }
}
