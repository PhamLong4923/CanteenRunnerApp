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
import com.fpt.canteenrunner.DTO.MyTicketDTO;
import com.fpt.canteenrunner.R;
import com.fpt.canteenrunner.ViewHolder.CanteenViewHolder;
import com.fpt.canteenrunner.ViewHolder.MyTicketViewHolder;

import java.util.List;

public class CanteenAdapter extends RecyclerView.Adapter<CanteenViewHolder> {

    private List<CanteenDTO> canteenList;

    public CanteenAdapter(List<CanteenDTO> canteenList) {
        this.canteenList = canteenList;
    }

    @NonNull
    @Override
    public CanteenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_canteens_select, parent, false);
        return new CanteenViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CanteenViewHolder holder, int position) {
        CanteenDTO canteenDTO = canteenList.get(position);
        holder.bind(canteenDTO);
    }

    @Override
    public int getItemCount() {
        return canteenList.size();
    }
}
