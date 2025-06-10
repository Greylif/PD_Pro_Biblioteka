package com.example.pdprobiblioteka.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.example.pdprobiblioteka.exceptions.AccountValidationException;
import com.example.pdprobiblioteka.model.Placowka;
import com.example.pdprobiblioteka.model.Uzytkownik;
import com.example.pdprobiblioteka.valid.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Testy walidatora")
class ValidatorTest {

  @Nested
  @DisplayName("Testy walidacji poprawnej daty")
  class DataValidationTest {

    @Test
    @DisplayName("Powinien zadzialac poprawnie dla poprawnej daty")
    void testDataTrue() {
      Uzytkownik user = new Uzytkownik(5, "Jan", "Kowalski", "2000-12-12", "Username", "Pass",
          "s092677@student.tu.kielce.pl", Boolean.FALSE, Boolean.FALSE, "secret", "USER");
      assertDoesNotThrow(() -> Validator.walidujUzytkownika(user));
    }

    @Test
    @DisplayName("Powinien zakonczyc sie bledem dla niepoprawnej daty")
    void testDataFalse() {
      Uzytkownik user = new Uzytkownik(5, "Jan", "Kowalski", "NOT_A_DATE", "Username", "Pass",
          "s092677@student.tu.kielce.pl", Boolean.FALSE, Boolean.FALSE, "secret", "USER");
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
    void testValidWiek(String dataurodzenia) {
      Uzytkownik uzytkownik = new Uzytkownik(1, "Jan", "Kowalski", dataurodzenia, "username",
          "password", "s092677@student.tu.kielce.pl", Boolean.FALSE, Boolean.FALSE, "secret",
          "USER");
      assertThat(Validator.walidujUzytkownika(uzytkownik))
          .as("Wiek %d powinien być poprawny", dataurodzenia)
          .isTrue();
    }

    @ParameterizedTest
    @DisplayName("Powinien zwrócić FALSE dla niepoprawnego wieku")
    @MethodSource("niepoprawnedaty")
    void testInvalidWiek(String dataurodzenia) {
      Uzytkownik uzytkownik = new Uzytkownik(1, "Jan", "Kowalski", dataurodzenia, "username",
          "password", "s092677@student.tu.kielce.pl", Boolean.FALSE, Boolean.FALSE, "secret",
          "USER");
      assertThat(Validator.walidujUzytkownika(uzytkownik))
          .as("Wiek %d powinien być niepoprawny", dataurodzenia)
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
