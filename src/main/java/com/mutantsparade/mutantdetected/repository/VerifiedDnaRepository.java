package com.mutantsparade.mutantdetected.repository;

import com.mutantsparade.mutantdetected.domain.VerifiedDna;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifiedDnaRepository extends CrudRepository<VerifiedDna, Long> {

    Optional<VerifiedDna> findByDnaHash(String dnaHash);

}
