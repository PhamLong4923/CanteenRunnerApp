package com.fpt.canteenrunner.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.canteenrunner.DTO.MyHistoryDTO;
import com.fpt.canteenrunner.R;
import com.fpt.canteenrunner.ViewHolder.MyHistoryViewHolder;

import java.util.List;

public class MyHistoryAdapter extends RecyclerView.Adapter<MyHistoryViewHolder> {
    private List<MyHistoryDTO> data;
    public MyHistoryAdapter(List<MyHistoryDTO> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_my_history, parent, false);
        return new MyHistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHistoryViewHolder holder, int position) {
        MyHistoryDTO myHistoryDTO = data.get(position);
        holder.setMyHistoryDTO(myHistoryDTO);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
