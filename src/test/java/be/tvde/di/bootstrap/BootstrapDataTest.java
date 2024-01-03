package be.tvde.di.bootstrap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import be.tvde.di.repositories.BeerRepository;
import be.tvde.di.repositories.CustomerRepository;

@DataJpaTest
class BootstrapDataTest {
   @Autowired
   private BeerRepository beerRepository;
   @Autowired
   private CustomerRepository customerRepository;

   private BootstrapData bootstrapData;

   @BeforeEach
   void init() {
      bootstrapData = new BootstrapData(beerRepository, customerRepository);
   }

   @Test
   void run() throws Exception {
      bootstrapData.run(null);

      assertThat(beerRepository.count()).isEqualTo(3);
      assertThat(customerRepository.count()).isEqualTo(3);


   }
}