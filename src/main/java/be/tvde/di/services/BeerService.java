package be.tvde.di.services;

import java.util.List;
import java.util.UUID;
import be.tvde.di.model.Beer;

public interface BeerService {

   List<Beer> listBeers();

   Beer getBeerById(UUID id);

   Beer saveNewBeer(Beer beer);

   void updateBeerById(UUID beerId, Beer beer);
}
