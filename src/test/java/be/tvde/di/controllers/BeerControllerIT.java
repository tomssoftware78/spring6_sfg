package be.tvde.di.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import be.tvde.di.entities.Beer;
import be.tvde.di.exception.NotFoundException;
import be.tvde.di.model.BeerDto;
import be.tvde.di.model.BeerStyle;
import be.tvde.di.repositories.BeerRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
      //this Spring Boot test splice is not using a Transaction and will not rollback the transaction
      //this is different than the @DataJpaTest (uses a Tx and rolls back by default)
      //so here we have to add it manually (see the annotations)
class BeerControllerIT {

   @Autowired
   private BeerController beerController;
   @Autowired
   private BeerRepository beerRepository;
   @Autowired
   private WebApplicationContext wac;
   @Autowired
   private ObjectMapper objectMapper;

   private MockMvc mockMvc;

   @BeforeEach
   void init() {
      mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
   }

   @Test
   void testListBeers() {
      final List<BeerDto> beerDtos = beerController.listBeers();
      assertThat(beerDtos.size()).isEqualTo(3);
   }

   @Test
   @Transactional //(String or jakarta are both allowed here)
   @Rollback
   void testEmptyList() {
      beerRepository.deleteAll();
      final List<BeerDto> beerDtos = beerController.listBeers();
      assertTrue(beerDtos.isEmpty());
   }

   @Test
   void testGetById() {
      final Beer beer = beerRepository.findAll().get(0);

      final BeerDto beerDto = beerController.getBeerById(beer.getId());
      assertThat(beerDto).isNotNull();
   }

   @Test
   void testBeerIdNotFound() {
      assertThrows(NotFoundException.class, ()->{
         beerController.getBeerById(UUID.randomUUID());
      });
   }

   @Test
   void testSaveNewBeer() {
      final BeerDto beerDto = BeerDto.builder()
                                     .beerName("a new beer")
                                     .beerStyle(BeerStyle.IPA)
                                     .upc("123")
                                     .price(new BigDecimal("12.45"))
                                     .build();
      final ResponseEntity responseEntity = beerController.handlePost(beerDto);
      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
      assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

      final URI location = responseEntity.getHeaders().getLocation();
      final String[] split = location.getPath().split("/");
      final UUID savedUUID = UUID.fromString(split[4]);
      final Beer beer = beerRepository.findById(savedUUID).get();
      assertThat(beer).isNotNull();
   }

   @Test
   void testPatchBeerBadName() throws Exception {
      final Beer beer = beerRepository.findAll().get(0);
      final HashMap<String, Object> beerMap = new HashMap<>();
      beerMap.put("beerName", "New name 01234567890123456789012345678901234567890123456789");

      mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(beerMap)))
             .andExpect(status().isBadRequest());
   }
}