package be.tvde.di.controllers.i18n;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("EN")
@SpringBootTest
public class MyI18NControllerTestEN {

   @Autowired
   private MyI18NController myI18NController;

   @Test
   void sayHello() {
      assertEquals("Hello World - EN", myI18NController.sayHello());//;
   }

}
