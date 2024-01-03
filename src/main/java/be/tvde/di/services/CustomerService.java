package be.tvde.di.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import be.tvde.di.model.CustomerDto;

public interface CustomerService {

   List<CustomerDto> listCustomers();

   Optional<CustomerDto> getCustomerById(UUID id);

   CustomerDto saveNewCustomer(CustomerDto customerDto);

   void updateCustomerById(UUID customerId, CustomerDto customerDto);

   void deleteCustomerById(UUID customerId);

   void patchCustomerById(UUID customerId, CustomerDto customerDto);
}
