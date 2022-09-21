package com.example.messagestomongodb.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(language = "cases")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CovidCases{

    @Id
    @JsonProperty("id")
    @CsvBindByPosition(position = 0)
    private Integer id;
    @JsonProperty("Continent")
    @CsvBindByPosition(position = 1)
    private String Continent;
    @JsonProperty("Country")
    @CsvBindByPosition(position = 2)
    private String Country;
    @JsonProperty("dateTime")
    @CsvBindByPosition(position = 3)
    private String dateTime;
    @JsonProperty("totalCases")
    @CsvBindByPosition(position = 4)
    private Integer totalCases;
}
