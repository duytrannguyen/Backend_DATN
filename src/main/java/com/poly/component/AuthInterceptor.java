 package com.poly.component;

 import org.springframework.stereotype.Component;
 import org.springframework.web.servlet.HandlerInterceptor;

 import com.poly.model.User;

 import jakarta.servlet.http.HttpServletRequest;
 import jakarta.servlet.http.HttpServletResponse;


 @Component
 public class AuthInterceptor implements HandlerInterceptor {
 	@Override
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         try {
             User user = (User) request.getSession().getAttribute("user");
             if (user == null) {
                 response.sendRedirect("/home/login?error=Vui long dang nhap");
                 return false;
             } else if (!(user.getRoleId().getRoleId()==1)) {
                 response.sendRedirect("/home/login?error=Khong co quyen truy cap");
                 return false;
             }
         } catch (Exception e) {
             response.sendRedirect("error?message=da xay ra loi");
             return false;
         }
         return true;
     }
 }
