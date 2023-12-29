package be.tvde.di.controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import be.tvde.di.model.Customer;
import be.tvde.di.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

   private CustomerService customerService;

   @RequestMapping(method = RequestMethod.GET)
   public List<Customer> listAllCustomers() {
      return customerService.listCustomers();
   }
   @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
   public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {
      return customerService.getCustomerById(customerId);
   }

}
