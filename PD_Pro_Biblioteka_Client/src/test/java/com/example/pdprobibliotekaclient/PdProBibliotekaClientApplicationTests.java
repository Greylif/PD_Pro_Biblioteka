package com.example.pdprobibliotekaclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test integracyjny uruchamiający kontekst aplikacji Spring Boot
 * w celu sprawdzenia, czy aplikacja startuje poprawnie.
 */
@SpringBootTest
class PdProBibliotekaClientApplicationTests {

    @Autowired
    private ConfigurableApplicationContext context;

    @Test
    public void contextLoads() {
        assertNotNull(context);
        assertTrue(context.isActive());
    }
}

