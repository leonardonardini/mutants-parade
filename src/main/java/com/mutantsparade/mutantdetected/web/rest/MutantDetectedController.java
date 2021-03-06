package com.mutantsparade.mutantdetected.web.rest;

import com.mutantsparade.mutantdetected.domain.Dna;
import com.mutantsparade.mutantdetected.domain.DnaStats;
import com.mutantsparade.mutantdetected.errors.DnaNotMutantException;
import com.mutantsparade.mutantdetected.errors.InvalidDnaCodeException;
import com.mutantsparade.mutantdetected.service.DnaStatsService;
import com.mutantsparade.mutantdetected.service.MutantDetectedService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class MutantDetectedController {

    private static final Logger log = LoggerFactory.getLogger(MutantDetectedController.class);

    @Autowired
    private MutantDetectedService mutantDetectedService;

    @Autowired
    private DnaStatsService dnaStatsService;

    public static final String ENTRY_ALLOWED = "ENTRY_ALLOWED";

    /**
     * Checks if given dna code is mutant or not, based on an algorithm.
     * If it's mutant, returns HTTP status 200, if not retunrs HTTP status 403.
     *
     * It also validates that the DNA code received has the correct format:
     * N chains of N characters each, where only ACGT characters are permited.
     * If dna code is invalid, an exception with HTTP status 400 is thrown.
     *
     * Keeps track of every dna verification in a database.
     *
     * @param dna The dna code to verify if it's mutant or not.
     */
    @PostMapping(path= "/mutant")
    public CompletableFuture<String> mutant(@RequestBody Dna dna)  {
        log.info("Dna to verify: " + Arrays.toString(dna.getDna()));

        return CompletableFuture.supplyAsync(() -> {
            try {
                mutantDetectedService.verifyMutant(dna);

            } catch (InvalidDnaCodeException idce) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, idce.getMessage(), idce);

            } catch (DnaNotMutantException dnme) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, dnme.getMessage(), dnme);

            }
            return ENTRY_ALLOWED;
        });

    }

    /**
     * Calculate and return stats about total dna verification requests:
     * 1. Number of requests with mutant dna (count_mutant_dna)
     * 2. Number of requests with human dna (count_human_dna)
     * 3. Percent of mutants requests over humans (ratio)
     *
     * @return a DnaStats object with the stats described above
     * that is finally converted to JSON format.
     */
    @GetMapping(path = "/stats")
    public CompletableFuture<DnaStats> stats() {
        log.info("Looking for dna verification stats ...");

        return dnaStatsService.getStats();
    }

}

