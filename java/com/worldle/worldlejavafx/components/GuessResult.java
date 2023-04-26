package com.worldle.worldlejavafx.components;

import com.worldle.worldlejavafx.Game;
import com.worldle.worldlejavafx.data.Guess;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.List;

import static com.worldle.worldlejavafx.utils.Utils.str;

// class GuessResult
// Constructor parameters: int -> length, int w -> width, int row -> the row for results to be added in
// extends: JavaFX Rectangle class for default features
// custom setResult() and finalDisplay() methods
public class GuessResult extends Rectangle {

    // static variables
    // locations for the squares to be displayed
    private static final int[] locations = {-145, -90, -35, 20, 75};
    private static final int SQUARE_CONTAINER_WIDTH = 294;

    private final int row;

    // Constructor -> calls the parent class constructor and creates a new Rectangle object
    public GuessResult(int l, int w, int row) {
        super(l, w);
        this.setFill(Color.rgb(70,84,104));
        this.setArcHeight(10);
        this.setArcWidth(10);
        this.row = row;
    }

    // void setResult()
    // Parameters: Guess data -> the data to be displayed on this row
    // returns nothing
    // Displays the user's guess data in a more aesthetically inclined way
    public void setResult(Guess data) {
        List<ImageView> squares = data.getSquares();
        // hide squares initially
        squares.forEach(s -> s.setOpacity(0));
        // styling
        this.setFill(Color.rgb(15, 23, 42));
        this.setStrokeWidth(2);
        this.setWidth(SQUARE_CONTAINER_WIDTH);
        this.setTranslateX(-27.5);
        this.setStroke(Color.WHITE);

        // proximity percent
        Rectangle percentageBox = new Rectangle(50, 30);
        Label percent = new Label(str(data.getProximity()) + "%");
        percent.setTranslateX(149);
        percentageBox.setFill(Color.rgb(15, 23, 42));
        percentageBox.setStrokeWidth(2);
        percentageBox.setStroke(Color.WHITE);
        percentageBox.setTranslateX(149);
        percentageBox.setArcWidth(10);
        percentageBox.setArcHeight(10);
        StackPane.setMargin(percentageBox, new Insets(this.getLayoutY()-this.getHeight()/2 - 5.5,0,0,0));
        StackPane.setMargin(percent, new Insets(this.getLayoutY()-this.getHeight()/2 + 3.5,0,0,0));
        Game.getRoot().getChildren().addAll(percentageBox, percent);

        // animation to gradually show squares
        Timeline time = new Timeline();
        Duration startTime = Duration.ZERO;
        Duration endTime = Duration.seconds(2);


        Duration offset = Duration.millis(400);

        // set location of squares and add them to animation
        for (int i = 0; i < squares.size(); i++) {
            ImageView square = squares.get(i);
            square.setTranslateX(locations[i]+5);

            KeyValue startValue = new KeyValue(square.opacityProperty(), 0);
            KeyValue endValue = new KeyValue(square.opacityProperty(), 1);
            KeyFrame start = new KeyFrame(startTime, startValue);
            KeyFrame end = new KeyFrame(endTime, endValue);

            time.getKeyFrames().add(start);
            time.getKeyFrames().add(end);
            Game.getRoot().getChildren().add(square);

            startTime = startTime.add(offset);
            endTime = endTime.add(offset);
        }
        // disable HelpButton and StatsButton for duration of animation
        GameSetup.setButtonsDisabled(true);
        // set final display after animation finishes
        time.setOnFinished(e -> finalDisplay(data, squares));

        // play animation
        time.play();

    }

    // void finalDisplay()
    // Parameters: Guess data -> to be displayed, List<ImageView> squares -> to be hidden again
    private void finalDisplay(Guess data, List<ImageView> squares) {
        // hide squares after animation
        squares.forEach(s -> s.setVisible(false));
        // hide original rectangle
        this.setVisible(false);

        // name of the country that user guessed
        // if name is too long, cut off
        Label name = new Label(data.getName().toUpperCase());
        if (name.getText().length() > 22) {
            name = new Label(data.getName().toUpperCase().substring(0, 19) + "...");
        }
        // box for the name and styling
        Rectangle nameContainer = new Rectangle(150, 30);
        nameContainer.setStrokeWidth(2);
        nameContainer.setStroke(Color.WHITE);
        nameContainer.setFill(Color.rgb(15, 23, 42));
        nameContainer.setArcHeight(10);
        nameContainer.setArcWidth(10);

        // box for distance and styling
        Rectangle distanceContainer = new Rectangle(80, 30);
        Label distance = new Label(String.format("%,dkm", data.getDistance()));
        distanceContainer.setStrokeWidth(2);
        distanceContainer.setStroke(Color.WHITE);
        distanceContainer.setFill(Color.rgb(15, 23, 42));
        distanceContainer.setArcHeight(10);
        distanceContainer.setArcWidth(10);

        nameContainer.setTranslateX(-350 + (350 - nameContainer.getWidth()) + 51);
        name.setTranslateX(-350 + (350 - nameContainer.getWidth()) + 51);

        distanceContainer.setTranslateX(-350 + (350 - distanceContainer.getWidth()) + 101);
        distance.setTranslateX(-350 + (350 - distanceContainer.getWidth()) + 101);

        // box for direction and styling
        Rectangle dirContainer = new Rectangle(53, 30);
        ImageView direction = data.getDirection();
        dirContainer.setStrokeWidth(2);
        dirContainer.setStroke(Color.WHITE);
        dirContainer.setFill(Color.rgb(15, 23, 42));
        dirContainer.setArcHeight(10);
        dirContainer.setArcWidth(10);

        dirContainer.setTranslateX(-350 + (350 - dirContainer.getWidth()) + 145.5);
        direction.setTranslateX(-350 + (350 - distance.getWidth()) + 93);

        // setting margins
        StackPane.setMargin(nameContainer, new Insets(this.getLayoutY()-this.getHeight()/2 - 6, 0,0,0));
        StackPane.setMargin(name, new Insets(this.getLayoutY()-this.getHeight()/2 + 1.5, 0,0,0));

        StackPane.setMargin(distanceContainer, new Insets(this.getLayoutY()-this.getHeight()/2 - 6, 0,0,0));
        StackPane.setMargin(distance, new Insets(this.getLayoutY()-this.getHeight()/2 + 1.5, 0,0,0));

        StackPane.setMargin(dirContainer, new Insets(this.getLayoutY()-this.getHeight()/2 - 6, 0,0,0));
        StackPane.setMargin(direction, new Insets(this.getLayoutY()-this.getHeight()/2 + 1.5, 0,0,0));

        // add data
        Game.getRoot().getChildren().addAll(nameContainer, name, distanceContainer, distance, dirContainer, direction);
        // enable buttons again because animations have finished
        GameSetup.setButtonsDisabled(false);

    }

}
