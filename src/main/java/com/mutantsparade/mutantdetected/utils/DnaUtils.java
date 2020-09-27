package com.mutantsparade.mutantdetected.utils;

import com.mutantsparade.mutantdetected.errors.InvalidDnaCodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class DnaUtils {

    private static final Logger log = LoggerFactory.getLogger(DnaUtils.class);

    /**
     * Validate is given dna has the correct format:
     * N chains of N characters each, where only ACGT characters are permited.
     *
     * If dna code is invalid, a InvalidDnaCodeException is thrown.
     *
     * @param dna DNA to validate
     * @throws InvalidDnaCodeException this exception is thrown in case the DNA is NOT valid
     *
     */
    public static void validateDnaFormat(String[] dna) throws InvalidDnaCodeException {
        log.debug("Validating DNA format");

        if ((dna == null) || (dna.length == 0)) {
            throw new InvalidDnaCodeException("Invalid DNA code -> must have same size ACGT characters chains");
        }

        if (dna.length < 4) {
            throw new InvalidDnaCodeException("Invalid DNA code -> there must be at least 4 chains");
        }

        if (dna.length > 15) {
            throw new InvalidDnaCodeException("Invalid DNA code -> there must be a maximum of 15 chains");
        }

        int chainSize = dna[0].length();

        if (chainSize != dna.length) {
            throw new InvalidDnaCodeException("Invalid DNA code -> must have as many chains as there are characters in each one");
        }

        String dnaChainRegExp = "^[ACGT]{" + chainSize + "}$";
        boolean notMatches = Arrays.stream(dna).anyMatch(s -> !s.matches(dnaChainRegExp));
        if (notMatches) {
            throw new InvalidDnaCodeException("Invalid DNA code -> every chain must match the following regexp: " + dnaChainRegExp);
        }

    }

    /**
     * Checks if given DNA code is mutant.
     * If it's mutant returns true, otherwise returns false.
     *
     * DNA is considered mutant if, thinkng about it as a N x N characters matrix,
     * at least 2 chains with 4 equal consecutive characters are found within that matrix,
     * arranged horizontally, vertically or obliquely.
     *
     * PRECONDITION: DNA code must be in a correct format:
     * N chains of N characters each, where only ACGT characters are permited.
     *
     * @param dna The DNA code to verify if it's mutant or not.
     * @return true if given DNA is consider mutant, otherwise returns false.
     */
    public static boolean isMutant(String[] dna) {
        int mutantChains = 0;

        for (int i = 0; ((i < dna.length) && (mutantChains < 2)); i++) {
            for (int j = 0; ((j < dna.length) && (mutantChains < 2)); j++) {

                // If possible, find horizontally arranged mutant string
                if ((j <= dna.length-4)
                        && (dna[i].charAt(j) == dna[i].charAt(j+1))
                        && (dna[i].charAt(j) == dna[i].charAt(j+2))
                        && (dna[i].charAt(j) == dna[i].charAt(j+3))) {
                    mutantChains = mutantChains + 1;

                }
                // If possible, find obliquely arranged mutant string
                if (((j <= dna.length-4) && (i <= dna.length-4))
                        && (dna[i].charAt(j) == dna[i+1].charAt(j+1))
                        && (dna[i].charAt(j) == dna[i+2].charAt(j+2))
                        && (dna[i].charAt(j) == dna[i+3].charAt(j+3))) {
                    mutantChains = mutantChains + 1;

                }
                // If possible, find vertically arranged mutant string
                if ((i <= dna.length-4)
                        && (dna[i].charAt(j) == dna[i+1].charAt(j))
                        && (dna[i].charAt(j) == dna[i+2].charAt(j))
                        && (dna[i].charAt(j) == dna[i+3].charAt(j))) {
                    mutantChains = mutantChains + 1;

                }
                if ((i == dna.length - 1) && (j == dna.length - 4)) {
                    //there is no chance of finding new mutant chains from this position, so we cut the process
                    break;
                }
            }
        }

        return mutantChains > 1;
    }

}
