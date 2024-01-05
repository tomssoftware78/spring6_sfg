package be.tvde.di.services;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import be.tvde.di.model.BeerCSVRecord;

public class BeerCSVServiceImplTest {

   private BeerCSVService beerCSVService = new BeerCSVServiceImpl();

   @Test
   void convertCSV() throws FileNotFoundException {
      final File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

      final List<BeerCSVRecord> records = beerCSVService.convertCSV(file);

      System.out.println(records.size());

      assertThat(records.size()).isGreaterThan(0);
   }
}
