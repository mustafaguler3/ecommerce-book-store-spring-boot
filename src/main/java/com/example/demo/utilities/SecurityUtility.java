package com.example.demo.utilities;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;

@Component
public class SecurityUtility {

    static final String SALT = "salt";

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12,new SecureRandom(SALT.getBytes()));
    }

    public static String randomPassword(){
        String saltchars = "ABCDEFGĞHIKLMNOÖRPSTWYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random random = new Random();

        while (salt.length() < 18){
            int index = (int)(random.nextFloat()*saltchars.length());
            salt.append(saltchars.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}




















