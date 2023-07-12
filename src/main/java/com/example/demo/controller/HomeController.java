package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.security.PasswordResetToken;
import com.example.demo.security.Role;
import com.example.demo.security.UserRole;
import com.example.demo.service.UserSecurityService;
import com.example.demo.service.UserService;
import com.example.demo.utilities.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserSecurityService userSecurityService;

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
}















