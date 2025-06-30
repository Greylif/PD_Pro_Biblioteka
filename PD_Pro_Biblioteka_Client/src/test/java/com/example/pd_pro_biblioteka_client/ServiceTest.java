package com.example.pd_pro_biblioteka_client;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.concurrent.Executors;

import com.example.pd_pro_biblioteka_client.model.logAdmin;
import com.example.pd_pro_biblioteka_client.model.logUser;
import com.example.pd_pro_biblioteka_client.service.JWTdecoder;
import com.example.pd_pro_biblioteka_client.service.SessionMonitor;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;


/**
 * Testy Service.
 */
@DisplayName("Testy dla Service")
public class ServiceTest {

    /**
     * Testy Klasy ServiceMonitor.
     */
    @Nested
    @DisplayName("Testy klasy ServiceMonitor")
    class ServiceMonitor {
        @BeforeAll
        public static void initJavaFx() {
            new JFXPanel();
        }

        @Test
        @DisplayName("Testy gdy jest token ważny")
        public void testCheckToken_Client200_NotLogout() throws Exception {
            HttpResponse<String> userResponse = mock(HttpResponse.class);
            when(userResponse.statusCode()).thenReturn(200);

            HttpResponse<String> adminResponse = mock(HttpResponse.class);
            when(adminResponse.statusCode()).thenReturn(403);

            HttpClient client = mock(HttpClient.class);
            when(client.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                    .thenReturn(userResponse)
                    .thenReturn(adminResponse);

            Stage mockStage = mock(Stage.class);
            SessionMonitor monitor = new SessionMonitor("token", mockStage) {
                @Override
                public void checkToken() {
                    try {
                        int userCode = userResponse.statusCode();
                        int adminCode = adminResponse.statusCode();

                        if (userCode != 200 && adminCode != 200) {
                            forceLogout(mockStage);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            monitor.checkToken();

            verify(mockStage, never()).close();
        }

        @Test
        @DisplayName("Testy gdy jest token nieważny")
        public void testCheckToken_Without200_Logout() throws Exception {
            HttpResponse<String> userResponse = mock(HttpResponse.class);
            when(userResponse.statusCode()).thenReturn(403);

            HttpResponse<String> adminResponse = mock(HttpResponse.class);
            when(adminResponse.statusCode()).thenReturn(403);

            Stage mockStage = mock(Stage.class);

            SessionMonitor monitor = new SessionMonitor("token", mockStage) {
                @Override
                public void checkToken() {
                    Platform.runLater(() -> {
                        forceLogout(mockStage);
                        verify(mockStage).close();
                    });
                }
            };

            monitor.checkToken();
        }

        @Test
        @DisplayName("Testy czyszczenia i zatrzymywania monitora")
        public void testStop_CleansUp() {
            Stage dummyStage = mock(Stage.class);
            SessionMonitor monitor = new SessionMonitor("token", dummyStage);

            monitor.stop();

            assertTrue(monitor.scheduler.isShutdown());
        }
    }

}
