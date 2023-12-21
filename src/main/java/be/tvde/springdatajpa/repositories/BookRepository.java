package be.tvde.springdatajpa.repositories;

import org.springframework.data.repository.CrudRepository;
import be.tvde.springdatajpa.domain.Book;

public interface BookRepository extends CrudRepository<Book, String> {

}
