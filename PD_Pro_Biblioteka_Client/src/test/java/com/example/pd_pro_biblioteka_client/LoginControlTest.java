package com.example.pd_pro_biblioteka_client;

import com.example.pd_pro_biblioteka_client.controller.Login;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.assertions.api.Assertions.assertThat;


@DisplayName("Testy Logowania")
@ExtendWith(ApplicationExtension.class)
class LoginControlTest extends ApplicationTest {


    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = loader.load();

        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    @DisplayName("Logowanie z nie poprawnymi danymi dla usera")
    public void shouldFailLoginWithWrongCredentials() {
        clickOn("#user_login").write("wrongUser");
        clickOn("#user_pass").write("wrongPass");

        clickOn("#login");

        Parent popupRoot = lookup("#undonePopup").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();
    }

    @Test
    @DisplayName("Logowanie z poprawnymi danymi dla usera")
    public void shouldSuccessLogin() {
        clickOn("#user_login").write("username4");
        clickOn("#user_pass").write("pass4");

        clickOn("#login");

        Parent popupRoot = lookup("#client").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();
    }

    @Test
    @DisplayName("Logowanie z nie poprawnymi danymi dla admina")
    public void shouldFailLoginAdminWithWrongCredentials() {
        clickOn("#user_login").write("wrongUser");
        clickOn("#user_pass").write("wrongPass");

        clickOn("#login_a");

        Parent popupRoot = lookup("#undonePopup").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();
    }

    @Test
    @DisplayName("Logowanie z poprawnymi danymi dla admina")
    public void shouldSuccessLoginAdmin() {
        clickOn("#user_login").write("admin10");
        clickOn("#user_pass").write("adminpass10");

        // Kliknij przycisk login (zakładam, że masz w fxml button z fx:id="loginButton" albo użyj kliknięcia po tekście)
        clickOn("#login_a");

        Parent popupRoot = lookup("#admin").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();
    }

    @Test
    @DisplayName("Rejestracja z danymi")
    public void shouldSuccessRegisterLoginAndDelete() {
        clickOn("#register");

        Parent popupRoot = lookup("#registerbox").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();

        clickOn("#user_name").write("Juan");
        clickOn("#user_surname").write("Pablo");
        clickOn("#user_date").write("6.06.2025");
        clickOn("#user_email").write("s093644@student.tu.kielce.pl");
        clickOn("#user_login").write("JuanII");
        clickOn("#user_password").write("123456");
        clickOn("#register");

        popupRoot = lookup("#loginbox").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();

        clickOn("#user_login").write("JuanII");
        clickOn("#user_pass").write("123456");

        clickOn("#login");

        popupRoot = lookup("#client").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();

        clickOn("#settings");
        clickOn("#del_button");

        popupRoot = lookup("#confirm").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();
        clickOn("#okButton");

        popupRoot = lookup("#login").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();
    }

    @Test
    @DisplayName("Rejestracja z niepoprawnymi danymi")
    public void shouldFailRegister() {
        clickOn("#register");

        Parent popupRoot = lookup("#register").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();

        clickOn("#user_name").write("Juan");
        clickOn("#user_surname").write("Pablo");
        clickOn("#user_date").write("6.06.2025");
        clickOn("#user_email").write("s093644@student.tu.kielce.pl");
        clickOn("#user_login").write("username6");
        clickOn("#user_password").write("123456");
        clickOn("#register");

        popupRoot = lookup("#undonePopup").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();
    }
}