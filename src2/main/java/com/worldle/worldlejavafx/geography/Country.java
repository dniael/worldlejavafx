package com.worldle.worldlejavafx.geography;

import org.json.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.worldle.worldlejavafx.utils.Utils.print;

// class Country
// Constructor parameters: String identifier -> the code/name of the country,
// boolean useCode -> is identifier the country code or name
// Represents a country, has methods such as:
// distanceFrom(Country otherCountry), bearingTo(Country otherCountry), proximityPercentage(Country otherCountry)
public class Country {

    // static constants
    private static final double EARTH_RADIUS_KM = 6370;
    private static final int MAX_DISTANCE_ON_EARTH_KM = 20_000;
    private static final int IMG_SIZE = 128;

    // instance constants
    public final String code, name, imagePath;
    public final double latitude, longitude;

    // Constructor
    public Country(String identifier, boolean useCode) {
        // get all countries data
        final List<JSONObject> countries = CountryManager.getCountries();

        // filter through data to find country that matches identifier
        final JSONObject data = countries.stream()
                .filter(c -> c.getString(useCode ? "country_code" : "name")
                        .equalsIgnoreCase(identifier))
                        .toList().get(0);

        // set instance constants using data
        this.code = data.getString("country_code").toUpperCase();
        this.name = data.getString("name");
        this.latitude = data.getJSONArray("latlng").getDouble(0);
        this.longitude = data.getJSONArray("latlng").getDouble(1);
        this.imagePath = CountryManager.getCountriesImagesPath() + "\\"
                + this.code.toLowerCase() + "\\" + IMG_SIZE  + ".png";
    }

    // int distanceFrom()
    // Parameters: Country otherCountry -> the country to calculate the distance of this country from
    // Returns: int -> the distance
    // Uses haversine formula (I just copied it) to calculate the distance between two points
    // on a sphere
    public int distanceFrom(Country otherCountry) {
        double lat1 = this.latitude, lat2 = otherCountry.latitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(otherCountry.longitude - this.longitude);

        // convert to radians
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // apply formulae
        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.pow(Math.sin(dLon / 2), 2) *
                        Math.cos(lat1) *
                        Math.cos(lat2);

        double c = 2 * Math.asin(Math.sqrt(a));
        return (int)(EARTH_RADIUS_KM * c);
    }

    // Direction bearingTo()
    // Parameter: Country destinationCountry -> the country to calculate this country's bearing to
    // Returns: Direction -> the direction of the destination country to this country
    // Uses another formula that I copied from one of my previous project to calculate the compass
    // bearing
    public Direction bearingTo(Country destinationCountry) {

        final List<Direction> allDirections = new ArrayList<>(Arrays.asList(Direction.values()));
        allDirections.add(Direction.N);

        final double radians = Math.atan2(
                (destinationCountry.longitude - this.longitude),
                (destinationCountry.latitude - this.latitude)
        );

        final double compassReading = radians * (180 / Math.PI);
        int coordIndex = (int) Math.round(compassReading / 45);
        if (compassReading < 0) coordIndex += 8;

        return allDirections.get(coordIndex);
    }

    // double proximityPercentage()
    // Parameters: Country otherCountry -> the country to calculate this country's proximity to
    // returns double: the proximity
    // calculates the proximity of this country to another country by calculating the distance
    // between them
    public double proximityPercentage(Country otherCountry) {
        int proximity = Math.max(MAX_DISTANCE_ON_EARTH_KM - distanceFrom(otherCountry), 0);
        return ((double) proximity / MAX_DISTANCE_ON_EARTH_KM * 100);
    }

}
