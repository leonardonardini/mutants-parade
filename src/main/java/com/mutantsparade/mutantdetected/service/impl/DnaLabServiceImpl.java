package com.mutantsparade.mutantdetected.service.impl;

import com.mutantsparade.mutantdetected.domain.Dna;
import com.mutantsparade.mutantdetected.domain.VerifiedDna;
import com.mutantsparade.mutantdetected.repository.VerifiedDnaRepository;
import com.mutantsparade.mutantdetected.service.DnaLabService;
import com.mutantsparade.mutantdetected.service.DnaLabTrackingService;
import com.mutantsparade.mutantdetected.utils.DnaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class DnaLabServiceImpl implements DnaLabService {

    private static final Logger log = LoggerFactory.getLogger(MutantDetectedServiceImpl.class);

    @Autowired
    private VerifiedDnaRepository verifiedDnaRepository;

    @Autowired
    private DnaLabTrackingService dnaLabTrackingService;

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
     * N chains of N characters each, where only ACGT characters are permited.
     *
     * @param dna The DNA code to verify if it's mutant or not.
     * @return true if DNA es mutant, false otherwise
     */
    @Async
    public CompletableFuture<Boolean> verifyMutantAndtrackRecord(Dna dna) {
        log.debug("Dna verification ...");

        boolean isMutant = DnaUtils.isMutant(dna.getDna());

        dnaLabTrackingService.track(dna, isMutant);

        return CompletableFuture.completedFuture(isMutant);
    }


}
