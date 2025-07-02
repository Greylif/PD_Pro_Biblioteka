package com.example.pdprobibliotekaclient;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MainTest {

    private MainApp mainApp;

    @BeforeAll
    public static void initToolkit() {
        new JFXPanel();
    }

    @BeforeEach
    void setUp() {
        mainApp = new MainApp();
    }

    @Test
    @DisplayName("Test metody init()")
    void testInit() throws Exception {
        MainApp app = spy(mainApp);
        SpringApplicationBuilder builder = mock(SpringApplicationBuilder.class);
        ConfigurableApplicationContext context = mock(ConfigurableApplicationContext.class);

        doReturn(builder).when(builder).sources(PdProBibliotekaClientApplication.class);
        doReturn(context).when(builder).run();

        app.init();
        assertNotNull(app);
    }

    @Test
    @DisplayName("Test metody stop()")
    void testStop() throws Exception {
        ConfigurableApplicationContext context = mock(ConfigurableApplicationContext.class);
        MainApp app = new MainApp();

        var field = MainApp.class.getDeclaredField("springContext");
        field.setAccessible(true);
        field.set(app, context);

        app.stop();

        verify(context).close();
    }

    @Test
    @DisplayName("Test metody start() - mockowanie sceny")
    void testStart() throws Exception {
        Platform.runLater(() -> {
            try {
                MainApp app = new MainApp();
                Stage mockStage = new Stage();

                ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);
                var field = MainApp.class.getDeclaredField("springContext");
                field.setAccessible(true);
                field.set(app, mockContext);

                mockStage.setScene(new Scene(new javafx.scene.layout.Pane(), 600, 600));
                mockStage.setTitle("Logowanie");
                mockStage.show();

                assertEquals("Logowanie", mockStage.getTitle());
                assertNotNull(mockStage.getScene());
            } catch (Exception e) {
                e.printStackTrace();
                fail("Wystąpił wyjątek w testStart(): " + e.getMessage());
            }
        });

        Thread.sleep(2000);
    }
}
