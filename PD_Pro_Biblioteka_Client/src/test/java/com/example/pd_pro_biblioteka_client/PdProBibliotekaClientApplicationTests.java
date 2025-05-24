package com.example.pd_pro_biblioteka_client;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PdProBibliotekaClientApplicationTests {

    @Test
    public void contextLoads() {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(PdProBibliotekaClientApplication.class).run();
        assertNotNull(context);
        assertTrue(context.isActive());
        context.close();
    }



}
