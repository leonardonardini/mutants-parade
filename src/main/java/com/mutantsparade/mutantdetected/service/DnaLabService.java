package com.mutantsparade.mutantdetected.service;

import com.mutantsparade.mutantdetected.domain.Dna;
import com.mutantsparade.mutantdetected.domain.VerifiedDna;

import java.util.Optional;

public interface DnaLabService {

    Optional<VerifiedDna> findByDnaHash(String dna);

    void update(VerifiedDna verifiedDna);

    Boolean verifyAndtrack(Dna dna);

}
