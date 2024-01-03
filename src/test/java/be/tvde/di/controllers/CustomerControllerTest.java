package be.tvde.di.controllers;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import be.tvde.di.model.CustomerDto;
import be.tvde.di.services.CustomerService;
import be.tvde.di.services.CustomerServiceImpl;

@WebMvcTest(value = CustomerController.class)
class CustomerControllerTest {

   @Autowired
   private MockMvc mockMvc;
   @Autowired
   private ObjectMapper objectMapper;

   @MockBean
   private CustomerService customerService;

   private CustomerServiceImpl customerServiceImpl;

   @Captor
   private ArgumentCaptor<UUID> uuidArgumentCaptor;
   @Captor
   private ArgumentCaptor<CustomerDto> customerArgumentCaptor;

   @BeforeEach
   void init() {
      customerServiceImpl = new CustomerServiceImpl();
   }

   @Test
   void testGetCustomerById() throws Exception {

      final CustomerDto testCustomerDto = customerServiceImpl.listCustomers().get(0);

      when(customerService.getCustomerById(testCustomerDto.getId())).thenReturn(Optional.of(testCustomerDto));

      mockMvc.perform(
                   get(CustomerController.CUSTOMER_PATH_ID, testCustomerDto.getId())
                         .accept(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk())
             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
             .andExpect(jsonPath("$.id", is(testCustomerDto.getId().toString())));
   }

   @Test
   void testListCustomers() throws Exception {
      when(customerService.listCustomers()).thenReturn(customerServiceImpl.listCustomers());

      mockMvc.perform(
                   get(CustomerController.CUSTOMER_PATH)
                         .accept(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk())
             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
             .andExpect(jsonPath("$.length()", is(3)));
   }

   @Test
   void testCreateCustomer() throws Exception {
      final CustomerDto customerDto = customerServiceImpl.listCustomers().get(0);
      customerDto.setVersion(null);
      customerDto.setId(null);

      when(customerService.saveNewCustomer(any(CustomerDto.class))).thenReturn(customerServiceImpl.listCustomers().get(1));
      mockMvc.perform(post(CustomerController.CUSTOMER_PATH)
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customerDto)))
             .andExpect(header().exists("Location"))
             .andExpect(status().isCreated());
   }

   @Test
   void testUpdateCustomer() throws Exception {
      final CustomerDto customerDto = customerServiceImpl.listCustomers().get(0);

      mockMvc.perform(put(CustomerController.CUSTOMER_PATH_ID, customerDto.getId())
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customerDto)))
             .andExpect(status().isNoContent());

      verify(customerService).updateCustomerById(any(UUID.class), any(CustomerDto.class));
   }

   @Test
   void testDeleteCustomer() throws Exception {
      final CustomerDto customerDto = customerServiceImpl.listCustomers().get(0);

      mockMvc.perform(delete(CustomerController.CUSTOMER_PATH_ID, customerDto.getId())
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isNoContent());
      verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());
      assertEquals(customerDto.getId(), uuidArgumentCaptor.getValue());
   }

   @Test
   void testPatchCustomer() throws Exception {
      final CustomerDto customerDto = customerServiceImpl.listCustomers().get(0);

      final Map<String, Object> customerMap = new HashMap<>();
      customerMap.put("name", "tvde");

      mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, customerDto.getId())
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customerMap)))
             .andExpect(status().isNoContent());
      verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());
      assertEquals(customerDto.getId(), uuidArgumentCaptor.getValue());
      assertEquals(customerArgumentCaptor.getValue().getName(), "tvde");
   }
}