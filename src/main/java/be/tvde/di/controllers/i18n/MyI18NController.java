package be.tvde.di.controllers.i18n;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import be.tvde.di.services.GreetingService;

@Controller
public class MyI18NController {

   private final GreetingService greetingService;

   public MyI18NController(@Qualifier("i18NService") final GreetingService greetingService) {
      this.greetingService = greetingService;
   }

   public String sayHello() {
      return greetingService.sayGreeting();
   }
}
