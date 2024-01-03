package be.tvde.di.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import be.tvde.di.entities.Beer;
import be.tvde.di.mappers.BeerMapper;
import be.tvde.di.model.BeerDto;
import be.tvde.di.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Primary
@Service
public class BeerServiceJPA implements BeerService {

   private final BeerRepository beerRepository;
   private final BeerMapper beerMapper;

   @Override
   public void deleteById(final UUID beerId) {

   }

   @Override
   public List<BeerDto> listBeers() {
      return beerRepository.findAll()
                           .stream()
                           .map(beerMapper::beerToBeerDto)
                           .collect(Collectors.toList());
   }

   @Override
   public Optional<BeerDto> getBeerById(final UUID id) {
      final Beer beer = beerRepository.findById(id).orElse(null);
      return Optional.ofNullable(beerMapper.beerToBeerDto(beer));
   }

   @Override
   public void patchBeerById(final UUID beerId, final BeerDto beerDto) {

   }

   @Override
   public BeerDto saveNewBeer(final BeerDto beerDto) {
      final Beer saved = beerRepository.save(beerMapper.beerDtoToBeer(beerDto));
      return beerMapper.beerToBeerDto(saved);
   }

   @Override
   public void updateBeerById(final UUID beerId, final BeerDto beerDto) {

   }
}
