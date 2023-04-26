package com.worldle.worldlejavafx.utils;

import java.util.Objects;

// input class to handle user input and turn it into various types for use in programs
public class Input {

    public final String value;
    Input(String inp) {
        this.value = inp;
    }

    public final int toInt() {
        return Integer.parseInt(this.value);
    }

    public final double toDouble() {
        return Double.parseDouble(this.value);
    }

    public final boolean toBool() {
        return Boolean.parseBoolean(this.value);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Input input1 = (Input) o;
        return Objects.equals(value, input1.value);
    }

    @Override
    public int hashCode() {
            return Objects.hash(value);
        }

}
