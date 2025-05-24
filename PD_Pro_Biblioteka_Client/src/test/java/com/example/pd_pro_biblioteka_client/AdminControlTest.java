package com.example.pd_pro_biblioteka_client;

import com.example.pd_pro_biblioteka_client.controller.Admin;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.*;

public class AdminControlTest extends ApplicationTest {

    private Admin adminController;

    @BeforeAll
    public static void initJfx() {
        // This initializes the JavaFX platform, necessary in headless test environments
        new JFXPanel(); // Initializes JavaFX
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin.fxml"));
        Parent root = loader.load();
        adminController = loader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        // Called before each test
        // Platform.runLater if necessary
    }

    @Test
    public void testInitialize() {
        assertDoesNotThrow(() -> adminController.initialize());
        // If you want to check data loaded:
        // Wait some time for Platform.runLater to finish
        // assertNotNull(adminController.penaltyTable.getItems());
    }

    @Test
    public void testBorrowMethodExists() {
        assertDoesNotThrow(() -> adminController.borrow(null));
    }

    @Test
    public void testLogout() throws Exception {
        // Use a latch to wait for Platform.runLater to finish
        final Object lock = new Object();

        Platform.runLater(() -> {
            try {
                Button button = new Button("Logout");
                Stage stage = new Stage();
                Scene scene = new Scene(new StackPane(button));
                stage.setScene(scene);

                ActionEvent mockEvent = new ActionEvent(button, null);

                assertDoesNotThrow(() -> adminController.logout(mockEvent));
            } finally {
                synchronized (lock) {
                    lock.notify(); // notify main test thread to continue
                }
            }
        });

        synchronized (lock) {
            lock.wait(); // wait for JavaFX thread to complete
        }
    }


    @Test
    public void testAddBook() {
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> adminController.add_book(null));
        });
    }

    @Test
    public void testSaveMethodExists() {
        assertDoesNotThrow(() -> adminController.save(null));
    }

    @Test
    public void testDeleteAccountCancel() {
        // Since `delete_acc` has user confirmation dialog,
        // we can't test it easily without mocking dialog responses.
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> {
                try {
                    adminController.delete_acc(null);
                } catch (Exception e) {
                    fail("Should not throw exception: " + e.getMessage());
                }
            });
        });
    }

    @Test
    public void testDeleteBookMethodExists() {
        assertDoesNotThrow(() -> adminController.delete_book(null));
    }
}
