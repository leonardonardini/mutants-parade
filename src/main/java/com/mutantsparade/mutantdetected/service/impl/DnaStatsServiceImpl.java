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

    Logger log = LoggerFactory.getLogger(DnaStatsServiceImpl.class);

    @Autowired
    DnaStatsRepository dnaStatsRepository;

    /**
     * Calculate and return stats about total dna verification requests:
     * 1. Number of requests with mutant dna (count_mutant_dna)
     * 2. Number of requests with human dna (count_human_dna)
     * 3. Percent of mutants requests over humans (ratio)
     *
     * @return stats described above
     */
    @Override
    @Async
    public CompletableFuture<DnaStats> getStats() {
        log.debug("Quering stats");

        return dnaStatsRepository.getStats();
    }

}
