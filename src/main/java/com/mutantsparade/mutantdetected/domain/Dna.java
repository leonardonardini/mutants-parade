package com.mutantsparade.mutantdetected.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class Dna {

    private static final String DNA_HASH_DELIMITER = "-";

    private String[] dna;

    @JsonIgnore
    public String getDnaHash() {
        return String.join(DNA_HASH_DELIMITER, getDna());
    }

}
