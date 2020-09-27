package com.mutantsparade.mutantdetected.repository;

import com.mutantsparade.mutantdetected.domain.VerifiedDna;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
public interface VerifiedDnaRepository extends CrudRepository<VerifiedDna, Long> {

    @Async
    CompletableFuture<Optional<VerifiedDna>> findByDnaHash(String dnaHash);

}
