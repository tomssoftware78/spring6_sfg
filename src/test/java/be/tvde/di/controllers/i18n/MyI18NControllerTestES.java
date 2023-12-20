package be.tvde.di.controllers.i18n;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("ES")
@SpringBootTest
public class MyI18NControllerTestES {

   @Autowired
   private MyI18NController myI18NController;

   @Test
   void sayHello() {
      assertEquals("Hola Mundo - ES", myI18NController.sayHello());//;
   }

}

