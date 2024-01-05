package be.tvde.di.bootstrap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import be.tvde.di.entities.Beer;
import be.tvde.di.entities.Customer;
import be.tvde.di.model.BeerStyle;
import be.tvde.di.repositories.BeerRepository;
import be.tvde.di.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile("!localmysql")
public class BootstrapData implements CommandLineRunner {

   private final BeerRepository beerRepository;
   private final CustomerRepository customerRepository;
   @Override
   public void run(final String... args) throws Exception {
      System.out.println("Bootstrapping the data");

      loadBeerData();
      loadCustomerData();
   }

   private void loadCustomerData() {
      if (customerRepository.count() == 0) {
         Customer customer1 = Customer.builder()
                                      .id(UUID.randomUUID())
                                      .name("Customer 1")
                                      .version(1)
                                      .createdDate(LocalDateTime.now())
                                      .lastModifiedDate(LocalDateTime.now())
                                      .build();

         Customer customer2 = Customer.builder()
                                      .id(UUID.randomUUID())
                                      .name("Customer 2")
                                      .version(1)
                                      .createdDate(LocalDateTime.now())
                                      .lastModifiedDate(LocalDateTime.now())
                                      .build();

         Customer customer3 = Customer.builder()
                                      .id(UUID.randomUUID())
                                      .name("Customer 3")
                                      .version(1)
                                      .createdDate(LocalDateTime.now())
                                      .lastModifiedDate(LocalDateTime.now())
                                      .build();

         customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
      }
   }

   private void loadBeerData() {
      if (beerRepository.count() == 0){
         Beer beer1 = Beer.builder()
                          .beerName("Galaxy Cat")
                          .beerStyle(BeerStyle.PALE_ALE)
                          .upc("12356")
                          .price(new BigDecimal("12.99"))
                          .quantityOnHand(122)
                          .createdDate(LocalDateTime.now())
                          .updateDate(LocalDateTime.now())
                          .build();

         Beer beer2 = Beer.builder()
                          .beerName("Crank")
                          .beerStyle(BeerStyle.PALE_ALE)
                          .upc("12356222")
                          .price(new BigDecimal("11.99"))
                          .quantityOnHand(392)
                          .createdDate(LocalDateTime.now())
                          .updateDate(LocalDateTime.now())
                          .build();

         Beer beer3 = Beer.builder()
                          .beerName("Sunshine City")
                          .beerStyle(BeerStyle.IPA)
                          .upc("12356")
                          .price(new BigDecimal("13.99"))
                          .quantityOnHand(144)
                          .createdDate(LocalDateTime.now())
                          .updateDate(LocalDateTime.now())
                          .build();

         beerRepository.save(beer1);
         beerRepository.save(beer2);
         beerRepository.save(beer3);
      }
   }
}
