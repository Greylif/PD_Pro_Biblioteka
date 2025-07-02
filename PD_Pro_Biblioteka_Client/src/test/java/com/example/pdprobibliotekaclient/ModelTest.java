package com.example.pdprobibliotekaclient;

import com.example.pdprobibliotekaclient.model.*;
import com.example.pdprobibliotekaclient.model.Wypozyczenia;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {
    Gson gson = new Gson();

    /**
     * Testy dla klasy modelu wypoąyczenia
     */
    private Wypozyczenia wypozyczenie;

    @BeforeEach
    void setUp() {
        wypozyczenie = new Wypozyczenia(1, "2024-01-01", "2024-02-01", "2024-01-15", 100, 200);
    }

    @Test
    void testConstructorAndProperties() {
        assertEquals(1, wypozyczenie.idProperty().get());
        assertEquals("2024-01-01", wypozyczenie.data_WypozyczeniaProperty().get());
        assertEquals("2024-02-01", wypozyczenie.data_OddaniaProperty().get());
        assertEquals("2024-01-15", wypozyczenie.termin_OddaniaProperty().get());
        assertEquals(100, wypozyczenie.id_ksiazkiProperty().get());
        assertEquals(200, wypozyczenie.id_uzytkownikaProperty().get());
    }

    @Test
    void testSettersAndGettersForExtraFields() {
        wypozyczenie.setImie("Jan");
        wypozyczenie.setNazwisko("Kowalski");
        wypozyczenie.setTytul("Pan Tadeusz");
        wypozyczenie.setAutor("Adam Mickiewicz");
        wypozyczenie.setDataWypozyczenia("2024-01-01");
        wypozyczenie.setTerminOddania("2024-02-01");

        assertEquals("Jan", wypozyczenie.getImie());
        assertEquals("Kowalski", wypozyczenie.getNazwisko());
        assertEquals("Pan Tadeusz", wypozyczenie.getTytul());
        assertEquals("Adam Mickiewicz", wypozyczenie.getAutor());
        assertEquals("2024-01-01", wypozyczenie.getDataWypozyczenia());
        assertEquals("2024-02-01", wypozyczenie.getTerminOddania());
    }

    @Test
    void testToStringOutput() {
        String expected = "Wypozyczenia{id=" + wypozyczenie.idProperty() +
                ", Data_Wypozyczenia=" + wypozyczenie.data_WypozyczeniaProperty() +
                ", Data_Oddania=" + wypozyczenie.data_OddaniaProperty() +
                ", Termin_Oddania=" + wypozyczenie.termin_OddaniaProperty() +
                ", id_ksiazki=" + wypozyczenie.id_ksiazkiProperty() +
                ", id_uzytkownika=" + wypozyczenie.id_uzytkownikaProperty() + "}";

        assertEquals(expected, wypozyczenie.toString());
    }


    /**
     * Testy dla klasy modelu użytkownik
     */
    private Uzytkownik uzytkownik;

    @BeforeEach
    void setUpUser() {
        uzytkownik = new Uzytkownik(
                1,
                "Jan",
                "Kowalski",
                "jkowalski",
                "tajnehaslo123",
                "jan.kowalski@example.com",
                "1990-01-01",
                false,
                true,
                "secret-key"
        );
    }

    @Test
    void testConstructorAndPropertiesUser() {
        assertEquals(1, uzytkownik.idProperty().get());
        assertEquals("Jan", uzytkownik.imieProperty().get());
        assertEquals("Kowalski", uzytkownik.nazwiskoProperty().get());
        assertEquals("1990-01-01", uzytkownik.wiekProperty().get());
        assertEquals("tajnehaslo123", uzytkownik.hasloProperty().get());
        assertEquals("jkowalski", uzytkownik.nazwaProperty().get());
        assertEquals("jan.kowalski@example.com", uzytkownik.emailProperty().get());
        assertFalse(uzytkownik.ZablokowanyProperty().get());
        assertTrue(uzytkownik.isMfaEnabled());
        assertEquals("secret-key", uzytkownik.getMfaSecret());
    }

    @Test
    void testSettersAndGetters() {
        uzytkownik.setImie("Anna");
        uzytkownik.setNazwisko("Nowak");
        uzytkownik.setDataUrodzenia("1985-12-12");
        uzytkownik.setNazwaUzytkownika("anowak");
        uzytkownik.setHaslo("nowehaslo");
        uzytkownik.setEmail("anna.nowak@example.com");
        uzytkownik.setZablokowany(true);

        assertEquals("Anna", uzytkownik.getImie());
        assertEquals("Nowak", uzytkownik.getNazwisko());
        assertEquals("1985-12-12", uzytkownik.getDataUrodzenia());
        assertEquals("anowak", uzytkownik.getNazwaUzytkownika());
        assertEquals("nowehaslo", uzytkownik.getHaslo());
        assertEquals("anna.nowak@example.com", uzytkownik.getEmail());
        assertTrue(uzytkownik.isZablokowany());
    }

    @Test
    void testToString() {
        String expected = "Uzytkownik{id=" + uzytkownik.idProperty() +
                ", Imie='" + uzytkownik.imieProperty() +
                "', Nazwisko='" + uzytkownik.nazwiskoProperty() +
                "', Wiek=" + uzytkownik.wiekProperty() +
                ", Nazwa_Uzytkownika='" + uzytkownik.nazwaProperty() +
                "', Haslo='" + uzytkownik.hasloProperty() +
                "', Email='" + uzytkownik.emailProperty() + "'}";

        assertEquals(expected, uzytkownik.toString());
    }

    /**
     * Testy dla klasy modelu Placowka
     */
    private Placowka placowka;

    @BeforeEach
    void setUpLocation() {
        placowka = new Placowka(1, "ul. Biblioteczna 10, Warszawa");
    }

    @Test
    void testConstructorAndPropertiesLocation() {
        assertEquals(1, placowka.idProperty().get());
        assertEquals("ul. Biblioteczna 10, Warszawa", placowka.adresProperty().get());
    }

    @Test
    void testToStringLocation() {
        String expected = "Placowka{id=" + placowka.idProperty() +
                ", Adres='" + placowka.adresProperty() + "'}";

        assertEquals(expected, placowka.toString());
    }

    /**
     * Testy dla klasy modelu Ksiazka
     */
    private Ksiazka ksiazka;

    @BeforeEach
    void setUpBook() {
        ksiazka = new Ksiazka(
                1,
                "Pan Tadeusz",
                "Epopeja",
                1834,
                "2024-06-15",
                10,
                5,
                true,
                false
        );
        ksiazka.setAutorName("Adam Mickiewicz");
    }

    @Test
    void testConstructorAndPropertiesBook() {
        assertEquals(1, ksiazka.idProperty().get());
        assertEquals("Pan Tadeusz", ksiazka.tytulProperty().get());
        assertEquals("Epopeja", ksiazka.gatunekProperty().get());
        assertEquals(1834, ksiazka.dataWydaniaProperty().get());
        assertEquals("2024-06-15", ksiazka.dodanoProperty().get());
        assertEquals(10, ksiazka.idAutoraProperty().get());
        assertEquals(5, ksiazka.idPlacowkiProperty().get());
        assertTrue(ksiazka.RezerwacjaProperty().get());
        assertFalse(ksiazka.WypozyczenieProperty().get());
    }

    @Test
    void testAutorNamePropertyBook() {
        assertEquals("Adam Mickiewicz", ksiazka.autorNameProperty().get());

        ksiazka.setAutorName("Juliusz Słowacki");
        assertEquals("Juliusz Słowacki", ksiazka.autorNameProperty().get());
    }

    /**
     * Testy dla klasy modelu Kary
     */
    private Kary kara;

    @BeforeEach
    void setUpPenalty() {
        kara = new Kary(
                1,
                50.0,
                "2024-05-01",
                "2024-06-01",
                false,
                100,
                "Przekroczenie terminu oddania książki"
        );
        kara.setAutorName("Henryk Sienkiewicz");
        kara.setBookTitle("Quo Vadis");
    }

    @Test
    void testConstructorAndPropertiesPenalty() {
        assertEquals(1, kara.idProperty().get());
        assertEquals(50.0, kara.KwotaProperty().get());
        assertEquals("2024-05-01", kara.Data_Wydania_Kary_Property().get());
        assertEquals("2024-06-01", kara.Termin_Zaplaty_Property().get());
        assertFalse(kara.CzyZaplaconoProperty().get());
        assertEquals(100, kara.id_uzytkownikaProperty().get());
        assertEquals("Przekroczenie terminu oddania książki", kara.OpisProperty().get());
    }

    @Test
    void testSetCzyZaplaconoPenalty() {
        assertFalse(kara.CzyZaplaconoProperty().get());
        kara.setCzyZaplacono(true);
        assertTrue(kara.CzyZaplaconoProperty().get());
    }

    @Test
    void testAutorNameAndBookTitlePenalty() {
        assertEquals("Henryk Sienkiewicz", kara.getAutorName());
        assertEquals("Quo Vadis", kara.getBookTitle());

        kara.setAutorName("Bolesław Prus");
        kara.setBookTitle("Lalka");

        assertEquals("Bolesław Prus", kara.getAutorName());
        assertEquals("Lalka", kara.getBookTitle());
    }

    @Test
    void testToStringOutputPenalty() {
        String expected = "Kary{id=" + kara.idProperty() +
                ", Kwota=" + kara.KwotaProperty() +
                ", Data_Wydania_Kary=" + kara.Data_Wydania_Kary_Property() +
                ", Termin_Zaplaty=" + kara.Termin_Zaplaty_Property() +
                ", Czy_Zaplacono=" + kara.CzyZaplaconoProperty() +
                ", id_uzytkownika=" + kara.id_uzytkownikaProperty() + "}";

        assertEquals(expected, kara.toString());
    }

    /**
     * Testy dla klasy modelu Filtry
     */
    private Filtr filtr;

    @BeforeEach
    void setUpFiltr() {
        filtr = new Filtr(
                true,
                false,
                "Fantasy",
                10,
                2001,
                1,
                5,
                "2024-06-01",
                "Wiedźmin",
                "Andrzej Sapkowski"
        );
    }

    @Test
    void testConstructorAndGettersFiltr() {
        assertEquals(1, filtr.getId());
        assertEquals("Wiedźmin", filtr.getTytul());
        assertEquals("Fantasy", filtr.getGatunek());
        assertEquals(10, filtr.getIdAutora());
        assertEquals(2001, filtr.getDataWydania());
        assertEquals("2024-06-01", filtr.getDodano());
        assertEquals(5, filtr.getIdPlacowki());
        assertTrue(filtr.isRezerwacja());
        assertFalse(filtr.isCzyWypozyczono());
        assertEquals("Andrzej Sapkowski", filtr.getAutorName());
    }

    @Test
    void testSettersFiltr() {
        filtr.setId(2);
        filtr.setTytul("Lalka");
        filtr.setGatunek("Powieść");
        filtr.setIdAutora(20);
        filtr.setDataWydania(1890);
        filtr.setDodano("2024-01-15");
        filtr.setRezerwacja(false);
        filtr.setCzyWypozyczono(true);
        filtr.setIdPlacowki(9);
        filtr.setAutorName("Bolesław Prus");

        assertEquals(2, filtr.getId());
        assertEquals("Lalka", filtr.getTytul());
        assertEquals("Powieść", filtr.getGatunek());
        assertEquals(20, filtr.getIdAutora());
        assertEquals(1890, filtr.getDataWydania());
        assertEquals("2024-01-15", filtr.getDodano());
        assertFalse(filtr.isRezerwacja());
        assertTrue(filtr.isCzyWypozyczono());
        assertEquals(9, filtr.getIdPlacowki());
        assertEquals("Bolesław Prus", filtr.getAutorName());
    }

    @Test
    void testPropertyAccessorsFiltr() {
        assertEquals("Wiedźmin", filtr.tytulProperty().get());
        assertEquals("Fantasy", filtr.gatunekProperty().get());
        assertEquals(10, filtr.idAutoraProperty().get());
        assertEquals(2001, filtr.dataWydaniaProperty().get());
        assertEquals("2024-06-01", filtr.dodanoProperty().get());
        assertTrue(filtr.rezerwacjaProperty().get());
        assertFalse(filtr.czyWypozyczonoProperty().get());
        assertEquals(5, filtr.idPlacowkiProperty().get());
    }

    @Test
    void testToStringFiltr() {
        String toStringOutput = filtr.toString();
        assertTrue(toStringOutput.contains("id="));
        assertTrue(toStringOutput.contains("tytul="));
        assertTrue(toStringOutput.contains("gatunek="));
        assertTrue(toStringOutput.contains("data_Wydania="));
        assertTrue(toStringOutput.contains("rezerwacja="));
        assertTrue(toStringOutput.contains("id_placowki="));
    }

    /**
     * Testy dla klasy modelu Autorzy
     */
    private Autorzy autor;

    @BeforeEach
    void setUpAuthor() {
        autor = new Autorzy(1, "Adam", "Mickiewicz", 1798);
    }

    @Test
    void testConstructorAndGettersAuthor() {
        assertEquals(1, autor.idProperty().get());
        assertEquals("Adam", autor.imieProperty().get());
        assertEquals("Mickiewicz", autor.nazwiskoProperty().get());
        assertEquals(1798, autor.rokUrProperty().get());
    }

    @Test
    void testSettersAuthor() {
        autor.idProperty().set(2);
        autor.imieProperty().set("Henryk");
        autor.nazwiskoProperty().set("Sienkiewicz");
        autor.rokUrProperty().set(1846);

        assertEquals(2, autor.idProperty().get());
        assertEquals("Henryk", autor.imieProperty().get());
        assertEquals("Sienkiewicz", autor.nazwiskoProperty().get());
        assertEquals(1846, autor.rokUrProperty().get());
    }

    @Test
    void testToStringAuthor() {
        String str = autor.toString();
        assertTrue(str.contains("id="));
        assertTrue(str.contains("Imie='"));
        assertTrue(str.contains("Nazwisko='"));
        assertTrue(str.contains("Rok_Urodzenia="));
    }

    /**
     * Testy dla klasy modelu Autorzy
     */
    private AdminModel admin;

    @BeforeEach
    void setUpAdmin() {
        admin = new AdminModel(
                1,
                "Jan",
                "Kowalski",
                "j.kowalski",
                "tajneHaslo123",
                101,
                true,
                "secretXYZ"
        );
    }

    @Test
    void testConstructorAndGettersAdmin() {
        assertEquals(1, admin.idProperty().get());
        assertEquals("Jan", admin.imieProperty().get());
        assertEquals("Kowalski", admin.nazwiskoProperty().get());
        assertEquals("j.kowalski", admin.nazwaProperty().get());
        assertEquals("tajneHaslo123", admin.hasloProperty().get());
        assertEquals(101, admin.id_placowkiProperty().get());
        assertTrue(admin.mfa_EnabledProperty().get());
        assertEquals("secretXYZ", admin.mfa_SecretProperty().get());
    }

    @Test
    void testSettersAdmin() {
        admin.idProperty().set(2);
        admin.imieProperty().set("Anna");
        admin.nazwiskoProperty().set("Nowak");
        admin.nazwaProperty().set("a.nowak");
        admin.hasloProperty().set("noweHaslo");
        admin.id_placowkiProperty().set(202);
        admin.mfa_EnabledProperty().set(false);
        admin.mfa_SecretProperty().set("newSecret");

        assertEquals(2, admin.idProperty().get());
        assertEquals("Anna", admin.imieProperty().get());
        assertEquals("Nowak", admin.nazwiskoProperty().get());
        assertEquals("a.nowak", admin.nazwaProperty().get());
        assertEquals("noweHaslo", admin.hasloProperty().get());
        assertEquals(202, admin.id_placowkiProperty().get());
        assertFalse(admin.mfa_EnabledProperty().get());
        assertEquals("newSecret", admin.mfa_SecretProperty().get());
    }

    @Test
    void testToStringAdmin() {
        String output = admin.toString();
        assertTrue(output.contains("Admin{id="));
        assertTrue(output.contains("Imie='"));
        assertTrue(output.contains("Nazwa_Uzytkownika='"));
        assertTrue(output.contains("mfa_enable="));
    }

    @Test
    void testSerializationAndDeserializationAdminDTO() {
        AdminDTO original = new AdminDTO();
        original.id = 2;
        original.Imie = "Anna";
        original.Nazwisko = "Nowak";
        original.Nazwa_Uzytkownika = "a.nowak";
        original.Haslo = "haslo456";
        original.id_placowki = 202;
        original.Mfa_Enabled = false;
        original.Mfa_Secret = "xyzSecret";

        String json = gson.toJson(original);
        AdminDTO deserialized = gson.fromJson(json, AdminDTO.class);

        assertEquals(original.id, deserialized.id);
        assertEquals(original.Imie, deserialized.Imie);
        assertEquals(original.Nazwisko, deserialized.Nazwisko);
        assertEquals(original.Nazwa_Uzytkownika, deserialized.Nazwa_Uzytkownika);
        assertEquals(original.Haslo, deserialized.Haslo);
        assertEquals(original.id_placowki, deserialized.id_placowki);
        assertEquals(original.Mfa_Enabled, deserialized.Mfa_Enabled);
        assertEquals(original.Mfa_Secret, deserialized.Mfa_Secret);
    }

    @Test
    void testSerializationAndDeserializationAuthorDTO() {
        AutorzyDTO original = new AutorzyDTO();
        original.id = 10;
        original.Imie = "Juliusz";
        original.Nazwisko = "Słowacki";
        original.Rok_Urodzenia = 1809;

        String json = gson.toJson(original);
        AutorzyDTO deserialized = gson.fromJson(json, AutorzyDTO.class);

        assertEquals(original.id, deserialized.id);
        assertEquals(original.Imie, deserialized.Imie);
        assertEquals(original.Nazwisko, deserialized.Nazwisko);
        assertEquals(original.Rok_Urodzenia, deserialized.Rok_Urodzenia);
    }

    @Test
    void testFieldAssignmentFiltrDTO() {
        FiltrDTO dto = new FiltrDTO();
        dto.id = 101;
        dto.Tytul = "Krzyżacy";
        dto.Gatunek = "Historyczna";
        dto.id_autora = 5;
        dto.Data_Wydania = 1900;
        dto.Dodano = "2023-01-15";
        dto.Rezerwacja = true;
        dto.czy_wypozyczono = false;
        dto.id_placowki = 3;
        dto.Imie = "Henryk";
        dto.Nazwisko = "Sienkiewicz";

        assertEquals(101, dto.id);
        assertEquals("Krzyżacy", dto.Tytul);
        assertEquals("Historyczna", dto.Gatunek);
        assertEquals(5, dto.id_autora);
        assertEquals(1900, dto.Data_Wydania);
        assertEquals("2023-01-15", dto.Dodano);
        assertTrue(dto.Rezerwacja);
        assertFalse(dto.czy_wypozyczono);
        assertEquals(3, dto.id_placowki);
        assertEquals("Henryk", dto.Imie);
        assertEquals("Sienkiewicz", dto.Nazwisko);
    }

    @Test
    void testJsonSerializationFiltrDTO() {
        FiltrDTO dto = new FiltrDTO();
        dto.id = 1;
        dto.Tytul = "Lalka";
        dto.Gatunek = "Powieść";
        dto.id_autora = 12;
        dto.Data_Wydania = 1890;
        dto.Dodano = "2022-12-01";
        dto.Rezerwacja = false;
        dto.czy_wypozyczono = true;
        dto.id_placowki = 2;
        dto.Imie = "Bolesław";
        dto.Nazwisko = "Prus";

        String json = gson.toJson(dto);
        FiltrDTO fromJson = gson.fromJson(json, FiltrDTO.class);

        assertEquals(dto.Tytul, fromJson.Tytul);
        assertEquals(dto.Nazwisko, fromJson.Nazwisko);
        assertTrue(fromJson.czy_wypozyczono);
    }

    @Test
    void testSerializationAndDeserializationKaryDTO() {
        KaryDTO kara = new KaryDTO();
        kara.id = 1;
        kara.Kwota = 123.45;
        kara.Data_Wydania_Kary = "2025-06-16";
        kara.Termin_Zaplaty = "2025-07-16";
        kara.Czy_Zaplacono = "true";
        kara.id_uzytkownika = 99;
        kara.opis = "Testowa kara";

        String json = gson.toJson(kara);

        KaryDTO kara2 = gson.fromJson(json, KaryDTO.class);

        assertEquals(kara.id, kara2.id);
        assertEquals(kara.Kwota, kara2.Kwota, 0.001);
        assertEquals(kara.Data_Wydania_Kary, kara2.Data_Wydania_Kary);
        assertEquals(kara.Termin_Zaplaty, kara2.Termin_Zaplaty);
        assertEquals(kara.Czy_Zaplacono, kara2.Czy_Zaplacono);
        assertEquals(kara.id_uzytkownika, kara2.id_uzytkownika);
        assertEquals(kara.opis, kara2.opis);
    }

    @Test
    void testSerializationAndDeserializationKsiazkaDTO() {
        KsiazkaDTO ksiazka = new KsiazkaDTO();
        ksiazka.id = 1;
        ksiazka.Tytul = "Pan Tadeusz";
        ksiazka.Gatunek = "Epos";
        ksiazka.Dodano = "2025-06-16";
        ksiazka.id_autora = 10;
        ksiazka.id_placowki = 3;
        ksiazka.Rezerwacja = false;
        ksiazka.czy_wypozyczono = true;
        ksiazka.Data_Wydania = 1834;

        String json = gson.toJson(ksiazka);

        KsiazkaDTO ksiazka2 = gson.fromJson(json, KsiazkaDTO.class);

        assertEquals(ksiazka.id, ksiazka2.id);
        assertEquals(ksiazka.Tytul, ksiazka2.Tytul);
        assertEquals(ksiazka.Gatunek, ksiazka2.Gatunek);
        assertEquals(ksiazka.Dodano, ksiazka2.Dodano);
        assertEquals(ksiazka.id_autora, ksiazka2.id_autora);
        assertEquals(ksiazka.id_placowki, ksiazka2.id_placowki);
        assertEquals(ksiazka.Rezerwacja, ksiazka2.Rezerwacja);
        assertEquals(ksiazka.czy_wypozyczono, ksiazka2.czy_wypozyczono);
        assertEquals(ksiazka.Data_Wydania, ksiazka2.Data_Wydania);
    }

    @Test
    void testSerializationAndDeserializationPlacowkaDTO() {
        PlacowkaDTO placowka = new PlacowkaDTO();
        placowka.id = 5;
        placowka.Adres = "ul. Kwiatowa 10, Warszawa";

        String json = gson.toJson(placowka);

        PlacowkaDTO placowka2 = gson.fromJson(json, PlacowkaDTO.class);

        assertEquals(placowka.id, placowka2.id);
        assertEquals(placowka.Adres, placowka2.Adres);
    }

    @Test
    void testSerializationAndDeserializationUzytkownikDTO() {
        UzytkownikDTO user = new UzytkownikDTO();
        user.id = 1;
        user.Imie = "Jan";
        user.Nazwisko = "Kowalski";
        user.Nazwa_Uzytkownika = "janek123";
        user.Haslo = "tajnehaslo";
        user.Email = "jan.kowalski@example.com";
        user.Data_Urodzenia = "1990-01-01";
        user.Zablokowany = false;
        user.Mfa_Enabled = true;
        user.Mfa_Secret = "sekretnyklucz";

        String json = gson.toJson(user);

        UzytkownikDTO user2 = gson.fromJson(json, UzytkownikDTO.class);

        assertEquals(user.id, user2.id);
        assertEquals(user.Imie, user2.Imie);
        assertEquals(user.Nazwisko, user2.Nazwisko);
        assertEquals(user.Nazwa_Uzytkownika, user2.Nazwa_Uzytkownika);
        assertEquals(user.Haslo, user2.Haslo);
        assertEquals(user.Email, user2.Email);
        assertEquals(user.Data_Urodzenia, user2.Data_Urodzenia);
        assertEquals(user.Zablokowany, user2.Zablokowany);
        assertEquals(user.Mfa_Enabled, user2.Mfa_Enabled);
        assertEquals(user.Mfa_Secret, user2.Mfa_Secret);
    }

    @Test
    void testSerializationAndDeserialization() {
        WypozyczeniaDTO wypozyczenie = new WypozyczeniaDTO();
        wypozyczenie.id = 123;
        wypozyczenie.Data_Wypozyczenia = "2025-06-16";
        wypozyczenie.Data_Oddania = "2025-07-01";
        wypozyczenie.Termin_Oddania = "2025-07-10";
        wypozyczenie.id_ksiazki = 456;
        wypozyczenie.id_uzytkownika = 789;

        String json = gson.toJson(wypozyczenie);

        WypozyczeniaDTO wypozyczenie2 = gson.fromJson(json, WypozyczeniaDTO.class);

        assertEquals(wypozyczenie.id, wypozyczenie2.id);
        assertEquals(wypozyczenie.Data_Wypozyczenia, wypozyczenie2.Data_Wypozyczenia);
        assertEquals(wypozyczenie.Data_Oddania, wypozyczenie2.Data_Oddania);
        assertEquals(wypozyczenie.Termin_Oddania, wypozyczenie2.Termin_Oddania);
        assertEquals(wypozyczenie.id_ksiazki, wypozyczenie2.id_ksiazki);
        assertEquals(wypozyczenie.id_uzytkownika, wypozyczenie2.id_uzytkownika);
    }

    @Test
    void testSerializationAndDeserializationTOTPSetup() {
        TOTPSetupResponse response = new TOTPSetupResponse();
        try {
            java.lang.reflect.Field qrCodeField = TOTPSetupResponse.class.getDeclaredField("qrCodeUrl");
            qrCodeField.setAccessible(true);
            qrCodeField.set(response, "https://example.com/qrcode");

            java.lang.reflect.Field secretField = TOTPSetupResponse.class.getDeclaredField("secret");
            secretField.setAccessible(true);
            secretField.set(response, "SECRET123");
        } catch (Exception e) {
            fail("Reflection failed: " + e.getMessage());
        }

        String json = gson.toJson(response);

        TOTPSetupResponse responseFromJson = gson.fromJson(json, TOTPSetupResponse.class);

        assertEquals("https://example.com/qrcode", responseFromJson.getQrCodeUrl());
        assertEquals("SECRET123", responseFromJson.getSecret());
    }

    @Test
    void testSerializationAndDeserializationTOTPRequest() {
        TOTPRequest request = new TOTPRequest("user123", 123456);

        String json = gson.toJson(request);
        System.out.println("Serialized JSON: " + json);

        TOTPRequest requestFromJson = gson.fromJson(json, TOTPRequest.class);

        assertEquals("user123", requestFromJson.getUsername());
        assertEquals(123456, requestFromJson.getTotp());
    }

    /**
     * Testy dla klasy modelu logUser
     */
    private logUser userInstance;

    @BeforeEach
    void setUplogUser() {
        userInstance = new logUser("sub123", "ROLE_USER", "userId456", "false", 123456789L, 987654321L);
        logUser.setUserToken(null);
        logUser.setUserEmail(null);
        logUser.clearUser();
        logUser.clear();
    }

    @Test
    void testConstructorAndGetterslogUser() {
        logUser.setUserIdStr("userId456");
        assertEquals("sub123", userInstance.getSub());
        assertEquals("ROLE_USER", userInstance.getRole());
        assertEquals("userId456", userInstance.getUserId());
        assertEquals("false", userInstance.getIsAdmin());
        assertEquals(123456789L, userInstance.getIat());
        assertEquals(987654321L, userInstance.getExp());
        assertEquals("userId456", logUser.userIdStr);  // static field set in constructor
    }

    @Test
    void testSetUserTokenAndGetUserToken() {
        logUser.setUserToken("token123");
        assertEquals("token123", logUser.getUserToken());
    }

    @Test
    void testSetUserEmailAndGetUserEmail() {
        logUser.setUserEmail("test@example.com");
        assertEquals("test@example.com", logUser.getUserEmail());
    }

    @Test
    void testClearUserClearsStaticFields() {
        logUser.setUserToken("token123");
        logUser.userIdStr = "someId";

        logUser.clearUser();

        assertNull(logUser.getUserToken());
        assertNull(logUser.userIdStr);
    }

    @Test
    void testSetAndGetUserInstance() {
        Uzytkownik mockUser = new Uzytkownik();
        logUser.set(mockUser);
        assertEquals(mockUser, logUser.get());
    }

    @Test
    void testClearInstance() {
        Uzytkownik mockUser = new Uzytkownik();
        logUser.set(mockUser);
        logUser.clear();
        assertNull(logUser.get());
    }

    /**
     * Testy dla klasy modelu LoginRequest
     */
    @Test
    void testConstructorAndGetters() {
        LoginRequest request = new LoginRequest("user1", "pass123", 123456);

        assertEquals("user1", request.getUsername());
        assertEquals("pass123", request.getPassword());
        assertEquals(123456, request.getTotp());
    }

    @Test
    void testConstructorWithNullTotp() {
        LoginRequest request = new LoginRequest("user2", "pass456", null);

        assertEquals("user2", request.getUsername());
        assertEquals("pass456", request.getPassword());
        assertNull(request.getTotp());
    }
}
