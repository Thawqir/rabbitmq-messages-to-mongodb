package com.example.messagestomongodb.csvparse;

import com.example.messagestomongodb.domain.CovidCases;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Component
public class CSVParse {

    public List<CovidCases> parseCSVFileIntoList() throws IOException{

        String fileName = "/Users/thawqir/Documents/dev/messages-to-mongodb/src/main/resources/owid-covid-data_3.csv";

        List<CovidCases> covidCasesList = new CsvToBeanBuilder(new FileReader(fileName))
                .withType(CovidCases.class)
                .build()
                .parse();

        return covidCasesList;
    }

}
