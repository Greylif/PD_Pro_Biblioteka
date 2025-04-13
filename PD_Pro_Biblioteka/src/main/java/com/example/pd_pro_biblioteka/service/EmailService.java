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
            LocalDate termin = LocalDate.parse(obj.getString("Termin_Oddania"));
            Object data_oddania = obj.opt("Data_Oddania");
            if((termin.minusDays(3)).isBefore(LocalDate.now()) && (data_oddania == null || JSONObject.NULL.equals(data_oddania))) {
                //sendEmail(obj.getString("Email"), "Przypomnienie o oddaniu ksiazki", "Termin oddania ksiazki o: " + obj.getString("Tytul") + " obj.getString("Termin_Oddania")");
                System.out.println("\nSent: \n" + i);
            }
        }
    }

    private JSONArray fetchWypozyczeniaZEmail() {
        try {
            String wypozyczeniaData = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/" + "Wypozyczenia")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            Map<Integer, String> uzytkownicyZEmail = fetchUzytkownicyEmail(wypozyczeniaData);
            Map<Integer, String> ksiazkiZTytulami = fetchKsiazkiTytuly(wypozyczeniaData);

            return polaczWypozyczeniaZEmail(wypozyczeniaData, uzytkownicyZEmail, ksiazkiZTytulami);

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

            String idsFilter = "id=in.(" + uzytkownikIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")) + ")";

            String uzytkownicyData;

            try {
                uzytkownicyData = webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/" + "Uzytkownik")
                                .query(idsFilter)
                                .build())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
            } catch (Exception e) {
                throw new SupabaseConnectionException("Failed to fetch uzytkownicy data: ", e);
            }


            JSONArray uzytkownicyJson = new JSONArray(uzytkownicyData);
            for (int i = 0; i < uzytkownicyJson.length(); i++) {
                JSONObject user = uzytkownicyJson.getJSONObject(i);
                mapIdEmail.put(user.getInt("id"), user.getString("Email"));
            }
            return mapIdEmail;

        } catch (Exception e) {
            throw new JsonFileException("Failed to process użytkownicy JSON", e);
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

            String idsFilter = "id=in.(" + ksiazkaIds.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(",")) + ")";

            String ksiazkiData;
            try {
                ksiazkiData = webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/" + "Ksiazka")
                                .query(idsFilter)
                                .build())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
            } catch (Exception e) {
                throw new SupabaseConnectionException("Failed to fetch ksiazki data: ", e);
            }

            JSONArray ksiazkiJson = new JSONArray(ksiazkiData);
            for (int i = 0; i < ksiazkiJson.length(); i++) {
                JSONObject ksiazka = ksiazkiJson.getJSONObject(i);
                mapIdTytul.put(ksiazka.getInt("id"), ksiazka.getString("Tytul"));
            }
            return mapIdTytul;

        } catch (Exception e) {
            throw new JsonFileException("Failed to process ksiazki JSON", e);
        }
    }


    private JSONArray polaczWypozyczeniaZEmail(String wypozyczeniaData, Map<Integer, String> uzytkownicyEmails, Map<Integer, String> ksiazkiTytuly) {
        JSONArray resultArray = new JSONArray();
        try {
            JSONArray jsonArray = new JSONArray(wypozyczeniaData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject wypozyczenie = jsonArray.getJSONObject(i);

                int userId = wypozyczenie.getInt("id_uzytkownika");
                int ksiazkaId = wypozyczenie.getInt("id_ksiazki");

                String email = uzytkownicyEmails.get(userId);
                String tytul = ksiazkiTytuly.get(ksiazkaId);

                JSONObject merged = new JSONObject();
                merged.put("Email", email);
                merged.put("Tytul", tytul);
                merged.put("Termin_Oddania", wypozyczenie.getString("Termin_Oddania"));
                merged.put("Data_Oddania", wypozyczenie.opt("Data_Oddania"));
                resultArray.put(merged);
            }
        } catch (Exception e) {
            throw new JsonFileException("Failed to combine wypozyczenia with email", e);
        }
        return resultArray;
    }

}
