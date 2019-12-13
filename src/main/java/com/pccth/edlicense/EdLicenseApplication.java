package com.pccth.edlicense;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EdLicenseApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdLicenseApplication.class, args);
	}

}
