package com.worldle.worldlejavafx.components;

import com.worldle.worldlejavafx.Game;
import com.worldle.worldlejavafx.geography.CountryManager;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

// class CloseButton
// Constructor parameters: Nodes... toClose, the nodes to close when the button is pressed
// extends: JavaFX ImageView class in order to show icon on screen
// custom onClickEvent added
public class CloseButton extends ImageView {

    // size of icon
    private static final int SIZE = 25;

    // Constructor
    public CloseButton(Node... toClose) {
        // create new red X emoji image
        super(new Image(CountryManager.getIconsPath() + "\\close.png"));
        this.setFitHeight(SIZE);
        this.setFitWidth(SIZE);
        this.minHeight(SIZE);
        this.minWidth(SIZE);
        this.setCursor(Cursor.HAND);
        this.setTranslateX(160);
        StackPane.setMargin(this, new Insets(3, 0,0,0));
        // when clicked, remove itself and all nodes that the caller wants to close
        this.setOnMouseClicked(e -> {
            Game.getRoot().getChildren().remove(this);
            for (Node n: toClose) Game.getRoot().getChildren().remove(n);
        });
    }
}
