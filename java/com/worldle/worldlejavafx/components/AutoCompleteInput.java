package com.worldle.worldlejavafx.components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import java.util.List;

// class AutoCompleteInput
// Constructor parameters: List<String> options, creates the selection list
// extends: JavaFX ComboBox class in order to use the default selection menu
// custom autocomplete feature added
public class AutoCompleteInput extends ComboBox<String> {

    private final List<String> selectOptions;


    // Constructor
    public AutoCompleteInput(List<String> options) {
        super(FXCollections.observableList(options));
        selectOptions = options;
        this.setMinWidth(350);
        this.setPrefWidth(350);
        this.setVisible(true);
        this.setPromptText("Country, territory...");

        // custom autocomplete
        this.setOnKeyPressed(this::handle);
        StackPane.setMargin(this, new Insets(440, 0, 0, 0));
    }

    private void handle(KeyEvent e) {
        if (!this.isShowing() && e.getCode() == KeyCode.SHIFT) return;
        if (!this.isShowing() && e.getCode() == KeyCode.S) return;
        if (!this.isShowing() && e.getCode() == KeyCode.WINDOWS) return;

        this.show();

        TextField input = this.getEditor();
        ObservableList<String> updatedItems = FXCollections.observableArrayList();

        // if user types backspace, delete last letter from current input
        if (e.getCode() == KeyCode.DELETE || e.getCode() == KeyCode.BACK_SPACE) {
            String currentText = input.getText();
            // if the entire input is gone, reset
            if (currentText.length() == 0) {
                input.setText("");
                this.setValue("");
                this.setPromptText("Country, territory...");
                this.hide();
                return;
            }
            input.setText(currentText.substring(0, currentText.length()-1));
        }

        // update input
        input.setText(input.getText() + e.getText());
        // filter through selection options, find ones that match input
        selectOptions.forEach(c -> { if (c.toLowerCase().contains(input.getText())) updatedItems.add(c); } );

        String text = input.getText();

        // if none match, hide dropdown
        if (updatedItems.size() == 0) {
            this.hide();
        } else {
            this.setItems(updatedItems);
        }

        // update input value and set dropdown choices to the filtered items
        this.setItems(updatedItems);
        input.setText(text);
        this.setValue(text);
        this.setPromptText(text);

    }

}
