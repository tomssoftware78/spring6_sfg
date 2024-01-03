package be.tvde.di.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import be.tvde.di.entities.Beer;

@DataJpaTest
class BeerRepositoryTest {

   @Autowired
   private BeerRepository beerRepository;

   @Test
   void testSaveBeer() {
      final Beer savedBeer = beerRepository.save(Beer.builder().beerName("Juliper").build());
      assertThat(savedBeer).isNotNull();
      assertThat(savedBeer.getId()).isNotNull();
   }
}