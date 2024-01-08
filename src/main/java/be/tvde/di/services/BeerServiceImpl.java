package be.tvde.di.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import be.tvde.di.model.BeerDto;
import be.tvde.di.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

   private Map<UUID, BeerDto> beerMap;

   public BeerServiceImpl() {
      this.beerMap = new HashMap<>();

      BeerDto beerDto1 = BeerDto.builder()
                                .id(UUID.randomUUID())
                                .version(1)
                                .beerName("Galaxy Cat")
                                .beerStyle(BeerStyle.PALE_ALE)
                                .upc("12356")
                                .price(new BigDecimal("12.99"))
                                .quantityOnHand(122)
                                .createdDate(LocalDateTime.now())
                                .updateDate(LocalDateTime.now())
                                .build();

      BeerDto beerDto2 = BeerDto.builder()
                                .id(UUID.randomUUID())
                                .version(1)
                                .beerName("Crank")
                                .beerStyle(BeerStyle.PALE_ALE)
                                .upc("12356222")
                                .price(new BigDecimal("11.99"))
                                .quantityOnHand(392)
                                .createdDate(LocalDateTime.now())
                                .updateDate(LocalDateTime.now())
                                .build();

      BeerDto beerDto3 = BeerDto.builder()
                                .id(UUID.randomUUID())
                                .version(1)
                                .beerName("Sunshine City")
                                .beerStyle(BeerStyle.IPA)
                                .upc("12356")
                                .price(new BigDecimal("13.99"))
                                .quantityOnHand(144)
                                .createdDate(LocalDateTime.now())
                                .updateDate(LocalDateTime.now())
                                .build();

      beerMap.put(beerDto1.getId(), beerDto1);
      beerMap.put(beerDto2.getId(), beerDto2);
      beerMap.put(beerDto3.getId(), beerDto3);
   }

   @Override
   public void deleteById(final UUID beerId) {
      beerMap.remove(beerId);
   }

   @Override
   public List<BeerDto> listBeers(final String beerName, final BeerStyle beerStyle, final Boolean showInventory) {
      return new ArrayList<>(beerMap.values());
   }

   @Override
   public Optional<BeerDto> getBeerById(final UUID id) {
      log.debug("Get Beer by Id - in service. Id: " + id.toString());

      return Optional.of(beerMap.get(id));
   }

   @Override
   public void patchBeerById(final UUID beerId, final BeerDto beerDto) {
      BeerDto existing = beerMap.get(beerId);

      if (StringUtils.hasText(beerDto.getBeerName())) {
         existing.setBeerName(beerDto.getBeerName());
      }

      if (beerDto.getBeerStyle() != null) {
         existing.setBeerStyle(beerDto.getBeerStyle());
      }

      if (beerDto.getPrice() != null) {
         existing.setPrice(beerDto.getPrice());
      }

      if (beerDto.getQuantityOnHand() != null) {
         existing.setQuantityOnHand(beerDto.getQuantityOnHand());
      }

      if (StringUtils.hasText(beerDto.getUpc())) {
         existing.setUpc(beerDto.getUpc());
      }
   }

   @Override
   public BeerDto saveNewBeer(final BeerDto beerDto) {
      BeerDto savedBeerDto = BeerDto.builder()
                                    .id(UUID.randomUUID())
                                    .createdDate(LocalDateTime.now())
                                    .updateDate(LocalDateTime.now())
                                    .beerName(beerDto.getBeerName())
                                    .beerStyle(beerDto.getBeerStyle())
                                    .quantityOnHand(beerDto.getQuantityOnHand())
                                    .upc(beerDto.getUpc())
                                    .price(beerDto.getPrice())
                                    .build();

      beerMap.put(savedBeerDto.getId(), savedBeerDto);

      return savedBeerDto;
   }

   @Override
   public void updateBeerById(final UUID beerId, final BeerDto beerDto) {
      BeerDto existing = beerMap.get(beerId);

      existing.setBeerName(beerDto.getBeerName());
      existing.setBeerStyle(beerDto.getBeerStyle());
      existing.setPrice(beerDto.getPrice());
      existing.setUpc(beerDto.getUpc());
      existing.setQuantityOnHand(beerDto.getQuantityOnHand());

      beerMap.put(existing.getId(), existing);
   }
}
