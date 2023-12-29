package be.tvde.di.controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import be.tvde.di.model.Beer;
import be.tvde.di.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

   private final BeerService beerService;

   @RequestMapping(method = RequestMethod.GET)
   public List<Beer> listBeers() {
      return beerService.listBeers();
   }

   @RequestMapping(value = "/{beerId}", method = RequestMethod.GET)
   public Beer getBeerById(@PathVariable("beerId") UUID beerId){

      log.debug("Get Beer by Id - in controller");

      return beerService.getBeerById(beerId);
   }

   @RequestMapping(method = RequestMethod.POST)
   public ResponseEntity handlePost(@RequestBody Beer beer) {
      Beer savedBeer = beerService.saveNewBeer(beer);

      HttpHeaders headers = new HttpHeaders();
      headers.add("Location", "/api/v1/beer/" + savedBeer.getId().toString());

      return new ResponseEntity(headers, HttpStatus.CREATED);
   }

   @RequestMapping(value = "/{beerId}", method = RequestMethod.PUT)
   public ResponseEntity handleUpdate(@PathVariable("beerId") UUID beerId, @RequestBody Beer beer) {
      beerService.updateBeerById(beerId, beer);

      return new ResponseEntity(HttpStatus.NO_CONTENT);
   }

   @DeleteMapping("/{beerId}")
   public ResponseEntity handleDelete(@PathVariable("beerId") UUID beerId) {
      beerService.deleteById(beerId);

      return new ResponseEntity(HttpStatus.NO_CONTENT);
   }
}
