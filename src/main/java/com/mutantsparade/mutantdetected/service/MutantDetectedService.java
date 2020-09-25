package com.mutantsparade.mutantdetected.service;

import com.mutantsparade.mutantdetected.domain.Dna;

public interface MutantDetectedService {

    /**
     * Checks if given dna code is mutant (or human).
     * If it's mutant returns true, otherwise returns false.
     *
     * It also validates that the DNA code received has the correct format:
     * 6 chains of 6 characters each, where only ACGT characters are permited.
     *
     * Keeps track of every dna verification in a database too.
     *
     * @param dna The dna code to verify if it's mutant or not.
     */
    Boolean isMutant(Dna dna);

}
