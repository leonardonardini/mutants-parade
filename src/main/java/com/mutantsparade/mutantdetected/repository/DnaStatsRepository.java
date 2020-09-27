package com.mutantsparade.mutantdetected.repository;

import com.mutantsparade.mutantdetected.domain.DnaStats;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

@Repository
public class DnaStatsRepository {

    private final JdbcTemplate jdbcTemplate;

    public DnaStatsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Executes a custom query to calculate and return stats about total dna verification requests:
     * 1. Number of requests with mutant dna (countMutantDna)
     * 2. Number of requests with human dna (countHumanDna)
     * 3. Percent of mutants requests over humans (ratio)
     *
     * @return a DnaStats object with the stats described above
     */
    @Async
    public CompletableFuture<DnaStats> getStats() {
        return CompletableFuture.supplyAsync(() -> {
            DnaStats dnaStats = jdbcTemplate.queryForObject("select (isnull(countMutantDna,0)) as countMutantDna, (isnull(countHumanDna, 0)) as countHumanDna, round(isnull((isnull(countMutantDna, 0) / isnull(countHumanDna, countMutantDna)),0),4) as ratio from\n" +
                    "            (select (select  sum(quantity) from VERIFIED_DNA where mutant = FALSE group by mutant) as countHumanDna,\n" +
                    "            (select  sum(quantity) from VERIFIED_DNA where mutant = TRUE group by mutant) as countMutantDna\n" +
                    "            from dual)", (resultSet, i) -> {
                DnaStats dnaStatsToMap  = new DnaStats();
                dnaStatsToMap.setCountMutantDna(resultSet.getLong("countMutantDna"));
                dnaStatsToMap.setCountHumanDna(resultSet.getLong("countHumanDna"));
                dnaStatsToMap.setRatio(resultSet.getDouble("ratio"));
                return dnaStatsToMap;
            });
            return dnaStats;
        });
    }

}
