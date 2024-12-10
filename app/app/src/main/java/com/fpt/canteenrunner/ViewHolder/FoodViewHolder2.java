package com.fpt.canteenrunner.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fpt.canteenrunner.DTO.FoodDTO;
import com.fpt.canteenrunner.DTO.FoodDTO2;
import com.fpt.canteenrunner.R;

public class FoodViewHolder2 extends RecyclerView.ViewHolder {
    private ImageView ivFoodImage;
    private TextView tvFoodName;

    public FoodViewHolder2(@NonNull View itemView) {
        super(itemView);
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
    }

    private void bindingView() {

        ivFoodImage = itemView.findViewById(R.id.ivFoodImage);
        tvFoodName = itemView.findViewById(R.id.tvFoodName);
    }

    public void bind(FoodDTO2 food) {
        Glide.with(itemView)
                .load(food.getImageURL())

                .error(R.drawable.broken)
                .placeholder(R.drawable.loading)
                .into(ivFoodImage);


        tvFoodName.setText(String.valueOf(food.getFoodName()));
    }
}
