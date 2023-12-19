package be.tvde.di.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SetterInjectedControllerTest {

   @Autowired
   private SetterInjectedController setterInjectedController;

   @Test
   void testSayHello() {
      assertEquals("Bonjour tous le monde", setterInjectedController.sayHello());
   }
}