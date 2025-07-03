package com.example.pdprobibliotekaclient;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.testfx.assertions.api.Assertions.assertThat;

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
    class AddborowTest extends ApplicationTest {

        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();
        }

        @Test
        @DisplayName("Dodanie testowego wypożyczenia")
        public void addborowTest() {
            clickOn("#userlogin").write("admin10");
            clickOn("#userpass").write("adminpass10");

            clickOn("#login_a");

            Parent popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#borrowbutton");

            popupRoot = lookup("#addBorrow").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#borrowUserid").write("15");
            clickOn("#borrowBookid").write("10000");
            clickOn("#borrowReturnDate").write("2.01.2037");

            clickOn("#badd");
            popupRoot = lookup("#DonePopup").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();
        }


    }

    @Nested
    @DisplayName("Testy klasy Addpenalnty")
    class AddpenaltyTest extends ApplicationTest {

        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();
        }

        @Test
        @DisplayName("Dodanie testowej kary")
        public void addpenaltyTest() {
            clickOn("#userlogin").write("admin10");
            clickOn("#userpass").write("adminpass10");

            clickOn("#login_a");

            Parent popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#Kary");
            clickOn("#addpenaltybutton2");

            popupRoot = lookup("#addPenalty").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#userid").write("15");
            clickOn("#userPen").write("10000");
            clickOn("#pendate").write("2.01.2037");
            clickOn("#pendesc").write("ROBOT - KARA");

            clickOn("#p_add");
            popupRoot = lookup("#DonePopup").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();
        }
    }

    @Nested
    @DisplayName("Testy klasy Addauthor")
    class AddauthorTest extends ApplicationTest {

        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();
        }

        @Test
        @DisplayName("Dodanie testowego autora")
        public void addauthorTest() {
            clickOn("#userlogin").write("admin10");
            clickOn("#userpass").write("adminpass10");

            clickOn("#login_a");

            Parent popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#add_button");

            popupRoot = lookup("#addbook_Modal").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#addAuthor");

            popupRoot = lookup("#admin_addauthor").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#auname").write("John");
            clickOn("#ausurname").write("Doe");
            clickOn("#auyear").write("2077");

            clickOn("#au_add_button");
            popupRoot = lookup("#DonePopup").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();
        }
    }

    @Nested
    @DisplayName("Testy klasy Addbook")
    class AddbookTest extends ApplicationTest {

        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();
        }

        @Test
        @DisplayName("Dodanie testowej książki")
        public void addbookTest() {
            clickOn("#userlogin").write("admin10");
            clickOn("#userpass").write("adminpass10");

            clickOn("#login_a");

            Parent popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#add_button");

            popupRoot = lookup("#addbook_Modal").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#addBook");

            popupRoot = lookup("#admin_addbook").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#atitle").write("Test");
            clickOn("#agenre").write("GATUNEK");
            clickOn("#arelaseDate").write("2077");
            clickOn("#aidauthor").write("5");
            clickOn("#aidplac").write("1");

            clickOn("#bk_add_button");
            popupRoot = lookup("#DonePopup").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();
        }
    }

    @Nested
    @DisplayName("Testy klasy setupTOTP")
    class AddsetupTOTPTest extends ApplicationTest {

        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();
        }

        @Test
        @DisplayName("Ustawienie TOTP dla usera")
        public void setupTOTPTestUser() {
            clickOn("#userlogin").write("username4");
            clickOn("#userpass").write("pass4");

            clickOn("#login");

            Parent popupRoot = lookup("#client").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#settings");


            clickOn("#2FA_button");
            popupRoot = lookup("#setup_totp").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#totpCodeField").write("123456");
            clickOn("#confirmButton");

            popupRoot = lookup("#UndonePopup").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();
        }

        @Test
        @DisplayName("Ustawienie TOTP dla admina")
        public void setupTOTPTest() {
            clickOn("#userlogin").write("admin10");
            clickOn("#userpass").write("adminpass10");

            clickOn("#login_a");

            Parent popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#Ustawienia");


            clickOn("#2FA_button");
            popupRoot = lookup("#setup_totp").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#totpCodeField").write("123456");
            clickOn("#confirmButton");

            popupRoot = lookup("#UndonePopup").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();
        }
    }

    @Nested
    class changeDataTest extends ApplicationTest {

        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();
        }

        @Test
        @DisplayName("Zmiana danych u admina")
        public void changeDataTestAdmin() {
            clickOn("#userlogin").write("admin10");
            clickOn("#userpass").write("adminpass10");
            clickOn("#login_a");

            Parent popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#Ustawienia");
            clickOn("#adminpassword").eraseText(20).write("password");
            clickOn("#savebutton");
            clickOn("#loggbutton");

            popupRoot = lookup("#login").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#userlogin").write("admin10");
            clickOn("#userpass").write("password");
            clickOn("#login_a");

            popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#Ustawienia");
            clickOn("#adminpassword").eraseText(20).write("adminpass10");
            clickOn("#savebutton");
            clickOn("#loggbutton");

            popupRoot = lookup("#login").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();
        }

        @Test
        @DisplayName("Zmiana danych u użytkownika")
        public void changeDataTestUser() {
            clickOn("#userlogin").write("username4");
            clickOn("#userpass").write("pass4");
            clickOn("#login");

            Parent popupRoot = lookup("#client").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#settings");
            clickOn("#userpassword").eraseText(20).write("password");
            clickOn("#savebutton");
            clickOn("#test");

            popupRoot = lookup("#login").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#userlogin").write("username4");
            clickOn("#userpass").write("password");
            clickOn("#login");

            popupRoot = lookup("#client").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#settings");
            clickOn("#userpassword").eraseText(20).write("pass4");
            clickOn("#savebutton");
            clickOn("#test");

            popupRoot = lookup("#login").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();
        }
    }

    @Nested
    @DisplayName("Testy filtrów")
    class UserFiltrTest extends ApplicationTest {

        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();
        }

        @Test
        @DisplayName("Testy filtrow")
        public void FiltrTest() {
            clickOn("#userlogin").write("username4");
            clickOn("#userpass").write("pass4");
            clickOn("#login");

            Parent popupRoot = lookup("#client").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#filtr");
            clickOn("#fcombo1").clickOn("tytul");
            clickOn("#fsearch1").write("Tom");

            clickOn("#fcombo2").clickOn("gatunek");
            clickOn("#fsearch2").write("H");
            clickOn("#filtrbutton");

        }
    }

    @Nested
    @DisplayName("Testy refresha")
    class RefreshTest extends ApplicationTest {

        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();
        }

        @Test
        @DisplayName("Refresh button")
        public void RefTestAdmin() {
            clickOn("#userlogin").write("admin10");
            clickOn("#userpass").write("adminpass10");
            clickOn("#login_a");

            Parent popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#refrbutton");


        }

        @Test
        @DisplayName("Refresh button")
        public void RefTestUser() {
            clickOn("#userlogin").write("username4");
            clickOn("#userpass").write("pass4");
            clickOn("#login");

            Parent popupRoot = lookup("#client").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#refrbutton");


        }
    }

    @Nested
    @DisplayName("Testy resetu hasła i TOTP")
    class ResetTest extends ApplicationTest {

        @Override
        public void start(Stage stage) throws Exception {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();
        }

        @Test
        @DisplayName("Test email reset")
        public void emailresetTest() {
            clickOn("#pass_rem");

            Parent popupRoot = lookup("#reminder").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#useremail").write("testmail@1337mail.com");
            clickOn("#remind");

            popupRoot = lookup("#DonePopup").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();
        }

        @Test
        @DisplayName("Test email reset")
        public void TOTPresetTest() {
            clickOn("#pass_rem");

            Parent popupRoot = lookup("#reminder").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#useremail").write("testmail@1337mail.com");
            clickOn("#remindTOTP");

            popupRoot = lookup("#totpRestart").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#codeField").write("123123");
            clickOn("#submitButton");
            popupRoot = lookup("#DonePopup").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();
        }
    }

}




