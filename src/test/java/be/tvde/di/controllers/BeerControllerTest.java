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
import java.util.Optional;
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
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import be.tvde.di.exception.NotFoundException;
import be.tvde.di.model.BeerDto;
import be.tvde.di.services.BeerService;
import be.tvde.di.services.BeerServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
   private ArgumentCaptor<BeerDto> beerArgumentCaptor;

   @BeforeEach
   void init() {
      beerServiceImpl = new BeerServiceImpl();
   }

   @Test
   void testCreateNewBeer() throws Exception {
      final BeerDto beerDto = beerServiceImpl.listBeers().get(0);
      beerDto.setVersion(null);
      beerDto.setId(null);

      when(beerService.saveNewBeer(any(BeerDto.class))).thenReturn(beerServiceImpl.listBeers().get(1));
      mockMvc.perform(
                   post(BeerController.BEER_PATH)
                         .accept(MediaType.APPLICATION_JSON)
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(beerDto)))
             .andExpect(status().isCreated())
             .andExpect(header().exists("Location"));
   }

   @Test
   void testGetBeerById() throws Exception {
      final BeerDto testBeerDto = beerServiceImpl.listBeers().get(0);
      when(beerService.getBeerById(any(UUID.class))).thenReturn(Optional.of(testBeerDto));
      mockMvc.perform(
                   get(BeerController.BEER_PATH + "/" + testBeerDto.getId())
                         .accept(MediaType.APPLICATION_JSON)
                     )
             .andExpect(
                   status().isOk()
                       )
             .andExpect(
                   content().contentType(MediaType.APPLICATION_JSON)
                       )
             .andExpect(jsonPath("$.id", is(testBeerDto.getId().toString())))
             .andExpect(jsonPath("$.beerName", is(testBeerDto.getBeerName())));
   }

   @Test
   void testListBeers() throws Exception {
      final List<BeerDto> testBeerDtos = beerServiceImpl.listBeers();
      when(beerService.listBeers()).thenReturn(testBeerDtos);
      mockMvc.perform(
                   get(BeerController.BEER_PATH)
                         .accept(MediaType.APPLICATION_JSON)
                     )
             .andExpect(
                   status().isOk()
                       )
             .andExpect(
                   content().contentType(MediaType.APPLICATION_JSON)
                       )
             .andExpect(jsonPath("$.length()", is(3)));

   }

   @Test
   void testUpdateBeer() throws Exception {
      final BeerDto beerDto = beerServiceImpl.listBeers().get(0);

      mockMvc.perform(put(BeerController.BEER_PATH + "/" + beerDto.getId().toString())
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(beerDto)))
             .andExpect(status().isNoContent());

      verify(beerService).updateBeerById(any(UUID.class), any(BeerDto.class));
   }

   @Test
   void testDeleteBeer() throws Exception {
      final BeerDto beerDto = beerServiceImpl.listBeers().get(0);

      mockMvc.perform(delete(BeerController.BEER_PATH + "/" + beerDto.getId())
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isNoContent());

      verify(beerService).deleteById(uuidArgumentCaptor.capture());
      assertThat(beerDto.getId()).isEqualTo(uuidArgumentCaptor.getValue());
   }

   @Test
   void testPatchBeer() throws Exception {
      final BeerDto beerDto = beerServiceImpl.listBeers().get(0);

      Map<String, Object> beerMap = new HashMap<>();
      beerMap.put("beerName", "Jupiler");

      mockMvc.perform(patch(BeerController.BEER_PATH_ID, beerDto.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(beerMap)))
             .andExpect(status().isNoContent());

      verify(beerService).patchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
      assertThat(beerDto.getId()).isEqualTo(uuidArgumentCaptor.getValue());
      assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
   }

   @Test
   void testBeerNotFound() throws Exception {
      when(beerService.getBeerById(any(UUID.class))).thenThrow(NotFoundException.class);

      mockMvc.perform(
                   get(BeerController.BEER_PATH_ID, UUID.randomUUID()))
             .andExpect(status().isNotFound());
   }

   @Test
   void testCreateBeerNullBeerName() throws Exception {
      final BeerDto beerDto = BeerDto.builder().build();

      when(beerService.saveNewBeer(any(BeerDto.class))).thenReturn(beerServiceImpl.listBeers().get(1));

      final MvcResult mvcResult = mockMvc.perform(post(BeerController.BEER_PATH)
                                                        .accept(MediaType.APPLICATION_JSON)
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(objectMapper.writeValueAsString(beerDto)))
                                         .andExpect(status().isBadRequest())
                                         .andReturn();
      log.debug(mvcResult.getResponse().getContentAsString());
   }
}