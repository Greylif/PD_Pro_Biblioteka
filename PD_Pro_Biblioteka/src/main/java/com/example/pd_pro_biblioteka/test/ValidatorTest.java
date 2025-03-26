package com.example.pd_pro_biblioteka.test;

import com.example.pd_pro_biblioteka.model.Uzytkownik;
import com.example.pd_pro_biblioteka.model.Placowka;
import com.example.pd_pro_biblioteka.valid.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Testy walidatora")
class ValidatorTest {

    @Nested
    @DisplayName("Testy walidacji wieku użytkownika")
    class WiekValidationTests {

        @ParameterizedTest
        @DisplayName("Powinien zwrócić TRUE dla poprawnego wieku")
        @ValueSource(ints = {18, 25, 99, 100})
        void testValidWiek(int wiek) {
            Uzytkownik uzytkownik = new Uzytkownik(1, "Jan", "Kowalski", wiek);
            assertThat(Validator.walidujUzytkownika(uzytkownik))
                    .as("Wiek %d powinien być poprawny", wiek)
                    .isTrue();
        }

        @ParameterizedTest
        @DisplayName("Powinien zwrócić FALSE dla niepoprawnego wieku")
        @ValueSource(ints = {17, 101, -5})
        void testInvalidWiek(int wiek) {
            Uzytkownik uzytkownik = new Uzytkownik(1, "Jan", "Kowalski", wiek);
            assertThat(Validator.walidujUzytkownika(uzytkownik))
                    .as("Wiek %d powinien być niepoprawny", wiek)
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
