package com.fpt.canteenrunner.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.canteenrunner.DTO.MyTicketDTO;
import com.fpt.canteenrunner.R;
import com.fpt.canteenrunner.ViewHolder.MyTicketViewHolder;

import java.util.List;

public class MyTicketAdapter extends RecyclerView.Adapter<MyTicketViewHolder> {
    List<MyTicketDTO> data;

    public MyTicketAdapter(List<MyTicketDTO> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyTicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_my_ticket, parent, false);
        return new MyTicketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTicketViewHolder holder, int position) {
        MyTicketDTO myTicketDTO = data.get(position);
        holder.setMyTicketDTO(myTicketDTO);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
