package com.mbs.qlcc.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {
    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;

    @Override
    @Async("mailExecutor")
    public void sendMail(String to, String subject, String complexName, String name, String username, String password) {
        try {
            String htmlContent = buildWelcomeEmail(complexName, name, username, password);
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Send mail failed", e);
        }
    }

    private String buildWelcomeEmail(String complexName, String name, String username, String password) {
        Context context = new Context();
        context.setVariable("complexName", complexName);
        context.setVariable("name", name);
        context.setVariable("username", username);
        context.setVariable("password", password);
        System.out.println(
                this.getClass().getClassLoader()
                        .getResource("templates/email/register_account.html")
        );
        return templateEngine.process("email/register_account", context);
    }
}
