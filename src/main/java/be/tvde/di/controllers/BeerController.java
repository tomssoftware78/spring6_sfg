package be.tvde.di.controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import be.tvde.di.exception.NotFoundException;
import be.tvde.di.model.BeerDto;
import be.tvde.di.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {

   public static final String BEER_PATH = "/api/v1/beer";
   public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

   private final BeerService beerService;

   @GetMapping(value = BEER_PATH)
   public List<BeerDto> listBeers() {
      return beerService.listBeers();
   }

   @GetMapping(value = BEER_PATH_ID)
   public BeerDto getBeerById(@PathVariable("beerId") UUID beerId) {

      log.debug("Get Beer by Id - in controller");

      return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
   }

   @PostMapping(value = BEER_PATH)
   public ResponseEntity handlePost(@RequestBody BeerDto beerDto) {
      BeerDto savedBeerDto = beerService.saveNewBeer(beerDto);

      HttpHeaders headers = new HttpHeaders();
      headers.add("Location", "/api/v1/beer/" + savedBeerDto.getId().toString());

      return new ResponseEntity(headers, HttpStatus.CREATED);
   }

   @PutMapping(value = BEER_PATH_ID)
   public ResponseEntity handleUpdate(@PathVariable("beerId") UUID beerId, @RequestBody BeerDto beerDto) {
      beerService.updateBeerById(beerId, beerDto);

      return new ResponseEntity(HttpStatus.NO_CONTENT);
   }

   @DeleteMapping(value = BEER_PATH_ID)
   public ResponseEntity handleDelete(@PathVariable("beerId") UUID beerId) {
      beerService.deleteById(beerId);

      return new ResponseEntity(HttpStatus.NO_CONTENT);
   }

   @PatchMapping(value = BEER_PATH_ID)
   public ResponseEntity handlePatch(@PathVariable("beerId") UUID beerId, @RequestBody BeerDto beerDto) {
      beerService.patchBeerById(beerId, beerDto);

      return new ResponseEntity(HttpStatus.NO_CONTENT);
   }

//   @ExceptionHandler(NotFoundException.class)
//   public ResponseEntity handleNotFoundException() {
//      return ResponseEntity.notFound().build();
//   }
}
