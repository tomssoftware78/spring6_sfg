package be.tvde.di;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import be.tvde.di.controllers.MyController;

@SpringBootTest
class DiApplicationTests {
	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private MyController myController;

	@Test
	void testGetControllerFromCtx() {
		final MyController myController = ctx.getBean("myController", MyController.class);
		System.out.println(myController.sayHello());
	}

	@Test
	void testGetAutowiredController() {
		System.out.println(myController.sayHello());
	}
}
