package com.fpt.canteenrunner.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fpt.canteenrunner.Database.Model.TicketEntity;
import com.fpt.canteenrunner.R;
import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private final List<TicketEntity> tickets;
    private final OnTicketSelectedListener listener;

    public interface OnTicketSelectedListener {
        void onTicketSelected(TicketEntity ticket);
    }

    public TicketAdapter(List<TicketEntity> tickets, OnTicketSelectedListener listener) {
        this.tickets = tickets;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TicketEntity ticket = tickets.get(position);
        holder.btnTicket.setText(String.format("%d VND", (int) ticket.getTicketPrice()));

        holder.btnTicket.setOnClickListener(v -> listener.onTicketSelected(ticket));
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button btnTicket;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnTicket = itemView.findViewById(R.id.btn_ticket);
        }
    }
}
