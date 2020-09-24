package com.mutantsparade.mutantdetected.service.impl;

import com.mutantsparade.mutantdetected.domain.Dna;
import com.mutantsparade.mutantdetected.domain.DnaStats;
import com.mutantsparade.mutantdetected.domain.VerifiedDna;
import com.mutantsparade.mutantdetected.errors.InvalidDnaCodeException;
import com.mutantsparade.mutantdetected.service.DnaLabService;
import com.mutantsparade.mutantdetected.service.DnaStatsService;
import com.mutantsparade.mutantdetected.service.MutantDetectedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MutantDetectedServiceImpl implements MutantDetectedService {

    Logger log = LoggerFactory.getLogger(MutantDetectedServiceImpl.class);

    @Autowired
    DnaLabService dnaLabService;

    @Autowired
    DnaStatsService dnaStatsService;

    @Override
    public Boolean verifyDna(Dna dna) {
        Boolean isMutant;

        validateDnaFormat(dna);

        Optional<VerifiedDna> verifiedDna = dnaLabService.findByDnaHash(dna.getDnaHash());

        if (verifiedDna.isPresent()) {
            VerifiedDna persistentVerifiedDna = verifiedDna.get();
            isMutant = persistentVerifiedDna.isMutant();
            dnaLabService.update(persistentVerifiedDna);

        } else {
            isMutant = dnaLabService.verifyAndtrack(dna);
        }

        return isMutant;
    }

    public CompletableFuture<DnaStats> getStats() {
        return dnaStatsService.getStats();
    }

    private void validateDnaFormat(Dna dna) {

        String[] dnaCode = dna.getDna();
        if (dnaCode.length != 6) {
            throw new InvalidDnaCodeException("Invalid DNA code -> must have 6 characters chains");
        }

        String dnaChainRegExp = "^[CAGT]{6}$";
        boolean notMatches = Arrays.stream(dnaCode).anyMatch(s -> !s.matches(dnaChainRegExp));
        if (notMatches) {
            throw new InvalidDnaCodeException("Invalid DNA code -> every chain must match the following regexp: " + dnaChainRegExp);
        }

    }

}
