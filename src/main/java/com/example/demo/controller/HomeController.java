package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.security.PasswordResetToken;
import com.example.demo.security.Role;
import com.example.demo.security.UserRole;
import com.example.demo.service.*;
import com.example.demo.utilities.SecurityUtility;
import com.example.demo.utilities.TRContants;
import com.mysql.cj.xdevapi.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.*;

@Controller
public class HomeController {

    @Autowired
    private ShippingService shippingService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private BookService bookService;
    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/myAccount")
    public String myAccount(){
        return "myAccount";
    }

    @RequestMapping("/login")
    public String login(Model model){
        model.addAttribute("classActiveLogin",true);
        return "myAccount";
    }

    @RequestMapping("/forgetPassword")
    public String forgetPassword(Model model){
        model.addAttribute("classActiveForgetPassword",true);
        return "myAccount";
    }

    @RequestMapping("/newUser")
    public String newUser(Model model, Locale locale, @RequestParam("token") String token){
        PasswordResetToken passToken = userService.getPasswordResetToken(token);

        if (passToken == null){
            String message = "Invalid Token";
            model.addAttribute("message",message);
            return "redirect:/badRequest";
        }

        User user = passToken.getUser();
        String username = user.getUsername();

        UserDetails userDetails = userSecurityService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("classActiveEdit",true);
        model.addAttribute("user",user);

        return "myProfile";
    }

    @RequestMapping(value = "/newUser",method = RequestMethod.POST)
    public String newUserPost(@ModelAttribute("email") String email,
                              @ModelAttribute("username") String username,
                              Model model, HttpServletRequest request) throws Exception {

        model.addAttribute("classActiveNewAccount",true);
        model.addAttribute("email",email);
        model.addAttribute("username",username);

        if (userService.findByUsername(username) != null){
            model.addAttribute("usernameExists",true);

            return "myAccount";
        }

        if (userService.findByEmail(email) != null){
            model.addAttribute("emailExists",true);

            return "myAccount";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

        String password = SecurityUtility.passwordEncoder().encode(user.getPassword());
        user.setPassword(password);

        Role role = new Role();
        role.setRoleId(1);
        role.setName("ROLE_USER");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user,role));

        userService.createUser(user,userRoles);

        return "redirect:/myAccount";
    }

    @GetMapping("/bookshelf")
    public String bookshelf(Model model){
        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList",bookList);

        return "bookshelf";
    }
    @GetMapping("/bookDetail")
    public String bookDetail(@PathParam("id") Long id, Model model, Principal principal){

        if (principal != null){
            String username = principal.getName();
            User user =userService.findByUsername(username);
            model.addAttribute("user",user);
        }

        Book book = bookService.findOne(id);
        model.addAttribute("book",book);

        List<Integer> lst = Arrays.asList(1,2,3,4,5,6,7,8,9);
        model.addAttribute("lst",lst);
        model.addAttribute("ls",1);

        return "bookDetail";
    }

    @GetMapping("/myProfile")
    public String myProfile(Model model,Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);

        UserShipping userShipping = new UserShipping();
        model.addAttribute("userShipping",userShipping);
        model.addAttribute("listOfCreditCards",true);
        model.addAttribute("listOfShippingAddresses",true);

        List<String> stateList = TRContants.listOfTrStatesCode;
        Collections.sort(stateList);
        model.addAttribute("stateList",stateList);
        model.addAttribute("classActiveEdit",true);
        return "myProfile";
    }

    @GetMapping("/listOfCreditCards")
    public String listOfCreditCards(Model model,Principal principal,HttpServletRequest request){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());

        model.addAttribute("listOfCreditCards",true);
        model.addAttribute("classActiveBilling",true);
        model.addAttribute("listOfShippingAddresses",true);
        return "myProfile";
    }

    @GetMapping("/listOfShippingAddresses")
    public String listOfShippingAddresses(Model model,Principal principal,HttpServletRequest request){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());

        model.addAttribute("listOfCreditCards",true);
        model.addAttribute("classActiveBilling",true);
        model.addAttribute("listOfShippingAddresses",true);
        return "myProfile";
    }
    @RequestMapping("/addNewCreditCard")
    public String addNewCreditCard(Model model,Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        model.addAttribute("addNewCreditCard",true);
        model.addAttribute("classActiveBilling",true);
        model.addAttribute("classActiveShipping",true);
        model.addAttribute("listOfShippingAddresses",true);

        UserBilling userBilling = new UserBilling();
        Payment payment = new Payment();

        model.addAttribute("userBilling",user);
        model.addAttribute("userPayment",payment);

        List<String> stateList = TRContants.listOfTrStatesCode;
        Collections.sort(stateList);
        model.addAttribute("stateList",stateList);
        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());

        return "myProfile";
    }
    @RequestMapping("/addNewShippingAddress")
    public String addNewShippingAddress(Model model,Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        model.addAttribute("addNewShippingAddress",true);

        UserBilling userBilling = new UserBilling();
        model.addAttribute("userBilling",userBilling);
        List<String> stateList = TRContants.listOfTrStatesCode;
        Collections.sort(stateList);
        model.addAttribute("stateList",stateList);
        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());

        return "myProfile";
    }

    @RequestMapping(value = "/addNewShippingAddress",method = RequestMethod.POST)
    public String addNewShippingAddressPost(@ModelAttribute("userShipping") UserShipping userShipping,Model model,Principal principal){
        User user = userService.findByUsername(principal.getName());
        userService.updateUserBilling(userShipping,user);

        model.addAttribute("user",user);
        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());
        model.addAttribute("listOfShippingAddresses",true);
        model.addAttribute("classActiveShipping",true);
        model.addAttribute("listOfCreditCards",true);


        return "myProfile";
    }

    @RequestMapping(value = "/addNewCreditCard",method = RequestMethod.POST)
    public String addNewCreditCardPost(@ModelAttribute("userPayment") Payment payment,
                                       @ModelAttribute("userBilling") UserBilling userBilling,
                                       Principal principal,Model model){
        User user = userService.findByUsername(principal.getName());
        userService.updateUserBilling(userBilling,payment,user);

        model.addAttribute("user",user);
        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());
        model.addAttribute("listOfCreditCards",true);
        model.addAttribute("classActiveBilling",true);
        model.addAttribute("listOfShippingAddresses",true);

        return "myProfile";
    }
    @RequestMapping("/updateCreditCard")
    public String updateCreditCard(@ModelAttribute("id") Long creditCardId,Principal principal,Model model){
        User user = userService.findByUsername(principal.getName());
        Payment payment = paymentService.findById(creditCardId);

        if (user.getId() != payment.getUser().getId()){
            return "badRequestPage";
        }else {
            model.addAttribute("user",user);
            UserBilling userBilling = payment.getUserBilling();
            model.addAttribute("userPayment",payment);
            model.addAttribute("userBilling",userBilling);
            List<String> stateList = TRContants.listOfTrStatesCode;
            Collections.sort(stateList);
            model.addAttribute("stateList",stateList);
            model.addAttribute("addNewCreditCards",true);
            model.addAttribute("classActiveBilling",true);
            model.addAttribute("listOfShippingAddresses",true);

            model.addAttribute("userPaymentList",user.getUserPaymentList());
            model.addAttribute("userShippingList",user.getUserShippingList());

            return "myProfile";
        }

    }



    public String removeCreditCard(@ModelAttribute("id") Long creditCardId,Model model,Principal principal){
        User user = userService.findByUsername(principal.getName());
        Payment payment = paymentService.findById(creditCardId);

        if (user.getId() != payment.getUser().getId()){
            return "badRequest";
        }else {
            paymentService.removeById(creditCardId);
            model.addAttribute("listOfCreditCards",true);
            model.addAttribute("classActiveBilling",true);
            model.addAttribute("listOfShippingAddresses",true);

            model.addAttribute("userPaymentList",user.getUserPaymentList());
            model.addAttribute("userShippingList",user.getUserShippingList());

            return "myProfile";
        }
    }

    @RequestMapping(value = "/setDefaultShippingAddress",method = RequestMethod.POST)
    public String setDefaultShippingAddress(@ModelAttribute("defaultShippingAddressId") Long defaultShippingId,Principal principal,Model model){
        User user = userService.findByUsername(principal.getName());

        userService.setUserDefaultPayment(defaultShippingId,user);

        model.addAttribute("user",user);
        model.addAttribute("listOfCreditCards",true);
        model.addAttribute("classActiveShipping",true);
        model.addAttribute("listOfShippingAddresses",true);

        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());

        return "myProfile";
    }

    @RequestMapping(value = "/setDefaultPayment",method = RequestMethod.POST)
    public String setDefaultPayment(@ModelAttribute("defaultUserPaymentId") Long defaultPaymentId,Principal principal,Model model){

        User user = userService.findByUsername(principal.getName());
        Payment payment = paymentService.findById(defaultPaymentId);
        userService.setUserDefaultPayment(defaultPaymentId,user);

        model.addAttribute("user",user);
        model.addAttribute("listOfCreditCards",true);
        model.addAttribute("classActiveBilling",true);
        model.addAttribute("listOfBillingAddresses",true);

        model.addAttribute("userPaymentList",user.getUserPaymentList());
        model.addAttribute("userShippingList",user.getUserShippingList());

        return "myProfile";
    }

    @RequestMapping("/removeUserShipping")
    public String removeUserShipping(@ModelAttribute("id") Long userShippingId,Model model,Principal principal){
        User user = userService.findByUsername(principal.getName());
        UserShipping userShipping = shippingService.findById(userShippingId);

        if (user.getId() != userShipping.getUser().getId()){
            return "badRequest";
        }else {
            model.addAttribute("user",user);
            shippingService.removeById(userShippingId);

            model.addAttribute("classActiveShipping",true);
            model.addAttribute("listOfShippingAddresses",true);

            model.addAttribute("userPaymentList",user.getUserPaymentList());
            model.addAttribute("userShippingList",user.getUserShippingList());

            return "myProfile";
        }
    }
}















