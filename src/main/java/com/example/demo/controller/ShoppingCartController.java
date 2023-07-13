package com.example.demo.controller;

import com.example.demo.domain.CartItem;
import com.example.demo.domain.ShoppingCart;
import com.example.demo.domain.User;
import com.example.demo.service.CartItemService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemService cartItemService;

    @RequestMapping("/cart")
    public String shoppingCart(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        model.addAttribute("cartItemList",cartItemList);
        model.addAttribute("shoppingCart",shoppingCart);

        return "shoppingCart";
    }
}






















