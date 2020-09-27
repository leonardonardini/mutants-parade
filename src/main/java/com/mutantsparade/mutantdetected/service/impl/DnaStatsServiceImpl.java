package com.mutantsparade.mutantdetected.service.impl;

import com.mutantsparade.mutantdetected.domain.DnaStats;
import com.mutantsparade.mutantdetected.repository.DnaStatsRepository;
import com.mutantsparade.mutantdetected.service.DnaStatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class DnaStatsServiceImpl implements DnaStatsService {

    private static final Logger log = LoggerFactory.getLogger(DnaStatsServiceImpl.class);

    @Autowired
    private DnaStatsRepository dnaStatsRepository;

    /**
     * Calculate and return stats about total dna verification requests:
     * 1. Number of requests with mutant dna (countMutantDna)
     * 2. Number of requests with human dna (countHumanDna)
     * 3. Percent of mutants requests over humans (ratio)
     *
     * @return a DnaStats object with the stats described above
     */
    @Override
    @Async
    public CompletableFuture<DnaStats> getStats() {
        log.debug("Querying stats");

        return dnaStatsRepository.getStats();
    }

}
