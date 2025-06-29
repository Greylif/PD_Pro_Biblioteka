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
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.*;

public class AdminControlTest extends ApplicationTest {

//    private Admin adminController;
//
//    @BeforeAll
//    public static void initJfx() {
//        new JFXPanel();
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin.fxml"));
//        Parent root = loader.load();
//        adminController = loader.getController();
//
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    @Test
//    public void testInitialize() {
//        assertDoesNotThrow(() -> adminController.initialize());
//    }
//
//    @Test
//    public void testBorrowMethodExists() {
//        assertDoesNotThrow(() -> adminController.borrow(null));
//    }
//
//    @Test
//    public void testLogout() throws Exception {
//        final Object lock = new Object();
//
//        Platform.runLater(() -> {
//            try {
//                Button button = new Button("Logout");
//                Stage stage = new Stage();
//                Scene scene = new Scene(new StackPane(button));
//                stage.setScene(scene);
//
//                ActionEvent mockEvent = new ActionEvent(button, null);
//
//                assertDoesNotThrow(() -> adminController.logout(mockEvent));
//            } finally {
//                synchronized (lock) {
//                    lock.notify();
//                }
//            }
//        });
//
//        synchronized (lock) {
//            lock.wait();
//        }
//    }
//
//    @Test
//    public void testDeleteAccountCancel() {
//        assertDoesNotThrow(() -> {
//            Platform.runLater(() -> {
//                try {
//                    adminController.delete_acc(null);
//                } catch (Exception e) {
//                    fail("Should not throw exception: " + e.getMessage());
//                }
//            });
//        });
//    }

}
