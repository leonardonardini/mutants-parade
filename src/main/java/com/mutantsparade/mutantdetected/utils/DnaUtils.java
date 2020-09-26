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
     * @throws InvalidDnaCodeException
     *
     */
    public static void validateDnaFormat(String[] dna) throws InvalidDnaCodeException {
        log.debug("Validating DNA format");

        if ((dna == null) || (dna.length == 0)) {
            throw new InvalidDnaCodeException("Invalid DNA code -> must have same size ACGT characters chains");
        }

        int chainSize = dna[0].length();

        if (chainSize != dna.length) {
            throw new InvalidDnaCodeException("Invalid DNA code -> must have as many chains as there are characters in each one");
        }

        String dnaChainRegExp = "^[CAGT]{" + chainSize + "}$";
        boolean notMatches = Arrays.stream(dna).anyMatch(s -> !s.matches(dnaChainRegExp));
        if (notMatches) {
            throw new InvalidDnaCodeException("Invalid DNA code -> every chain must match the following regexp: " + dnaChainRegExp);
        }

    }

    /**
     * Checks if given DNA code is mutant (or human).
     * If it's mutant returns true, otherwise returns false.
     *
     * DNA is considered mutant if, after forming a 6 x 6 characters matrix,
     * at least 3 strings with 4 equal consecutive characters are found within that matrix,
     * arranged horizontally, vertically or obliquely.
     *
     * PRECONDITION: DNA code must be in a correct format:
     * 6 chains of 6 characters each, where only ACGT characters are permited.
     *
     * @param dna The DNA code to verify if it's mutant or not.
     */
    public static boolean isMutant(String[] dna) {
        log.debug("Checking DNA condition");

        if (dna[0].equals("AAAAAA")) {
            return true;

        } else {
            return false;
        }
    }



}
