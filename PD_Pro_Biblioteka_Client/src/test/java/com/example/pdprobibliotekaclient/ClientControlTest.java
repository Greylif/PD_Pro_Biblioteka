package com.example.pdprobibliotekaclient;

import com.example.pdprobibliotekaclient.controller.Client;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URL;

import static org.mockito.Mockito.*;

public class ClientControlTest {

    private Client client;



    @BeforeEach
    public void setUp() {
        client = new Client();
    }

    @Test
    public void testLogout() throws IOException {
        // Mocks
        ActionEventMock actionEventMock = new ActionEventMock();
        FXMLLoader loader = mock(FXMLLoader.class);
        Parent mockRoot = mock(Parent.class);

        when(loader.load()).thenReturn(mockRoot);
        when(loader.getLocation()).thenReturn(getClass().getResource("/login.fxml"));

        try (MockedStatic<FXMLLoader> fxmlLoaderMockedStatic = Mockito.mockStatic(FXMLLoader.class)) {
            fxmlLoaderMockedStatic.when(() -> FXMLLoader.load((URL) any())).thenReturn(mockRoot);

            client.logout(actionEventMock.getEvent());

            verify(actionEventMock.stage, times(1)).close();
        }
    }



    // Helper class to mock ActionEvent and GUI hierarchy
    static class ActionEventMock {
        javafx.event.ActionEvent event;
        Node node;
        Scene scene;
        Stage stage;

        public ActionEventMock() {
            stage = mock(Stage.class);
            scene = mock(Scene.class);
            node = mock(Node.class);

            when(node.getScene()).thenReturn(scene);
            when(scene.getWindow()).thenReturn(stage);

            event = new javafx.event.ActionEvent(node, null);
        }

        public javafx.event.ActionEvent getEvent() {
            return event;
        }
    }
}
