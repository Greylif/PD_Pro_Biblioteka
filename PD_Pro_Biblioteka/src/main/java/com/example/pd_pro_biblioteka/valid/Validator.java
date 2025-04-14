package com.example.pd_pro_biblioteka.valid;

import com.example.pd_pro_biblioteka.exceptions.AccountValidationException;
import com.example.pd_pro_biblioteka.model.Uzytkownik;
import com.example.pd_pro_biblioteka.model.Placowka;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Validator {
    private static final int MIN_WIEK = 18;
    private static final int MAX_WIEK = 100;
    private static final Pattern POLSKI_ADRES_PATTERN = Pattern.compile(
            "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+(?: [A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż]+){0,256} \\d+[A-Za-z]?,? (\\d{2}-\\d{3}) [A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+(?: [A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż]+){0,256}$"
    );

    private Validator() {}

    public static boolean walidujUzytkownika(Uzytkownik uzytkownik) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dataUrodzenia = LocalDate.parse(uzytkownik.getData_Urodzenia(), formatter);
            int wiek = Period.between(dataUrodzenia, LocalDate.now()).getYears();
            return wiek >= MIN_WIEK && wiek <= MAX_WIEK;
        } catch (Exception e) {
            throw new AccountValidationException("Blad walidowania uzytkownika: ", e.getMessage());
        }
    }

    public static boolean walidujPlacowke(Placowka placowka) {
        return POLSKI_ADRES_PATTERN.matcher(placowka.getAdres()).matches();
    }
}
