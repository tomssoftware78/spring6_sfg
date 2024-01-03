package be.tvde.di.mappers;

import org.mapstruct.Mapper;
import be.tvde.di.entities.Customer;
import be.tvde.di.model.CustomerDto;

@Mapper
public interface CustomerMapper {

   Customer customerDtoToCustomer(final CustomerDto customerDto);

   CustomerDto customerToCustomerDto(final Customer customer);
}
