package com.example.pd_pro_biblioteka;

import com.example.pd_pro_biblioteka.control.Control;
import com.example.pd_pro_biblioteka.model.*;
import com.example.pd_pro_biblioteka.service.SupabaseClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Testy Control")
@WebMvcTest(Control.class)
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
        class POSTTests {

            @Test
            @DisplayName("Tworzenie admina")
            void createAdmin() {
                Mockito.when(supabaseClient.addAdmin(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()))
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
                Mockito.when(supabaseClient.addAutor(Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()))
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
                Mockito.when(supabaseClient.addKara(Mockito.anyDouble(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt()))
                        .thenReturn(String.valueOf(kara));
                try {
                    mockMvc.perform(post("/library/kary")
                                    .param("kwota", "20.5")
                                    .param("dataWydaniaKary", "2025-04-16")
                                    .param("terminZaplaty", "2025-04-18")
                                    .param("idUzytkownika", "101"))
                            .andExpect(status().isOk());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Test
            @DisplayName("Tworzenie ksiazki")
            void createKsiazka() {
                Mockito.when(supabaseClient.addKsiazka(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
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
                Mockito.when(supabaseClient.addUzytkownik(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                        .thenReturn(String.valueOf(uzytkownik));
                try {
                    mockMvc.perform(post("/library/uzytkownicy")
                                    .param("imie", "Jan")
                                    .param("nazwisko", "Kowalski")
                                    .param("Data_Urodzenia", "2000-01-01")
                                    .param("Nazwa_Uzytkownika", "username")
                                    .param("Haslo", "pass")
                                    .param("Email", "s092677@student.tu.kielce.pl"))
                            .andExpect(status().isOk());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Test
            @DisplayName("Tworzenie wypozyczenia")
            void createWypozyczenie() {
                Mockito.when(supabaseClient.addWypozyczenie(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt()))
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
    class GETTests {

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
            Mockito.when(supabaseClient.getKsiazkaFiltr(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt())).thenReturn("Ksiazka");

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
            Mockito.when(supabaseClient.getPlacowki()).thenReturn("Uzytkownik");

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
            Mockito.when(supabaseClient.getUzytkownicyLogin(Mockito.anyString(), Mockito.anyString())).thenReturn("Uzytkownik");

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
            Mockito.when(supabaseClient.getPlacowki()).thenReturn("Wyporzyczenie");

            try {
                mockMvc.perform(get("/library/wypozyczenia"))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Nested
    @DisplayName("Testy PUT")
    class PUTTest
    {
        @Test
        @DisplayName("Aktualizacja admina")
        void updateAdmin() {
            Mockito.when(supabaseClient.updateAdmin(
                            Mockito.eq(1),
                            Mockito.eq("Jan"),
                            Mockito.eq("Kowalski"),
                            Mockito.eq("usernameadmin"),
                            Mockito.eq("adminpass"),
                            Mockito.eq(101)))
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
                            Mockito.eq(1),
                            Mockito.eq("John"),
                            Mockito.eq("Tolkien"),
                            Mockito.eq(1893)))
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
                            Mockito.eq(1),
                            Mockito.eq(20.5),
                            Mockito.eq("2025-04-16"),
                            Mockito.eq("2025-04-18"),
                            Mockito.eq(Boolean.FALSE),
                            Mockito.eq(101)))
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
                            Mockito.eq(1),
                            Mockito.eq("Silmaril"),
                            Mockito.eq("Fantasy"),
                            Mockito.eq("1977"),
                            Mockito.eq("2025-04-16"),
                            Mockito.eq(101),
                            Mockito.eq(201)))
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
            Mockito.when(supabaseClient.updatePlacowka(Mockito.eq(1), Mockito.eq("Nowy Adres")))
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
                            Mockito.eq(1),
                            Mockito.eq("Jan"),
                            Mockito.eq("Kowalski"),
                            Mockito.eq("2000-01-01"),
                            Mockito.eq("user"),
                            Mockito.eq("pass"),
                            Mockito.eq("s092677@student.tu.kielce.pl"),
                            Mockito.eq(Boolean.FALSE)))
                    .thenReturn("Uzytkownik zaktualizowany");

            try {
                mockMvc.perform(put("/library/uzytkownicy/1")
                                .param("Imie", "Jacek"))
                        .andExpect(status().isOk());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Test
        @DisplayName("Aktualizacja wypozyczenia")
        void updateWypozyczenia() {
            Mockito.when(supabaseClient.updateWypozyczenie(
                            Mockito.eq(1),
                            Mockito.eq("2025-04-16"),
                            Mockito.eq("2025-04-17"),
                            Mockito.eq("2025-04-18"),
                            Mockito.eq(101),
                            Mockito.eq(201)))
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
    class DELETETest
    {

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

            try {
                mockMvc.perform(delete("/library/uzytkownicy/1"))
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

