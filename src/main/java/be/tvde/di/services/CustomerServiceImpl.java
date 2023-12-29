package be.tvde.di.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import be.tvde.di.model.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

   private Map<UUID, Customer> customerMap;

   public CustomerServiceImpl() {
      Customer customer1 = Customer.builder()
                                   .id(UUID.randomUUID())
                                   .name("Customer 1")
                                   .version(1)
                                   .createdDate(LocalDateTime.now())
                                   .lastModifiedDate(LocalDateTime.now())
                                   .build();

      Customer customer2 = Customer.builder()
                                   .id(UUID.randomUUID())
                                   .name("Customer 2")
                                   .version(1)
                                   .createdDate(LocalDateTime.now())
                                   .lastModifiedDate(LocalDateTime.now())
                                   .build();

      Customer customer3 = Customer.builder()
                                   .id(UUID.randomUUID())
                                   .name("Customer 3")
                                   .version(1)
                                   .createdDate(LocalDateTime.now())
                                   .lastModifiedDate(LocalDateTime.now())
                                   .build();

      customerMap = new HashMap<>();
      customerMap.put(customer1.getId(), customer1);
      customerMap.put(customer2.getId(), customer2);
      customerMap.put(customer3.getId(), customer3);
   }

   @Override
   public List<Customer> listCustomers() {
      return customerMap.values().stream().toList();
   }

   @Override
   public Customer getCustomerById(final UUID id) {
      return customerMap.get(id);
   }
}
