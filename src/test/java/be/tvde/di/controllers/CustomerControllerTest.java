package be.tvde.di.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import be.tvde.di.model.Customer;
import be.tvde.di.services.CustomerService;
import be.tvde.di.services.CustomerServiceImpl;

@WebMvcTest(value = CustomerController.class)
class CustomerControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @MockBean
   private CustomerService customerService;

   private CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

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
}