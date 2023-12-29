package be.tvde.di.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import be.tvde.di.model.Beer;
import be.tvde.di.services.BeerService;
import be.tvde.di.services.BeerServiceImpl;

@WebMvcTest(value = BeerController.class)
class BeerControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private BeerService beerService;

   private BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

   @Test
   void testGetBeerById() throws Exception {
      final Beer testBeer = beerServiceImpl.listBeers().get(0);
      when(beerService.getBeerById(any(UUID.class))).thenReturn(testBeer);
      mockMvc.perform(
                   get("/api/v1/beer/" + testBeer.getId())
                         .accept(MediaType.APPLICATION_JSON)
                     )
             .andExpect(
                   status().isOk()
                       )
            .andExpect(
                  content().contentType(MediaType.APPLICATION_JSON)
                      )
            .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))
            .andExpect(jsonPath("$.beerName", is(testBeer.getBeerName())));
   }

   @Test
   void testListBeers() throws Exception {
      final List<Beer> testBeers = beerServiceImpl.listBeers();
      when(beerService.listBeers()).thenReturn(testBeers);
      mockMvc.perform(
            get("/api/v1/beer")
                  .accept(MediaType.APPLICATION_JSON)
                     )
             .andExpect(
                   status().isOk()
                       )
            .andExpect(
                  content().contentType(MediaType.APPLICATION_JSON)
                      )
            .andExpect(jsonPath("$.length()",is(3)));

   }

}