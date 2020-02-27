package com.pccth.edlicense;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.pccth.edlicense.model.Bussiness;
import com.pccth.edlicense.model.Owner;
import com.pccth.edlicense.repository.OwnerRepository;

@SpringBootApplication
@EnableJpaAuditing
public class EdLicenseApplication {

	private static final Logger log = LoggerFactory.getLogger(EdLicenseApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(EdLicenseApplication.class, args);
	}

	@Bean
	  public CommandLineRunner demo(OwnerRepository repo) {
	    return (args) -> {
			
			/*
			 * Owner owner1 = new Owner(); owner1.setName("TEST A"); Owner owner2 = new
			 * Owner(); owner2.setName("TEST B");
			 * 
			 * Bussiness buss1 = new Bussiness(); buss1.setName("BUSSINESS 1");
			 * buss1.setOwner(owner1);
			 * 
			 * Bussiness buss2 = new Bussiness(); buss2.setName("BUSSINESS 2");
			 * buss2.setOwner(owner1);
			 * 
			 * Bussiness buss3 = new Bussiness(); buss3.setName("BUSSINESS 3");
			 * buss3.setOwner(owner2);
			 * 
			 * Bussiness buss4 = new Bussiness(); buss4.setName("BUSSINESS 4");
			 * buss4.setOwner(owner2);
			 */
			 
	    	
			
			
			/*
			 * repo.save(owner1); repo.save(owner2);
			 */
			 
	     
	    };
	  }
}
