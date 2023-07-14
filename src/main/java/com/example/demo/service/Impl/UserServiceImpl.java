package com.example.demo.service.Impl;

import com.example.demo.domain.*;
import com.example.demo.repository.*;
import com.example.demo.security.PasswordResetToken;
import com.example.demo.security.UserRole;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ShippingRepository shippingRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token,user);
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {
        User localUser = userRepository.findByUsername(user.getUsername());

        if (localUser != null){
            throw new Exception("User already exists.");
        }else {
            for (UserRole role : userRoles){
                roleRepository.save(role.getRole());
            }
            user.getUserRoles().addAll(userRoles);

            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(user);
            user.setShoppingCart(shoppingCart);

            user.setUserShippingList(new ArrayList<UserShipping>());
            user.setUserPaymentList(new ArrayList<Payment>());


            localUser = userRepository.save(user);
        }
        return localUser;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void updateUserBilling(UserBilling userBilling, Payment payment, User user) {
        payment.setUser(user);
        payment.setUserBilling(userBilling);
        payment.setDefaultPayment(true);
        userBilling.setUserPayment(payment);
        user.getUserPaymentList().add(payment);
        save(user);
    }

    @Override
    public void setUserDefaultPayment(Long defaultPaymentId, User user) {
        List<Payment> userPaymentList = paymentRepository.findAll();

        for (Payment payment : userPaymentList){
            if (payment.getId() == defaultPaymentId){
                payment.setDefaultPayment(true);
                paymentRepository.save(payment);
            }else {
                payment.setDefaultPayment(false);
                paymentRepository.save(payment);
            }
        }
    }

    @Override
    public void updateUserBilling(UserShipping userShipping, User user) {
        userShipping.setUser(user);
        userShipping.setUserShippingDefault(true);
        user.getUserShippingList().add(userShipping);
        save(user);
    }

    @Override
    public void setUserDefaultShipping(Long userShippingId, User user) {
        List<UserShipping> userShippingList = shippingRepository.findAll();

        for (UserShipping userShipping : userShippingList){
            if (userShipping.getId() == userShippingId){
                userShipping.setUserShippingDefault(true);
                shippingRepository.save(userShipping);
            }else {
                userShipping.setUserShippingDefault(false);
                shippingRepository.save(userShipping);
            }
        }
    }
}














