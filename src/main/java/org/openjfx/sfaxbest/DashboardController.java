package org.openjfx.sfaxbest;

import Services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DashboardController {

    @FXML private PieChart categoryPieChart;
    @FXML private BarChart<String, Number> topMoviesBarChart;
    @FXML private LineChart<String, Number> subscribersLineChart;

    @FXML private Label statTotalFilms;
    @FXML private Label statSeries;
    @FXML private Label statSubscribers;
    @FXML private Label statComments;

    private FilmService filmService = new FilmService();
    private SerieService serieService = new SerieService();
    private UserService userService = new UserService();
    private DocumentaryService  documentaryService = new DocumentaryService();
    private MultimediaService multimediaService = new MultimediaService();



    @FXML public void goToSeries()      throws IOException { App.setRoot("seriesAdmin"); }
    @FXML public void goToDocumantary() throws IOException { App.setRoot("documantaryAdmin"); }
    @FXML public void goToFilms()       throws IOException { App.setRoot("mainAdmin"); }
    @FXML public void goToComments()    throws IOException { App.setRoot("commentAdmin"); }


    @FXML
    public void initialize() {
        loadStatCards();
        loadCategoryChart();
        loadTopMoviesChart();
        loadSubscribersChart();
    }



    private void loadStatCards() {
        int films       = filmService.countFilms();
        int series      =  serieService.countSerie();
        int subscribers = userService.countUsers();
        int documentry   =  documentaryService.countDocumentary() ;

        statTotalFilms.setText(String.valueOf(films));
        statSeries.setText(String.valueOf(series));
        statSubscribers.setText(String.valueOf(subscribers));
        statComments.setText(String.valueOf(documentry));
    }

    private void loadCategoryChart() {
        Map<String, Integer> data = multimediaService.countFilmsByCategory();

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        data.forEach((category, count) ->
                pieData.add(new PieChart.Data(category, count))
        );

        categoryPieChart.setData(pieData);
    }

    private void loadTopMoviesChart() {
       List<Object[]> stats = filmService.getTopByViews(1000000);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Views");

        for (Object[] entry : stats) {
            String title = (String) entry[0];
            Number views = (Number) entry[1];
            series.getData().add(new XYChart.Data<>(title, views.intValue()));
        }

        topMoviesBarChart.getData().add(series);
    }


    private void loadSubscribersChart() {
        Map<String, Long> data = userService.countSubscribersByWeekDays();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("New Subscribers");

        data.forEach((day, count) ->
                series.getData().add(new XYChart.Data<>(day, count))
        );

        subscribersLineChart.getData().add(series);
    }
}