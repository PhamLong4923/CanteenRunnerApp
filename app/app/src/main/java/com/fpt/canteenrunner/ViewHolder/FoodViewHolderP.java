package com.fpt.canteenrunner.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fpt.canteenrunner.DTO.FoodDTO;
import com.fpt.canteenrunner.Database.Model.FoodsEntity;
import com.fpt.canteenrunner.R;

public class FoodViewHolderP extends RecyclerView.ViewHolder {
    private ImageView ivFood;
    private TextView tvFoodName, tvFoodPrice;

    public FoodViewHolderP(@NonNull View itemView) {
        super(itemView);
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
    }

    private void bindingView() {
        ivFood = itemView.findViewById(R.id.ivFoodHome);
        tvFoodName = itemView.findViewById(R.id.tvFoodNameHome);
        tvFoodPrice = itemView.findViewById(R.id.tvFoodPriceHome);
    }

    public void bind(FoodDTO food) {
        Glide.with(itemView)
                .load(food.getImageURL())

                .error(R.drawable.broken)
                .placeholder(R.drawable.loading)
                .into(ivFood);

        tvFoodName.setText(food.getFoodName());
        tvFoodPrice.setText(String.valueOf(food.getPrice()) + "VND");
    }
}
