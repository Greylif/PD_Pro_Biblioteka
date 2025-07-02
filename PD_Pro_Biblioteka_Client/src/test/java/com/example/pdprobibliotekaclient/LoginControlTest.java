package com.example.pdprobibliotekaclient;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;

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
        clickOn("#userlogin").write("wrongUser");
        clickOn("#userpass").write("wrongPass");

        clickOn("#login");

        Parent popupRoot = lookup("#UndonePopup").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();
    }

    @Test
    @DisplayName("Logowanie z poprawnymi danymi dla usera")
    public void shouldSuccessLogin() {
        clickOn("#userlogin").write("username4");
        clickOn("#userpass").write("pass4");

        clickOn("#login");

        Parent popupRoot = lookup("#client").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();
    }

    @Test
    @DisplayName("Logowanie z nie poprawnymi danymi dla admina")
    public void shouldFailLoginAdminWithWrongCredentials() {
        clickOn("#userlogin").write("wrongUser");
        clickOn("#userpass").write("wrongPass");

        clickOn("#login_a");

        Parent popupRoot = lookup("#UndonePopup").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();
    }

    @Test
    @DisplayName("Logowanie z poprawnymi danymi dla admina")
    public void shouldSuccessLoginAdmin() {
        clickOn("#userlogin").write("admin10");
        clickOn("#userpass").write("adminpass10");

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

        clickOn("#username").write("Juan");
        clickOn("#usersurname").write("Pablo");
        clickOn("#userdate").write("6.06.2025");
        clickOn("#useremail").write("s093644@student.tu.kielce.pl");
        clickOn("#userlogin").write("JuanII");
        clickOn("#userpassword").write("123456");
        clickOn("#register");

        popupRoot = lookup("#loginbox").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();

        clickOn("#userlogin").write("JuanII");
        clickOn("#userpass").write("123456");

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

        clickOn("#username").write("Juan");
        clickOn("#usersurname").write("Pablo");
        clickOn("#userdate").write("6.06.2025");
        clickOn("#useremail").write("s093644@student.tu.kielce.pl");
        clickOn("#userlogin").write("username6");
        clickOn("#userpassword").write("123456");
        clickOn("#register");

        popupRoot = lookup("#UndonePopup").queryAs(Parent.class);
        assertThat(popupRoot).isVisible();
    }
}