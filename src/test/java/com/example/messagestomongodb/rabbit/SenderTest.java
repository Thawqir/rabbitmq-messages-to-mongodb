package com.example.messagestomongodb.rabbit;

import com.example.messagestomongodb.domain.CovidCases;
import com.example.messagestomongodb.repository.CasesRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SenderTest {

    @InjectMocks
    private Sender sender;

    @Mock
    private CasesRepository repository;

    @Test
    public void returnTrue_ForDuplicates(){
        CovidCases cases = new CovidCases(1,"Europe","United Kingdom","23/09/2022",1000);

        when(repository.returnIntegerFromDB(any(Integer.class))).thenReturn(1);

        boolean isDuplicate = sender.checkForDuplicateRecords(1,cases);

        Assert.assertTrue(isDuplicate);

    }

    @Test
    public void returnFalse_ForDuplicates(){
        CovidCases cases = new CovidCases(1,"Europe","United Kingdom","23/09/2022",1000);

        when(repository.returnIntegerFromDB(any(Integer.class))).thenReturn(0);

        boolean isDuplicate = sender.checkForDuplicateRecords(1,cases);

        Assert.assertFalse(isDuplicate);

    }
}
