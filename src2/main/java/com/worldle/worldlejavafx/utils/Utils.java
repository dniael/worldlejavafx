package com.worldle.worldlejavafx.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// util class I made with static methods that can be
// imported into other files because I got tired of typing System.out.println and Scanner sc all the time
public final class Utils {

    private final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void print(Object... stuff) {
        List<String> strArgs = Arrays.stream(stuff).map(String::valueOf).toList();
        String str = String.join(" ", strArgs);
        System.out.println(str);
    }

    public static void printf(String str, Object... args) {
        System.out.printf(str + "\n", args);
    }

    public static Input input(String prompt) throws IOException {
        System.out.print(prompt);
        return new Input(br.readLine().split(" ")[0]);
    }

    public static String inputln(String prompt) throws IOException {
        System.out.print(prompt);
        return br.readLine();
    }

    public static int randInt(int min, int max) {
        if (min > max) throw new IllegalArgumentException("Minimum number cannot be larger than maximum");
        return (min + (int)(Math.random() * (max - min) + 1));
    }

    public static <T> String str(T obj) {
        return String.valueOf(obj);
    }




    public static int _int(String str) {
        return Integer.parseInt(str);
    }

    public static double _double(String str) {
        return Double.parseDouble(str);
    }

    public static boolean _bool(String str) {
        return Boolean.parseBoolean(str);
    }

    // helper method with a low and high parameter to
    // return if a number is within the range of the low and high parameters
    public static boolean range(int low, int high, int num) {
        return (num > low && num <= high);
    }
}
