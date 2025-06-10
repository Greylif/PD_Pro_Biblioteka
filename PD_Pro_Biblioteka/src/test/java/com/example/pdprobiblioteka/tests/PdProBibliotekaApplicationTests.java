package com.example.pdprobiblioteka.tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.example.pdprobiblioteka.PdProBibliotekaApplication;
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
    assertDoesNotThrow(() -> PdProBibliotekaApplication.main(new String[]{}));
  }


}
