package be.tvde.di.services;

import org.springframework.stereotype.Service;

@Service("french_service")
public class GreetingServiceFrenchImpl implements GreetingService {

   @Override
   public String sayGreeting() {
      return "Bonjour tous le monde";
   }
}
