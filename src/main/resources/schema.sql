DROP TABLE IF EXISTS VERIFIED_DNA;
CREATE TABLE VERIFIED_DNA (
  id BIGINT AUTO_INCREMENT  PRIMARY KEY,
  dna_hash VARCHAR(50) NOT NULL,
  mutant BOOLEAN NOT NULL,
  quantity BIGINT NOT NULL,
  UNIQUE KEY verified_dna_dna_hash_UNIQUE (dna_hash)
);