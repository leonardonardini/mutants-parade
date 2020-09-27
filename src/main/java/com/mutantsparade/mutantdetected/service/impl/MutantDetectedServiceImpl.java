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
    @SneakyThrows
    @Override
    public void verifyMutant(Dna dna) throws InvalidDnaCodeException, DnaNotMutantException {
        Boolean isMutant;

        //may throws InvalidDnaCodeException
        DnaUtils.validateDnaFormat(dna.getDna());

        Optional<VerifiedDna> verifiedDna = dnaLabService.findByDnaHash(dna.getDnaHash()).get();

        if (verifiedDna.isPresent()) {
            log.debug("DNA already verified");
            VerifiedDna persistentVerifiedDna = verifiedDna.get();
            //take the result of the already verified DNA entity
            isMutant = persistentVerifiedDna.isMutant();
            dnaLabService.incrementQuantityAndSave(persistentVerifiedDna);

        } else {
            //first time the given DNA is being verified
            isMutant = dnaLabService.verifyMutantAndTrackRecord(dna).get();
        }

        if (!isMutant) {
            throw new DnaNotMutantException(String.format("You are NOT a mutant: %s", Arrays.toString(dna.getDna())));
        }
    }

}
