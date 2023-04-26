package com.worldle.worldlejavafx.components;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

// class CountryImage
// Constructor parameters: String s -> the URL of the country image
// extends: Image class in order to show country image and add custom features
// custom invert() method added
public class CountryImage extends Image {

    // Constructor creates country image by calling
    // the constructor of the parent Image class
    public CountryImage(String s) {
        super(s);
    }

    // WritableImage invert()
    // Parameters: None
    // Returns: An inverted version of itself (WritableImage)
    // edits the image so that the colour of each of its pixel is inverted
    // this was required because the country images that I downloaded were black and
    // I was using a dark background
    public WritableImage invert() {
        PixelReader reader = this.getPixelReader();
        final int width = (int) this.getWidth(), height = (int) this.getHeight();
        WritableImage newImage = new WritableImage(width, height);
        PixelWriter writer = newImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = reader.getColor(x, y);
                writer.setColor(x, y, color.invert());
            }
        }
        return newImage;
    }
}

