package com.emptyseat.kss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class KssApplication {

	public static void main(String[] args) {
		SpringApplication.run(KssApplication.class, args);
	}

}
