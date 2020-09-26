package com.mutantsparade.mutantdetected.service;

import com.mutantsparade.mutantdetected.domain.Dna;
import com.mutantsparade.mutantdetected.domain.VerifiedDna;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface DnaLabService {

    /**
     * Look at the persistence service to find an already verified DNA
     * for a given DNA hash code.
     *
     * @param dna DNA hash code to find
     * @return a DNA already verified
     */
    Optional<VerifiedDna> findByDnaHash(String dna);

    /**
     * Updates in the database the times that a certain DNA was verified.
     *
     * @param verifiedDna the DNA being verified
     */
    void update(VerifiedDna verifiedDna);

    /**
     * Checks if given DNA code is mutant (or human).
     * If it's mutant returns true, otherwise returns false.
     *
     * Keeps track of every DNA verification in a database too.
     *
     * PRECONDITION: DNA code must be in a correct format:
     * N chains of N characters each, where only ACGT characters are permited.
     *
     * @param dna The DNA code to verify if it's mutant or not.
     * @return true if DNA es mutant, false otherwise
     */
    CompletableFuture<Boolean> verifyMutantAndtrackRecord(Dna dna);

}
