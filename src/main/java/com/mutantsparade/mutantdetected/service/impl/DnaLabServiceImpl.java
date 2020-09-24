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
import java.util.Random;

@Service
public class DnaLabServiceImpl implements DnaLabService {

    Logger log = LoggerFactory.getLogger(MutantDetectedServiceImpl.class);

    @Autowired
    VerifiedDnaRepository verifiedDnaRepository;

    @Autowired
    DnaLabTrackingService dnaLabTrackingService;

    public Optional<VerifiedDna> findByDnaHash(String dna) {
        return verifiedDnaRepository.findByDnaHash(dna);
    }

    @Override
    @Async
    public synchronized void update(VerifiedDna verifiedDna) {
        verifiedDna.setQuantity(verifiedDna.getQuantity() + 1);
        verifiedDnaRepository.save(verifiedDna);
    }

    public synchronized Boolean verifyAndtrack(Dna dna) {
        log.debug("Dna verification ...");

        boolean isMutant = isMutant(dna.getDna());

        dnaLabTrackingService.track(dna, isMutant);

        return isMutant;

    }

    private boolean isMutant(String[] dna) {
        return new Random().nextBoolean();
    }

}
