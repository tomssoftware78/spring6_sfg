package be.tvde.di.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.GenericGenerator;
import be.tvde.di.model.BeerStyle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Beer {

   @Id
   @GeneratedValue(generator = "UUID")
   @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
   @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
   private UUID id;

   @Version
   private Integer version;

   @NotNull
   @NotBlank
   private String beerName;

   @NotNull
   private BeerStyle beerStyle;

   @NotNull
   @NotBlank
   private String upc;
   private Integer quantityOnHand;

   @NotNull
   private BigDecimal price;
   private LocalDateTime createdDate;
   private LocalDateTime updateDate;

}
