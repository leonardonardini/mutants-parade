package com.mutantsparade.mutantdetected.service;

import com.mutantsparade.mutantdetected.domain.Dna;

public interface DnaLabTrackingService {

    void track(Dna dna, boolean isMutant);

}
