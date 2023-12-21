package be.tvde.springdatajpa.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import be.tvde.springdatajpa.domain.Author;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {

}
