package be.tvde.di.services;

import org.springframework.stereotype.Service;

@Service("english_service")
public class GreetingServiceEnglishImpl implements GreetingService {

   @Override
   public String sayGreeting() {
      return "Hello everyone";
   }
}
