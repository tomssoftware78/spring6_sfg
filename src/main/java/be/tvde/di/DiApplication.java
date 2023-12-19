package be.tvde.di;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import be.tvde.di.controllers.MyController;

@SpringBootApplication
public class DiApplication {

	public static void main(String[] args) {
		final ApplicationContext ctx = SpringApplication.run(DiApplication.class, args);

		final MyController controller = ctx.getBean("myController", MyController.class);
		controller.sayHello();
	}

}
