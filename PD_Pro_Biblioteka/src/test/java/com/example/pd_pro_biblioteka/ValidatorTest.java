package com.example.pd_pro_biblioteka;

import com.example.pd_pro_biblioteka.exceptions.AccountValidationException;
import com.example.pd_pro_biblioteka.model.Uzytkownik;
import com.example.pd_pro_biblioteka.model.Placowka;
import com.example.pd_pro_biblioteka.valid.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Testy walidatora")
class ValidatorTest {

    @Nested
    @DisplayName("Testy walidacji poprawnej daty")
    class DataValidationTest {

        @Test
        @DisplayName("Powinien zadzialac poprawnie dla poprawnej daty")
        void testDataTrue()
        {
            Uzytkownik user = new Uzytkownik(5,"Jan","Kowalski","2000-12-12","Username","Pass","s092677@student.tu.kielce.pl", Boolean.FALSE);
            assertDoesNotThrow(() -> Validator.walidujUzytkownika(user));
        }

        @Test
        @DisplayName("Powinien zakonczyc sie bledem dla niepoprawnej daty")
        void testDataFalse()
        {
            Uzytkownik user = new Uzytkownik(5,"Jan","Kowalski","NOT_A_DATE","Username","Pass","s092677@student.tu.kielce.pl", Boolean.FALSE);
            assertThatThrownBy(() -> Validator.walidujUzytkownika(user))
                    .as("Niepoprawne tworzenie uzytkownika")
                    .isInstanceOf(AccountValidationException.class);
        }
    }


    @Nested
    @DisplayName("Testy walidacji wieku użytkownika")
    class WiekValidationTests {

        static List<String> poprawnedaty() {
            List<String> result = new ArrayList<>();
            List<Integer> wiekr = Arrays.asList(18, 19, 22, 55, 100);
            for (int wiek : wiekr) {
                result.add(LocalDate.now().minusYears(wiek).toString());
            }
            return result;
        }

        static List<String> niepoprawnedaty() {
            List<String> result = new ArrayList<>();
            List<Integer> wiekw = Arrays.asList(17, -5, 0, 101, 500);
            for (int wiek : wiekw) {
                result.add(LocalDate.now().minusYears(wiek).toString());
            }
            return result;
        }

        @ParameterizedTest
        @DisplayName("Powinien zwrócić TRUE dla poprawnego wieku")
        @MethodSource("poprawnedaty")
        void testValidWiek(String data_urodzenia) {
            Uzytkownik uzytkownik = new Uzytkownik(1, "Jan", "Kowalski", data_urodzenia, "username", "password", "s092677@student.tu.kielce.pl", Boolean.FALSE);
            assertThat(Validator.walidujUzytkownika(uzytkownik))
                    .as("Wiek %d powinien być poprawny", data_urodzenia)
                    .isTrue();
        }

        @ParameterizedTest
        @DisplayName("Powinien zwrócić FALSE dla niepoprawnego wieku")
        @MethodSource("niepoprawnedaty")
        void testInvalidWiek(String data_urodzenia) {
            Uzytkownik uzytkownik = new Uzytkownik(1, "Jan", "Kowalski", data_urodzenia, "username", "password", "s092677@student.tu.kielce.pl", Boolean.FALSE);
            assertThat(Validator.walidujUzytkownika(uzytkownik))
                    .as("Wiek %d powinien być niepoprawny", data_urodzenia)
                    .isFalse();
        }
    }

    @Nested
    @DisplayName("Testy walidacji adresu placówki")
    class AdresValidationTests {

        @ParameterizedTest
        @DisplayName("Powinien zwrócić TRUE dla poprawnych adresów")
        @ValueSource(strings = {
                "Marszałkowska 10, 00-001 Warszawa",
                "Aleje Jerozolimskie 25, 30-567 Kraków",
                "Piotrkowska 3, 90-001 Łódź"
        })
        void testValidAdres(String adres) {
            Placowka placowka = new Placowka(1, adres);
            assertThat(Validator.walidujPlacowke(placowka))
                    .as("Adres %s powinien być poprawny", adres)
                    .isTrue();
        }

        @ParameterizedTest
        @DisplayName("Powinien zwrócić FALSE dla niepoprawnych adresów")
        @ValueSource(strings = {
                "Marszałkowska 10 Warszawa",
                "00-001 Warszawa, Marszałkowska 10",
                "Plac Defilad, Warszawa"
        })
        void testInvalidAdres(String adres) {
            Placowka placowka = new Placowka(1, adres);
            assertThat(Validator.walidujPlacowke(placowka))
                    .as("Adres %s powinien być niepoprawny", adres)
                    .isFalse();
        }
    }
}
