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
import be.tvde.di.model.Beer;
import be.tvde.di.model.Customer;
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
   private ArgumentCaptor<Customer> customerArgumentCaptor;

   @BeforeEach
   void init() {
      customerServiceImpl = new CustomerServiceImpl();
   }

   @Test
   void testGetCustomerById() throws Exception {

      final Customer testCustomer = customerServiceImpl.listCustomers().get(0);

      when(customerService.getCustomerById(testCustomer.getId())).thenReturn(testCustomer);

      mockMvc.perform(
                   get("/api/v1/customer/" + testCustomer.getId())
                         .accept(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk())
             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
             .andExpect(jsonPath("$.id", is(testCustomer.getId().toString())));
   }

   @Test
   void testListCustomers() throws Exception {
      when(customerService.listCustomers()).thenReturn(customerServiceImpl.listCustomers());

      mockMvc.perform(
                   get("/api/v1/customer")
                         .accept(MediaType.APPLICATION_JSON))
             .andExpect(status().isOk())
             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
             .andExpect(jsonPath("$.length()", is(3)));
   }

   @Test
   void testCreateCustomer() throws Exception {
      final Customer customer = customerServiceImpl.listCustomers().get(0);
      customer.setVersion(null);
      customer.setId(null);

      when(customerService.saveNewCustomer(any(Customer.class))).thenReturn(customerServiceImpl.listCustomers().get(1));
      mockMvc.perform(post("/api/v1/customer")
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customer)))
             .andExpect(header().exists("Location"))
             .andExpect(status().isCreated());
   }

   @Test
   void testUpdateCustomer() throws Exception {
      final Customer customer = customerServiceImpl.listCustomers().get(0);

      mockMvc.perform(put("/api/v1/customer/" + customer.getId())
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customer)))
            .andExpect(status().isNoContent());

      verify(customerService).updateCustomerById(any(UUID.class), any(Customer.class));
   }

   @Test
   void testDeleteCustomer() throws Exception {
      final Customer customer = customerServiceImpl.listCustomers().get(0);

      mockMvc.perform(delete("/api/v1/customer/" + customer.getId())
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON))
             .andExpect(status().isNoContent());
      verify(customerService).deleteCustomerById(uuidArgumentCaptor.capture());
      assertEquals(customer.getId(), uuidArgumentCaptor.getValue());
   }

   @Test
   void testPatchCustomer() throws Exception {
      final Customer customer = customerServiceImpl.listCustomers().get(0);

      final Map<String, Object> customerMap = new HashMap<>();
      customerMap.put("name", "tvde");

      mockMvc.perform(patch("/api/v1/customer/" + customer.getId())
                            .accept(MediaType.APPLICATION_JSON)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(customerMap)))
             .andExpect(status().isNoContent());
      verify(customerService).patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());
      assertEquals(customer.getId(), uuidArgumentCaptor.getValue());
      assertEquals(customerArgumentCaptor.getValue().getName(), "tvde");
   }
}