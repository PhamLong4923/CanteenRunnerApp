package com.fpt.canteenrunner.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fpt.canteenrunner.DTO.CanteenDTO;
import com.fpt.canteenrunner.R;

public class CanteenViewHolder extends RecyclerView.ViewHolder {
    private TextView tvName;
    private ImageView ivImg;
    public CanteenViewHolder(@NonNull View itemView) {
        super(itemView);
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
    }

    private void bindingView() {
        tvName = itemView.findViewById(R.id.tvCanteenName);
        ivImg = itemView.findViewById(R.id.ivCanteenImage);
    }

    public void bind(CanteenDTO canteenDTO) {
        tvName.setText(canteenDTO.getCanteenName());
        Glide.with(itemView)
                .load(canteenDTO.getCanteenImageUrl())

                .error(R.drawable.broken)
                .placeholder(R.drawable.loading)
                .into(ivImg);
    }


}
