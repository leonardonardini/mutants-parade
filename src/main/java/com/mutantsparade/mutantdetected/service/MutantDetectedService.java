package com.mutantsparade.mutantdetected.service;

import com.mutantsparade.mutantdetected.domain.Dna;
import com.mutantsparade.mutantdetected.domain.DnaStats;

import java.util.concurrent.CompletableFuture;

public interface MutantDetectedService {

    Boolean verifyDna(Dna dna);

    CompletableFuture<DnaStats> getStats();

}
