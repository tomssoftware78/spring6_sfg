package be.tvde.di.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConstructorInjectedControllerTest {

   @Autowired
   private ConstructorInjectedController constructorInjectedController;

   @Test
   void testSayHello() {
      System.out.println(constructorInjectedController.sayHello());
   }
}