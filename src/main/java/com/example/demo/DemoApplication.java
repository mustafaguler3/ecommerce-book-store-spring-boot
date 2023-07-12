package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.security.Role;
import com.example.demo.security.UserRole;
import com.example.demo.service.UserService;
import com.example.demo.utilities.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/*@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setFirstName("Musa");
		user.setLastName("güler");
		user.setUsername("musa");
		user.setPassword(SecurityUtility.passwordEncoder().encode("123"));
		user.setEmail("musa@gmail.com");

		Set<UserRole> userRoles = new HashSet<>();
		Role role = new Role();
		role.setRoleId(2);
		role.setName("ROLE_USER");
		userRoles.add(new UserRole(user,role));

		userService.createUser(user,userRoles);
	}*/

}
















