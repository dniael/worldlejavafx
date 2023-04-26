package com.worldle.worldlejavafx.components;

import com.worldle.worldlejavafx.Game;
import com.worldle.worldlejavafx.data.Guess;
import com.worldle.worldlejavafx.geography.Country;
import com.worldle.worldlejavafx.geography.CountryManager;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// class GuessButton
// Constructor Parameters: int l -> length, int w -> width
// extends: the JavaFX Rectangle class
// custom label and onClick event handler
public class GuessButton extends Rectangle {

    // button text
    private final Label text;

    // Constructor
    public GuessButton(int l, int w) {
        super(l, w);

        // styling
        this.setCursor(Cursor.HAND);
        this.setFill(Color.rgb(15, 23, 42));
        this.setStroke(Color.WHITE);
        this.setStrokeWidth(2);
        this.setArcHeight(10);
        this.setArcWidth(10);

        text = new Label("GUESS");
        text.setStyle("-fx-font-weight: bold; fx-font-size: 20px");
        text.setCursor(Cursor.HAND);
        StackPane.setMargin(this, new Insets(470, 0, 0, 0));
        StackPane.setMargin(text, new Insets(477, 0, 0, 0));
        // binding onClick event to custom method
        this.setOnMouseClicked(e -> handleClick());
        text.setOnMouseClicked(e -> handleClick());
    }

    // void handleClick()
    // No parameters
    // returns nothing
    // Manages what happens when the user clicks this button (guess)
    private void handleClick() {
        boolean won;
        AutoCompleteInput input = GameSetup.getInput();
        String guess = input.getValue();
        // if invalid input, show error and return
        if (!CountryManager.getCountryNames().contains(guess)) {
            GameSetup.showErrorMsg("Not in Country list!", true);
            return;
        }
        // clear error if otherwise
        GameSetup.showErrorMsg("", false);
        // creating new country object from the user's guess
        Country guessCountry = new Country(guess,false);
        Country correctCountry = Game.getCurrentCountry();
        // if the user guesses the correct country, won = true
        won = guessCountry.distanceFrom(correctCountry) == 0;

        // reset input text
        input.setValue("");
        input.getEditor().setText("");
        input.setPromptText("Country, Territory...");

        // for loop top find which row user is currently on
        int row = 0;
        for (GuessResult r: GameSetup.getGuesses().getAll()) {
            if (r.getId().contains("inactive")) {
                // create new guess object
                Guess guessData = new Guess(guessCountry, correctCountry, row);
                GameSetup.addGuess(guessData);
                r.setResult(guessData);
                r.setId("active");
                break;
            }
            row++;
        }
        // game over conditions
        if (row == 5 || won) {
            // set game over and disable
            GameSetup.gameOver(won, row + 1);
            this.setOnMouseClicked(null);
            text.setOnMouseClicked(null);
        }

    };

    // Label getText()
    // No parameters
    // Returns text -> Label object to be added to the root
    // Used to customize the position of the button label
    public Label getText() {
        return text;
    }

}
