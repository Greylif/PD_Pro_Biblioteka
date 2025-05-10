package com.example.pd_pro_biblioteka.service;

import com.example.pd_pro_biblioteka.exceptions.JsonFileException;
import com.example.pd_pro_biblioteka.exceptions.SupabaseConnectionException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final WebClient webClient;
    private static final String TERMIN_ODDANIA = "Termin_Oddania";
    private static final String EMAIL = "Email";
    private static final String TYTUL = "Tytul";
    private static final String DATA_ODDANIA = "Data_Oddania";
    public EmailService(JavaMailSender mailSender, WebClient.Builder webClientBuilder) {
        this.mailSender = mailSender;
        try {
            this.webClient = webClientBuilder
                    .baseUrl("https://pcrbtauvyjxsspmfmwia.supabase.co/rest/v1")
                    .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBjcmJ0YXV2eWp4c3NwbWZtd2lhIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc0MjM5MTczNCwiZXhwIjoyMDU3OTY3NzM0fQ.L5av7QMn8OqyF8WhaPo6IJOApwQcqJPCzqLlzJHz6zw")
                    .defaultHeader("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBjcmJ0YXV2eWp4c3NwbWZtd2lhIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDIzOTE3MzQsImV4cCI6MjA1Nzk2NzczNH0.xdr4z5_udXpL4sbJpccFQrOPj_7_6w1bIs-FMGcdn1U")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .build();
        }
        catch (Exception e)
        {
            throw new SupabaseConnectionException("Failed to connect to Supabase: ", e);
        }
    }

    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);

        mailSender.send(message);
    }

    @Scheduled(cron = "0 00 17 * * ?")
    public void scheduledEmail() throws MessagingException {
        System.out.println("\nEmail scheduled\n");
        JSONArray Arrayout = fetchWypozyczeniaZEmail();
        System.out.println(Arrayout + "\n");
        for(int i = 0; i < Arrayout.length(); i++) {
            JSONObject obj = Arrayout.getJSONObject(i);
            LocalDate termin = LocalDate.parse(obj.getString(TERMIN_ODDANIA));
            Object data_oddania = obj.opt(DATA_ODDANIA);
            if((termin.minusDays(3)).isBefore(LocalDate.now()) && (data_oddania == null)) {
                sendEmail(obj.getString(EMAIL), "Przypomnienie o oddaniu ksiazki", "Termin oddania ksiazki o tytule: " + obj.getString(TYTUL) + " mija: "+ obj.getString(TERMIN_ODDANIA));
                System.out.println("\nSent: \n" + i);
            }
        }
    }

    private JSONArray fetchWypozyczeniaZEmail() {
        String wypozyczeniaData;

        try {
            wypozyczeniaData = fetchWypozyczeniaData();

            Map<Integer, String> uzytkownicyZEmail = fetchUzytkownicyEmail(wypozyczeniaData);
            Map<Integer, String> ksiazkiZTytulami = fetchKsiazkiTytuly(wypozyczeniaData);

            return polaczWypozyczeniaZEmail(wypozyczeniaData, uzytkownicyZEmail, ksiazkiZTytulami);

        } catch (SupabaseConnectionException e) {
            throw new SupabaseConnectionException("Failed to connect to Supabase: ", e);
        }
    }

    private String fetchWypozyczeniaData() {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/Wypozyczenia")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new SupabaseConnectionException("Failed to fetch wypozyczenia data: ", e);
        }
    }



    private Map<Integer, String> fetchUzytkownicyEmail(String wypozyczeniaData) {
        Set<Integer> uzytkownikIds = new HashSet<>();
        Map<Integer, String> mapIdEmail = new HashMap<>();

        try {
            JSONArray jsonArray = new JSONArray(wypozyczeniaData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject wypozyczenie = jsonArray.getJSONObject(i);
                uzytkownikIds.add(wypozyczenie.getInt("id_uzytkownika"));
            }

            String uzytkownicyData = fetchUzytkownicyData(uzytkownikIds);

            JSONArray uzytkownicyJson = new JSONArray(uzytkownicyData);
            for (int i = 0; i < uzytkownicyJson.length(); i++) {
                JSONObject user = uzytkownicyJson.getJSONObject(i);
                mapIdEmail.put(user.getInt("id"), user.getString(EMAIL));
            }
            return mapIdEmail;

        } catch (SupabaseConnectionException e) {
            throw e;
        } catch (Exception e) {
            throw new JsonFileException("Failed to process użytkownicy JSON", e);
        }
    }

    private String fetchUzytkownicyData(Set<Integer> uzytkownikIds) {
        String idsFilter = "id=in.(" + uzytkownikIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) + ")";
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/Uzytkownik")
                            .query(idsFilter)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new SupabaseConnectionException("Failed to fetch uzytkownicy data: ", e);
        }
    }



    private Map<Integer, String> fetchKsiazkiTytuly(String wypozyczeniaData) {
        Set<Integer> ksiazkaIds = new HashSet<>();
        Map<Integer, String> mapIdTytul = new HashMap<>();

        try {
            JSONArray jsonArray = new JSONArray(wypozyczeniaData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject wypozyczenie = jsonArray.getJSONObject(i);
                ksiazkaIds.add(wypozyczenie.getInt("id_ksiazki"));
            }

            String ksiazkiData = fetchKsiazkiData(ksiazkaIds);

            JSONArray ksiazkiJson = new JSONArray(ksiazkiData);
            for (int i = 0; i < ksiazkiJson.length(); i++) {
                JSONObject ksiazka = ksiazkiJson.getJSONObject(i);
                mapIdTytul.put(ksiazka.getInt("id"), ksiazka.getString(TYTUL));
            }
            return mapIdTytul;

        } catch (SupabaseConnectionException e) {
            throw e;
        } catch (Exception e) {
            throw new JsonFileException("Failed to process ksiazki JSON", e);
        }
    }

    private String fetchKsiazkiData(Set<Integer> ksiazkaIds) {
        String idsFilter = "id=in.(" + ksiazkaIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) + ")";
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/Ksiazka")
                            .query(idsFilter)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new SupabaseConnectionException("Failed to fetch ksiazki data: ", e);
        }
    }




    private JSONArray polaczWypozyczeniaZEmail(String wypozyczeniaData, Map<Integer, String> uzytkownicyEmails, Map<Integer, String> ksiazkiTytuly) {
        JSONArray resultArray = new JSONArray();
            JSONArray jsonArray = new JSONArray(wypozyczeniaData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject wypozyczenie = jsonArray.getJSONObject(i);

                int userId = wypozyczenie.getInt("id_uzytkownika");
                int ksiazkaId = wypozyczenie.getInt("id_ksiazki");

                String email = uzytkownicyEmails.get(userId);
                String tytul = ksiazkiTytuly.get(ksiazkaId);

                JSONObject merged = new JSONObject();
                merged.put(EMAIL, email);
                merged.put(TYTUL, tytul);
                merged.put(TERMIN_ODDANIA, wypozyczenie.getString(TERMIN_ODDANIA));
                merged.put(DATA_ODDANIA, wypozyczenie.opt(DATA_ODDANIA));
                resultArray.put(merged);
            }
        return resultArray;
    }

}
