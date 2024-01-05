package be.tvde.di.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import be.tvde.di.model.BeerCSVRecord;

public interface BeerCSVService {

   List<BeerCSVRecord> convertCSV(File file) throws FileNotFoundException;
}
