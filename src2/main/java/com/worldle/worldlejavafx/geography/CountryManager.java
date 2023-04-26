package com.worldle.worldlejavafx.geography;

import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import static com.worldle.worldlejavafx.utils.Utils.*;

// final class CountryManager
// No constructor, not meant to be instantiated
// Utility class that provides path constants and other useful methods
public final class CountryManager {

    // path constants
    private static final String rootPath = "src/main/resources/com/worldle/worldlejavafx/";
    private static final String countriesImagesPath = Paths.get(rootPath + "all/").toAbsolutePath().toString();
    private static final String filePath = Paths.get(rootPath + "countrycodes.json").toAbsolutePath().toString();
    private static final String directionsPath = Paths.get(rootPath + "directions/").toAbsolutePath().toString();
    private static final String squaresPath = Paths.get(rootPath + "squares/").toAbsolutePath().toString();
    private static final String iconsPath = Paths.get(rootPath + "iconbuttons/").toAbsolutePath().toString();

    // arrays
    private static final List<JSONObject> countriesJSON = new ArrayList<>();
    private static final List<String> countryNames = new ArrayList<>();

    // static void init()
    // No parameters
    // returns nothing
    // reads the countrycodes.json file with the data of each country
    // and adds the data of each country to the countriesJSON and countryNames array
    // only meant to be called ONCE at the start of the main method
    public static void init() {
        Path p = Paths.get(filePath);
        try {
            final String contents = new String(Files.readAllBytes(p));
            final JSONArray countriesMedium = new JSONArray(contents);

            countriesMedium.forEach(c -> {
                JSONObject data = (JSONObject) c;
                countriesJSON.add(data);
                countryNames.add(data.getString("name"));
            });
        } catch (IOException e) {
            print(e);
            print("Error trying to get countries...");
            System.exit(0);
        }
    }

    // static Country getRandomCountry()
    // No parameters
    // Returns Country: the country for the user to guess
    // picks a random country from the countryNames array and
    // returns a new Country object
    public static Country getRandomCountry() {
        final int min = 0, max = countryNames.size()-1;
        String code = countryNames.get(randInt(min, max));
        return new Country(code, false);
    }

    public static List<JSONObject> getCountries() {
        return countriesJSON;
    }

    public static List<String> getCountryNames() {
        return countryNames;
    }

    public static String getDirectionsPath() {
        return directionsPath;
    }

    public static String getSquaresPath() {
        return squaresPath;
    }

    public static String getIconsPath() {
        return iconsPath;
    }

    public static String getCountriesImagesPath() {
        return countriesImagesPath;
    }
}
