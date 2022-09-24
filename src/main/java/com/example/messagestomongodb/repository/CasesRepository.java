package com.example.messagestomongodb.repository;

import com.example.messagestomongodb.domain.CovidCases;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CasesRepository extends MongoRepository<CovidCases, Integer> {

    @Query(value = "{ '_id' : ?0 }", fields = "{ '_id' : 1}", count=true)
    Integer returnIntegerFromDB(Integer id);

}
