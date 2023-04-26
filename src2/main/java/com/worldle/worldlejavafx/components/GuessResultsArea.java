package com.worldle.worldlejavafx.components;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

// class GuessResultsArea
// No constructor parameters
// Does not extend anything
// Class to hold all 6 GuessResults together
public class GuessResultsArea {

    // GuessResults array
    private final GuessResult[] results;

    // Constructor
    public GuessResultsArea() {
        // adding rows
        this.results = new GuessResult[6];
        for (int i = 0; i < this.results.length; i++) {
            GuessResult r = new GuessResult(350, 30, i+1);
            r.setId("inactive-" + (i+1));
            StackPane.setMargin(r, new Insets(220 + 35 * i, 0, 0, 0));
            results[i] = r;
        }
    }

    // GuessResult[] getAll()
    // No parameters
    // Returns: this.results -> all 6 GuessResults  as an array
    // Called in the GuessButton custom click handler to set the data
    // of the current row
    public GuessResult[] getAll() {
        return this.results;
    }

}
