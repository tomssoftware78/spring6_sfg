package be.tvde.di.repositories;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import be.tvde.di.entities.Beer;
import be.tvde.di.model.BeerStyle;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

   List<Beer> findAllByBeerNameIsLikeIgnoreCase(final String beerName);
   List<Beer> findAllByBeerStyle(final BeerStyle beerStyle);
   List<Beer> findAllByBeerNameIsLikeIgnoreCaseAndBeerStyle(final String beerName, final BeerStyle beerStyle);

}
