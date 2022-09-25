package com.example.messagestomongodb.repository;

import com.example.messagestomongodb.domain.CovidCases;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

@DataMongoTest
@RunWith(SpringRunner.class)
public class CasesRepositoryIntegrationTest {

    @Autowired
    CasesRepository casesRepository;

    @Test
    public void return1FromDatabase_WhenRecordExists(){

        CovidCases cases = new CovidCases(1,"Europe","France","24/09/2022",500);
        casesRepository.save(cases);

        Integer actual = casesRepository.returnIntegerFromDB(cases.getId());
        Assert.assertTrue(actual == 1);
    }

    @Test
    public void return0FromDatabase_WhenRecordDoesNotExist(){

        Integer actual = casesRepository.returnIntegerFromDB(99999999);
        Assert.assertTrue(actual == 0);
    }
}
