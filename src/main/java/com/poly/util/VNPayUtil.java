package com.poly.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.poly.config.VNPAYConfig;

import jakarta.servlet.http.HttpServletRequest;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.codec.binary.Hex;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
public class VNPayUtil {

    // Phương thức tạo chữ ký HMAC SHA-512
//    public static String hmacSHA512(final String key, final String data) {
//        try {
//            if (key == null || data == null) {
//                throw new NullPointerException();
//            }
//            final Mac hmac512 = Mac.getInstance("HmacSHA512");
//            final SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
//            hmac512.init(secretKey);
//            byte[] result = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
//            StringBuilder sb = new StringBuilder(2 * result.length);
//            for (byte b : result) {
//                sb.append(String.format("%02x", b & 0xff));
//            }
//            return sb.toString();
//        } catch (Exception ex) {
//            throw new RuntimeException("Error generating HMAC SHA-512 signature", ex);
//        }
//    }
	private static String hmacSHA512(String key, String data) throws Exception {
        Mac sha512_HMAC = Mac.getInstance("HmacSHA512");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA512");
        sha512_HMAC.init(secret_key);
        return new String(Hex.encodeHex(sha512_HMAC.doFinal(data.getBytes())));
    }
    // Phương thức lấy địa chỉ IP từ HttpServletRequest
    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null || ipAddress.isEmpty()) {
                ipAddress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAddress = "Invalid IP: " + e.getMessage();
        }
        return ipAddress;
    }

    // Phương thức tạo số ngẫu nhiên có độ dài xác định
    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // Phương thức tạo URL thanh toán
    public static String getPaymentURL(Map<String, String> paramsMap, String secretKey) throws Exception {
        // Tạo URL yêu cầu thanh toán VNPay từ các tham số và ký
        StringBuilder signData = new StringBuilder();
        List<String> fieldNames = new ArrayList<>(paramsMap.keySet());
        Collections.sort(fieldNames);
        for (String fieldName : fieldNames) {
            String fieldValue = paramsMap.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty() && !fieldName.equals("vnp_SecureHash")) {
                signData.append(fieldName).append("=").append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8)).append("&");
            }
        }
        if (signData.length() > 0) {
            signData.setLength(signData.length() - 1); // Loại bỏ ký tự '&' cuối cùng
        }
        // In ra toàn bộ URL để ký
        System.out.println("URL để ký: " + signData.toString());
        String hmac = VNPayUtil.hmacSHA512(secretKey, signData.toString());
        String queryUrl = signData.append("&vnp_SecureHash=").append(hmac).toString();

        return queryUrl;
    }
    
    private static VNPAYConfig vnpayConfig = new VNPAYConfig();
    // Phương thức xác thực chữ ký từ Map các tham số
    public static boolean validateSignature(Map<String, String> paramsMap, String secretKey) {
        try {
        	StringBuilder signData = new StringBuilder();
            List<String> fieldNames = new ArrayList<>(paramsMap.keySet());
            Collections.sort(fieldNames);
            for (String fieldName : fieldNames) {
                String fieldValue = paramsMap.get(fieldName);
                if (fieldValue != null && !fieldValue.isEmpty() && !fieldName.equals("vnp_SecureHash")) {
                    signData.append(fieldName).append("=").append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8)).append("&");
                }
            }
            if (signData.length() > 0) {
                signData.setLength(signData.length() - 1); // Loại bỏ ký tự '&' cuối cùng
            }
            // In ra toàn bộ URL để ký
            System.out.println("URL để ký 2: " + signData.toString());
            System.out.println("A-Z : " + fieldNames);

            // Tạo chữ ký
            String generatedSignature = hmacSHA512(secretKey, signData.toString());
            System.out.println("Generated Signature: " + generatedSignature);
            System.out.println("Received Signature: " + paramsMap.get("vnp_SecureHash"));

            // So sánh chữ ký
            return generatedSignature.equals(paramsMap.get("vnp_SecureHash"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}

