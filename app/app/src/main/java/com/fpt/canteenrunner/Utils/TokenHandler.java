package com.fpt.canteenrunner.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;


public class TokenHandler {
    private String getEmailFromToken(String token) {
        try {
            // Giải mã JWT và lấy thông tin subject (email của người dùng)
            DecodedJWT decodedJWT = JWT.decode(token);
            return decodedJWT.getSubject(); // Lấy email (subject)
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
//    public boolean isTokenExpired(String token, String secretKey) {
//        try {
//            Claims claims = Jwts.parser()
//                    .setSigningKey(secretKey)  // Secret key để xác thực
//                    .parseClaimsJws(token)  // Giải mã token
//                    .getBody();
//
//            // Kiểm tra thời gian hết hạn của token
//            Date expiration = claims.getExpiration();
//            return expiration.before(new Date());  // Nếu hết hạn, trả về true
//        } catch (Exception e) {
//            return true;  // Nếu có lỗi (token không hợp lệ), xem như token hết hạn
//        }
//    }
}
