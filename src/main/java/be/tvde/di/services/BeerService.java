package be.tvde.di.services;

import java.util.List;
import java.util.UUID;
import be.tvde.di.model.Beer;

public interface BeerService {

   void deleteById(UUID beerId);

   List<Beer> listBeers();

   Beer getBeerById(UUID id);

   void patchBeerById(UUID beerId, Beer beer);

   Beer saveNewBeer(Beer beer);

   void updateBeerById(UUID beerId, Beer beer);
}
