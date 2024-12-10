package com.fpt.canteenrunner.ViewHolder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fpt.canteenrunner.ACT17dActivity;
import com.fpt.canteenrunner.Adapter.FoodAdapter;
import com.fpt.canteenrunner.DTO.FoodDTO;
import com.fpt.canteenrunner.Database.Model.FoodsEntity;
import com.fpt.canteenrunner.R;

public class FoodViewHolder extends RecyclerView.ViewHolder {
    private ImageView ivFood;
    private TextView tvFoodName, tvFoodPrice;
    private String foodId;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            if (view.getContext() instanceof FoodAdapter.OnFoodClickListener) {
                FoodAdapter.OnFoodClickListener listener = (FoodAdapter.OnFoodClickListener) view.getContext();
                listener.onFoodClick(foodId);
            }

        }
    }

    private void bindingView() {
        ivFood = itemView.findViewById(R.id.ivFood);
        tvFoodName = itemView.findViewById(R.id.tvFoodName);
        tvFoodPrice = itemView.findViewById(R.id.tvFoodPrice);

    }

    public void bind(FoodDTO food) {
        Glide.with(itemView)
                .load(food.getImageURL())

                .error(R.drawable.broken)
                .placeholder(R.drawable.loading)
                .into(ivFood);

        tvFoodName.setText(food.getFoodName());
        tvFoodPrice.setText(String.valueOf(food.getPrice()));
        foodId = food.getFoodId();
    }
}
