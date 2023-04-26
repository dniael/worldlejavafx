package com.worldle.worldlejavafx.components;

import com.worldle.worldlejavafx.Game;
import com.worldle.worldlejavafx.data.Guess;
import com.worldle.worldlejavafx.data.StatsManager;
import com.worldle.worldlejavafx.geography.CountryManager;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.worldle.worldlejavafx.utils.Utils.print;

// final class GameSetup
// Utility class to help with setting up and managing the game state
// No constructor, not meant to be instantiated as an object
// contains a variety of static methods and variables
public final class GameSetup {

    // static variables
    private static AutoCompleteInput input;
    private static Label errorMsg;
    private static GuessResultsArea area;
    private static final List<Guess> guesses = new ArrayList<>(6);
    private static boolean buttonsDisabled = false;

    // static ImageView createCountryImageView()
    // No parameters
    // Returns ImageView -> the image view of the country image to be shown on screen
    // creates a new CountryImage object, calls its invert method and returns an imageview
    private static ImageView createCountryImageView() {
        WritableImage outline = new CountryImage(Game.getCurrentCountry().imagePath).invert();
        ImageView view = new ImageView();
        view.setImage(outline);
        view.setFitWidth(outline.getWidth()+40);
        view.setFitHeight(outline.getHeight()+40);
        view.setX(300);
        view.setY(400);
        StackPane.setMargin(view, new Insets(40, 0, 0, 0));
        return view;
    }

    // static StackPane createRoot()
    // Parameter: FXMLLoader loader, the loader of the default FXML file
    // Returns: StackPane root, the root node of the game where everything is added to/removed from
    // Creates all the components and adds them to the root -> called ONLY once at the very start
    // of the game
    public static StackPane createRoot(FXMLLoader loader) throws IOException {

        ImageView view = createCountryImageView();

        input = new AutoCompleteInput(CountryManager.getCountryNames());
        GuessButton guessButton = new GuessButton(350, 30);
        area = new GuessResultsArea();
        Line line = new Line();
        line.setStartX(0);
        line.setEndX(350);
        line.setStrokeWidth(2);
        line.setStroke(Color.WHITE);
        StackPane.setMargin(line, new Insets(35, 0,0,0));

        // msg to be shown with custom text whenever user enters invalid country
        // or to notify that the game is over
        errorMsg = new Label();
        errorMsg.setTextFill(Color.RED);
        errorMsg.setStyle("-fx-font-weight: bold;");
        StackPane.setMargin(errorMsg, new Insets(510, 0, 0,0));

        StackPane root = new StackPane(
                loader.load(), new HelpButton(), new StatsButton(),
                line, view, input, guessButton, guessButton.getText(), errorMsg
        );
        root.getChildren().addAll(area.getAll());
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));
        root.minHeight(600);
        root.minWidth(500);

        return root;
    }

    // static AutoCompleteInput getInput()
    // No parameters
    // Returns: The static variable holding the instance of the AutoCompleteInput component
    // Used in the GuessButton class to get user input whenever they press the guess button
    public static AutoCompleteInput getInput() {
        return input;
    }

    // static GuessResultsArea getGuesses()
    // No parameters
    // Returns: The static variable holding the instance of the GuessResultsArea component
    // Used in the GuessButton class to find which row the user is currently on
    public static GuessResultsArea getGuesses() {
        return area;
    }

    // static void setButtonsDisabled()
    // No parameters
    // Returns nothing
    // Disables the HelpButton and StatButton during animations to prevent glitches on the screen
    public static void setButtonsDisabled(boolean b) {
        buttonsDisabled = b;
    }

    // static boolean isButtonDisabled()
    // No parameters
    // Returns boolean -> if the buttons are disabled or not
    // Called in the HelpButton and StatButton class to check if they are disabled
    public static boolean isButtonsDisabled() {
        return buttonsDisabled;
    }

    // static void showErrorMsg()
    // Parameters: String s -> the msg to be shown, boolean f -> play fade animation or not
    // Returns: nothing
    // Called whenever the user enters an invalid input or when the game is over
    public static void showErrorMsg(String s, boolean f) {
        FadeTransition fade = new FadeTransition(Duration.millis(3500));
        fade.setFromValue(1);
        fade.setToValue(0);
        // if fade, play the fade animation
        if (f) {
            fade.setNode(errorMsg);
            errorMsg.setText(s);
            fade.play();
        }
        errorMsg.setText(s);
    }

    // static void addGuess()
    // Parameter: Guess guess, the user guess to be added to the guesses ArrayList
    // Returns: nothing
    // Adds each user guess to the guesses ArrayList that will be used to update the user's stats
    public static void addGuess(Guess guess) {
        guesses.add(guess);
    }

    // static void gameOver()
    // Parameters: boolean won -> did the user win, int row -> on which guess they won
    // Returns: nothing
    // Ends the game, shows the user a message, updates their stats
    public static void gameOver(boolean won, int row) {
        errorMsg.setTextFill(won ? Color.GREEN : Color.RED);
        String grammar = row > 1 ? " guesses!" : " guess!";
        String msg = won ? "You won in " + row + grammar : "Game over! The country was: " + Game.getCurrentCountry().name;
        showErrorMsg(msg, false);
        StatsManager.setStats(guesses, won, row);
    }
}
