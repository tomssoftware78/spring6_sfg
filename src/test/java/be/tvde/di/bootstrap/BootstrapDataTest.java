package be.tvde.di.bootstrap;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import be.tvde.di.repositories.BeerRepository;
import be.tvde.di.repositories.CustomerRepository;
import be.tvde.di.services.BeerCSVService;
import be.tvde.di.services.BeerCSVServiceImpl;

@DataJpaTest
@Import(BeerCSVServiceImpl.class)
//@SpringBootTest
//@AutoConfigureTestDatabase
class BootstrapDataTest {

   @Autowired
   private BeerRepository beerRepository;
   @Autowired
   private CustomerRepository customerRepository;
   @Autowired
   private BeerCSVService beerCSVService;

   private BootstrapData bootstrapData;

   @BeforeEach
   void init() {
      bootstrapData = new BootstrapData(beerRepository, customerRepository, beerCSVService);
   }

   @Test
   void run() throws Exception {
      bootstrapData.run(null);

      assertThat(beerRepository.count()).isEqualTo(3 + 2410);
      assertThat(customerRepository.count()).isEqualTo(3);

   }
}