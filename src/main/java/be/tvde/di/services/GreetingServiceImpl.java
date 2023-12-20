package be.tvde.di.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class GreetingServiceImpl implements GreetingService {

   @Override
   public String sayGreeting() {
      return "Hello Everyone from base service";
   }
}
