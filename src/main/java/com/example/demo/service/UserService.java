package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.security.PasswordResetToken;

public interface UserService {

    PasswordResetToken getPasswordResetToken(String token);
    void createPasswordResetTokenForUser(User user,String token);
}
