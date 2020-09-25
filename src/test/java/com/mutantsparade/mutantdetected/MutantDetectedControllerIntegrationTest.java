package com.mutantsparade.mutantdetected;

import com.mutantsparade.mutantdetected.domain.DnaStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MutantDetectedControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql("/test.sql")
    public void getStats() { //throws ExecutionException, InterruptedException {
//        ResponseEntity<DnaStats> responseEntity = testRestTemplate.getForEntity("/api/stats", DnaStats.class);
//        assertEquals(10, responseEntity.getBody().getCount_human_dna());
//        assertEquals(0, responseEntity.getBody().getCount_mutant_dna());
//        assertEquals(1, responseEntity.getBody().getRatio());
    }

}
