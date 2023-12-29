package be.tvde.springdatajpa;

import be.tvde.springdatajpa.domain.Author;
import be.tvde.springdatajpa.domain.Book;

public class TestDataUtil {

   private TestDataUtil(){
   }

   public static Author createTestAuthorA() {
      return Author.builder()
                   .id(1L)
                   .name("Abigail Rose")
                   .age(80)
                   .build();
   }

   public static Author createTestAuthorB() {
      return Author.builder()
                   .id(2L)
                   .name("Thomas Cronin")
                   .age(44)
                   .build();
   }

   public static Author createTestAuthorC() {
      return Author.builder()
                   .id(3L)
                   .name("Jesse A Casey")
                   .age(24)
                   .build();
   }

   public static Book createTestBookA() {
      return Book.builder()
                 .isbn("978-1-2345-6789-0")
                 .title("The Shadow in the Attic")
                 .build();
   }

   public static Book createTestBookB() {
      return Book.builder()
                 .isbn("978-1-2345-6789-1")
                 .title("Beyond the Horizon")
                 .build();
   }

   public static Book createTestBookC() {
      return Book.builder()
                 .isbn("978-1-2345-6789-2")
                 .title("The Last Ember")
                 .build();
   }
}
