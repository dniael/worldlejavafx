package com.worldle.worldlejavafx.data;

import com.worldle.worldlejavafx.geography.Country;
import com.worldle.worldlejavafx.geography.CountryManager;
import com.worldle.worldlejavafx.geography.Direction;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

// class Guess
// Constructor parameters: Country guess -> country that the user guessed,
// Country correct -> correct country, int row -> the row of the guess
// extends nothing
// Represents the data of a user's guess
public class Guess {

    // static variables
    private static final int MAX_SQUARES = 5, ICON_SIZE = 18;

    // instance variables
    private final int distance, row;
    private final double proximity;
    private final Direction direction;
    private final String name;

    // Constructor and setting instance variables
    public Guess(Country guess, Country correct, int row) {
        this.distance = guess.distanceFrom(correct);
        this.direction = guess.bearingTo(correct);
        this.proximity = guess.proximityPercentage(correct);
        this.row = row;
        this.name = guess.name;
    }

    // List<ImageView> getSquares()
    // No parameters
    // Returns the squares (pseudo progress bar) that corresponds with the proximity of the user's guess
    // to the correct country
    // Creates an array of green, yellow and/or white squares that represents a pseudo
    // progress bar
    public List<ImageView> getSquares() {
        final List<ImageView> squares = new ArrayList<>(MAX_SQUARES);
        final String path = CountryManager.getSquaresPath();

        // calc # of green squares, yellow squares and white squares
        final int greenSquares = (int) (this.proximity / 20);
        final int yellowSquares = this.proximity - greenSquares * 20 >= 10 ? 1 : 0;
        final int leftOver = MAX_SQUARES - (greenSquares + yellowSquares);

        // add square icons to array
        for (int i = 0; i < greenSquares; i++)
            squares.add(createIcon(path + "\\green.png", false));

        for (int i = 0; i < yellowSquares; i++)
            squares.add(createIcon(path + "\\yellow.png", false));

        for (int i = 0; i < leftOver; i++)
            squares.add(createIcon(path + "\\white.png", false));

        return squares;
    }

    // ImageView getDirection()
    // No parameters
    // Returns the icon of the direction that the correct country is from the user's guess
    // If the user guesses the correct country, return party popper icon instead
    // Calls the createIcon() method to create the icon
    public ImageView getDirection() {
        if (this.distance == 0) {
            return createIcon(CountryManager.getDirectionsPath() + "\\WIN.png", true);
        }
        return createIcon(this.direction.getImgPath(), true);
    }
    // int getDistance()
    // No parameters
    // Returns instance distance variable
    public int getDistance() {
        return this.distance;
    }

    // int getName()
    // No parameters
    // Returns the name of the user's guess
    public String getName() {
        return this.name;
    }

    // int getProximity()
    // No parameters
    // returns the proximity of the user's guess to the correct country
    public int getProximity() {
        return (int) this.proximity;
    }

    private ImageView createIcon(String s, boolean isDir) {
        Image img = new Image(s);
        ImageView view = new ImageView(img);
        view.setFitHeight(ICON_SIZE);
        view.setFitWidth(ICON_SIZE);
        view.minWidth(ICON_SIZE);
        view.minHeight(ICON_SIZE);
        StackPane.setMargin(view, new Insets(227 + this.row * 35, 0,0,0));
        if (!isDir) view.setId("square-" + this.row);
        return view;
    }
}
