package com.fpt.canteenrunner.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fpt.canteenrunner.DTO.CanteenDTO;
import com.fpt.canteenrunner.R;

import java.util.List;

public class CanteenAdapter extends RecyclerView.Adapter<CanteenAdapter.CanteenViewHolder> {

    private final List<CanteenDTO> data;

    public CanteenAdapter(List<CanteenDTO> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CanteenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_canteens_select, parent, false);
        return new CanteenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CanteenViewHolder holder, int position) {
        CanteenDTO canteenDTO = data.get(position);
        holder.bind(canteenDTO);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // ViewHolder Class
    static class CanteenViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvCanteenName;
        private final ImageView ivCanteenImage;

        public CanteenViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCanteenName = itemView.findViewById(R.id.tvCanteenName);
            ivCanteenImage = itemView.findViewById(R.id.ivCanteenImage);
        }

        public void bind(CanteenDTO myCanteenDTO) {
            // Set the canteen name
            tvCanteenName.setText(myCanteenDTO.getCanteenName());

            // Load the image using Glide
            Glide.with(itemView.getContext())
                    .load(myCanteenDTO.getCanteenImageUrl())
                    .placeholder(R.drawable.ic_placeholder) // Placeholder image
                    .into(ivCanteenImage);
        }
    }
}
