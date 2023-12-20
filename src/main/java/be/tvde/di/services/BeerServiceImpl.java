package be.tvde.di.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.stereotype.Service;
import be.tvde.di.model.Beer;
import be.tvde.di.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

   @Override
   public Beer getBeerById(UUID id) {

      log.debug("Get Beer by id in service was called");
      return Beer.builder()
                 .id(UUID.randomUUID())
                 .version(1)
                 .beerName("Galaxy Cat")
                 .beerStyle(BeerStyle.PALE_ALE)
                 .upc("12345")
                 .price(new BigDecimal("12.99"))
                 .quantityOnHand(122)
                 .createdDate(LocalDateTime.now())
                 .updateDate(LocalDateTime.now())
                 .build();
   }

}
