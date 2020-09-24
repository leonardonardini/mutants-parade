package com.mutantsparade.mutantdetected.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;

@Data
@EqualsAndHashCode
@ToString
public class VerifiedDna {

    @Id
    private Long id;
    private String dnaHash;
    private boolean mutant;
    private Long quantity;

}
