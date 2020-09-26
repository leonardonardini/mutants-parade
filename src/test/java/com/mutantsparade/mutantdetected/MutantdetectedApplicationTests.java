package com.mutantsparade.mutantdetected;

import com.mutantsparade.mutantdetected.domain.Dna;
import com.mutantsparade.mutantdetected.domain.DnaStats;
import com.mutantsparade.mutantdetected.domain.VerifiedDna;
import com.mutantsparade.mutantdetected.errors.DnaNotMutantException;
import com.mutantsparade.mutantdetected.errors.InvalidDnaCodeException;
import com.mutantsparade.mutantdetected.repository.VerifiedDnaRepository;
import com.mutantsparade.mutantdetected.service.DnaLabService;
import com.mutantsparade.mutantdetected.service.MutantDetectedService;
import com.mutantsparade.mutantdetected.utils.DnaUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MutantdetectedApplicationTests {

	@Autowired
	private DnaLabService dnaLabService;

	@Autowired
	private VerifiedDnaRepository verifiedDnaRepository;

	@Autowired
	private MutantDetectedService mutantDetectedService;

	@Test
	@Transactional
	void testIsMutant() throws Exception {
		Dna dna = new Dna();
		dna.setDna(new String[]{"AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA"});
		CompletableFuture<Boolean> isMutant = dnaLabService.verifyMutantAndtrackRecord(dna);
		assertEquals(true, isMutant.get());
	}

	@Test
	@Transactional
	void testIsNotMutant() throws Exception {
		Dna dna = new Dna();
		dna.setDna(new String[]{"AACAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA"});
		CompletableFuture<Boolean> isMutant = dnaLabService.verifyMutantAndtrackRecord(dna);
		assertEquals(false, isMutant.get());
	}

	@Test
	@Transactional
	void testVerifyMutant() {
		Dna dna = new Dna();
		dna.setDna(new String[]{"AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA"});
		assertDoesNotThrow(() -> mutantDetectedService.verifyMutant(dna));
	}

	@Test
	@Transactional
	void testVerifyNotMutant() {
		Dna dna = new Dna();
		dna.setDna(new String[]{"AACAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA"});
		assertThrows(DnaNotMutantException.class, () -> mutantDetectedService.verifyMutant(dna));
	}

	@Test
	@Sql("/test.sql")
	@Transactional
	void testVerifyMutantAlreadyVerified() {
		Dna dna = new Dna();
		dna.setDna(new String[]{"AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA"});
		assertDoesNotThrow(() -> mutantDetectedService.verifyMutant(dna));
	}

	@Test
	void testValidDnaCode() {
		String[] dna = new String[]{"AACAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA"};
		assertDoesNotThrow(() -> DnaUtils.validateDnaFormat(dna));
	}

	@Test
	void testNotValidDnaCode_null() {
		String[] dna = null;
		assertThrows(InvalidDnaCodeException.class, () -> DnaUtils.validateDnaFormat(dna));
	}

	@Test
	void testNotValidDnaCode_empty() {
		String[] dna = new String[]{};
		assertThrows(InvalidDnaCodeException.class, () -> DnaUtils.validateDnaFormat(dna));
	}

	@Test
	void testNotValidDnaCode_size() {
		String[] dna = new String[]{"AACAAA","AAAAAA","AAAAAA","AAAAAA"};
		assertThrows(InvalidDnaCodeException.class, () -> DnaUtils.validateDnaFormat(dna));
	}

	@Test
	void testNotValidDnaCode_regexp1() {
		String[] dna = new String[]{"AACAAAAAAAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA"};
		assertThrows(InvalidDnaCodeException.class, () -> DnaUtils.validateDnaFormat(dna));
	}

	@Test
	void testNotValidDnaCode_regexp2() {
		String[] dna = new String[]{"AAEWAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA","AAAAAA"};
		assertThrows(InvalidDnaCodeException.class, () -> DnaUtils.validateDnaFormat(dna));
	}

	@Test
	@Sql("/test.sql")
	@Transactional
	void testFindVerifiedDnaExists() {
		String dnaHash = "AAAAAA-AAAAAA-AAAAAA-AAAAAA-AAAAAA-AAAAAA";
		Optional<VerifiedDna> verifiedDna = dnaLabService.findByDnaHash(dnaHash);

		assertEquals(verifiedDna.get().getDnaHash(), dnaHash);
		assertEquals(verifiedDna.get().getQuantity(), 10);
		assertEquals(verifiedDna.get().getId(), 1);
		assertEquals(verifiedDna.get().isMutant(), true);
	}

	@Test
	void testFindVerifiedDnaNotExists() {
		String dnaHash = "sarasa";
		Optional<VerifiedDna> verifiedDna = dnaLabService.findByDnaHash(dnaHash);

		assertEquals(verifiedDna.isPresent(), false);
	}

	@SneakyThrows
	@Test
	@Sql("/testUpdate.sql")
	void testUpdateExistingVerifiedDna() {
		Optional<VerifiedDna> verifiedDna = dnaLabService.findByDnaHash("UPDATE");
		Long quantityBeforeUpdate = verifiedDna.get().getQuantity();
		assertDoesNotThrow(() -> dnaLabService.update(verifiedDna.get()));
		Thread.sleep(100);
		Optional<VerifiedDna> verifiedDnaAfterUpdate = dnaLabService.findByDnaHash("UPDATE");
		assertEquals(quantityBeforeUpdate + 1, verifiedDnaAfterUpdate.get().getQuantity());
	}

	@SneakyThrows
	@Test
	void testTrackDnaToDatabase() {
		VerifiedDna verifiedDna = new VerifiedDna();
		verifiedDna.setDnaHash("H");
		verifiedDna.setQuantity(1L);
		verifiedDna.setMutant(false);
		assertDoesNotThrow(() -> verifiedDnaRepository.save(verifiedDna));
		Thread.sleep(100);
		Optional<VerifiedDna> verifiedDnaAfterUpdate = dnaLabService.findByDnaHash("H");
		assertEquals("H", verifiedDnaAfterUpdate.get().getDnaHash());
		assertEquals(1L, verifiedDnaAfterUpdate.get().getQuantity());
		assertEquals(false, verifiedDnaAfterUpdate.get().isMutant());
	}

}
