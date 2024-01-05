package be.tvde.di.bootstrap;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import be.tvde.di.entities.Beer;
import be.tvde.di.entities.Customer;
import be.tvde.di.model.BeerCSVRecord;
import be.tvde.di.model.BeerStyle;
import be.tvde.di.repositories.BeerRepository;
import be.tvde.di.repositories.CustomerRepository;
import be.tvde.di.services.BeerCSVService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//@Profile("!localmysql")
public class BootstrapData implements CommandLineRunner {

   private final BeerRepository beerRepository;
   private final CustomerRepository customerRepository;
   private final BeerCSVService beerCSVService;

   @Transactional
   @Override
   public void run(final String... args) throws Exception {
      System.out.println("Bootstrapping the data");

      loadBeerData();
      loadCsvBeerData();
      loadCustomerData();
   }

   private void loadCsvBeerData() throws FileNotFoundException {
      if (beerRepository.count() < 10) {
         final File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
         final List<BeerCSVRecord> beerCSVRecords = beerCSVService.convertCSV(file);

         beerCSVRecords.forEach(beerCSVRecord->{
            BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
               case "American Pale Lager" -> BeerStyle.LAGER;
               case "American Pale Ale (APA)",
                     "American Black Ale",
                     "Belgian Dark Ale",
                     "American Blonde Ale" -> BeerStyle.ALE;
               case "American IPA",
                     "American Double / Imperial IPA",
                     "Belgian IPA" -> BeerStyle.IPA;
               case "American Porter" -> BeerStyle.PORTER;
               case "Oatmeal Stout",
                     "American Stout" -> BeerStyle.STOUT;
               case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
               case "Fruit / Vegetable Beer",
                     "Winter Warmer",
                     "Berliner Weissbier" -> BeerStyle.WHEAT;
               case "English Pale Ale" -> BeerStyle.PALE_ALE;
               default -> BeerStyle.PILSNER;
            };
            beerRepository.save(Beer.builder()
                                    .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                                    .beerStyle(beerStyle)
                                    .price(BigDecimal.TEN)
                                    .upc(beerCSVRecord.getRow().toString())
                                    .quantityOnHand(beerCSVRecord.getCount())
                                    .build());
         });
      }
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
      if (beerRepository.count() == 0) {
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
