package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.service.*;
import com.example.demo.utilities.TRContants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.model.IModel;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Controller
public class CheckoutController {

    private ShippingAddress shippingAddress = new ShippingAddress();
    private BillingAddress billingAddress = new BillingAddress();
    private Payment payment = new Payment();

    @Autowired
    private UserService userService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ShippingAddressService shippingAddressService;
    @Autowired
    private BillingAddressService billingAddressService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ShippingService shippingService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private JavaMailSender mailSender;

    @RequestMapping("/checkout")
    public String checkout(@RequestParam("id") Long cartId,
                           @RequestParam(value = "missingRequiredField",required = false) boolean missing, Model model,
                           Principal principal){
        User user = userService.findByUsername(principal.getName());

        if (cartId != user.getShoppingCart().getId()){
            return "badRequestPage";
        }
        List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

        if (cartItemList.size() == 0){
            model.addAttribute("emptyCart",true);
            return "forward:/shoppingCart/cart";
        }

        for (CartItem cartItem: cartItemList){
            if (cartItem.getBook().getInStockNumber() < cartItem.getQyt()){
                model.addAttribute("notEnoughStock",true);
            }

            return "forward:/shoppingCart/cart";
        }

        List<UserShipping> userShippingList = user.getUserShippingList();
        List<Payment> userPaymentList = user.getUserPaymentList();

        model.addAttribute("userShippingList",userPaymentList);
        model.addAttribute("userPaymentList",userPaymentList);

        if (userPaymentList.size() == 0){
            model.addAttribute("emptyPaymentList",true);
        }else {
            model.addAttribute("emptyPaymentList",false);
        }

        if (userShippingList.size() == 0){
            model.addAttribute("emptyShippingList",true);
        }else {
            model.addAttribute("emptyShippingList",false);
        }

        ShoppingCart shoppingCart = user.getShoppingCart();

        for (UserShipping userShipping : userShippingList){
            if (userShipping.isUserShippingDefault()){
                shippingAddressService.setByUserShipping(userShipping,shippingAddress);
            }
        }

        for (Payment userPayment: userPaymentList){
            if (payment.isDefaultPayment()){
                paymentService.setByUserPayment(userPayment,payment);
                billingAddressService.setByUserBilling(userPayment.getUserBilling(),billingAddress);
            }
        }

        model.addAttribute("shippingAddress",shippingAddress);
        model.addAttribute("payment",payment);
        model.addAttribute("billingAddress",billingAddress);
        model.addAttribute("cartItemList",cartItemList);
        model.addAttribute("shoppingCart",user.getShoppingCart());

        List<String> stateList = TRContants.listOfTrStatesCode;
        Collections.sort(stateList);
        model.addAttribute("stateList",stateList);
        model.addAttribute("classActiveShipping",true);

        if (missing){
            model.addAttribute("missingRequiredField",true);
        }
        return "checkout";
    }

    @RequestMapping("/setShippingAddress")
    public String setShippingAddress(@RequestParam("userShippingId") Long userShippingId,Principal principal,Model model){
        User user = userService.findByUsername(principal.getName());
        UserShipping userShipping = shippingService.findById(userShippingId);

        if (userShipping.getUser().getId() != user.getId()){
            return "badRequestPage";
        }else {
            shippingAddressService.setByUserShipping(userShipping,shippingAddress);

            List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

            BillingAddress billingAddress = new BillingAddress();

            model.addAttribute("payment",payment);
            model.addAttribute("billingAddress",billingAddress);
            model.addAttribute("shoppingCart",user.getShoppingCart());

            List<String> stateList = TRContants.listOfTrStatesCode;
            Collections.sort(stateList);
            model.addAttribute("stateList",stateList);

            List<UserShipping> userShippingList = user.getUserShippingList();
            List<Payment> userPaymentList = user.getUserPaymentList();

            model.addAttribute("userShippingList",userPaymentList);
            model.addAttribute("userPaymentList",userPaymentList);
            model.addAttribute("shippingAddress",shippingAddress);
            model.addAttribute("classActiveShipping",true);

            return "checkout";
        }
    }

    @RequestMapping("/setPaymentMethod")
    public String setPaymentMethod(@RequestParam("userPaymentId") Long userPaymentId,Principal principal,Model model){

        User user = userService.findByUsername(principal.getName());
        Payment userPayment = paymentService.findById(userPaymentId);
        UserBilling userBilling = payment.getUserBilling();

        if (payment.getUser().getId() != user.getId()){
            return "badRequestPage";
        }else {
            paymentService.setByUserPayment(userPayment,payment);

            List<CartItem> cartItemList = cartItemService.findByShoppingCart(user.getShoppingCart());

            model.addAttribute("shippingAddress",billingAddress);
            model.addAttribute("payment",payment);
            model.addAttribute("billingAddress",billingAddress);
            model.addAttribute("shoppingCart",user.getShoppingCart());

            List<String> stateList = TRContants.listOfTrStatesCode;
            Collections.sort(stateList);
            model.addAttribute("stateList",stateList);

            List<UserShipping> userShippingList = user.getUserShippingList();
            List<Payment> userPaymentList = user.getUserPaymentList();

            model.addAttribute("userShippingList",userShippingList);
            model.addAttribute("userPaymentList",userPaymentList);

            model.addAttribute("shippingAddress",shippingAddress);
            model.addAttribute("classActivePayment",true);

            model.addAttribute("emptyPaymentList",false);

            if (userShippingList.size() == 0){
                model.addAttribute("emptyShippingList",true);
            }else {
                model.addAttribute("emptyShippingList",false);
            }

            return "checkout";
        }
    }

    //add billing same as shipping

    @RequestMapping(value = "/checkout",method = RequestMethod.POST)
    public String checkoutPost(@ModelAttribute("shippingAddress") ShippingAddress shippingAddress,
                               @ModelAttribute("billingAddress") BillingAddress billingAddress,
                               @ModelAttribute("payment") Payment payment,
                               @ModelAttribute("billingSameAsShipping") String billingSameAsShipping,
                               @ModelAttribute("shippingMethod") String shippingMethod,
                               Principal principal,Model model){
        User user = userService.findByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
        model.addAttribute("cartItemList",cartItemList);

        if (billingSameAsShipping.equals(true)){
            billingAddress.setBillingAddressName(shippingAddress.getShippingAddressName());
            billingAddress.setBillingAddressStreet1(shippingAddress.getShippingAddressStreet1());
            billingAddress.setBillingAddressStreet2(shippingAddress.getShippingAddressStreet2());
            billingAddress.setBillingAddressCity(shippingAddress.getShippingAddressCity());
            billingAddress.setBillingAddressState(shippingAddress.getShippingAddressState());
            billingAddress.setBillingAddressCountry(shippingAddress.getShippingAddressCountry());
            billingAddress.setBillingAddressZipCode(shippingAddress.getShippingAddressZipCode());
        }

        if (shippingAddress.getShippingAddressStreet1().isEmpty() || shippingAddress.getShippingAddressCity().isEmpty() || shippingAddress.getShippingAddressName().isEmpty() || shippingAddress.getShippingAddressState().isEmpty() || shippingAddress.getShippingAddressZipCode().isEmpty() || payment.getCardName().isEmpty() || payment.getCvc() == 0 || billingAddress.getBillingAddressStreet1().isEmpty() || billingAddress.getBillingAddressCity().isEmpty() || billingAddress.getBillingAddressState().isEmpty() || billingAddress.getBillingAddressName().isEmpty() || billingAddress.getBillingAddressZipCode().isEmpty()){
            return "redirect:/checkout?id="+shoppingCart.getId()+"&missingRequiredField=true";
        }

        Order order = orderService.createOrder(shoppingCart,shippingAddress,billingAddress,payment,shippingMethod,user);

        mailSender.send(mailContructor.constructOrderConfirmationEmail(user,order, Locale.ENGLISH));

        LocalDate today = LocalDate.now();
        LocalDate estimatedDeliveryDate;

        if (shippingMethod.equals("groundShipping")){
            estimatedDeliveryDate = today.plusDays(5);
        }else {
            estimatedDeliveryDate = today.plusDays(3);
        }
        model.addAttribute("estimatedDeliveryDate",estimatedDeliveryDate);

        return "orderSubmittedPage";
    }
}






















