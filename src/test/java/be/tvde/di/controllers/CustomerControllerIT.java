package be.tvde.di.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import be.tvde.di.entities.Customer;
import be.tvde.di.exception.NotFoundException;
import be.tvde.di.model.CustomerDto;
import be.tvde.di.repositories.CustomerRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
public class CustomerControllerIT {

   @Autowired
   private CustomerController customerController;
   @Autowired
   private CustomerRepository customerRepository;

   @Test
   void testListCustomers() {
      final List<CustomerDto> customerDtos = customerController.listAllCustomers();
      assertThat(customerDtos.size()).isEqualTo(3);
   }

   @Test
   void testGetCustomerById() {
      final Customer customer = customerRepository.findAll().get(0);
      final CustomerDto customerDto = customerController.getCustomerById(customer.getId());
      assertThat(customerDto).isNotNull();
      assertThat(customerDto.getId()).isEqualTo(customer.getId());
   }

   @Test
   void testCustomerIdNotFound() {
      assertThrows(NotFoundException.class, ()->{
         customerController.getCustomerById(UUID.randomUUID());
      });
   }

   @Transactional
   @Rollback
   @Test
   void testEmptyList() {
      customerRepository.deleteAll();
      assertTrue(customerController.listAllCustomers().isEmpty());
   }
}
