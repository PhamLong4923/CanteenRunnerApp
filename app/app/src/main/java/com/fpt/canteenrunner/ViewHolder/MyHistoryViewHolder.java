package com.fpt.canteenrunner.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fpt.canteenrunner.DTO.MyHistoryDTO;
import com.fpt.canteenrunner.R;

public class MyHistoryViewHolder extends RecyclerView.ViewHolder {
    private TextView code, date, status;
    public MyHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        bindingView();
        bindingAction();
    }

    private void bindingAction() {
    }

    private void bindingView() {
        code = itemView.findViewById(R.id.tv_code);
        date = itemView.findViewById(R.id.tv_buy_date);
        status = itemView.findViewById(R.id.tv_status);
    }

    public void setMyHistoryDTO(MyHistoryDTO myHistoryDTO) {
        code.setText(myHistoryDTO.getTiketCode());
        date.setText(myHistoryDTO.getBuyDate());
        status.setText(myHistoryDTO.getStatus() ? "Đã dùng" : "Chưa dùng");


    }
}
