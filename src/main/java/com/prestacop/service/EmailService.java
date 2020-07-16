package com.prestacop.service;

public interface EmailService {
    void sendMail(String toEmail, String subject, String message);
}
