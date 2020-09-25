package com.mutantsparade.mutantdetected.service;

import com.mutantsparade.mutantdetected.domain.DnaStats;

import java.util.concurrent.CompletableFuture;

public interface DnaStatsService {

    /**
     * Calculate and return stats about total dna verification requests:
     * 1. Number of requests with mutant dna (count_mutant_dna)
     * 2. Number of requests with human dna (count_human_dna)
     * 3. Percent of mutants requests over humans (ratio)
     *
     * @return stats described above
     */
    CompletableFuture<DnaStats> getStats();

}
