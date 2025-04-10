package com.example.pd_pro_biblioteka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PdProBibliotekaApplication {

	public static void main(String[] args) { SpringApplication.run(PdProBibliotekaApplication.class, args);
	}
}
