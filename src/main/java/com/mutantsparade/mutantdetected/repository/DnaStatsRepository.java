package com.mutantsparade.mutantdetected.repository;

import com.mutantsparade.mutantdetected.domain.DnaStats;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

@Repository
public interface DnaStatsRepository  extends CrudRepository<DnaStats, Long> {

    /**
     * Execute a custom query to calculate and return stats about total DNA verification requests:
     * 1. Number of requests with mutant DNA (count_mutant_dna)
     * 2. Number of requests with human DNA (count_human_dna)
     * 3. Percent of mutants requests over humans (ratio)
     *
     * @return stats described above
     */
    @Query("select (isnull(count_mutant_dna,0)) as count_mutant_dna, (isnull(count_human_dna, 0)) as count_human_dna, isnull((isnull(count_mutant_dna, 0) / isnull(count_human_dna, count_mutant_dna)),0) as ratio from\n" +
            "            (select (select  sum(quantity) from VERIFIED_DNA where mutant = FALSE group by mutant) as count_human_dna,\n" +
            "            (select  sum(quantity) from VERIFIED_DNA where mutant = TRUE group by mutant) as count_mutant_dna\n" +
            "            from dual)")
    @Async
    CompletableFuture<DnaStats> getStats();

}
