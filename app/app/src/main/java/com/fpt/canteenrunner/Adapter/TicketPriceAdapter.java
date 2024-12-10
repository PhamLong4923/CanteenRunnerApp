package com.fpt.canteenrunner.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.fpt.canteenrunner.Database.Model.FoodPricesEntity;
import com.fpt.canteenrunner.R;
import java.util.List;

public class TicketPriceAdapter extends RecyclerView.Adapter<TicketPriceAdapter.ViewHolder> {

    private final List<FoodPricesEntity> prices;
    private final OnPriceSelectedListener listener;

    public interface OnPriceSelectedListener {
        void onPriceSelected(FoodPricesEntity price);
    }

    public TicketPriceAdapter(List<FoodPricesEntity> prices, OnPriceSelectedListener listener) {
        this.prices = prices;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_price, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodPricesEntity price = prices.get(position);
        holder.btnPrice.setText(String.format("%d", (int) price.getPrice()));
        holder.btnPrice.setOnClickListener(v -> listener.onPriceSelected(price));
    }

    @Override
    public int getItemCount() {
        return prices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button btnPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnPrice = itemView.findViewById(R.id.btn_ticket_price);
        }
    }
}
