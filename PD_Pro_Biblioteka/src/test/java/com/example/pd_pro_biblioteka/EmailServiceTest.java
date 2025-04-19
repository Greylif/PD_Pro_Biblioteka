package com.example.pd_pro_biblioteka;

import com.example.pd_pro_biblioteka.exceptions.JsonFileException;
import com.example.pd_pro_biblioteka.exceptions.SupabaseConnectionException;
import com.example.pd_pro_biblioteka.service.EmailService;
import com.example.pd_pro_biblioteka.service.SupabaseClient;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.shadow.com.univocity.parsers.common.NoopProcessorErrorHandler.instance;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private EmailService emailService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.defaultHeader(any(), any())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);

        emailService = new EmailService(mailSender, webClientBuilder);
    }

    @Nested
    @DisplayName("Testy poprawnego dzialania email")
    class TestEmailCorrect {

        @Test
        @DisplayName("Testowanie poprawnego wysylania emailu")
        void testScheduledEmail() throws MessagingException {

            String wypozyczeniaResponse = """
                    [{"id":1,"id_uzytkownika":101,"id_ksiazki":201,"Termin_Oddania":"%s"}]
                    """.formatted(LocalDate.now().plusDays(2));

            String uzytkownicyResponse = """
                    [{"id":101,"Email":"user@example.com"}]
                    """;

            String ksiazkiResponse = """
                    [{"id":201,"Tytul":"Hobbit"}]
                    """;

            when(webClient.get()).thenReturn(requestHeadersUriSpec);
            when(requestHeadersUriSpec.uri(any(Function.class))).thenAnswer(invocation -> {
                Function<UriBuilder, URI> uriFn = invocation.getArgument(0);
                uriFn.apply(UriComponentsBuilder.fromUriString("http://localhost"));
                return requestHeadersSpec;
            });

            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class))
                    .thenReturn(Mono.just(wypozyczeniaResponse))
                    .thenReturn(Mono.just(uzytkownicyResponse))
                    .thenReturn(Mono.just(ksiazkiResponse));

            MimeMessage dummyMessage = mock(MimeMessage.class);
            when(mailSender.createMimeMessage()).thenReturn(dummyMessage);

            emailService.scheduledEmail();

            verify(mailSender, times(1)).send(any(MimeMessage.class));
        }

        @Test
        @DisplayName("Testowanie poprawnego nie wysylania Przed odpowiednia data")
        void testScheduledEmailnotsend() throws MessagingException {

            String wypozyczeniaResponse = """
                    [{"id":1,"id_uzytkownika":101,"id_ksiazki":201,"Termin_Oddania":"%s"}]
                    """.formatted(LocalDate.now().plusDays(5));

            String uzytkownicyResponse = """
                    [{"id":101,"Email":"user@example.com"}]
                    """;

            String ksiazkiResponse = """
                    [{"id":201,"Tytul":"Hobbit"}]
                    """;

            when(webClient.get()).thenReturn(requestHeadersUriSpec);
            when(requestHeadersUriSpec.uri(any(Function.class))).thenAnswer(invocation -> {
                Function<UriBuilder, URI> uriFn = invocation.getArgument(0);
                uriFn.apply(UriComponentsBuilder.fromUriString("http://localhost"));
                return requestHeadersSpec;
            });

            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class))
                    .thenReturn(Mono.just(wypozyczeniaResponse))
                    .thenReturn(Mono.just(uzytkownicyResponse))
                    .thenReturn(Mono.just(ksiazkiResponse));

            MimeMessage dummyMessage = mock(MimeMessage.class);
            when(mailSender.createMimeMessage()).thenReturn(dummyMessage);

            emailService.scheduledEmail();

            verify(mailSender, times(0)).send(any(MimeMessage.class));
        }


        @Test
        @DisplayName("Testowanie poprawnego nie wysylania emailu gdy ksiazka jest oddana")
        void testScheduledEmailReturned() throws MessagingException {

            String wypozyczeniaResponse = """
                    [{"id":1,"id_uzytkownika":101,"id_ksiazki":201,"Termin_Oddania":"%s","Data_Oddania":"2025-04-01"}]
                    """.formatted(LocalDate.now().plusDays(2));

            String uzytkownicyResponse = """
                    [{"id":101,"Email":"user@example.com"}]
                    """;

            String ksiazkiResponse = """
                    [{"id":201,"Tytul":"Hobbit"}]
                    """;

            when(webClient.get()).thenReturn(requestHeadersUriSpec);
            when(requestHeadersUriSpec.uri(any(Function.class))).thenAnswer(invocation -> {
                Function<UriBuilder, URI> uriFn = invocation.getArgument(0);
                uriFn.apply(UriComponentsBuilder.fromUriString("http://localhost"));
                return requestHeadersSpec;
            });

            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class))
                    .thenReturn(Mono.just(wypozyczeniaResponse))
                    .thenReturn(Mono.just(uzytkownicyResponse))
                    .thenReturn(Mono.just(ksiazkiResponse));

            emailService.scheduledEmail();

            verify(mailSender, times(0)).send(any(MimeMessage.class));
        }
    }

    @Nested
    @DisplayName("Testy throw dla wysylania emaili")
    class TestEmailError {

        @Test
        @DisplayName("Throws SupabaseConnectionException when WebClient fails during wypozyczenia fetch")
        void testWypozyczeniaFetchThrowsSupabaseConnectionException() {
            when(webClient.get()).thenReturn(requestHeadersUriSpec);
            when(requestHeadersUriSpec.uri(any(Function.class))).thenThrow(new RuntimeException("WebClient failure"));

            SupabaseConnectionException thrown = assertThrows(SupabaseConnectionException.class, () -> {
                emailService.scheduledEmail();
            });

            assertTrue(thrown.getMessage().contains("Failed to connect to Supabase")
                    || thrown.getMessage().contains("Failed to fetch wypozyczenia data"));
        }



        @Test
        @DisplayName("Throws SupabaseConnectionException when WebClient fails during uzytkownicy fetch")
        void testUzytkownicyFetchThrowsSupabaseConnectionException() {
            String wypozyczeniaResponse = """
                    [{"id":1,"id_uzytkownika":101,"id_ksiazki":201,"Termin_Oddania":"%s"}]
                    """.formatted(LocalDate.now().plusDays(2));

            when(webClient.get()).thenReturn(requestHeadersUriSpec);
            when(requestHeadersUriSpec.uri(any(Function.class))).thenAnswer(invocation -> {
                Function<UriBuilder, URI> uriFn = invocation.getArgument(0);
                uriFn.apply(UriComponentsBuilder.fromUriString("http://localhost"));
                return requestHeadersSpec;
            });

            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class))
                    .thenReturn(Mono.just(wypozyczeniaResponse))
                    .thenThrow(new RuntimeException("WebClient user fetch failed"));

            assertThrows(SupabaseConnectionException.class, () -> {
                emailService.scheduledEmail();
            });
        }

        @Test
        @DisplayName("Throws SupabaseConnectionException when WebClient fails during ksiazki fetch")
        void testKsiazkiFetchThrowsSupabaseConnectionException() {
            String wypozyczeniaResponse = """
        [{"id":1,"id_uzytkownika":101,"id_ksiazki":201,"Termin_Oddania":"%s"}]
        """.formatted(LocalDate.now().plusDays(2));

            String uzytkownicyResponse = """
        [{"id":101,"Email":"user@example.com"}]
        """;

            when(webClient.get()).thenReturn(requestHeadersUriSpec);
            when(requestHeadersUriSpec.uri(any(Function.class))).thenAnswer(invocation -> {
                Function<UriBuilder, URI> uriFn = invocation.getArgument(0);
                uriFn.apply(UriComponentsBuilder.fromUriString("http://localhost"));
                return requestHeadersSpec;
            });

            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class))
                    .thenReturn(Mono.just(wypozyczeniaResponse))
                    .thenReturn(Mono.just(uzytkownicyResponse))
                    .thenThrow(new RuntimeException("WebClient ksiazki fetch failed"));

            assertThrows(SupabaseConnectionException.class, () -> {
                emailService.scheduledEmail();
            });
        }

        @Test
        @DisplayName("Throws JsonFileException when ksiazki JSON is invalid")
        void testInvalidKsiazkiJsonThrowsJsonFileException() {
            String wypozyczeniaResponse = """
        [{"id":1,"id_uzytkownika":101,"id_ksiazki":201,"Termin_Oddania":"%s"}]
        """.formatted(LocalDate.now().plusDays(2));

            String uzytkownicyResponse = """
        [{"id":101,"Email":"user@example.com"}]
        """;

            String invalidKsiazkiJson = "INVALID_JSON";

            when(webClient.get()).thenReturn(requestHeadersUriSpec);
            when(requestHeadersUriSpec.uri(any(Function.class))).thenAnswer(invocation -> {
                Function<UriBuilder, URI> uriFn = invocation.getArgument(0);
                uriFn.apply(UriComponentsBuilder.fromUriString("http://localhost"));
                return requestHeadersSpec;
            });

            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class))
                    .thenReturn(Mono.just(wypozyczeniaResponse))
                    .thenReturn(Mono.just(uzytkownicyResponse))
                    .thenReturn(Mono.just(invalidKsiazkiJson));

            assertThrows(JsonFileException.class, () -> {
                emailService.scheduledEmail();
            });
        }

        @Test
        @DisplayName("Throws JsonFileException when wypozyczenia JSON is invalid in merge step")
        void testInvalidWypozyczeniaJsonThrowsJsonFileException() {
            String invalidWypozyczeniaJson = "INVALID_JSON";

            when(webClient.get()).thenReturn(requestHeadersUriSpec);
            when(requestHeadersUriSpec.uri(any(Function.class))).thenAnswer(invocation -> {
                Function<UriBuilder, URI> uriFn = invocation.getArgument(0);
                uriFn.apply(UriComponentsBuilder.fromUriString("http://localhost"));
                return requestHeadersSpec;
            });

            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class))
                    .thenReturn(Mono.just(invalidWypozyczeniaJson));

            assertThrows(JsonFileException.class, () -> {
                emailService.scheduledEmail();
            });
        }

    }

    @Test
    @DisplayName("Nieudane polaczenie do bazy danych")
    void EmailServiceThrowsSupabaseConnectionException(){
        Mockito.when(webClientBuilder.baseUrl(Mockito.anyString()))
                .thenThrow(new RuntimeException("Failed to build WebClient"));

        assertThrows(SupabaseConnectionException.class, () -> {
            new EmailService(mailSender,webClientBuilder);
        });
    }
}
