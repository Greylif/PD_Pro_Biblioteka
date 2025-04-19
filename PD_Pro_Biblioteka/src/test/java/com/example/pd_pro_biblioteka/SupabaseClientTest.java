package com.example.pd_pro_biblioteka;

import com.example.pd_pro_biblioteka.exceptions.InstanceNotFoundException;
import com.example.pd_pro_biblioteka.exceptions.JsonFileException;
import com.example.pd_pro_biblioteka.exceptions.SupabaseConnectionException;
import com.example.pd_pro_biblioteka.service.SupabaseClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.*;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class SupabaseClientTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @SuppressWarnings("rawtypes")
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private SupabaseClient supabaseClient;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.defaultHeader(anyString(), anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        supabaseClient = new SupabaseClient(webClientBuilder);
    }


    @Nested
    @DisplayName("Testy GET")
    class GetTest {

        @Nested
        @DisplayName("Poprawnie wykonujace sie testy GET")
        class GetCorrectTest {

            @Test
            @DisplayName("Test Get Admini")
            void testGetAdmini() {
                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("[{\"id\":1,\"id_placowki\":\"2\",\"Imie\":\"Jan\",\"Nazwisko\":\"Kowalski\",\"Haslo\":\"pass\",\"Nazwa_Uzytkownika\":\"username\" }]"));

                String result = supabaseClient.getAdmini();

                assertAll(
                        () -> assertTrue(result.contains("1")),
                        () -> assertTrue(result.contains("2")),
                        () -> assertTrue(result.contains("Jan")),
                        () -> assertTrue(result.contains("Kowalski")),
                        () -> assertTrue(result.contains("pass")),
                        () -> assertTrue(result.contains("username"))
                );
            }

            @Test
            @DisplayName("Test Get Autorzy")
            void testGetAutorzy() {
                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("[{\"id\":1, \"Imie\":\"John\",\"Nazwisko\":\"Tolkien\",\"Rok_Urodzenia\":\"1892\"}]"));

                String result = supabaseClient.getAutorzy();

                assertAll(
                        () -> assertTrue(result.contains("1")),
                        () -> assertTrue(result.contains("John")),
                        () -> assertTrue(result.contains("Tolkien")),
                        () -> assertTrue(result.contains("1892"))
                );
            }

            @Test
            @DisplayName("Test Get Kary")
            void testGetKary() {
                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("[{\"id\":1, \"Kwota\":25.5, \"Termin_Zaplaty\":2025-04-16, \"Czy_Zaplacono\":false, \"Data_Wydania_Kary\":2025-03-20, \"id_uzytkownika\":101}]"));

                String result = supabaseClient.getKary();

                assertAll(
                        () -> assertTrue(result.contains("1")),
                        () -> assertTrue(result.contains("25.5")),
                        () -> assertTrue(result.contains("2025-04-16")),
                        () -> assertTrue(result.contains("false")),
                        () -> assertTrue(result.contains("2025-03-20")),
                        () -> assertTrue(result.contains("101"))

                );
            }

            @Test
            @DisplayName("Test Get Ksiazki")
            void testGetKsiazki() {
                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("[{\"id\":1, \"Tytul\":\"Silmarillion\", \"Gatunek\":Fantasy, \"Data_Wydania\":1977-09-15, \"Dodano\":2025-04-16, \"id_autora\":101, \"id_placowki\":201}]"));

                String result = supabaseClient.getKsiazki();

                assertAll(
                        () -> assertTrue(result.contains("1")),
                        () -> assertTrue(result.contains("Silmarillion")),
                        () -> assertTrue(result.contains("Fantasy")),
                        () -> assertTrue(result.contains("1977-09-15")),
                        () -> assertTrue(result.contains("2025-04-16")),
                        () -> assertTrue(result.contains("101")),
                        () -> assertTrue(result.contains("201"))

                );
            }

            @Test
            @DisplayName("Test Get Placowki")
            void testGetPlacowki() {
                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("[{\"id\":1,\"Adres\":\"Aleja Tysiąclecia Państwa Polskiego 7, 28-340 Kielce\"}]"));

                String result = supabaseClient.getPlacowki();
                assertAll(
                        () -> assertTrue(result.contains("1")),
                        () -> assertTrue(result.contains("Aleja Tysiąclecia Państwa Polskiego 7, 28-340 Kielce"))
                );
            }

            @Test
            @DisplayName("Test Get Uzytkownicy")
            void testGetUzytkownicy() {
                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("[{\"id\":1, \"Nazwa_Uzytkownika\":\"username\", \"Imie\":\"Jan\",\"Nazwisko\":\"Kowalski\", \"Haslo\":\"pass\", \"Email\":\"s092677@student.tu.kielce.pl\", \"Data_Urodzenia\":\"2025-04-16\", \"Zablokowany\":\"false\"}]"));

                String result = supabaseClient.getUzytkownicy();

                assertAll(
                        () -> assertTrue(result.contains("1")),
                        () -> assertTrue(result.contains("username")),
                        () -> assertTrue(result.contains("Jan")),
                        () -> assertTrue(result.contains("Kowalski")),
                        () -> assertTrue(result.contains("pass")),
                        () -> assertTrue(result.contains("s092677@student.tu.kielce.pl")),
                        () -> assertTrue(result.contains("2025-04-16")),
                        () -> assertTrue(result.contains("false"))
                );
            }

            @Test
            @DisplayName("Test Get Wypozyczenia")
            void testGetWypozyczenia() {
                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenAnswer(invocation -> {
                    Function<UriBuilder, URI> uriFn = invocation.getArgument(0);
                    uriFn.apply(UriComponentsBuilder.fromUriString("http://localhost"));
                    return requestHeadersSpec;
                });
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("[{\"id\":1, \"Data_Wypozyczenia\":\"2025-04-16\", \"Data_Oddania\":\"2025-04-17\", \"Termin_Oddania\":\"2025-04-18\", \"id_ksiazki\":\"101\", \"id_uzytkownika\":\"201\"}]"));

                String result = supabaseClient.getWypozyczenia();

                assertAll(
                        () -> assertTrue(result.contains("1")),
                        () -> assertTrue(result.contains("2025-04-16")),
                        () -> assertTrue(result.contains("2025-04-17")),
                        () -> assertTrue(result.contains("2025-04-18")),
                        () -> assertTrue(result.contains("101")),
                        () -> assertTrue(result.contains("201"))
                );
            }



        }

        @Nested
        @DisplayName("Niepoprawnie wykonujace sie testy GET")
        class GetIncorrectTest {

            @Test
            @DisplayName("Blad polaczenia podczas pobierania adminow")
            void testGetThrowAdmini() {

                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.getAdmini()
                );

                assertTrue(exception.getMessage().contains("Failed to fetch Admin"));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas pobierania autorow")
            void testGetThrowAutorzy() {

                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.getAutorzy()
                );

                assertTrue(exception.getMessage().contains("Failed to fetch Autorzy"));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas pobierania kar")
            void testGetThrowKary() {

                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.getKary()
                );

                assertTrue(exception.getMessage().contains("Failed to fetch Kary"));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas pobierania ksiazek")
            void testGetThrowKsiazka() {

                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.getKsiazki()
                );

                assertTrue(exception.getMessage().contains("Failed to fetch Ksiazka"));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas pobierania placowek")
            void testGetThrowPlacowka() {

                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.getPlacowki()
                );

                assertTrue(exception.getMessage().contains("Failed to fetch Placowka"));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas pobierania uzytkownikow")
            void testGetThrowUzytkownik() {

                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.getUzytkownicy()
                );

                assertTrue(exception.getMessage().contains("Failed to fetch Uzytkownik"));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas pobierania wypozyczen")
            void testGetThrowWypozyczenia() {

                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.getWypozyczenia()
                );

                assertTrue(exception.getMessage().contains("Failed to fetch Wypozyczenia"));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

        }
    }

    @Nested
    @DisplayName("Testy DELETE")
    class DeleteTest {

        @Nested
        @DisplayName("Poprawnie wykonujace sie testy DELETE")
        class DeleteCorrectTest {

            @Test
            @DisplayName("Test Delete Admini")
            void testDeleteAdmini() {
                when(webClient.delete()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"deleted\":true}"));

                String result = supabaseClient.deleteAdmin(10);

                assertTrue(result.contains("deleted"));
            }

            @Test
            @DisplayName("Test Delete Autorzy")
            void testDeleteAutorzy() {
                when(webClient.delete()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"deleted\":true}"));

                String result = supabaseClient.deleteAutor(10);

                assertTrue(result.contains("deleted"));
            }

            @Test
            @DisplayName("Test Delete Kary")
            void testDeleteKary() {
                when(webClient.delete()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"deleted\":true}"));

                String result = supabaseClient.deleteKara(10);

                assertTrue(result.contains("deleted"));
            }

            @Test
            @DisplayName("Test Delete Ksiazki")
            void testDeleteKsiazki() {
                when(webClient.delete()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"deleted\":true}"));

                String result = supabaseClient.deleteKsiazka(10);

                assertTrue(result.contains("deleted"));
            }

            @Test
            @DisplayName("Test Delete Placowki")
            void testDeletePlacowki() {
                when(webClient.delete()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"deleted\":true}"));

                String result = supabaseClient.deletePlacowka(10);

                assertTrue(result.contains("deleted"));
            }

            @Test
            @DisplayName("Test Delete Uzytkownicy")
            void testDeleteUzytkownicy() {
                when(webClient.delete()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"deleted\":true}"));

                String result = supabaseClient.deleteUzytkownik(10);

                assertTrue(result.contains("deleted"));
            }

            @Test
            @DisplayName("Test Delete Wypozyczenia")
            void testDeleteWypozyczenia() {
                when(webClient.delete()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenAnswer(invocation -> {
                    Function<UriBuilder, URI> uriFn = invocation.getArgument(0);
                    uriFn.apply(UriComponentsBuilder.fromUriString("http://localhost"));
                    return requestHeadersSpec;
                });
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"deleted\":true}"));

                String result = supabaseClient.deleteWypozyczenie(10);

                assertTrue(result.contains("deleted"));
            }



        }

        @Nested
        @DisplayName("Niepoprawnie wykonujace sie testy Delete")
        class DeleteIncorrectTest {

            @Test
            @DisplayName("Blad polaczenia podczas usuwania adminow")
            void testDeleteThrowAdmini() {

                when(webClient.delete()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.deleteAdmin(10)
                );

                assertTrue(exception.getMessage().contains("Failed to delete id: 10 in table: Admin: "));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas usuwania autorow")
            void testDeleteThrowAutorzy() {

                when(webClient.delete()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.deleteAutor(10)
                );

                assertTrue(exception.getMessage().contains("Failed to delete id: 10 in table: Autorzy: "));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas usuwania kar")
            void testDeleteThrowKary() {

                when(webClient.delete()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.deleteKara(10)
                );

                assertTrue(exception.getMessage().contains("Failed to delete id: 10 in table: Kary: "));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas usuwania ksiazek")
            void testDeleteThrowKsiazka() {

                when(webClient.delete()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.deleteKsiazka(10)
                );

                assertTrue(exception.getMessage().contains("Failed to delete id: 10 in table: Ksiazka: "));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas usuwania placowek")
            void testDeleteThrowPlacowka() {

                when(webClient.delete()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.deletePlacowka(10)
                );

                assertTrue(exception.getMessage().contains("Failed to delete id: 10 in table: Placowka: "));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas usuwania uzytkownikow")
            void testDeleteThrowUzytkownik() {

                when(webClient.delete()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.deleteUzytkownik(10)
                );

                assertTrue(exception.getMessage().contains("Failed to delete id: 10 in table: Uzytkownik: "));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas usuwania wypozyczen")
            void testDeleteThrowWypozyczenia() {

                when(webClient.delete()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.deleteWypozyczenie(10)
                );

                assertTrue(exception.getMessage().contains("Failed to delete id: 10 in table: Wypozyczenia: "));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

        }
    }

    @Nested
    @DisplayName("Testy POST")
    class PostTest {

    @Nested
    @DisplayName("Poprawnie wykonujace sie testy POST")
    class PostCorrectTest {

        @Test
        @DisplayName("Test Post Admini")
        void testPostAdmini() {
            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
            when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

            String result = supabaseClient.addAdmin("Jan","Kowalski","Username","pass",1);

            assertTrue(result.contains("success"));
        }

        @Test
        @DisplayName("Test Post Autorzy")
        void testPostAutorzy() {
            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
            when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

            String result = supabaseClient.addAutor("John","Tolkien",1892);

            assertTrue(result.contains("success"));
        }

        @Test
        @DisplayName("Test Post Kary")
        void testPostKary() {
            ArgumentCaptor<Map<String, Object>> captor = ArgumentCaptor.forClass(Map.class);
            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
            when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.bodyValue(captor.capture())).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

            String result = supabaseClient.addKara(20.5,"2025-04-17","2025-04-18",1);


            Map<String, Object> sentData = captor.getValue();
            assertEquals("2025-04-17", sentData.get("Data_Wydania_Kary"));
            assertTrue(result.contains("success"));
        }

        @Test
        @DisplayName("Test Post Kary z null")
        void testPostKarywithnull() {
            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
            when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

            String result = supabaseClient.addKara(20.5,null,"2025-04-18",1);

            assertTrue(result.contains("success"));
        }

        @Test
        @DisplayName("Test Post Ksiazki")
        void testPostKsiazki() {
            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
            when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

            String result = supabaseClient.addKsiazka("Silmarillion","Fantasy","1977-09-15",1,101);

            assertTrue(result.contains("success"));
        }

        @Test
        @DisplayName("Test Post Placowki")
        void testPostPlacowki() {
            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
            when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

            String result = supabaseClient.addPlacowka("Aleja Tysiąclecia Państwa Polskiego 7, 28-340 Kielce");

            assertTrue(result.contains("success"));
        }

        @Test
        @DisplayName("Test Post Uzytkownicy")
        void testPostUzytkownicy() {
            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
            when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

            String result = supabaseClient.addUzytkownik("Jan", "Kowalski", "2025-04-17", "username", "pass", "s092677@student.tu.kielce.pl");

            assertTrue(result.contains("success"));
        }

        @Test
        @DisplayName("Test Post Wypozyczenia")
        void testPostWypozyczenia() {
            ArgumentCaptor<Map<String, Object>> captor = ArgumentCaptor.forClass(Map.class);

            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(anyString())).thenAnswer(invocation -> {
                String uri = invocation.getArgument(0);
                URI.create(uri);
                return requestBodySpec;
            });
            when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.bodyValue(captor.capture())).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

            String result = supabaseClient.addWypozyczenie("2025-04-16", "2025-04-17", "2025-04-18", 1, 101);

            Map<String, Object> actualData = captor.getValue();
            assertEquals("2025-04-16", actualData.get("Data_Wypozyczenia"));
            assertEquals("2025-04-17", actualData.get("Data_Oddania"));
            assertTrue(result.contains("success"));
        }

        @Test
        @DisplayName("Test Post Wypozyczenia z null")
        void testPostWypozyczeniawithnull() {

            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(anyString())).thenAnswer(invocation -> {
                String uri = invocation.getArgument(0);
                URI.create(uri);
                return requestBodySpec;
            });
            when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

            String result = supabaseClient.addWypozyczenie(null, null, "2025-04-18", 1, 101);

            assertTrue(result.contains("success"));
        }



    }

    @Nested
    @DisplayName("Niepoprawnie wykonujace sie testy POST")
    class PostIncorrectTest {

        @Test
        @DisplayName("Blad polaczenia podczas dodawania adminow")
        void testPostThrowAdmini() {

            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
            when(requestBodySpec.body(any(), eq(String.class))).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

            SupabaseConnectionException exception = assertThrows(
                    SupabaseConnectionException.class,
                    () -> supabaseClient.addAdmin("Jan", "Kowalski", "Username", "pass", 1)
            );

            assertTrue(exception.getMessage().contains("Failed to post to table Admin: "));
            assertInstanceOf(RuntimeException.class, exception.getCause());

        }

        @Test
        @DisplayName("Blad polaczenia podczas dodawania autorow")
        void testPostThrowAutorzy() {

            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
            when(requestBodySpec.body(any(), eq(String.class))).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

            SupabaseConnectionException exception = assertThrows(
                    SupabaseConnectionException.class,
                    () -> supabaseClient.addAutor("John", "Tolkien", 1892)
            );

            assertTrue(exception.getMessage().contains("Failed to post to table Autorzy: "));
            assertInstanceOf(RuntimeException.class, exception.getCause());

        }

        @Test
        @DisplayName("Blad polaczenia podczas dodawania kar")
        void testPostThrowKary() {

            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
            when(requestBodySpec.body(any(), eq(String.class))).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

            SupabaseConnectionException exception = assertThrows(
                    SupabaseConnectionException.class,
                    () -> supabaseClient.addKara(20.5, null, "2025-04-18", 1)
            );

            assertTrue(exception.getMessage().contains("Failed to post to table Kary: "));
            assertInstanceOf(RuntimeException.class, exception.getCause());

        }

        @Test
        @DisplayName("Blad polaczenia podczas dodawania ksiazek")
        void testPostThrowKsiazka() {

            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
            when(requestBodySpec.body(any(), eq(String.class))).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

            SupabaseConnectionException exception = assertThrows(
                    SupabaseConnectionException.class,
                    () -> supabaseClient.addKsiazka("Silmarillion", "Fantasy", "1977-09-15", 1, 101)
            );

            assertTrue(exception.getMessage().contains("Failed to post to table Ksiazka: "));
            assertInstanceOf(RuntimeException.class, exception.getCause());

        }

        @Test
        @DisplayName("Blad polaczenia podczas dodawania placowek")
        void testPostThrowPlacowka() {

            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
            when(requestBodySpec.body(any(), eq(String.class))).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

            SupabaseConnectionException exception = assertThrows(
                    SupabaseConnectionException.class,
                    () -> supabaseClient.addPlacowka("Aleja Tysiąclecia Państwa Polskiego 7, 28-340 Kielce")
            );

            assertTrue(exception.getMessage().contains("Failed to post to table Placowka: "));
            assertInstanceOf(RuntimeException.class, exception.getCause());

        }

        @Test
        @DisplayName("Blad polaczenia podczas dodawania uzytkownikow")
        void testPostThrowUzytkownik() {

            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
            when(requestBodySpec.body(any(), eq(String.class))).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

            SupabaseConnectionException exception = assertThrows(
                    SupabaseConnectionException.class,
                    () -> supabaseClient.addUzytkownik("Jan", "Kowalski", "2025-04-17", "username", "pass", "s092677@student.tu.kielce.pl")
            );

            assertTrue(exception.getMessage().contains("Failed to post to table Uzytkownik: "));
            assertInstanceOf(RuntimeException.class, exception.getCause());

        }

        @Test
        @DisplayName("Blad polaczenia podczas dodawania wypozyczen")
        void testPostThrowWypozyczenia() {

            when(webClient.post()).thenReturn(requestBodyUriSpec);
            when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
            when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
            when(requestBodySpec.body(any(), eq(String.class))).thenReturn(requestHeadersSpec);
            when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
            when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

            SupabaseConnectionException exception = assertThrows(
                    SupabaseConnectionException.class,
                    () -> supabaseClient.addWypozyczenie(null, null, "2025-04-18", 1, 101)
            );

            assertTrue(exception.getMessage().contains("Failed to post to table Wypozyczenia: "));
            assertInstanceOf(RuntimeException.class, exception.getCause());

        }
    }

  }

    @Nested
    @DisplayName("Testy PUT")
    class PutTest {

        @Nested
        @DisplayName("Poprawnie wykonujace sie testy PUT")
        class PutCorrectTest {

            @Test
            @DisplayName("Test Put Admini")
            void testPutAdmini() {
                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

                String result = supabaseClient.updateAdmin(1, "Jan", "Kowalski", "Username", "pass", 101);

                assertTrue(result.contains("success"));
            }

            @Test
            @DisplayName("Test Put Admini with nulls")
            void testPutAdmininull() {
                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

                String result = supabaseClient.updateAdmin(1, null, null, null, null, null);

                assertTrue(result.contains("success"));
            }

            @Test
            @DisplayName("Test Put Autorzy")
            void testPutAutorzy() {
                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

                String result = supabaseClient.updateAutor(1, "John", "Tolkien", 1892);

                assertTrue(result.contains("success"));
            }

            @Test
            @DisplayName("Test Put Autorzy with nulls")
            void testPutAutorzynull() {
                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

                String result = supabaseClient.updateAutor(1, null, null, null);

                assertTrue(result.contains("success"));
            }

            @Test
            @DisplayName("Test Put Kary")
            void testPutKary() {
                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

                String result = supabaseClient.updateKara(1, 20.5, "2025-04-16", "2025-04-18", Boolean.FALSE, 101);

                assertTrue(result.contains("success"));
            }

            @Test
            @DisplayName("Test Put Kary with nulls")
            void testPutKarynull() {
                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

                String result = supabaseClient.updateKara(1, null, null, null, null, null);

                assertTrue(result.contains("success"));
            }

            @Test
            @DisplayName("Test Put Ksiazki")
            void testPutKsiazki() {
                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

                String result = supabaseClient.updateKsiazka(1, "Silmarillion", "Fantasy", "1977-09-15", "2025-04-17", 101, 201);

                assertTrue(result.contains("success"));
            }

            @Test
            @DisplayName("Test Put Ksiazki with nulls")
            void testPutKsiazkinull() {
                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

                String result = supabaseClient.updateKsiazka(1, null, null, null, null, null, null);

                assertTrue(result.contains("success"));
            }

            @Test
            @DisplayName("Test Put Placowki")
            void testPutPlacowki() {
                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

                String result = supabaseClient.updatePlacowka(1, "Aleja Tysiąclecia Państwa Polskiego 7, 28-340 Kielce");

                assertTrue(result.contains("success"));
            }

            @Test
            @DisplayName("Test Put Placowki with nulls")
            void testPutPlacowkinull() {
                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

                String result = supabaseClient.updatePlacowka(1, null);

                assertTrue(result.contains("success"));
            }

            @Test
            @DisplayName("Test Put Uzytkownicy")
            void testPutUzytkownicy() {
                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

                String result = supabaseClient.updateUzytkownik(1, "Jan", "Kowalski", "2025-04-17", "username", "pass", "s092677@student.tu.kielce.pl", false);

                assertTrue(result.contains("success"));
            }

            @Test
            @DisplayName("Test Put Uzytkownicy with nulls")
            void testPutUzytkownicynull() {
                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

                String result = supabaseClient.updateUzytkownik(1, null, null, null, null, null, null, null);

                assertTrue(result.contains("success"));
            }

            @Test
            @DisplayName("Test Put Wypozyczenia")
            void testPutWypozyczenia() {
                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

                String result = supabaseClient.updateWypozyczenie(1, "2025-04-16", "2025-04-17", "2025-04-18", 1, 101);

                assertTrue(result.contains("success"));
            }

            @Test
            @DisplayName("Test Put Wypozyczenia with nulls")
            void testPutWypozyczenianull() {
                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenAnswer(invocation -> {
                    Function<UriBuilder, URI> uriFn = invocation.getArgument(0);
                    uriFn.apply(UriComponentsBuilder.fromUriString("http://localhost"));
                    return requestBodySpec;
                });
                when(requestBodySpec.contentType(any(MediaType.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{\"success\":true}"));

                String result = supabaseClient.updateWypozyczenie(1, null, null, null, null, null);

                assertTrue(result.contains("success"));
            }


        }

        @Nested
        @DisplayName("Niepoprawnie wykonujace sie testy PUT")
        class PutIncorrectTest {

            @Test
            @DisplayName("Blad polaczenia podczas aktualizowania adminow")
            void testPutThrowAdmini() {

                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
                when(requestBodySpec.body(any(), eq(String.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.updateAdmin(1, "Jan", "Kowalski", "Username", "pass", 101)
                );

                assertTrue(exception.getMessage().contains("Failed to update to table Admin: "));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas aktualizowania autorow")
            void testPutThrowAutorzy() {

                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
                when(requestBodySpec.body(any(), eq(String.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.updateAutor(1, "John", "Tolkien", 1892)
                );

                assertTrue(exception.getMessage().contains("Failed to update to table Autorzy: "));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas aktualizowania kar")
            void testPutThrowKary() {

                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
                when(requestBodySpec.body(any(), eq(String.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.updateKara(1, 20.5, null, "2025-04-18", Boolean.FALSE, 101)
                );

                assertTrue(exception.getMessage().contains("Failed to update to table Kary: "));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas aktualizowania ksiazek")
            void testPutThrowKsiazka() {

                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
                when(requestBodySpec.body(any(), eq(String.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.updateKsiazka(1, "Silmarillion", "Fantasy", "1977-09-15", null, 101, 201)
                );

                assertTrue(exception.getMessage().contains("Failed to update to table Ksiazka: "));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas aktualizowania placowek")
            void testPutThrowPlacowka() {

                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
                when(requestBodySpec.body(any(), eq(String.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.updatePlacowka(1, "Aleja Tysiąclecia Państwa Polskiego 7, 28-340 Kielce")
                );

                assertTrue(exception.getMessage().contains("Failed to update to table Placowka: "));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas aktualizowania uzytkownikow")
            void testPutThrowUzytkownik() {

                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
                when(requestBodySpec.body(any(), eq(String.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.updateUzytkownik(1, "Jan", "Kowalski", "2025-04-17", "username", "pass", "s092677@student.tu.kielce.pl", false)
                );

                assertTrue(exception.getMessage().contains("Failed to update to table Uzytkownik: "));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }

            @Test
            @DisplayName("Blad polaczenia podczas aktualizowania wypozyczen")
            void testPutThrowWypozyczenia() {

                when(webClient.put()).thenReturn(requestBodyUriSpec);
                when(requestBodyUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
                when(requestBodySpec.header(anyString(), anyString())).thenReturn(requestBodySpec);
                when(requestBodySpec.body(any(), eq(String.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.error(new RuntimeException("Connection error")));

                SupabaseConnectionException exception = assertThrows(
                        SupabaseConnectionException.class,
                        () -> supabaseClient.updateWypozyczenie(1, null, null, "2025-04-18", 1, 101)
                );

                assertTrue(exception.getMessage().contains("Failed to update to table Wypozyczenia: "));
                assertInstanceOf(RuntimeException.class, exception.getCause());

            }
        }
    }



        @Nested
        @DisplayName("Testy pozostalych, wyspecjalizowanych getow")
        class specGettest {

            static Stream<Arguments> getKsiazkaFiltrdata() {
                return Stream.of(
                        Arguments.of(1, "Silmarillion", "Fantasy", "1977-09-15", "John", "Tolkien", 101),
                        Arguments.of(null, null, null, null, null, null, null)
                );
            }

            @ParameterizedTest
            @DisplayName("getKsiazkaFiltr Testy polaczenia + filtrowania")
            @MethodSource("getKsiazkaFiltrdata")
            void testgetKsiazkaFiltrdata(Integer id, String tytul, String gatunek, String dataWydania, String autorImie, String autorNazwisko, Integer idPlacowki) throws JSONException {

                String ksiazkiResponse = "[{\"id\":1,\"Tytul\":\"Silmarillion\",\"Gatunek\":\"Fantasy\",\"Data_Wydania\":\"1977-09-15\",\"id_autora\":101,\"id_placowki\":201}]";
                String autorzyResponse = "[{\"id\":101,\"Imie\":\"John\",\"Nazwisko\":\"Tolkien\"}]";

                when(webClient.get()).thenReturn(requestHeadersUriSpec);

                when(requestHeadersUriSpec.uri(any(Function.class))).thenAnswer(invocation -> {
                    Function<UriBuilder, URI> uriFn = invocation.getArgument(0);
                    uriFn.apply(UriComponentsBuilder.fromUriString("http://localhost"));
                    return requestHeadersSpec;
                });

                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

                when(responseSpec.bodyToMono(String.class))
                        .thenReturn(Mono.just(ksiazkiResponse))
                        .thenReturn(Mono.just(autorzyResponse));

                String result = supabaseClient.getKsiazkaFiltr(id, tytul, gatunek, dataWydania, autorImie, autorNazwisko, idPlacowki);

                assertNotNull(result);
                JSONArray resultArray = new JSONArray(result);
                assertEquals(1, resultArray.length());
                assertEquals("Silmarillion", resultArray.getJSONObject(0).getString("Tytul"));
            }

            @ParameterizedTest
            @DisplayName("getKsiazkaFiltr Testy polaczenia + filtrowania")
            @MethodSource("getKsiazkaFiltrdata")
            void testgetKsiazkaFiltrdata2(Integer id, String tytul, String gatunek, String dataWydania, String autorImie, String autorNazwisko, Integer idPlacowki) throws JSONException {

                String ksiazkiResponse = "[{\"id\":1,\"Tytul\":\"Silmarillion\",\"Gatunek\":\"Fantasy\",\"Data_Wydania\":\"1977-09-15\",\"id_autora\":101,\"id_placowki\":201}]";
                String autorzyResponse = "[{\"id\":102,\"Imie\":\"Andrzej\",\"Nazwisko\":\"Sapkowski\"}]";

                when(webClient.get()).thenReturn(requestHeadersUriSpec);

                when(requestHeadersUriSpec.uri(any(Function.class))).thenAnswer(invocation -> {
                    Function<UriBuilder, URI> uriFn = invocation.getArgument(0);
                    uriFn.apply(UriComponentsBuilder.fromUriString("http://localhost"));
                    return requestHeadersSpec;
                });

                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class))
                        .thenReturn(Mono.just(ksiazkiResponse))
                        .thenReturn(Mono.just(autorzyResponse));

                String result = supabaseClient.getKsiazkaFiltr(id, tytul, gatunek, dataWydania, autorImie, autorNazwisko, idPlacowki);

                assertNotNull(result);
                JSONArray resultArray = new JSONArray(result);
                assertEquals(0, resultArray.length());
            }


            @Test
            @DisplayName("getKsiazkaFiltr Testy SupabaseConnectionException")
            void testgetKsiazkaFiltrdataThrowsSupabaseConnectionException() {
                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenThrow(new RuntimeException("Connection error"));

                assertThrows(SupabaseConnectionException.class, () -> {
                    supabaseClient.getKsiazkaFiltr(1, null, null, null, null, null, null);
                });
            }

            @Test
            @DisplayName("getKsiazkaFiltr Testy JsonFileException dla extractAutorIds")
            void testgetKsiazkaFiltrdataextractAutorIdsThrowsJsonFileException() {
                String invalidJson = "invalid_json";
                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class))
                        .thenReturn(Mono.just("[{\"id\":1,\"Tytul\":\"Silmarillion\",\"id_autora\":101}]"))
                        .thenReturn(Mono.just(invalidJson));

                assertThrows(JsonFileException.class, () -> {
                    supabaseClient.getKsiazkaFiltr(1, null, null, null, "Jan", "Kowalski", null);
                });
            }


            @Test
            @DisplayName("getKsiazkaFiltr Testy JsonFileException dla filterKsiazkiByAutor")
            void testgetKsiazkaFiltrdatafilterKsiazkiByAutorThrowsJsonFileException() {
                String invalidJson = "invalid_json";
                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class))
                        .thenReturn(Mono.just(invalidJson))
                        .thenReturn(Mono.just("[{\"id\":1,\"Imie\":\"Jan\",\"Nazwisko\":\"Kowalski\"}]"));

                assertThrows(JsonFileException.class, () -> {
                    supabaseClient.getKsiazkaFiltr(null, null, null, null, "Jan", "Kowalski", null);
                });
            }

            @Test
            @DisplayName("getUzytkownicyLogin Test poprawnego dzialania")
            void testgetUzytkownicyLogin() {
                String mockResponse = "[{\"id\":1,\"Nazwa_Uzytkownika\":\"testuser\"}]";
                String login = "testuser";
                String password = "password123";

                when(webClient.get()).thenReturn(requestHeadersUriSpec);

                when(requestHeadersUriSpec.uri(any(Function.class))).thenAnswer(invocation -> {
                    Function<UriBuilder, URI> uriFunction = invocation.getArgument(0);
                    uriFunction.apply(UriComponentsBuilder.fromUriString("http://localhost"));
                    return requestHeadersSpec;
                });
                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just(mockResponse));

                String result = supabaseClient.getUzytkownicyLogin(login, password);

                assertEquals(mockResponse, result);
            }

            @Test
            @DisplayName("getUzytkownicyLogin Test InstanceNotFoundException")
            void testgetUzytkownicyLoginThrowsInstanceNotFoundException() {
                String login = "wronglogin";
                String password = "wrongpass";

                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenAnswer(invocation -> {
                    Function<UriBuilder, URI> uriFunction = invocation.getArgument(0);
                    uriFunction.apply(UriComponentsBuilder.fromUriString("http://localhost"));
                    return requestHeadersSpec;
                });

                when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
                when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.empty());

                assertThrows(InstanceNotFoundException.class, () ->
                        supabaseClient.getUzytkownicyLogin(login, password));
                }

            @Test
            @DisplayName("getUzytkownicyLogin Test SupabaseConnectionException")
            void testgetUzytkownicyLoginThrowsSupabaseConnectionException() {
                String login = "username";
                String password = "password";

                when(webClient.get()).thenReturn(requestHeadersUriSpec);
                when(requestHeadersUriSpec.uri(any(Function.class))).thenThrow(new RuntimeException("Connection error"));

                SupabaseConnectionException ex = assertThrows(SupabaseConnectionException.class, () ->
                        supabaseClient.getUzytkownicyLogin(login, password));

                assertTrue(ex.getMessage().contains("Failed to fetch user"));
            }
        }

        @Test
        @DisplayName("Nieudane polaczenie do bazy danych")
        void SupabaseClientThrowsSupabaseConnectionException(){

            Mockito.when(webClientBuilder.baseUrl(Mockito.anyString()))
                    .thenThrow(new RuntimeException("Failed to build WebClient"));

            assertThrows(SupabaseConnectionException.class, () -> {
                new SupabaseClient(webClientBuilder);
            });
    }
}
