package com.mutantsparade.mutantdetected.service;

import com.mutantsparade.mutantdetected.domain.Dna;
import com.mutantsparade.mutantdetected.errors.DnaNotMutantException;
import com.mutantsparade.mutantdetected.errors.InvalidDnaCodeException;

public interface MutantDetectedService {

    /**
     * Checks if given dna code is mutant (or human).
     * If it's NOT mutant throws a DnaNotMutantException.
     *
     * It also validates that the DNA code received has the correct format:
     * N chains of N characters each, where only ACGT characters are permited,
     * and throws InvalidDnaCodeException if it is not a valid DNA code.
     *
     * Keeps track of every dna verification in a database too.
     *
     * @param dna The dna code to verify if it's mutant or not.
     *
     * @throws InvalidDnaCodeException may be thrown by DnaUtils.validateDnaFormat
     * @throws DnaNotMutantException is throws by this method if param dna is not mutant
     *
     */
    void verifyMutant(Dna dna) throws InvalidDnaCodeException, DnaNotMutantException;

}
