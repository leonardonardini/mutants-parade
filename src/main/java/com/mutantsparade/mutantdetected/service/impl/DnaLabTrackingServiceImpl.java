package com.mutantsparade.mutantdetected.service.impl;

import com.mutantsparade.mutantdetected.domain.Dna;
import com.mutantsparade.mutantdetected.domain.VerifiedDna;
import com.mutantsparade.mutantdetected.repository.VerifiedDnaRepository;
import com.mutantsparade.mutantdetected.service.DnaLabTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class DnaLabTrackingServiceImpl implements DnaLabTrackingService {

    @Autowired
    VerifiedDnaRepository verifiedDnaRepository;

    @Override
    @Async
    public void track(Dna dna, boolean isMutant) {
        VerifiedDna verifiedDna = new VerifiedDna();
        verifiedDna.setDnaHash(dna.getDnaHash());
        verifiedDna.setMutant(isMutant);
        verifiedDna.setQuantity(1L);
        verifiedDnaRepository.save(verifiedDna);
    }
}
