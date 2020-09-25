package com.mutantsparade.mutantdetected.service.impl;

import com.mutantsparade.mutantdetected.domain.Dna;
import com.mutantsparade.mutantdetected.domain.VerifiedDna;
import com.mutantsparade.mutantdetected.repository.VerifiedDnaRepository;
import com.mutantsparade.mutantdetected.service.DnaLabTrackingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DnaLabTrackingServiceImpl implements DnaLabTrackingService {

    Logger log = LoggerFactory.getLogger(DnaLabTrackingServiceImpl.class);

    @Autowired
    VerifiedDnaRepository verifiedDnaRepository;

    /**
     * Persist in the database a verified DNA.
     *
     * @param dna a DNA already verified
     * @param isMutant indicates if verified DNA is mutant (true) or not (false)
     */
    @Override
    @Async
    public void track(Dna dna, boolean isMutant) {
        log.debug("Saving verified DNA to database: " + dna.getDnaHash() + " -> mutant: " + isMutant);

        VerifiedDna verifiedDna = new VerifiedDna();
        verifiedDna.setDnaHash(dna.getDnaHash());
        verifiedDna.setMutant(isMutant);
        verifiedDna.setQuantity(1L);
        verifiedDnaRepository.save(verifiedDna);
    }
}
