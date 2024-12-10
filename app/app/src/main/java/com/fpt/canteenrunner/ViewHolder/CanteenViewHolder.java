package com.fpt.canteenrunner.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fpt.canteenrunner.Canteen.ACT7_Canteen_Selected;
import com.fpt.canteenrunner.DTO.CanteenDTO;
import com.fpt.canteenrunner.R;

public class CanteenViewHolder extends RecyclerView.ViewHolder {
    private  TextView tvCanteenId;
    private TextView tvName;
    private ImageView ivImg;

    public CanteenViewHolder(@NonNull View itemView ) {
        super(itemView);
        bindingView();
        bindingAction();

    }

    private void bindingAction() {
        ivImg.setOnClickListener(this::selectCanteen);
    }

    private void selectCanteen(View view) {
        // Lấy id của canteen
        String canteenId = tvCanteenId.getText().toString();

        // Tạo Intent và truyền id sang Activity A
        Intent intent = new Intent(itemView.getContext(), ACT7_Canteen_Selected.class);
        intent.putExtra("canteen_id", canteenId);
        itemView.getContext().startActivity(intent);
    }

    private void bindingView() {
        tvCanteenId = itemView.findViewById(R.id.tvCanteenId);
        tvName = itemView.findViewById(R.id.tvCanteenName);
        ivImg = itemView.findViewById(R.id.ivCanteenImage);
    }

    public void bind(CanteenDTO canteenDTO) {
        tvCanteenId.setText(canteenDTO.getCanteenId());
        tvName.setText(canteenDTO.getCanteenName());
        Glide.with(itemView)
                .load(canteenDTO.getCanteenImageUrl())

                .error(R.drawable.broken)
                .placeholder(R.drawable.loading)
                .into(ivImg);
    }


}
