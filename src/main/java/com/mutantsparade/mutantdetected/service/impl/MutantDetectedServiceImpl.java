package com.mutantsparade.mutantdetected.service.impl;

import com.mutantsparade.mutantdetected.domain.Dna;
import com.mutantsparade.mutantdetected.domain.VerifiedDna;
import com.mutantsparade.mutantdetected.errors.DnaNotMutantException;
import com.mutantsparade.mutantdetected.errors.InvalidDnaCodeException;
import com.mutantsparade.mutantdetected.service.DnaLabService;
import com.mutantsparade.mutantdetected.service.MutantDetectedService;
import com.mutantsparade.mutantdetected.utils.DnaUtils;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class MutantDetectedServiceImpl implements MutantDetectedService {

    private static final Logger log = LoggerFactory.getLogger(MutantDetectedServiceImpl.class);

    @Autowired
    private DnaLabService dnaLabService;

    /**
     * Checks if given dna code is mutant (or human).
     * If it's NOT mutant throws a DnaNotMutantException.
     *
     * It also validates that the DNA code received has the correct format:
     * 6 chains of 6 characters each, where only ACGT characters are permited,
     * and throws InvalidDnaCodeException if it is not a valid DNA code.
     *
     * Keeps track of every dna verification in a database too.
     *
     * @param dna The dna code to verify if it's mutant or not.
     *
     * @throws InvalidDnaCodeException
     * @throws DnaNotMutantException
     *
     */
    @SneakyThrows
    @Override
    public void verifyMutant(Dna dna) throws InvalidDnaCodeException, DnaNotMutantException {
        Boolean isMutant;

        DnaUtils.validateDnaFormat(dna.getDna());

        Optional<VerifiedDna> verifiedDna = dnaLabService.findByDnaHash(dna.getDnaHash());

        if (verifiedDna.isPresent()) {
            log.debug("DNA already verified");
            VerifiedDna persistentVerifiedDna = verifiedDna.get();
            isMutant = persistentVerifiedDna.isMutant();
            dnaLabService.update(persistentVerifiedDna);

        } else {
            CompletableFuture<Boolean> cf = dnaLabService.verifyMutantAndtrackRecord(dna);
            isMutant = cf.get();
        }

        if (!isMutant) {
            throw new DnaNotMutantException(String.format("You are NOT a mutant: %s", Arrays.toString(dna.getDna())));
        }
    }

}
