package liquibasedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "liquibasedemo,com.data")
public class LiquibasedemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiquibasedemoApplication.class, args);
	}

}
