package com.authservice.authservice;

import com.authservice.authservice.entity.Account;
import com.authservice.authservice.entity.Role;
import com.authservice.authservice.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

@EnableEurekaClient
@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner init(
			AccountService accountService
	) {
		List<Account> accountsList = new LinkedList<>();

		Account user = new Account();
		user.setUsername("user");
		user.setPassword("password");

		accountsList.add(user);

		Account admin = new Account();
		admin.setUsername("admin");
		admin.setPassword("password");
		admin.grantAuthority(Role.ROLE_ADMIN);

		accountsList.add(admin);

		return (evt) -> accountsList.forEach(
				acct -> {
					accountService.registerUser(acct);
				}
		);
	}
}
