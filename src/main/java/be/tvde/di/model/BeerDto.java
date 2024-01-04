package be.tvde.di.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BeerDto {
   private UUID id;
   private Integer version;
   @NotNull
   @NotBlank
   private String beerName;
   private BeerStyle beerStyle;
   private String upc;
   private Integer quantityOnHand;
   private BigDecimal price;
   private LocalDateTime createdDate;
   private LocalDateTime updateDate;
}
