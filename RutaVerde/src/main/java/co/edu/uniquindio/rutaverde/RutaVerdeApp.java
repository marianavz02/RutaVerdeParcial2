package co.edu.uniquindio.rutaverde;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RutaVerdeApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Carga la vista desde la carpeta resources
        FXMLLoader fxmlLoader = new FXMLLoader(RutaVerdeApp.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.setTitle("Ruta Verde - Gestión Turística");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}