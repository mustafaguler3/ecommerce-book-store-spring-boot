package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.security.PasswordResetToken;
import com.example.demo.security.UserRole;

import java.util.Set;

public interface UserService {

    PasswordResetToken getPasswordResetToken(String token);

    void createPasswordResetTokenForUser(User user,String token);

    User findByUsername(String username);

    User findByEmail(String email);

    User createUser(User user, Set<UserRole> userRoles) throws Exception;
}
