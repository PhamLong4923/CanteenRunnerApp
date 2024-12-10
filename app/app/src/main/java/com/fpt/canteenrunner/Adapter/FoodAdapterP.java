package com.fpt.canteenrunner.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.canteenrunner.DTO.FoodDTO;
import com.fpt.canteenrunner.Database.Model.FoodsEntity;
import com.fpt.canteenrunner.R;
import com.fpt.canteenrunner.ViewHolder.FoodViewHolderP;

import java.util.List;

public class FoodAdapterP extends RecyclerView.Adapter<FoodViewHolderP> {
    List<FoodDTO> foodList;
    public FoodAdapterP(List<FoodDTO> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolderP onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_food_home, parent, false);
        return new FoodViewHolderP(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolderP holder, int position) {
        FoodDTO food = foodList.get(position);
        holder.bind(food);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
