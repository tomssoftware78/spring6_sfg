package be.tvde.di.mappers;

import org.mapstruct.Mapper;
import be.tvde.di.entities.Beer;
import be.tvde.di.model.BeerDto;

@Mapper
public interface BeerMapper {

   Beer beerDtoToBeer(final BeerDto beerDto);

   BeerDto beerToBeerDto(final Beer beer);
}
