package com.aerb.budget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BudgetMonitoringPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetMonitoringPortalApplication.class, args);
	    String encoded = new BCryptPasswordEncoder().encode("admin123");
	    System.out.println("abc");
	    System.out.println(encoded);
		
	}

}
