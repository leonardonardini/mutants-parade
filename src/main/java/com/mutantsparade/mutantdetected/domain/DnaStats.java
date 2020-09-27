package com.mutantsparade.mutantdetected.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class DnaStats {

    @JsonProperty("count_mutant_dna")
    private long countMutantDna;
    @JsonProperty("count_human_dna")
    private Long countHumanDna;
    private Double ratio;

}
