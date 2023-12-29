package be.tvde.di.services;

import java.util.List;
import java.util.UUID;
import be.tvde.di.model.Customer;

public interface CustomerService {

   List<Customer> listCustomers();

   Customer getCustomerById(UUID id);
}
