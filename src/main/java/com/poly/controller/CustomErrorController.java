//package com.poly.controller;
//
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@Controller
//public class CustomErrorController implements ErrorController {
//    
//    @RequestMapping("/custom-error")
//    public String handleError(HttpServletRequest request) {
//        // Xử lý lỗi tùy chỉnh và trả về view lỗi
//        return "errorpage";
//    }
//    
//    public String getErrorPath() {
//        return "/custom-error";
//    }
//}
//
// 
