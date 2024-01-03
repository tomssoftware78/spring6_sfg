package be.tvde.di.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import be.tvde.di.entities.Beer;

public interface BeerRepository extends JpaRepository<Beer, UUID> {

}
