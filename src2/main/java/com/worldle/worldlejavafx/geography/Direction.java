package com.worldle.worldlejavafx.geography;

// enum Direction
// All directions and their respective icon paths
public enum Direction {

    N(CountryManager.getDirectionsPath() + "\\N.png"),
    NE(CountryManager.getDirectionsPath() + "\\NE.png"),
    E(CountryManager.getDirectionsPath() + "\\E.png"),
    SE(CountryManager.getDirectionsPath() + "\\SE.png"),
    S(CountryManager.getDirectionsPath() + "\\S.png"),
    SW(CountryManager.getDirectionsPath() + "\\SW.png"),
    W(CountryManager.getDirectionsPath() + "\\W.png"),
    NW(CountryManager.getDirectionsPath() + "\\NW.png"),
    ;

    final String imgPath;

    Direction(String s) {
        this.imgPath = s;
    }

    public String getImgPath() {
        return this.imgPath;
    }
}

//class Dir {
//
//}
