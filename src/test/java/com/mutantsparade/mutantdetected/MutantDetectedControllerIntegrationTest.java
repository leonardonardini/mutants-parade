package com.mutantsparade.mutantdetected;

import com.mutantsparade.mutantdetected.domain.DnaStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MutantDetectedControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Sql("/test.sql")
    public void testGetStats() {
        ResponseEntity<DnaStats> responseEntity = testRestTemplate.getForEntity("/api/stats", DnaStats.class);
        assertEquals(10, responseEntity.getBody().getCount_human_dna());
        assertEquals(20, responseEntity.getBody().getCount_mutant_dna());
        assertEquals(2, responseEntity.getBody().getRatio());
    }

    @Test
    @Transactional
    public void testDnaAlreadyExists() {
        testRestTemplate.postForEntity ("/api/mutant", "{\\n\" +\n" +
                "                        \"\\\"dna\\\":[\\\"\\\",\\\"AAAAAA\\\",\\\"AAAAAA\\\",\\\"AAAAAA\\\",\\\"AAAAAA\\\",\\\"AAAAAA\\\"]\\n\" +\n" +
                "                        \"}", Void.TYPE) .getStatusCode().is2xxSuccessful();
    }

    @Test
    @Transactional
    public void testDnaNotExists() {
        testRestTemplate.postForEntity ("/api/mutant", "{\\n\" +\n" +
                "                        \"\\\"dna\\\":[\\\"\\\",\\\"CCCCCC\\\",\\\"AAAAAA\\\",\\\"AAAAAA\\\",\\\"AAAAAA\\\",\\\"AAAAAA\\\"]\\n\" +\n" +
                "                        \"}", Void.TYPE).getStatusCode().is2xxSuccessful();
    }
    
}
