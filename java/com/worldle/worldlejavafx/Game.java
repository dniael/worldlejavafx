package com.worldle.worldlejavafx;

import com.worldle.worldlejavafx.components.GameSetup;
import com.worldle.worldlejavafx.data.Guess;
import com.worldle.worldlejavafx.geography.Country;
import com.worldle.worldlejavafx.geography.CountryManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

import static com.worldle.worldlejavafx.utils.Utils.print;

// class Game
// main class from where the program is run
// extends: JavaFX Application class to use launch() and start() method -> opens game window
// No constructor
public class Game extends Application {

    private static Country currentCountry;
    private static StackPane root;

    // start method
    @Override
    public void start(Stage defaultStage) throws IOException {
        // static variables
        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("main.fxml"));
        defaultStage.setTitle("Worldle");

        root = GameSetup.createRoot(fxmlLoader);

        Scene scene = new Scene(root, 600, 650);

        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        defaultStage.setScene(scene);
        defaultStage.setResizable(false);
        defaultStage.show();
    }

    public static Country getCurrentCountry() {
        return currentCountry;
    }

    public static StackPane getRoot() {
        return root;
    }

    public static void main(String[] args) {
        CountryManager.init();
        currentCountry = CountryManager.getRandomCountry();
        launch();
    }
}