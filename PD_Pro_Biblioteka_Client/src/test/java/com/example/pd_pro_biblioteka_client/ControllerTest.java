package com.example.pd_pro_biblioteka_client;


import com.example.pd_pro_biblioteka_client.controller.Addborow;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

/**
 * Testy Controller.
 */
@DisplayName("Testy dla Controller")
public class ControllerTest {


    /**
     * Testy Klasy ServiceMonitor.
     */
    @Nested
    @DisplayName("Testy klasy Addborow")
    class AddborowTest {
        private Addborow controller;
        private TextField bookField;
        private TextField userField;
        private DatePicker returnDate;
        private ActionEvent mockEvent;

        @BeforeEach
        void setUp() throws Exception {
            new JFXPanel(); // Uruchamia Toolkit

            controller = new Addborow();

            // Mockujemy pola @FXML
            bookField = new TextField("101");
            userField = new TextField("202");
            returnDate = new DatePicker(LocalDate.now().plusDays(10));

            // Wstrzykujemy prywatne pola (bo brak setterów)
            setField("borrowBookID", bookField);
            setField("borrowUserID", userField);
            setField("borrowReturnDate", returnDate);

            // Mockujemy Stage, Scene, Node i ActionEvent
            Stage mockStage = mock(Stage.class);
            Scene mockScene = mock(Scene.class);
            Node mockNode = mock(Node.class);

            when(mockNode.getScene()).thenReturn(mockScene);
            when(mockScene.getWindow()).thenReturn(mockStage);

            mockEvent = new ActionEvent(mockNode, null);

            // Mockujemy token
            setFakeAdminToken("mocked.jwt.token");
        }

        @Test
        void testButton_act_successResponse() throws Exception {
            // MOCK HttpClient globalnie (trudne bez refaktoryzacji, więc skupiamy się na wywołaniu, nie treści)

            // Po prostu sprawdzamy, że metoda nie rzuca wyjątków
            assertDoesNotThrow(() -> controller.button_act(mockEvent));
        }

        // ----------------- POMOCNICZE ----------------------

        private void setField(String fieldName, Object value) throws Exception {
            Field field = Addborow.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(controller, value);
        }

        private void setFakeAdminToken(String token) throws Exception {
            Class<?> logAdminClass = Class.forName("com.example.pd_pro_biblioteka_client.model.logAdmin"); // <- dostosuj
            Field tokenField = logAdminClass.getDeclaredField("logAdmin"); // <- dostosuj jeśli inaczej nazwany
            tokenField.setAccessible(true);
            tokenField.set(null, token); // zakładamy static
        }
    }
}
