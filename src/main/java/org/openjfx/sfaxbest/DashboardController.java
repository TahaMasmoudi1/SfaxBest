package org.openjfx.sfaxbest;

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

    // ═══════════════════════════════════════════════════════
    //  FXML INJECTIONS
    // ═══════════════════════════════════════════════════════

    @FXML private PieChart categoryPieChart;
    @FXML private BarChart<String, Number> topMoviesBarChart;
    @FXML private LineChart<String, Number> subscribersLineChart;

    // Stat card labels — add fx:id to these in your FXML if you want live counts
    @FXML private Label statTotalFilms;
    @FXML private Label statSeries;
    @FXML private Label statSubscribers;
    @FXML private Label statComments;


    // ═══════════════════════════════════════════════════════
    //  NAVIGATION
    // ═══════════════════════════════════════════════════════

    @FXML public void goToSeries()      throws IOException { App.setRoot("seriesAdmin"); }
    @FXML public void goToDocumantary() throws IOException { App.setRoot("documantaryAdmin"); }
    @FXML public void goToFilms()       throws IOException { App.setRoot("mainAdmin"); }
    @FXML public void goToComments()    throws IOException { App.setRoot("commentAdmin"); }


    // ═══════════════════════════════════════════════════════
    //  INITIALIZE
    // ═══════════════════════════════════════════════════════

    @FXML
    public void initialize() {
        loadStatCards();
        loadCategoryChart();
        loadTopMoviesChart();
        loadSubscribersChart();
    }


    // ═══════════════════════════════════════════════════════
    //  STAT CARDS
    //
    //  What your service must return:
    //    • int  — total count of films
    //    • int  — total count of series
    //    • int  — total count of users / subscribers
    //    • int  — total count of comments
    //
    //  Example service call:
    //    FilmService.getTotalCount()        → int
    //    SeriesService.getTotalCount()      → int
    //    UserService.getTotalCount()        → int
    //    CommentService.getTotalCount()     → int
    // ═══════════════════════════════════════════════════════

    private void loadStatCards() {
        // Replace these with your real service calls
        // e.g.  int films = FilmService.getTotalCount();
        int films       = 0;  // FilmService.getTotalCount()
        int series      = 0;  // SeriesService.getTotalCount()
        int subscribers = 0;  // UserService.getTotalCount()
        int comments    = 0;  // CommentService.getTotalCount()

        if (statTotalFilms   != null) statTotalFilms.setText(String.valueOf(films));
        if (statSeries       != null) statSeries.setText(String.valueOf(series));
        if (statSubscribers  != null) statSubscribers.setText(String.valueOf(subscribers));
        if (statComments     != null) statComments.setText(String.valueOf(comments));
    }


    // ═══════════════════════════════════════════════════════
    //  PIE CHART — Content by category
    //
    //  What your service must return:
    //    Map<String, Integer>
    //      key   = category name  (e.g. "Horror", "Comedy")
    //      value = number of titles in that category
    //
    //  Example service call:
    //    FilmService.getCountByCategory()  → Map<String, Integer>
    //
    //  Example map:
    //    { "Horror" -> 36, "Comedy" -> 14, "Drama" -> 80 }
    // ═══════════════════════════════════════════════════════

    private void loadCategoryChart() {
        // Replace with: Map<String, Integer> data = FilmService.getCountByCategory();
        Map<String, Integer> data = Map.of(
                "Horror",  36,
                "Comedy",  14,
                "Drama",   80
        );

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        data.forEach((category, count) ->
                pieData.add(new PieChart.Data(category, count))
        );

        categoryPieChart.setData(pieData);
    }


    // ═══════════════════════════════════════════════════════
    //  BAR CHART — Top films by views
    //
    //  What your service must return:
    //    List<Object[2]>  where each entry is:
    //      [0] = String  — film title
    //      [1] = int     — view count
    //
    //  OR more cleanly, a List of a small DTO:
    //    List<FilmViewStat>  where FilmViewStat has:
    //      String title
    //      int    views
    //
    //  Example service call:
    //    FilmService.getTopByViews(5)  → List<FilmViewStat>
    //
    //  Example list:
    //    [ ("Batman", 2000), ("Matrix", 1800), ("Avatar", 1700) ]
    // ═══════════════════════════════════════════════════════

    private void loadTopMoviesChart() {
        // Replace with: List<FilmViewStat> stats = FilmService.getTopByViews(5);
        List<Object[]> stats = List.of(
                new Object[]{"Batman",    2000},
                new Object[]{"Matrix",    1800},
                new Object[]{"Avatar",    1700},
                new Object[]{"Inception", 1600},
                new Object[]{"Titanic",   1500}
        );

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Views");

        for (Object[] entry : stats) {
            String title = (String)  entry[0];
            int    views = (Integer) entry[1];
            series.getData().add(new XYChart.Data<>(title, views));
        }

        topMoviesBarChart.getData().add(series);
    }


    // ═══════════════════════════════════════════════════════
    //  LINE CHART — New subscribers over time
    //
    //  What your service must return:
    //    Map<String, Integer>  — ordered (use LinkedHashMap!)
    //      key   = time label  (e.g. "Mon", "Jan", "2024-01")
    //      value = number of new subscribers in that period
    //
    //  Example service call:
    //    UserService.getNewSubscribersPerDay(7)  → LinkedHashMap<String, Integer>
    //
    //  Example map (must preserve insertion order → LinkedHashMap):
    //    { "Mon" -> 20, "Tue" -> 35, "Wed" -> 15, "Thu" -> 40, "Fri" -> 28 }
    // ═══════════════════════════════════════════════════════

    private void loadSubscribersChart() {
        // Replace with: Map<String, Integer> data = UserService.getNewSubscribersPerDay(7);
        // IMPORTANT: use LinkedHashMap to keep day order
        Map<String, Integer> data = new java.util.LinkedHashMap<>();
        data.put("Mon", 20);
        data.put("Tue", 35);
        data.put("Wed", 15);
        data.put("Thu", 40);
        data.put("Fri", 28);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("New Subscribers");

        data.forEach((day, count) ->
                series.getData().add(new XYChart.Data<>(day, count))
        );

        subscribersLineChart.getData().add(series);
    }
}