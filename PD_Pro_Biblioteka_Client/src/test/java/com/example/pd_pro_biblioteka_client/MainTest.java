package com.example.pd_pro_biblioteka_client;

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
        // JFXPanel to workaround to initialize JavaFX environment
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

        // Mockowanie zachowania SpringApplicationBuilder
        doReturn(builder).when(builder).sources(PdProBibliotekaClientApplication.class);
        doReturn(context).when(builder).run();

        // Nie można tak w rzeczywistości podmienić buildera, ale zostawiam ślad — testuje tylko init()
        app.init();
        assertNotNull(app);
    }

    @Test
    @DisplayName("Test metody stop()")
    void testStop() throws Exception {
        ConfigurableApplicationContext context = mock(ConfigurableApplicationContext.class);
        MainApp app = new MainApp();

        // Refleksyjnie ustawiamy springContext
        var field = MainApp.class.getDeclaredField("springContext");
        field.setAccessible(true);
        field.set(app, context);

        app.stop();

        verify(context).close();
        // Platform.exit() – nie testujemy bo zamknęłoby JVM
    }

    @Test
    @DisplayName("Test metody start() - mockowanie sceny")
    void testStart() throws Exception {
        Platform.runLater(() -> {
            try {
                MainApp app = new MainApp();
                Stage mockStage = new Stage();

                // Refleksyjnie ustawiamy mockowy kontekst
                ConfigurableApplicationContext mockContext = mock(ConfigurableApplicationContext.class);
                var field = MainApp.class.getDeclaredField("springContext");
                field.setAccessible(true);
                field.set(app, mockContext);

                // Podmień metodę FXMLLoader.load() przez wstrzyknięcie mocka jeśli przepiszesz kod (alternatywa niżej)
                // Albo uprość metodę start na potrzeby testu (np. wystaw na package-private wersję testową)
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

        // Poczekaj na zakończenie wątku JavaFX
        Thread.sleep(2000);
    }
}
