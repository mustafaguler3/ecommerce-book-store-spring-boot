package com.example.demo.utilities;

import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MailConstructor {
    @Autowired
    private Environment env;

    public SimpleMailMessage constructResetTokenMail(String path, Locale locale, String token, User user,String password){

        String url = path = "newUser?token"+token;
        String message = "\nPlease click on this link to verify your email and edit your personal information";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("MG BookStore - New User");
        email.setText(url+message);
        email.setFrom(env.getProperty("support.email"));

        return email;
    }
}



















