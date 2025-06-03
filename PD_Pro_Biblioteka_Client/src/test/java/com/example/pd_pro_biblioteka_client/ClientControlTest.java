package com.example.pd_pro_biblioteka_client;

import com.example.pd_pro_biblioteka_client.controller.Client;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.fxml.FXMLLoader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class ClientControlTest {

    private Client client;



    @BeforeEach
    public void setUp() {
        client = new Client();
    }

    @Test
    public void testReserve() {
        // Just checks that the method prints/logs without error
        client.reserve(null);
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

    @Test
    public void testDeleteAccountConfirmed() throws Exception {
        ActionEventMock actionEventMock = new ActionEventMock();

        Client.AlertService mockAlertService = mock(Client.AlertService.class);
        when(mockAlertService.showConfirmation(any(), any(), any()))
                .thenReturn(Optional.of(ButtonType.OK));

        Client spyClient = Mockito.spy(new Client());
        spyClient.setAlertService(mockAlertService);

        // Nie wykonuj prawdziwego GUI logoutu
        doNothing().when(spyClient).performLogout(any());

        spyClient.delete_acc(actionEventMock.getEvent());

        verify(mockAlertService, times(1)).showConfirmation(any(), any(), any());
        verify(spyClient, times(1)).performLogout(any()); // <-- Sprawdzamy, że się wykonało
    }




    @Test
    public void testDeleteAccountCancelled() throws Exception {
        ActionEventMock actionEventMock = new ActionEventMock();

        Client.AlertService mockAlertService = mock(Client.AlertService.class);
        when(mockAlertService.showConfirmation(any(), any(), any()))
                .thenReturn(Optional.empty());

        client.setAlertService(mockAlertService);

        client.delete_acc(actionEventMock.getEvent());

        verify(mockAlertService, times(1)).showConfirmation(any(), any(), any());
        verify(actionEventMock.stage, never()).close();
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
