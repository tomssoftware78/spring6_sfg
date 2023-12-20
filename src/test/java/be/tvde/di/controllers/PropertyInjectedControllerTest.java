package be.tvde.di.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("EN")
@SpringBootTest
class PropertyInjectedControllerTest {

   @Autowired
   private PropertyInjectedController propertyInjectedController;

   @Test
   void testSayHello() {
      System.out.println(propertyInjectedController.sayHello());
   }
}