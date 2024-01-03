package be.tvde.di.controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import be.tvde.di.exception.NotFoundException;
import be.tvde.di.model.Beer;
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
   public List<Beer> listBeers() {
      return beerService.listBeers();
   }

   @GetMapping(value = BEER_PATH_ID)
   public Beer getBeerById(@PathVariable("beerId") UUID beerId) {

      log.debug("Get Beer by Id - in controller");

      return beerService.getBeerById(beerId);
   }

   @PostMapping(value = BEER_PATH)
   public ResponseEntity handlePost(@RequestBody Beer beer) {
      Beer savedBeer = beerService.saveNewBeer(beer);

      HttpHeaders headers = new HttpHeaders();
      headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

      return new ResponseEntity(headers, HttpStatus.CREATED);
   }

   @PutMapping(value = BEER_PATH_ID)
   public ResponseEntity handleUpdate(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
      beerService.updateBeerById(beerId, beer);

      return new ResponseEntity(HttpStatus.NO_CONTENT);
   }

   @DeleteMapping(value = BEER_PATH_ID)
   public ResponseEntity handleDelete(@PathVariable("beerId") UUID beerId) {
      beerService.deleteById(beerId);

      return new ResponseEntity(HttpStatus.NO_CONTENT);
   }

   @PatchMapping(value = BEER_PATH_ID)
   public ResponseEntity handlePatch(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
      beerService.patchBeerById(beerId, beer);

      return new ResponseEntity(HttpStatus.NO_CONTENT);
   }

//   @ExceptionHandler(NotFoundException.class)
//   public ResponseEntity handleNotFoundException() {
//      return ResponseEntity.notFound().build();
//   }
}
