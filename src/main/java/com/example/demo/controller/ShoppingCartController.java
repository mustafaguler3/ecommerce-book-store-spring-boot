package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.domain.CartItem;
import com.example.demo.domain.ShoppingCart;
import com.example.demo.domain.User;
import com.example.demo.service.BookService;
import com.example.demo.service.CartItemService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private BookService bookService;

    @RequestMapping("/cart")
    public String shoppingCart(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        model.addAttribute("cartItemList",cartItemList);
        model.addAttribute("shoppingCart",shoppingCart);

        return "shoppingCart";
    }

    @RequestMapping(value = "/addItem",method = RequestMethod.POST)
    public String addItem(@ModelAttribute("book")Book book,
                          @ModelAttribute("qyt") String qyt, Model model,
                          Principal principal){
        User user = userService.findByUsername(principal.getName());
        book = bookService.findOne(book.getId());
        if (Integer.parseInt(qyt) > book.getInStockNumber()){
            model.addAttribute("notEnoughStock",true);
            return "forward:/bookDetail?id="+book.getId();
        }
        CartItem cartItem = cartItemService.addBookToCartItem(book,user,Integer.parseInt(qyt));
        model.addAttribute("addBookSuccess",true);

        return "forward:/bookDetail?id="+book.getId();
    }
    @RequestMapping("/updateCartItem")
    public String updateCartItem(@ModelAttribute("id") Long cartItemId,
                                 @ModelAttribute("qty") int qty){
        CartItem cartItem = cartItemService.findById(cartItemId);
        cartItem.setQyt(qty);
        cartItemService.updateCartItem(cartItem);

        return "forward:/shoppingCart/cart";
    }
    @RequestMapping("/removeItem")
    public String removeItem(@RequestParam("id") Long id){
        CartItem cartItemId = cartItemService.findById(id);
        cartItemService.removeCartItem(cartItemId);

        return "forward:/shoppingCart/cart";
    }
}






















