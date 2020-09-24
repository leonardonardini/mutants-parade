package com.mutantsparade.mutantdetected.service.impl;

import com.mutantsparade.mutantdetected.domain.DnaStats;
import com.mutantsparade.mutantdetected.repository.DnaStatsRepository;
import com.mutantsparade.mutantdetected.service.DnaStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class DnaStatsServiceImpl implements DnaStatsService {

    @Autowired
    DnaStatsRepository dnaStatsRepository;

    @Override
    public CompletableFuture<DnaStats> getStats() {
        return dnaStatsRepository.getStats();
    }

}
