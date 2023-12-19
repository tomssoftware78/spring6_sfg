package be.tvde.di.services;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceFrenchImpl implements GreetingService {

   @Override
   public String sayGreeting() {
      return "Bonjour tous le monde";
   }
}
