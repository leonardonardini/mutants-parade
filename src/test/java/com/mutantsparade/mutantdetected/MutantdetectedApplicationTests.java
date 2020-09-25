package com.mutantsparade.mutantdetected;

import com.mutantsparade.mutantdetected.domain.Dna;
import com.mutantsparade.mutantdetected.service.DnaLabService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class MutantdetectedApplicationTests {

	@Autowired
	private DnaLabService dnaLabService;

	@Test
	void testIsMutant() {
		Dna dna = new Dna();
		dna.setDna(new String[]{"AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA"});
		boolean isMutant = dnaLabService.verifyMutantAndtrackRecord(dna);
		assertEquals(true, isMutant);
	}

	@Test
	void testIsNotMutant() {
		Dna dna = new Dna();
		dna.setDna(new String[]{"AACAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA"});
		boolean isMutant = dnaLabService.verifyMutantAndtrackRecord(dna);
		assertEquals(false, isMutant);
	}

}
