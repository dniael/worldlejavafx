package com.worldle.worldlejavafx.components;

import com.worldle.worldlejavafx.Game;
import com.worldle.worldlejavafx.geography.CountryManager;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import java.nio.file.Paths;

// class HelpButton
// no Constructor parameters
// extends: JavaFX ImageView class to show icon on screen
// Custom method: displayHelpMenu() shows the help menu
public class HelpButton extends ImageView {

    // static variables
    private static final int SIZE = 30;
    private static final String HELP_MENU_PATH = Paths.get("src/main/resources/com/worldle/worldlejavafx/helpmenu.png")
                                                        .toAbsolutePath()
                                                        .toString();

    // Constructor
    public HelpButton() {
        // calls parent class constructor and creates a new image/icon
        super(new Image(CountryManager.getIconsPath() + "\\help.png"));

        // styling
        this.setFitHeight(SIZE);
        this.setFitWidth(SIZE);
        this.minHeight(SIZE);
        this.minWidth(SIZE);
        this.setCursor(Cursor.HAND);
        this.setTranslateX(-160);
        // custom onClick handler shows help menu
        this.setOnMouseClicked(e -> { if (!GameSetup.isButtonsDisabled()) displayHelpMenu(); });
    }

    // void displayHelpMenu()
    // No parameters
    // returns nothing
    // shows help menu
    private void displayHelpMenu() {
        // create new image (i took screenshot of the help menu of the original game)
        Image img = new Image(HELP_MENU_PATH);

        // styling
        ImageView view = new ImageView(img);
        view.setFitHeight(img.getHeight());
        view.setFitWidth(img.getWidth());
        view.minHeight(img.getHeight());
        view.minWidth(img.getWidth());
        StackPane.setMargin(view, new Insets(-6,0,0,0));
        // add image as well as a new Closebutton that will close the image and itself when pressed
        Game.getRoot().getChildren().addAll(view, new CloseButton(view));
    }

}
