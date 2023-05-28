package io.excelmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.excelmanager.v1")
public class ExcelManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ExcelManagerApplication.class, args);
	}

}
