package com.example.pd_pro_biblioteka;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PdProBibliotekaApplicationTests {

	@Test
	@DisplayName("contexLoader")
	void contextLoads() {
		//Context load test
	}
	@Test
	@DisplayName("maintest")
	void testMain() {
		PdProBibliotekaApplication.main(new String[]{});
	}


}
