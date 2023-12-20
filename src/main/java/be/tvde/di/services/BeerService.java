package be.tvde.di.services;

import java.util.UUID;
import be.tvde.di.model.Beer;

public interface BeerService {

   Beer getBeerById(UUID id);
}
