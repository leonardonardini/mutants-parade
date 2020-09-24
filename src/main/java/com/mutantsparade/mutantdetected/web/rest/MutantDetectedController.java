package com.mutantsparade.mutantdetected.web.rest;

import com.mutantsparade.mutantdetected.domain.Dna;
import com.mutantsparade.mutantdetected.domain.DnaStats;
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

    Logger log = LoggerFactory.getLogger(MutantDetectedController.class);

    @Autowired
    MutantDetectedService mutantDetectedService;

    @PostMapping(path= "/mutant")
    public void mutant(@RequestBody Dna dna)  {
        log.info("Dna to verify: " + Arrays.toString(dna.getDna()));

        if (!(mutantDetectedService.verifyDna(dna))) {
            String message = "You are NOT a mutant: " + Arrays.toString(dna.getDna());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, message);
        }
    }

    @GetMapping(path = "/stats")
    public CompletableFuture<DnaStats> stats() {
        log.info("Looking for stats ...");

        return mutantDetectedService.getStats();
    }

}

