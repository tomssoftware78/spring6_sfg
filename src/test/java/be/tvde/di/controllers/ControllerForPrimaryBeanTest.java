package be.tvde.di.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ControllerForPrimaryBeanTest {

   @Autowired
   private ConstructorInjectedController controller;

   @Test
   void testSayHello() {
      assertThat(controller.sayHello()).isEqualTo("Hello from the primary greeting service");
   }
}
