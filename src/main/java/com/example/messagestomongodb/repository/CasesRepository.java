package com.example.messagestomongodb.repository;

import com.example.messagestomongodb.domain.CovidCases;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasesRepository extends MongoRepository<CovidCases, Integer> {


}
