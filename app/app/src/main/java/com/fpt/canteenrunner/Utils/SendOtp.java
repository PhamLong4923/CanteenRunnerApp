package com.fpt.canteenrunner.Utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

public class SendOtp { // Renamed for clarity

    // Phương thức này sinh mã OTP ngẫu nhiên
    public static String generateOTP() {
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }
    // Method to send the OTP via SMS
    public static void sendOTP(Context context, String phone, String otp) {
        //  Phương thức này kiểm tra quyền của ứng dụng trước
        //  khi thực hiện hành động yêu cầu quyền đó (ở đây là quyền gửi SMS).
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();
            // Chia tin nhắn thành các phần nhỏ
            ArrayList<String> parts = smsManager.divideMessage(otp + " là mã OTP của bạn.");
            // Gửi tin nhắn đã chia thành các phần nhỏ đến số điện thoại được chỉ định
            smsManager.sendMultipartTextMessage(phone, null, parts, null, null);
            Toast.makeText(context, "SMS đã được gửi thành công!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Lỗi khi gửi SMS: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
