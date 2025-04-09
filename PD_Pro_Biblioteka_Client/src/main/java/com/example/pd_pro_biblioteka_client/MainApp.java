package com.example.pd_pro_biblioteka_client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class MainApp extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() throws Exception {
        springContext = new SpringApplicationBuilder(PdProBibliotekaClientApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/logowanie.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        primaryStage.setTitle("Logowanie");
        primaryStage.setScene(new Scene(fxmlLoader.load(), 600, 600));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        springContext.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(); // <-- startuje JavaFX
    }
}
