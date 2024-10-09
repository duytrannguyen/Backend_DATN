//package com.poly.controller;
//
//import com.poly.dto.request.UserRequest;
//import com.poly.model.User;
//import com.poly.repo.UserRepo;
//import com.poly.service.UserService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/home")
//public class UserController {
//
//    private final UserService userService;
//
//    public User userDisplay = null;
//
//    private final UserRepo userRepo;
//
//    @GetMapping("/login")
//    public String showL(@ModelAttribute("userRequest") UserRequest userRequest) {
//        return "views/Login";
//    }
//
//
//    @GetMapping("/register")
//    public String showR() {
//        return "views/Register";
//    }
//
//    @PostMapping("/login")
//    public String login(@ModelAttribute("userRequest") UserRequest userRequest, Model model) {
//        User user = userService.login(userRequest);
//        if (user != null) {
//            model.addAttribute("user", user);
//            model.addAttribute("display", "block");
//            userDisplay = user;
//            return "views/Login-Success";
//        } else {
//            model.addAttribute("message", "Username or password is incorrect");
//            return "views/Login";
//        }
//    }
//
//    @GetMapping("/success")
//    public String success(HttpServletRequest request, HttpServletResponse response, Model model){
//        response.setContentType("text/utf-8");
//        String code = request.getParameter("code");
//        if (code == null) {
//            String error = request.getParameter("error");
//            if (error != null) {
//                model.addAttribute("message", "Login failed");
//                return "redirect:/login";
//            } else {
//                model.addAttribute("user", userDisplay);
//                model.addAttribute("display", "block");
//                return "redirect:/home/index";
//            }
//        } else {
//            String token = userService.getTokenGoogle(code);
//            User user = userService.GoogleAccountGetUserInfo(token);
//            model.addAttribute("user", user);
//            userDisplay = user;
//            return "redirect:/home/index";
//        }
//    }
//
//    @GetMapping("/forgot-password")
//    public String showP(@ModelAttribute("userRequest") UserRequest userRequest) {
//        return "views/Forgot-Password";
//    }
//
//    @PostMapping("/forgot-password")
//    public String forgotPassword(@ModelAttribute("userRequest") UserRequest userRequest, Model model) {
//        boolean user = userService.forgotPassword(userRequest.getEmail());
//        model.addAttribute("user", user);
//        if (user) {
//            model.addAttribute("message", "Please check your email to reset password");
//            return "views/Forgot-Password";
//        } else {
//            model.addAttribute("message", "Email is incorrect");
//            return "views/Forgot-Password";
//        }
//    }
//
//    @PostMapping("/change-password")
//    public String changePassword(@ModelAttribute("userRequest") UserRequest userRequest, Model model) {
//
//        if (userRequest.getCurrentPassword().equals(userDisplay.getPassword())) {
//            if (!(userRequest.getNewPassword().equals(userRequest.getConfirmNewPassword()))) {
//                model.addAttribute("er","same");
//                model.addAttribute("message", "New password and confirm password are not the same");
//                model.addAttribute("display", "block");
//                model.addAttribute("color", "red");
//                return "views/Change-Password";
//            } else {
//                model.addAttribute("er","success");
//                User user = userService.changePassword(userDisplay.getUsersId(), userRequest);
//                model.addAttribute("message", "Change password successfully");
//                model.addAttribute("display", "block");
//                model.addAttribute("color", "green");
//                return "views/Change-Password";
//            }
//        } else {
//            model.addAttribute("er","incorrect");
//            model.addAttribute("message", "Password is incorrect");
//            model.addAttribute("display", "block");
//            model.addAttribute("color", "red");
//            return "views/Change-Password";
//        }
//    }
//
//    @GetMapping("/change-password")
//    public String changePassWord(@ModelAttribute("userRequest") UserRequest userRequest) {
//        return "views/Change-Password";
//    }
//
//    @GetMapping("/edit-profile")
//    public String edit(Model model) {
//        model.addAttribute("user", userRepo.findByUsersId(userDisplay.getUsersId()));
//        return "views/Edit-Profile";
//    }
//
//    @GetMapping("/edit-address")
//    public String showE(Model model) {
//        model.addAttribute("user", userRepo.findByUsersId(userDisplay.getUsersId()));
//        model.addAttribute("addresses", userService.getAddress(userDisplay.getUsersId()));
//        return "views/Edit-Address";
//    }
//}
