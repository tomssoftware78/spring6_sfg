package be.tvde.di.controllers;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import be.tvde.di.exception.NotFoundException;
import be.tvde.di.model.Customer;
import be.tvde.di.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class CustomerController {

   public static final String CUSTOMER_PATH = "/api/v1/customer";
   public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

   private CustomerService customerService;

   @GetMapping(value = CUSTOMER_PATH)
   public List<Customer> listAllCustomers() {
      return customerService.listCustomers();
   }

   @GetMapping(value = CUSTOMER_PATH_ID)
   public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {
      return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
   }

   @PatchMapping(value = CUSTOMER_PATH_ID)
   public ResponseEntity patchCustomerById(@PathVariable("customerId") UUID customerId,
                                           @RequestBody Customer customer) {

      customerService.patchCustomerById(customerId, customer);

      return new ResponseEntity(HttpStatus.NO_CONTENT);
   }

   @DeleteMapping(value = CUSTOMER_PATH_ID)
   public ResponseEntity deleteCustomerById(@PathVariable("customerId") UUID customerId) {

      customerService.deleteCustomerById(customerId);

      return new ResponseEntity(HttpStatus.NO_CONTENT);
   }

   @PutMapping(value = CUSTOMER_PATH_ID)
   public ResponseEntity updateCustomerByID(@PathVariable("customerId") UUID customerId,
                                            @RequestBody Customer customer) {

      customerService.updateCustomerById(customerId, customer);

      return new ResponseEntity(HttpStatus.NO_CONTENT);
   }

   @PostMapping(value = CUSTOMER_PATH)
   public ResponseEntity handlePost(@RequestBody Customer customer) {
      Customer savedCustomer = customerService.saveNewCustomer(customer);

      HttpHeaders headers = new HttpHeaders();
      headers.add("Location", "/api/v1/customer/" + savedCustomer.getId().toString());

      return new ResponseEntity(headers, HttpStatus.CREATED);
   }

}
