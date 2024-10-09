package com.poly.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.poly.util.VNPayUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;
import java.util.TimeZone;
@Getter
@Configuration
public class VNPAYConfig {

	@Value("${payment.vnPay.url}")
    private String vnp_PayUrl;

    @Value("${payment.vnPay.returnUrl}")
    private String vnp_ReturnUrl;

    @Value("${payment.vnPay.tmnCode}")
    private String vnp_TmnCode;

    @Value("${payment.vnPay.secretKey}")
    private String secretKey;

    @Value("${payment.vnPay.version}")
    private String vnp_Version;

    @Value("${payment.vnPay.command}")
    private String vnp_Command;

    @Value("${payment.vnPay.orderType}")
    private String vnp_OrderType;

    public Map<String, String> getVNPayConfig(HttpServletRequest request) {
        Map<String, String> vnpParamsMap = new TreeMap<>(); // Sử dụng TreeMap để tự động sắp xếp các tham số

        vnpParamsMap.put("vnp_Version", "2.1.0");
        vnpParamsMap.put("vnp_Command", "pay");
        vnpParamsMap.put("vnp_TmnCode", "92KPTRKI");
        vnpParamsMap.put("vnp_CurrCode", "VND");
        //vnpParamsMap.put("vnp_TxnRef", VNPayUtil.getRandomNumber(8)); // ID giao dịch
        vnpParamsMap.put("vnp_TxnRef", VNPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang:" + VNPayUtil.getRandomNumber(8)); // Thông tin đơn hàng
        vnpParamsMap.put("vnp_OrderType", "other");
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", "http://localhost:8080/response");
     // Lấy địa chỉ IP của người dùng
        String userIpAddress = VNPayUtil.getIpAddress(request);
        vnpParamsMap.put("vnp_IpAddr", userIpAddress);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 15);
        String vnpExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnpExpireDate);

        return vnpParamsMap;
    }
}
