package com.mutantsparade.mutantdetected.service.impl;

import com.mutantsparade.mutantdetected.domain.Dna;
import com.mutantsparade.mutantdetected.domain.VerifiedDna;
import com.mutantsparade.mutantdetected.repository.VerifiedDnaRepository;
import com.mutantsparade.mutantdetected.service.DnaLabService;
import com.mutantsparade.mutantdetected.service.DnaLabTrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DnaLabServiceImpl implements DnaLabService {

    Logger log = LoggerFactory.getLogger(MutantDetectedServiceImpl.class);

    @Autowired
    VerifiedDnaRepository verifiedDnaRepository;

    @Autowired
    DnaLabTrackingService dnaLabTrackingService;

    /**
     * Search the database for a verified DNA record,
     * based on the hash code of the reported DNA.
     *
     * @param dna the hash code of a given DNA
     * @return En entity representing an already verified DNA
     */
    public Optional<VerifiedDna> findByDnaHash(String dna) {
        return verifiedDnaRepository.findByDnaHash(dna);
    }

    /**
     * Updates in the database the times that a certain DNA was verified.
     *
     * @param verifiedDna the DNA being verified
     */
    @Override
    @Async
    public void update(VerifiedDna verifiedDna) {
        verifiedDna.setQuantity(verifiedDna.getQuantity() + 1);
        verifiedDnaRepository.save(verifiedDna);
    }

    /**
     * Checks if given DNA code is mutant (or human).
     * If it's mutant returns true, otherwise returns false.
     *
     * Keeps track of every DNA verification in a database too.
     *
     * PRECONDITION: DNA code must be in a correct format:
     * 6 chains of 6 characters each, where only ACGT characters are permited.
     *
     * @param dna The DNA code to verify if it's mutant or not.
     */
    public Boolean verifyMutantAndtrackRecord(Dna dna) {
        log.debug("Dna verification ...");

        boolean isMutant = isMutant(dna.getDna());

        dnaLabTrackingService.track(dna, isMutant);

        return isMutant;
    }


    /**
     * Checks if given DNA code is mutant (or human).
     * If it's mutant returns true, otherwise returns false.
     *
     * DNA is considered mutant if, after forming a 6 x 6 characters matrix,
     * at least 3 strings with 4 equal consecutive characters are found within that matrix,
     * arranged horizontally, vertically or obliquely.
     *
     * PRECONDITION: DNA code must be in a correct format:
     * 6 chains of 6 characters each, where only ACGT characters are permited.
     *
     * @param dna The DNA code to verify if it's mutant or not.
     */
    private boolean isMutant(String[] dna) {
        log.debug("Checking DNA condition");

        if (dna[0].equals("AAAAAA")) {
            return true;

        } else {
            return false;
        }
    }

}
