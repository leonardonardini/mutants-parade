package com.mutantsparade.mutantdetected.web.rest;

import com.mutantsparade.mutantdetected.domain.DnaStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class contains INTEGRATION tests, which means
 * that all layers are tested over a real running server.
 * This can be achived thanks to Spring's TestRestTemplate
 * and setting of "webEnviroment" on @SpringBootTest annotation.
 *
 */
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MutantDetectedControllerIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    //init variables used in tests
    private static final String statsUri = "/api/stats";
    private static final String mutantsUri = "/api/mutant";

    private static final HttpHeaders headers;
    static {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    private static final String dnaMutantAlreadyExists = "{\n" +
            "  \"dna\": [\n" +
            "    \"AAAA\",\"AAAA\",\"AAAA\",\"AAAA\"\n" +
            "  ]\n" +
            "}";

    private static final String dnaNotMutant = "{\n" +
            "  \"dna\": [\n" +
            "    \"ACGT\",\"TGCA\",\"CATT\",\"AAAC\"\n" +
            "  ]\n" +
            "}";

    private static final String dnaMutant = "{\n" +
            "  \"dna\": [\n" +
            "    \"ACGT\",\"ACGT\",\"ACGT\",\"ACGT\"\n" +
            "  ]\n" +
            "}";

    private static final String invalidDna_Null = "{}";

    private static final String invalidDna_Empty = "{\n" +
            "  \"dna\": []\n" +
            "}";

    private static final String invalidDna_Bad = "{\n" +
            "  \"dna\": [\n" +
            "    \"ACGT\",\"ACGT\",\"AT\",\"ACGT\"\n" +
            "  ]\n" +
            "}";

    @Test
    @Sql("/test.sql")
    public void testGetStats() {
        ResponseEntity<DnaStats> responseEntity = testRestTemplate.getForEntity(statsUri, DnaStats.class);
        assertEquals(10, responseEntity.getBody().getCountHumanDna());
        assertEquals(20, responseEntity.getBody().getCountMutantDna());
        assertEquals(2, responseEntity.getBody().getRatio());
    }

    @Test
    @Transactional
    public void testMutantAlreadyExists() {

        HttpEntity<String> request = new HttpEntity<>(dnaMutantAlreadyExists, headers);

        ResponseEntity<String> mutantResponse = testRestTemplate.postForEntity(mutantsUri, request, String.class);
        assertEquals(HttpStatus.OK, mutantResponse.getStatusCode());

    }

    @Test
    @Transactional
    public void testNotMutant() {

        HttpEntity<String> request = new HttpEntity<>(dnaNotMutant, headers);

        ResponseEntity<String> mutantResponse = testRestTemplate.postForEntity(mutantsUri, request, String.class);
        assertEquals(HttpStatus.FORBIDDEN, mutantResponse.getStatusCode());

    }

    @Test
    @Transactional
    public void testMutant() {

        HttpEntity<String> request = new HttpEntity<>(dnaMutant, headers);

        ResponseEntity<String> mutantResponse = testRestTemplate.postForEntity(mutantsUri, request, String.class);
        assertEquals(HttpStatus.OK, mutantResponse.getStatusCode());

    }

    @Test
    @Transactional
    public void testInvalidDna_null() {

        HttpEntity<String> request = new HttpEntity<>(invalidDna_Null, headers);

        ResponseEntity<String> mutantResponse = testRestTemplate.postForEntity(mutantsUri, request, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, mutantResponse.getStatusCode());

    }

    @Test
    @Transactional
    public void testInvalidDna_empty() {

        HttpEntity<String> request = new HttpEntity<>(invalidDna_Empty, headers);

        ResponseEntity<String> mutantResponse = testRestTemplate.postForEntity(mutantsUri, request, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, mutantResponse.getStatusCode());

    }

    @Test
    @Transactional
    public void testInvalidDna_badFormat() {

        HttpEntity<String> request = new HttpEntity<>(invalidDna_Bad, headers);

        ResponseEntity<String> mutantResponse = testRestTemplate.postForEntity(mutantsUri, request, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, mutantResponse.getStatusCode());

    }

}
