package com.example.pd_pro_biblioteka.valid;

import com.example.pd_pro_biblioteka.model.Uzytkownik;
import com.example.pd_pro_biblioteka.model.Placowka;

import java.util.regex.Pattern;

public class Validator {
    private static final int MIN_WIEK = 18;
    private static final int MAX_WIEK = 100;
    private static final Pattern POLSKI_ADRES_PATTERN = Pattern.compile(
            "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+(?: [A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż]+)* \\d+[A-Za-z]?,? (\\d{2}-\\d{3}) [A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+(?: [A-ZĄĆĘŁŃÓŚŹŻa-ząćęłńóśźż]+)*$"
    );

    private Validator() {}

    public static boolean walidujUzytkownika(Uzytkownik uzytkownik) {
        return uzytkownik.getWiek() >= MIN_WIEK && uzytkownik.getWiek() <= MAX_WIEK;
    }

    public static boolean walidujPlacowke(Placowka placowka) {
        return POLSKI_ADRES_PATTERN.matcher(placowka.getAdres()).matches();
    }
}
