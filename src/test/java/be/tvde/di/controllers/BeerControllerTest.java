package be.tvde.di.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import be.tvde.di.model.Beer;
import be.tvde.di.services.BeerService;
import be.tvde.di.services.BeerServiceImpl;

@WebMvcTest(value = BeerController.class)
class BeerControllerTest {

   @Autowired
   private MockMvc mockMvc;
   @Autowired
   private ObjectMapper objectMapper;

   @MockBean
   private BeerService beerService;

   private BeerServiceImpl beerServiceImpl;

   @Captor
   private ArgumentCaptor<UUID> uuidArgumentCaptor;
   @Captor
   private ArgumentCaptor<Beer> beerArgumentCaptor;

   @BeforeEach
   void init() {
      beerServiceImpl= new BeerServiceImpl();
   }

   @Test
   void testCreateNewBeer() throws Exception {
      final Beer beer = beerServiceImpl.listBeers().get(0);
      beer.setVersion(null);
      beer.setId(null);

      when(beerService.saveNewBeer(any(Beer.class))).thenReturn(beerServiceImpl.listBeers().get(1));
      mockMvc.perform(
            post("/api/v1/beer")
                  .accept(MediaType.APPLICATION_JSON)
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(objectMapper.writeValueAsString(beer)))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"));
   }

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

   @Test
   void testUpdateBeer() throws Exception {
      final Beer beer = beerServiceImpl.listBeers().get(0);

      mockMvc.perform(put("/api/v1/beer/" + beer.getId().toString())
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(beer)))
             .andExpect(status().isNoContent());

      verify(beerService).updateBeerById(any(UUID.class), any(Beer.class));
   }

   @Test
   void testDeleteBeer() throws Exception {
      final Beer beer = beerServiceImpl.listBeers().get(0);

      mockMvc.perform(delete("/api/v1/beer/" + beer.getId())
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

      verify(beerService).deleteById(uuidArgumentCaptor.capture());
      assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
   }

   @Test
   void testPatchBeer() throws Exception {
      final Beer beer = beerServiceImpl.listBeers().get(0);

      Map<String, Object> beerMap = new HashMap<>();
      beerMap.put("beerName", "Jupiler");

      mockMvc.perform(patch("/api/v1/beer/" + beer.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(beerMap)))
            .andExpect(status().isNoContent());

      verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
      assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
      assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
   }

}