package com.worldle.worldlejavafx.data;

import org.json.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.worldle.worldlejavafx.utils.Utils.print;
import static com.worldle.worldlejavafx.utils.Utils.str;

// class StatsManager
// No constructor, not meant to be instantiated
// extends nothing
// Utility class that provides methods to get and set the user's stats
public class StatsManager {

    // stats file path variable
    private static final String PATH = "src/main/resources/com/worldle/worldlejavafx/stats.json";

    // static JSONObject getStats()
    // No parameters
    // Returns JSONObject -> the user's stats in JSONObject form
    // reads the stats.json file
    public static JSONObject getStats() {
        String contents;
        try {
            contents = new String(Files.readAllBytes(Paths.get(PATH).toAbsolutePath()));
        } catch (IOException e) {
            print("Error reading stats file! " + e);
            return new JSONObject();
        }
        return new JSONObject(contents);
    }

    // static void setStats()
    // Parameters: List<Guess> guesses -> the guesses of the game,
    // boolean won -> if the game was won,
    // row -> which row the game was won on
    // returns nothing
    // calls the processGuesses() method to update the user's stats with the parameters
    // and writes to the stats.json file
    public static void setStats(List<Guess> guesses, boolean won, int row) {
        final JSONObject stats = processGuesses(guesses, won, row);
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(Paths.get(PATH).toAbsolutePath().toString()));
            bw.write(stats.toString());
            bw.close();
        } catch (IOException e) {
            print("Error writing to stats file, your stats from this round won't be recorded! " + e);
        }
    }

    // static JSONObject processGuesses
    // Parameters: same as above
    // returns JSONObject -> the new user data
    // Parses, updates and returns the user's current stats
    private static JSONObject processGuesses(List<Guess> guesses, boolean won, int row) {
        final JSONObject currentData = getStats();
        final int firstDistance = guesses.get(0).getDistance();
        final int avgFirstDistance = currentData.getInt("average first distance");
        
        currentData.put("games played", currentData.getInt("games played") + 1);
        final JSONObject guessDistros = currentData.getJSONObject("guess distributions");

        // if won, add winstreak and games won
        if (won) {
            currentData.put("games won", currentData.getInt("games won") + 1);
            currentData.put("current winstreak", currentData.getInt("current winstreak") + 1);
            final int ws = currentData.getInt("current winstreak");
            // if current winstreak is bigger than highest winstreak, update highest winstreak
            currentData.put("highest winstreak", Math.max(ws, currentData.getInt("highest winstreak")));
            // update guess distributions
            guessDistros.put(str(row), guessDistros.getInt(str(row)) + 1);

        } else {
            // reset ws
            currentData.put("current winstreak", 0);
        }
        // calc avg distance
        currentData.put("average first distance", (avgFirstDistance+firstDistance)/currentData.getInt("games played"));

        currentData.put("guess distributions", guessDistros);

        return currentData;
    }


}
