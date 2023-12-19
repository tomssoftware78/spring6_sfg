package be.tvde.di.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import be.tvde.di.services.GreetingService;

@Controller
public class PropertyInjectedController {

   @Qualifier("french_service")
   @Autowired
   private GreetingService greetingService;

   public String sayHello() {
      return greetingService.sayGreeting();
   }

}
