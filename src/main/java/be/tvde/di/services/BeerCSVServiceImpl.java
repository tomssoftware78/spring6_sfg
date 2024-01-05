package be.tvde.di.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import org.springframework.stereotype.Service;
import com.opencsv.bean.CsvToBeanBuilder;
import be.tvde.di.model.BeerCSVRecord;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BeerCSVServiceImpl implements BeerCSVService {

   @Override
   public List<BeerCSVRecord> convertCSV(final File file) throws FileNotFoundException {
      List<BeerCSVRecord> beerCSVRecords = new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(file))
            .withType(BeerCSVRecord.class)
            .build()
            .parse();
      return beerCSVRecords;
   }
}
