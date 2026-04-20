package org.openjfx.sfaxbest;

import jakarta.persistence.criteria.Root;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        scene = new Scene(loadFXML("mainAdmin" ), 1000, 600);
        stage.setMinHeight(600);
        stage.setMinWidth(1000);
        stage.setScene(scene);
        stage.setTitle("SfaxBest");
        Image appIcon = new Image(getClass().getResource("/icons/SfaxBest_icon.png").toExternalForm());
        stage.getIcons().add(appIcon);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    static void maximizeStage(){
        primaryStage.setMaximized(true);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}