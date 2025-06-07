package com.example.pdprobiblioteka.valid;

import com.example.pdprobiblioteka.exceptions.AccountValidationException;
import com.example.pdprobiblioteka.model.Placowka;
import com.example.pdprobiblioteka.model.Uzytkownik;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Klasa pomocnicza do walidacji danych użytkowników i placówek.
 */
public class Validator {

  private static final int MIN_WIEK = 18;
  private static final int MAX_WIEK = 100;
  private static final Pattern POLSKI_ADRES_PATTERN = Pattern.compile(
      "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+(?: [A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż0-9.]+){0,256} "
          + "\\d+[A-Za-z]?,? (\\d{2}-\\d{3}) "
          + "[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+(?: [A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż]+){0,256}$"
  );

  private Validator() {
  }


  /**
   * Sprawdza czy użytkownik spełnia wymagania wiekowe.
   *
   * @param uzytkownik obiekt użytkownika
   * @return true jeśli wiek użytkownika jest pomiędzy 18 a 100 lat
   * @throws AccountValidationException jeśli wystąpi błąd parsowania daty
   */
  public static boolean walidujUzytkownika(Uzytkownik uzytkownik) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate dataUrodzenia = LocalDate.parse(uzytkownik.getDataUrodzenia(), formatter);
      int wiek = Period.between(dataUrodzenia, LocalDate.now()).getYears();
      return wiek >= MIN_WIEK && wiek <= MAX_WIEK;
    } catch (Exception e) {
      throw new AccountValidationException("Blad walidowania uzytkownika: ", e.getMessage());
    }
  }

  /**
   * Sprawdza czy adres placówki spełnia wymagany polski format.
   *
   * @param placowka obiekt placówki
   * @return true jeśli adres jest zgodny ze wzorcem
   */
  public static boolean walidujPlacowke(Placowka placowka) {
    return POLSKI_ADRES_PATTERN.matcher(placowka.getAdres()).matches();
  }
}
