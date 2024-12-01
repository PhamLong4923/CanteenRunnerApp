package com.fpt.canteenrunner.ViewHolder;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.canteenrunner.DTO.MyTicketDTO;
import com.fpt.canteenrunner.R;

public class MyTicketViewHolder extends RecyclerView.ViewHolder {
   private TextView tvCode, tvDate, tvPrice;
    public MyTicketViewHolder(@NonNull View itemView) {
        super(itemView);
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
        itemView.setOnClickListener(this::onItemClick);
    }

    private void onItemClick(View view) {
        Toast.makeText(itemView.getContext(), tvCode.getText(), Toast.LENGTH_SHORT).show();
    }

    private void bindingView() {
        tvCode = itemView.findViewById(R.id.tv_code);
        tvDate = itemView.findViewById(R.id.tv_buy_date);
        tvPrice = itemView.findViewById(R.id.tv_price);
    }

    public void setMyTicketDTO(MyTicketDTO myTicketDTO) {
        tvCode.setText(myTicketDTO.getTicketCode());
        tvDate.setText(myTicketDTO.getBuyDate());
        tvPrice.setText(myTicketDTO.getPrice());
    }
}
