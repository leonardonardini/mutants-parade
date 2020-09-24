package com.mutantsparade.mutantdetected.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class DnaStats {

    private Long count_mutant_dna;
    private Long count_human_dna;
    private Double ratio;

}
