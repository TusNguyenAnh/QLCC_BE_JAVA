package com.mbs.qlcc.service;

public interface IEmailService {
    void sendMail(String to, String subject, String complexName, String name, String username, String password);
}
