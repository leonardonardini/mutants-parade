package com.mutantsparade.mutantdetected.service;

import com.mutantsparade.mutantdetected.domain.Dna;

public interface DnaLabTrackingService {

    /**
     * Persist in the database a verified DNA.
     *
     * @param dna a DNA already verified
     * @param isMutant indicates if verified DNA is mutant (true) or not (false)
     */
    void track(Dna dna, boolean isMutant);

}
