package be.tvde.di.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import be.tvde.di.entities.Customer;
import be.tvde.di.mappers.CustomerMapper;
import be.tvde.di.model.CustomerDto;
import be.tvde.di.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

   private final CustomerRepository customerRepository;
   private final CustomerMapper customerMapper;

   @Override
   public List<CustomerDto> listCustomers() {
      return customerRepository.findAll()
                               .stream()
                               .map(customerMapper::customerToCustomerDto)
                               .collect(Collectors.toList());
   }

   @Override
   public Optional<CustomerDto> getCustomerById(final UUID id) {
      final Customer customer = customerRepository.findById(id).orElse(null);
      return Optional.ofNullable(customerMapper.customerToCustomerDto(customer));
   }

   @Override
   public CustomerDto saveNewCustomer(final CustomerDto customerDto) {
      return null;
   }

   @Override
   public void updateCustomerById(final UUID customerId, final CustomerDto customerDto) {

   }

   @Override
   public void deleteCustomerById(final UUID customerId) {

   }

   @Override
   public void patchCustomerById(final UUID customerId, final CustomerDto customerDto) {

   }
}
