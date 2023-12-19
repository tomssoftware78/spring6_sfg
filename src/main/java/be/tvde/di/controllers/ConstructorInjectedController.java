package be.tvde.di.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import be.tvde.di.services.GreetingService;

@Controller
public class ConstructorInjectedController {

   private final GreetingService greetingService;

   public ConstructorInjectedController(@Qualifier("french_service") GreetingService greetingService) {
      this.greetingService = greetingService;
   }

   public String sayHello() {
      return greetingService.sayGreeting();
   }
}
