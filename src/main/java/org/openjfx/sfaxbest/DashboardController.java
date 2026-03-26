package org.openjfx.sfaxbest;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.io.IOException;

public class DashboardController {


    @FXML
    private PieChart categoryPieChart;

    @FXML
    private BarChart<String, Number> topMoviesBarChart;

    @FXML
    private LineChart<String, Number> subscribersLineChart;

    @FXML
    public void goToSeries() throws IOException {
        App.setRoot("seriesAdmin");
    }
    @FXML
    public void goToDocumantary() throws IOException {
        App.setRoot("documantaryAdmin");
    }
    @FXML
    public void goToFilms() throws IOException {
        App.setRoot("mainAdmin");
    }
    @FXML
    public void goToComments() throws IOException{
        App.setRoot("commentAdmin");
    }
    @FXML
    public void initialize() {

        loadCategoryChart();
        loadTopMoviesChart();
        loadSubscribersChart();

    }
    private void loadCategoryChart() {

        ObservableList<PieChart.Data> data =
                FXCollections.observableArrayList(
                        new PieChart.Data("Horror", 36),
                        new PieChart.Data("Comedy", 14),
                        new PieChart.Data("Drama", 80)
                );

        categoryPieChart.setData(data);
    }
    private void loadTopMoviesChart() {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Top Movies");

        series.getData().add(new XYChart.Data<>("Batman", 2000));
        series.getData().add(new XYChart.Data<>("Matrix", 1800));
        series.getData().add(new XYChart.Data<>("Avatar", 1700));
        series.getData().add(new XYChart.Data<>("Inception", 1600));
        series.getData().add(new XYChart.Data<>("Titanic", 1500));

        topMoviesBarChart.getData().add(series);
    }
    private void loadSubscribersChart() {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("New Subscribers");

        series.getData().add(new XYChart.Data<>("Mon", 20));
        series.getData().add(new XYChart.Data<>("Tue", 35));
        series.getData().add(new XYChart.Data<>("Wed", 15));
        series.getData().add(new XYChart.Data<>("Thu", 40));
        series.getData().add(new XYChart.Data<>("Fri", 28));

        subscribersLineChart.getData().add(series);
    }
}
