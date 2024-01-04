package be.tvde.di.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import be.tvde.di.entities.Beer;
import be.tvde.di.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;

@DataJpaTest
class BeerRepositoryTest {

   @Autowired
   private BeerRepository beerRepository;

   @Test
   void testSaveBeer() {
      final Beer savedBeer = beerRepository.save(Beer.builder()
                                                     .beerName("Juliper")
                                                     .beerStyle(BeerStyle.IPA)
                                                     .upc("123")
                                                     .price(new BigDecimal("11.99"))
                                                     .build());
      beerRepository.flush();
      //we need an explicit flush
      // to see the validation errors (hibernate needs to write immediately to the db
      // so we see the errors)
      assertThat(savedBeer).isNotNull();
      assertThat(savedBeer.getId()).isNotNull();
   }

   @Test
   void testSaveBeerNameTooLong() {
      //ConstraintViolationException is the result of the fact that we put the @Size annotation on the beerName field
      assertThrows(ConstraintViolationException.class, () -> {
         final Beer savedBeer = beerRepository.save(Beer.builder()
                                                        .beerName("Juliper 01234567890123456789012345678901234567890123456789")
                                                        .beerStyle(BeerStyle.IPA)
                                                        .upc("123")
                                                        .price(new BigDecimal("11.99"))
                                                        .build());
         beerRepository.flush();
         //we need an explicit flush
         // to see the validation errors (hibernate needs to write immediately to the db
         // so we see the errors)
      });

   }
}