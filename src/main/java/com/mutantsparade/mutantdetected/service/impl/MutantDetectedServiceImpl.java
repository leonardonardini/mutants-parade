package com.mutantsparade.mutantdetected.service.impl;

import com.mutantsparade.mutantdetected.domain.Dna;
import com.mutantsparade.mutantdetected.domain.VerifiedDna;
import com.mutantsparade.mutantdetected.errors.InvalidDnaCodeException;
import com.mutantsparade.mutantdetected.service.DnaLabService;
import com.mutantsparade.mutantdetected.service.MutantDetectedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class MutantDetectedServiceImpl implements MutantDetectedService {

    Logger log = LoggerFactory.getLogger(MutantDetectedServiceImpl.class);

    @Autowired
    DnaLabService dnaLabService;

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
    @Override
    public Boolean isMutant(Dna dna) {
        Boolean isMutant;

        validateDnaFormat(dna);

        Optional<VerifiedDna> verifiedDna = dnaLabService.findByDnaHash(dna.getDnaHash());

        if (verifiedDna.isPresent()) {
            log.debug("DNA already verified");
            VerifiedDna persistentVerifiedDna = verifiedDna.get();
            isMutant = persistentVerifiedDna.isMutant();
            dnaLabService.update(persistentVerifiedDna);

        } else {
            isMutant = dnaLabService.verifyMutantAndtrackRecord(dna);
        }

        return isMutant;
    }

    /**
     * Validate is given dna has the correct format:
     * 6 chains of 6 characters each, where only ACGT characters are permited.
     *
     * If dna code is invalid, a custom RuntimeException is thrown.
     *
     * @param dna
     */
    private void validateDnaFormat(Dna dna) {
        log.debug("Validating DNA format");

        String[] dnaCode = dna.getDna();
        if (dnaCode.length != 6) {
            throw new InvalidDnaCodeException("Invalid DNA code -> must have 6 characters chains");
        }

        String dnaChainRegExp = "^[CAGT]{6}$";
        boolean notMatches = Arrays.stream(dnaCode).anyMatch(s -> !s.matches(dnaChainRegExp));
        if (notMatches) {
            throw new InvalidDnaCodeException("Invalid DNA code -> every chain must match the following regexp: " + dnaChainRegExp);
        }

    }

}
