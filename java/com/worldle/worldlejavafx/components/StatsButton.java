package com.worldle.worldlejavafx.components;

import com.worldle.worldlejavafx.Game;
import com.worldle.worldlejavafx.data.StatsManager;
import com.worldle.worldlejavafx.geography.CountryManager;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.json.JSONObject;
import java.util.Arrays;

// class StatsButton
// no Constructor Parameters
// extends: JavaFX ImageView class to show icon on screen
// custom methods: displayStatsMenu() to show user stats and createGuessDistributionChart() to create a bar
// chart of the user's guess distributions
public class StatsButton extends ImageView {

    // static variables
    private static final int SIZE = 30;
    private static final String STYLE = "-fx-font-weight: bold; -fx-font-size: 15px;";

    // Constructor
    public StatsButton() {
        // call constructor of parent class to be able to show image on screen
        super(new Image(CountryManager.getIconsPath() + "\\stats.png"));
        // styling
        this.setFitHeight(SIZE);
        this.setFitWidth(SIZE);
        this.minHeight(SIZE);
        this.minWidth(SIZE);
        this.setCursor(Cursor.HAND);
        this.setTranslateX(160);
        // custom onClick handler -> shows stats menu if animation not playing
        this.setOnMouseClicked(e -> { if (!GameSetup.isButtonsDisabled()) displayStatsMenu(); });
    }

    // void displayStatsMenu()
    // No parameters
    // returns nothing
    // shows the user's stats
    public void displayStatsMenu() {
        final JSONObject stats = StatsManager.getStats();
        final JSONObject guessDistros = stats.getJSONObject("guess distributions");

        // creating rectangle that covers entire screen to hide game
        Rectangle bg = new Rectangle(600, 650);
        bg.setFill(Color.rgb(15, 23, 42));
        bg.toFront();

        // guess distribution barchart
        BarChart<Number, String> bc = createGuessDistributions(guessDistros);
        Label title = new Label("STATISTICS");
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 25px;");

        // title styling
        Line line = new Line();
        line.setStartX(0);
        line.setEndX(350);
        line.setStrokeWidth(2);
        line.setStroke(Color.WHITE);
        StackPane.setMargin(line, new Insets(35, 0,0,0));

        // textstats creating and styling
        Label textStats = new Label("Games played: " + stats.getInt("games played") + "\n\n"
                + "Average first guess distance: " + stats.getInt("average first distance") + "km" + "\n\n"
                + "Current Winstreak: " + stats.getInt("current winstreak") + "\n\n"
                + "Games won: " + stats.getInt("games won") + "\n\n"
                + "Highest Winstreak: " + stats.getInt("highest winstreak") + "\n\n"
                + "Win rate: " + (int) ((double) stats.getInt("games won") / stats.getInt("games played") * 100) + "%"
        );
        textStats.setStyle(STYLE);
        StackPane.setMargin(textStats, new Insets(55,0,0,0));


        // creating closeButton that closes itself and all stat components when pressed
        CloseButton close = new CloseButton(bg, bc, title, line, textStats);
        // add all components to screen
        Game.getRoot().getChildren().addAll(bg, bc, title, line, textStats, close);
    }

    // BarChart<Number, String> createGuessDistributions()
    // Parameters: JSONObject guessDistributions -> data to be displayed on barchart
    // Returns: the barchart to be displayed on the stats menu
    // Creates a barchart with the Y Axis being the rows of the game (1-6)
    // and the X Axis being the amount of correct guesses the user has on each row
    private BarChart<Number, String> createGuessDistributions(JSONObject guessDistributions) {

        // create Y Axis
        CategoryAxis yAxis = new CategoryAxis();
        // styling
        yAxis.setCategories(FXCollections.observableArrayList(Arrays.asList("6", "5", "4", "3", "2", "1")));
        yAxis.setAnimated(true);
        yAxis.setTickMarkVisible(false);
        yAxis.setTickLabelFont(Font.font("", FontWeight.BOLD, 20));

        // create X Axis
        NumberAxis xAxis = new NumberAxis();
        // rotate 90 degrees so bars are horizontal instead of vertical
        // styling
        xAxis.setTickLabelRotation(90);
        xAxis.setTickMarkVisible(false);
        xAxis.setAnimated(true);
        xAxis.setTickLabelFont(Font.font("", FontWeight.BOLD, 10));

        // create barchart object
        BarChart<Number, String> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Guess Distribution:");

        // create barchart data object
        XYChart.Series<Number, String> data = new XYChart.Series<>();
        data.setName("Guesses Correct");

        // set data
        guessDistributions.toMap().forEach((row, correct) -> {
            data.getData().add(new XYChart.Data<>((Number) correct, row));
        });

        barChart.getData().add(data);

        // styling barchart
        barChart.setAnimated(true);
        barChart.setLegendVisible(false);
        barChart.setHorizontalGridLinesVisible(false);
        barChart.setVerticalGridLinesVisible(false);
        barChart.setHorizontalZeroLineVisible(false);
        barChart.setVerticalZeroLineVisible(false);
        barChart.setMaxWidth(350);
        StackPane.setMargin(barChart, new Insets(300, 0, 0, 0));

        return barChart;
    }

}
