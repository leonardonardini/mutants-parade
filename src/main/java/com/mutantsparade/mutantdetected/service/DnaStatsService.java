package com.mutantsparade.mutantdetected.service;

import com.mutantsparade.mutantdetected.domain.DnaStats;

import java.util.concurrent.CompletableFuture;

public interface DnaStatsService {

    CompletableFuture<DnaStats> getStats();

}
