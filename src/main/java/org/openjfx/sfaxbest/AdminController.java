package org.openjfx.sfaxbest;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class AdminController {
    @FXML
    public void goToFilms() throws IOException {
        App.setRoot("mainAdmin");
    }
    @FXML
    public void goToSeries() throws IOException {
        App.setRoot("seriesAdmin");
    }
    @FXML
    public void goToDocumantary() throws IOException {
        App.setRoot("documantaryAdmin");
    }


}
