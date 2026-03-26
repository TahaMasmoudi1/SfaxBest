package org.openjfx.sfaxbest;

import javafx.fxml.FXML;

import java.io.IOException;

public class CommentController {
    @FXML
    public void goToSeries() throws IOException {
        App.setRoot("seriesAdmin");
    }

    @FXML
    public void goToDocumantary() throws IOException {
        App.setRoot("documantaryAdmin");
    }
    @FXML
    public void goToDashbaord() throws IOException{
        App.setRoot("dashboardAdmin");
    }
    @FXML
    public void goToFilms() throws IOException {
        App.setRoot("mainAdmin");
    }
}
