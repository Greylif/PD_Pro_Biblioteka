package com.example.pd_pro_biblioteka_client;

import com.example.pd_pro_biblioteka_client.controller.Login;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class LoginControlTest {

    private Login controller;

    @Start
    private void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = loader.load();
        controller = loader.getController();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    void testLoginButtonExists(FxRobot robot) {
        assertNotNull(controller.login);
        assertEquals("Zaloguj", controller.login.getText());
    }

    @Test
    void testRegisterButton(FxRobot robot) {
        assertNotNull(controller.register);
        assertEquals("Zarejestruj", controller.register.getText());
    }

    @Test
    void testPasswordField(FxRobot robot) {
        robot.clickOn("#user_pass").write("secret");
        assertEquals("secret", controller.user_pass.getText());
    }

    @Test
    void testLoginField(FxRobot robot) {
        robot.clickOn("#user_login").write("admin");
        assertEquals("admin", controller.user_login.getText());
    }
}