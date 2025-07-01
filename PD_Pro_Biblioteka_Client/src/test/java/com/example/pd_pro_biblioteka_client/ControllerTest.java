package com.example.pd_pro_biblioteka_client;


import com.example.pd_pro_biblioteka_client.controller.Addborow;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;
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
            clickOn("#user_login").write("admin10");
            clickOn("#user_pass").write("adminpass10");

            clickOn("#login_a");

            Parent popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#borrow_button");

            popupRoot = lookup("#addBorrow").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#borrowUserID").write("15");
            clickOn("#borrowBookID").write("10000");
            clickOn("#borrowReturnDate").write("2.01.2037");

            clickOn("#b_add");
            popupRoot = lookup("#donePopup").queryAs(Parent.class);
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
            clickOn("#user_login").write("admin10");
            clickOn("#user_pass").write("adminpass10");

            clickOn("#login_a");

            Parent popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#Kary");
            clickOn("#add_penalty_button");

            popupRoot = lookup("#addPenalty").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#userID").write("15");
            clickOn("#userPen").write("10000");
            clickOn("#pen_date").write("2.01.2037");
            clickOn("#pen_desc").write("ROBOT - KARA");

            clickOn("#p_add");
            popupRoot = lookup("#donePopup").queryAs(Parent.class);
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
            clickOn("#user_login").write("admin10");
            clickOn("#user_pass").write("adminpass10");

            clickOn("#login_a");

            Parent popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#add_button");

            popupRoot = lookup("#addbook_Modal").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#addAuthor");

            popupRoot = lookup("#admin_addauthor").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#au_name").write("John");
            clickOn("#au_surname").write("Doe");
            clickOn("#au_year").write("2077");

            clickOn("#au_add_button");
            popupRoot = lookup("#donePopup").queryAs(Parent.class);
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
            clickOn("#user_login").write("admin10");
            clickOn("#user_pass").write("adminpass10");

            clickOn("#login_a");

            Parent popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#add_button");

            popupRoot = lookup("#addbook_Modal").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#addBook");

            popupRoot = lookup("#admin_addbook").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#a_title").write("Test");
            clickOn("#a_genre").write("GATUNEK");
            clickOn("#a_relaseDate").write("2077");
            clickOn("#a_id_author").write("5");
            clickOn("#a_id_plac").write("1");

            clickOn("#bk_add_button");
            popupRoot = lookup("#donePopup").queryAs(Parent.class);
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
            clickOn("#user_login").write("username4");
            clickOn("#user_pass").write("pass4");

            clickOn("#login");

            Parent popupRoot = lookup("#client").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#settings");


            clickOn("#2FA_button");
            popupRoot = lookup("#setup_totp").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#totpCodeField").write("123456");
            clickOn("#confirmButton");

            popupRoot = lookup("#undonePopup").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();
        }

        @Test
        @DisplayName("Ustawienie TOTP dla admina")
        public void setupTOTPTest() {
            clickOn("#user_login").write("admin10");
            clickOn("#user_pass").write("adminpass10");

            clickOn("#login_a");

            Parent popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#Ustawienia");


            clickOn("#2FA_button");
            popupRoot = lookup("#setup_totp").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#totpCodeField").write("123456");
            clickOn("#confirmButton");

            popupRoot = lookup("#undonePopup").queryAs(Parent.class);
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
            clickOn("#user_login").write("admin10");
            clickOn("#user_pass").write("adminpass10");
            clickOn("#login_a");

            Parent popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#Ustawienia");
            clickOn("#admin_password").eraseText(20).write("password");
            clickOn("#save_button");
            clickOn("#logg_button");

            popupRoot = lookup("#login").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#user_login").write("admin10");
            clickOn("#user_pass").write("password");
            clickOn("#login_a");

            popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#Ustawienia");
            clickOn("#admin_password").eraseText(20).write("adminpass10");
            clickOn("#save_button");
            clickOn("#logg_button");

            popupRoot = lookup("#login").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();
        }

        @Test
        @DisplayName("Zmiana danych u użytkownika")
        public void changeDataTestUser() {
            clickOn("#user_login").write("username4");
            clickOn("#user_pass").write("pass4");
            clickOn("#login");

            Parent popupRoot = lookup("#client").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#settings");
            clickOn("#user_password").eraseText(20).write("password");
            clickOn("#save_button");
            clickOn("#test");

            popupRoot = lookup("#login").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#user_login").write("username4");
            clickOn("#user_pass").write("password");
            clickOn("#login");

            popupRoot = lookup("#client").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#settings");
            clickOn("#user_password").eraseText(20).write("pass4");
            clickOn("#save_button");
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
            clickOn("#user_login").write("username4");
            clickOn("#user_pass").write("pass4");
            clickOn("#login");

            Parent popupRoot = lookup("#client").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#filtr");
            clickOn("#f_combo1").clickOn("tytul");
            clickOn("#f_search1").write("Tom");

            clickOn("#f_combo2").clickOn("gatunek");
            clickOn("#f_search2").write("H");
            clickOn("#filtr_button");

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
            clickOn("#user_login").write("admin10");
            clickOn("#user_pass").write("adminpass10");
            clickOn("#login_a");

            Parent popupRoot = lookup("#admin").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#refr_button");


        }

        @Test
        @DisplayName("Refresh button")
        public void RefTestUser() {
            clickOn("#user_login").write("username4");
            clickOn("#user_pass").write("pass4");
            clickOn("#login");

            Parent popupRoot = lookup("#client").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#refr_button");


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

            clickOn("#user_email").write("testmail@1337mail.com");
            clickOn("#remind");

            popupRoot = lookup("#donePopup").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();
        }

        @Test
        @DisplayName("Test email reset")
        public void TOTPresetTest() {
            clickOn("#pass_rem");

            Parent popupRoot = lookup("#reminder").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#user_email").write("testmail@1337mail.com");
            clickOn("#remindTOTP");

            popupRoot = lookup("#totpRestart").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();

            clickOn("#codeField").write("123123");
            clickOn("#submitButton");
            popupRoot = lookup("#donePopup").queryAs(Parent.class);
            assertThat(popupRoot).isVisible();
        }
    }

}




