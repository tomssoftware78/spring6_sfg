package be.tvde.di.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import be.tvde.di.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

}
