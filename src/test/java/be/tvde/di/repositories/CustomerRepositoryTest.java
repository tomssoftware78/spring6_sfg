package be.tvde.di.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import be.tvde.di.entities.Customer;

@DataJpaTest
class CustomerRepositoryTest {

   @Autowired
   private CustomerRepository customerRepository;

   @Test
   void testSaveCustomer() {
      final Customer savedCustomer = customerRepository.save(Customer.builder().name("tvde").build());

      assertThat(savedCustomer).isNotNull();
      assertThat(savedCustomer.getId()).isNotNull();
   }
}