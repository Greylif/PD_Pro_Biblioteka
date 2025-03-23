package com.example.pd_pro_biblioteka.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SupabaseClient {
    private final WebClient webClient;

    public SupabaseClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://pcrbtauvyjxsspmfmwia.supabase.co/rest/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBjcmJ0YXV2eWp4c3NwbWZtd2lhIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc0MjM5MTczNCwiZXhwIjoyMDU3OTY3NzM0fQ.L5av7QMn8OqyF8WhaPo6IJOApwQcqJPCzqLlzJHz6zw")
                .defaultHeader("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InBjcmJ0YXV2eWp4c3NwbWZtd2lhIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDIzOTE3MzQsImV4cCI6MjA1Nzk2NzczNH0.xdr4z5_udXpL4sbJpccFQrOPj_7_6w1bIs-FMGcdn1U")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String getPlacowki() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/Placowka")
                        .queryParam("select", "id, Adres")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
