package com.example.pdprobiblioteka;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.pdprobiblioteka.control.Control;
import com.example.pdprobiblioteka.model.Admin;
import com.example.pdprobiblioteka.model.Autorzy;
import com.example.pdprobiblioteka.model.Kary;
import com.example.pdprobiblioteka.model.Ksiazka;
import com.example.pdprobiblioteka.model.Placowka;
import com.example.pdprobiblioteka.model.Uzytkownik;
import com.example.pdprobiblioteka.model.Wypozyczenia;
import com.example.pdprobiblioteka.service.SupabaseClient;
import java.util.Base64;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Testy Klasy Control.
 */
@DisplayName("Testy Control")
@WebMvcTest(controllers = Control.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class ControlTest {

  private Admin admin;
  private Autorzy autor;
  private Kary kara;
  private Ksiazka ksiazka;
  private Placowka placowka;
  private Uzytkownik uzytkownik;
  private Wypozyczenia wypozyczenie;


  @MockitoBean
  private SupabaseClient supabaseClient;

  @Autowired
  private MockMvc mockMvc;

  @Nested
  @DisplayName("Testy POST")
  class PostTest {

    @Test
    @DisplayName("Tworzenie admina")
    void createAdmin() {
      Mockito.when(
              supabaseClient.addAdmin(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                  Mockito.anyString(), Mockito.anyInt()))
          .thenReturn(String.valueOf(admin));
      try {
        mockMvc.perform(post("/library/admini")
                .param("imie", "Jan")
                .param("nazwisko", "Kowalski")
                .param("nazwaUzytkownika", "usernameadmin")
                .param("haslo", "passadmin")
                .param("idPlacowki", "101"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Tworzenie autora")
    void createAutor() {
      Mockito.when(
              supabaseClient.addAutor(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()))
          .thenReturn(String.valueOf(autor));
      try {
        mockMvc.perform(post("/library/autorzy")
                .param("imie", "John")
                .param("nazwisko", "Tolkien")
                .param("rokUrodzenia", "1892"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }


    @Test
    @DisplayName("Tworzenie kary")
    void createKara() {
      Mockito.when(
              supabaseClient.addKara(Mockito.anyDouble(), Mockito.anyString(), Mockito.anyString(),
                  Mockito.anyInt(), Mockito.anyString()))
          .thenReturn(String.valueOf(kara));
      try {
        mockMvc.perform(post("/library/kary")
                .param("kwota", "20.5")
                .param("dataWydaniaKary", "2025-04-16")
                .param("terminZaplaty", "2025-04-18")
                .param("idUzytkownika", "101")
                .param("opis", "opis"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Tworzenie ksiazki")
    void createKsiazka() {
      Mockito.when(
              supabaseClient.addKsiazka(Mockito.anyString(), Mockito.anyString(),
                  Mockito.anyString(),
                  Mockito.anyInt(), Mockito.anyInt()))
          .thenReturn(String.valueOf(ksiazka));
      try {
        mockMvc.perform(post("/library/ksiazki")
                .param("tytul", "Silmarillion")
                .param("gatunek", "Fantasy")
                .param("dataWydania", "1977-09-15")
                .param("idAutora", "101")
                .param("idPlacowki", "201"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Tworzenie placowki")
    void createPlacowka() {
      Mockito.when(supabaseClient.addPlacowka(Mockito.anyString()))
          .thenReturn(String.valueOf(placowka));
      try {
        mockMvc.perform(post("/library/placowki")
                .param("adres", "Aleja Tysiąclecia Państwa Polskiego 7, 28-340 Kielce"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Tworzenie uzytkownika")
    void createUzytkownik() {
      Mockito.when(supabaseClient.addUzytkownik(Mockito.anyString(), Mockito.anyString(),
              Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
          .thenReturn(String.valueOf(uzytkownik));
      try {
        mockMvc.perform(post("/library/uzytkownicy")
                .param("imie", "Jan")
                .param("nazwisko", "Kowalski")
                .param("dataUrodzenia", "2000-01-01")
                .param("nazwaUzytkownika", "username")
                .param("haslo", "pass")
                .param("email", "s092677@student.tu.kielce.pl"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Tworzenie wypozyczenia")
    void createWypozyczenie() {
      Mockito.when(supabaseClient.addWypozyczenie(Mockito.anyString(), Mockito.anyString(),
              Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
          .thenReturn(String.valueOf(wypozyczenie));
      try {
        mockMvc.perform(post("/library/wypozyczenia")
                .param("dataWypozyczenia", "2025-04-16")
                .param("dataOddania", "2025-04-17")
                .param("terminOddania", "2025-04-18")
                .param("idKsiazki", "101")
                .param("idUzytkownika", "102"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

  }

  @Nested
  @DisplayName("Testy GET")
  class GetTests {

    @Test
    @DisplayName("Pobieranie listy adminow")
    void getAdmin() {
      Mockito.when(supabaseClient.getAdmini()).thenReturn("Admin");

      try {
        mockMvc.perform(get("/library/admini"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Pobieranie listy adminow o konkretnym id")
    void getAdminaid() {
      Mockito.when(supabaseClient.getAdminaid(1)).thenReturn("Admin");

      try {
        mockMvc.perform(get("/library/admini/1"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Pobieranie listy autorow")
    void getAutorzy() {
      Mockito.when(supabaseClient.getAutorzy()).thenReturn("Autor");

      try {
        mockMvc.perform(get("/library/autorzy"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Pobieranie listy kar")
    void getKary() {
      Mockito.when(supabaseClient.getKary()).thenReturn("Kara");

      try {
        mockMvc.perform(get("/library/kary"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Pobieranie kary o konkretnym id uzytkownika")
    void getKaryuid() {
      Mockito.when(supabaseClient.getKaryuid(1)).thenReturn("Kara");

      try {
        mockMvc.perform(get("/library/kary/1"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Pobieranie listy ksiazek")
    void getKsiazki() {
      Mockito.when(supabaseClient.getKsiazki()).thenReturn("Ksiazka");

      try {
        mockMvc.perform(get("/library/ksiazki"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Pobieranie listy ksiazek z filtrem")
    void getKsiazkifiltr() {
      Mockito.when(
              supabaseClient.getKsiazkaFiltr(Mockito.anyInt(),
                  Mockito.anyString(), Mockito.anyString(),
                  Mockito.anyString(), Mockito.anyString(),
                  Mockito.anyString(), Mockito.anyInt()))
          .thenReturn("Ksiazka");

      try {
        mockMvc.perform(get("/library/ksiazki/filtr"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Pobieranie listy placowek")
    void getPlacowki() {
      Mockito.when(supabaseClient.getPlacowki()).thenReturn("Placowka");

      try {
        mockMvc.perform(get("/library/placowki"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Pobieranie listy uzytkownikow")
    void getUzytkownicy() {
      Mockito.when(supabaseClient.getUzytkownicy()).thenReturn("Uzytkownik");

      try {
        mockMvc.perform(get("/library/uzytkownicy"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Pobieranie uzytkownika do logowania")
    void getUzytkowniklogin() {
      Mockito.when(supabaseClient.getUzytkownicyLogin(Mockito.anyString(), Mockito.anyString()))
          .thenReturn("Uzytkownik");

      try {
        mockMvc.perform(get("/library/uzytkownicy/login/password"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Pobieranie listy wyporzyczen")
    void getWypozyczenia() {
      Mockito.when(supabaseClient.getWypozyczenia()).thenReturn("Wyporzyczenie");

      try {
        mockMvc.perform(get("/library/wypozyczenia"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Pobieranie listy wyporzyczen o konkretnym id uzytkownika")
    void getWypozyczeniauid() {
      Mockito.when(supabaseClient.getWypozyczeniauid(1)).thenReturn("Wyporzyczenie");

      try {
        mockMvc.perform(get("/library/wypozyczenia/1"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Logowanie admina")
    void getAdminLogin() {
      Mockito.when(supabaseClient.getAdminLogin("login1", "password")).thenReturn("Admin Login");

      try {
        mockMvc.perform(get("/library/admini/login1/password"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

  }

  @Nested
  @DisplayName("Testy PUT")
  class PutTests {

    @Test
    @DisplayName("Aktualizacja admina")
    void updateAdmin() {
      Mockito.when(supabaseClient.updateAdmin(
              new Admin(1, "Jan", "Kowalski", "usernameadmin", "adminpass",
                  101, false, "secret", "ADMIN")))
          .thenReturn("Admin zaktualizowany");

      try {
        mockMvc.perform(put("/library/admini/1")
                .param("Imie", "Jacek"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Aktualizacja Autora")
    void updateAutor() {
      Mockito.when(supabaseClient.updateAutor(
              1,
              "John",
              "Tolkien",
              1893))
          .thenReturn("Autor zaktualizowany");

      try {
        mockMvc.perform(put("/library/autorzy/1")
                .param("rokUrodzenia", "1893"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Aktualizacja kary")
    void updateKary() {
      Mockito.when(supabaseClient.updateKara(
              1,
              20.5,
              "2025-04-16",
              "2025-04-18",
              Boolean.FALSE,
              101,
              "opis"))
          .thenReturn("Kara zaktualizowana");

      try {
        mockMvc.perform(put("/library/kary/1")
                .param("kwota", "100"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }


    @Test
    @DisplayName("Aktualizacja ksiazek")
    void updateKsiazka() {
      Mockito.when(supabaseClient.updateKsiazka(
              new Ksiazka(1, "Silmaril", "Fantasy", "1977", "2025-04-16", 101, 201, Boolean.FALSE,
                  Boolean.FALSE)))
          .thenReturn("Ksiazka zaktualizowana");

      try {
        mockMvc.perform(put("/library/ksiazki/1")
                .param("tytul", "Silmarillion"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Aktualizacja placowki")
    void updatePlacowka() {
      Mockito.when(supabaseClient.updatePlacowka(1, "Nowy Adres"))
          .thenReturn("Placowka zaktualizowana");

      try {
        mockMvc.perform(put("/library/placowki/1")
                .param("adres", "Nowy Adres"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Aktualizacja uzytkownika")
    void updateUzytkownik() {
      Mockito.when(supabaseClient.updateUzytkownik(
              new Uzytkownik(
                  1,
                  "Jan",
                  "Kowalski",
                  "2000-01-01",
                  "user",
                  "pass",
                  "s092677@student.tu.kielce.pl",
                  Boolean.FALSE,
                  Boolean.FALSE,
                  "secret",
                  "USER")))
          .thenReturn("Uzytkownik zaktualizowany");

      String payload = Base64.getUrlEncoder().withoutPadding()
          .encodeToString("{\"userId\":\"1\"}".getBytes());
      String dummyJwt = "Bearer header." + payload + ".signature";
      try {
        mockMvc.perform(put("/library/uzytkownicy/1")
                .param("Imie", "Jacek")
                .header("Authorization", dummyJwt))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Przypomnienie hasla uzytkownika")
    void updatePassUzytkownik() {
      Mockito.when(supabaseClient.updateUzytkownik(
              new Uzytkownik(
                  1,
                  "Jan",
                  "Kowalski",
                  "2000-01-01",
                  "user",
                  "pass",
                  "s092677@student.tu.kielce.pl",
                  Boolean.FALSE,
                  Boolean.FALSE,
                  "secret",
                  "USER")))
          .thenReturn("Haslo zaktualizowane");

      try {
        mockMvc.perform(put("/library/uzytkownicy/passwordreset/email"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }


    @Test
    @DisplayName("Aktualizacja wypozyczenia")
    void updateWypozyczenia() {
      Mockito.when(supabaseClient.updateWypozyczenie(
              1,
              "2025-04-16",
              "2025-04-17",
              "2025-04-18",
              101,
              201))
          .thenReturn("Wpozyczenie zaktualizowane");

      try {
        mockMvc.perform(put("/library/wypozyczenia/1")
                .param("terminOddania", "2025-04-19"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

  }

  @Nested
  @DisplayName("Testy DELETE")
  class DeleteTests {

    @Test
    @DisplayName("Usuniecie Admina")
    void deleteAdmina() {
      Mockito.when(supabaseClient.deleteAdmin(1)).thenReturn("Admin usuniety");

      try {
        mockMvc.perform(delete("/library/admini/1"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Usuniecie Autora")
    void deleteAutora() {
      Mockito.when(supabaseClient.deleteAutor(1)).thenReturn("Autor usuniety");

      try {
        mockMvc.perform(delete("/library/autorzy/1"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Usuniecie Kary")
    void deleteKara() {
      Mockito.when(supabaseClient.deletePlacowka(1)).thenReturn("Kara usunieta");

      try {
        mockMvc.perform(delete("/library/kary/1"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Usuniecie ksiazki")
    void deleteKsiazka() {
      Mockito.when(supabaseClient.deletePlacowka(1)).thenReturn("Ksiazka usunieta");

      try {
        mockMvc.perform(delete("/library/ksiazki/1"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Usuniecie placowki")
    void deletePlacowka() {
      Mockito.when(supabaseClient.deletePlacowka(1)).thenReturn("Placowka usunieta");

      try {
        mockMvc.perform(delete("/library/placowki/1"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Usuniecie uzytkownika")
    void deleteUzytkownik() {
      Mockito.when(supabaseClient.deleteUzytkownik(1)).thenReturn("Uzytkownik usuniety");
      String payload = Base64.getUrlEncoder().withoutPadding()
          .encodeToString("{\"userId\":\"1\"}".getBytes());
      String dummyJwt = "Bearer header." + payload + ".signature";

      try {
        mockMvc.perform(delete("/library/uzytkownicy/1")
                .header("Authorization", dummyJwt))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Test
    @DisplayName("Usuniecie wypozyczenia")
    void deleteWypozyczenie() {
      Mockito.when(supabaseClient.deleteUzytkownik(1)).thenReturn("Wypozeczenie usuniete");

      try {
        mockMvc.perform(delete("/library/wypozyczenia/1"))
            .andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

  }

}

