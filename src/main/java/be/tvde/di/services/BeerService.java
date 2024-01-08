package be.tvde.di.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import be.tvde.di.model.BeerDto;
import be.tvde.di.model.BeerStyle;

public interface BeerService {

   void deleteById(UUID beerId);

   List<BeerDto> listBeers(final String beerName, final BeerStyle beerStyle, final Boolean showInventory);

   Optional<BeerDto> getBeerById(UUID id);

   void patchBeerById(UUID beerId, BeerDto beerDto);

   BeerDto saveNewBeer(BeerDto beerDto);

   void updateBeerById(UUID beerId, BeerDto beerDto);
}
