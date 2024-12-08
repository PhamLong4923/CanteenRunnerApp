package com.fpt.canteenrunner.Network.api;

import com.fpt.canteenrunner.Network.model.PaymentStatusResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PaymentApi {
    @GET("payment/status/{ticketId}")
    Call<PaymentStatusResponse> getPaymentStatus(@Path("ticketId") String ticketId);
}

