package be.tvde.springdatajpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringDataJpaApplication {

	public static void main(String[] args) {
		final ApplicationContext ctx = SpringApplication.run(SpringDataJpaApplication.class, args);

	}

}
