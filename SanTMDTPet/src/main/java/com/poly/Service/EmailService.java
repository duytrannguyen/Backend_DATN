package com.poly.Service;


public interface EmailService {

    boolean send(String to, String email, String subject);

    String buildEmail(String name, String link, String password, boolean isValidEmail);

}
