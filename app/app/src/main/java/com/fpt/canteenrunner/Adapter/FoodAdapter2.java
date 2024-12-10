package com.fpt.canteenrunner.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.canteenrunner.DTO.FoodDTO2;
import com.fpt.canteenrunner.R;
import com.fpt.canteenrunner.ViewHolder.FoodViewHolder;
import com.fpt.canteenrunner.ViewHolder.FoodViewHolder2;

import java.util.List;

public class FoodAdapter2 extends RecyclerView.Adapter<FoodViewHolder2> {
    List<FoodDTO2> foodList;
    public FoodAdapter2(List<FoodDTO2> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_foodincanteen, parent, false);
        return new FoodViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder2 holder, int position) {
        FoodDTO2 food = foodList.get(position);
        holder.bind(food);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }
}
