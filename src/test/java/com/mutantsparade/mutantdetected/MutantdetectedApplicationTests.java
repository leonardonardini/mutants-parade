package com.mutantsparade.mutantdetected;

import com.mutantsparade.mutantdetected.web.rest.MutantDetectedController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MutantdetectedApplicationTests {

	@Autowired
	MutantDetectedController mutantDetectedController;
	
	@Test
	void contextLoads() {
		assertThat(mutantDetectedController).isNotNull();
	}

}
