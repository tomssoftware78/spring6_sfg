package be.tvde.di.services;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
   public Optional<Customer> getCustomerById(final UUID id) {
      return Optional.of(customerMap.get(id));
   }

   @Override
   public void patchCustomerById(UUID customerId, Customer customer) {
      Customer existing = customerMap.get(customerId);

      if (StringUtils.hasText(customer.getName())) {
         existing.setName(customer.getName());
      }
   }

   @Override
   public void deleteCustomerById(UUID customerId) {
      customerMap.remove(customerId);
   }

   @Override
   public void updateCustomerById(UUID customerId, Customer customer) {
      Customer existing = customerMap.get(customerId);
      existing.setName(customer.getName());
   }

   @Override
   public Customer saveNewCustomer(Customer customer) {
      Customer savedCustomer = Customer.builder()
                                       .id(UUID.randomUUID())
                                       .version(1)
                                       .lastModifiedDate(LocalDateTime.now())
                                       .createdDate(LocalDateTime.now())
                                       .name(customer.getName())
                                       .build();

      customerMap.put(savedCustomer.getId(), savedCustomer);

      return savedCustomer;
   }

}
