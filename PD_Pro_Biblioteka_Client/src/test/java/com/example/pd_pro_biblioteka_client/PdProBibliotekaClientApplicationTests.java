package com.example.pd_pro_biblioteka_client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

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

