package be.tvde.di.services.i18n;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import be.tvde.di.services.GreetingService;

@Profile("EN")
@Service("i18NService")
public class EnglishGreetingService implements GreetingService {

   @Override
   public String sayGreeting() {
      return "Hello World - EN";
   }
}
