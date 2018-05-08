package com.mobilesmashers.HelpClasses;

import com.mobilesmashers.Board;

public class MainMenuDimensions {
    public static int logoHeight = 950;
    public static int logoWidth = 250;
    public static int logoBegX = Board.WIDTH / 2 - logoHeight / 2;
    public static int logoBegY = Board.HEIGHT - 270;

    public static int playHeight = 380;
    public static int playWidth = 150;
    public static int playBegX = Board.WIDTH / 2 - playHeight / 2;
    public static int playBegY = Board.HEIGHT / 2 - playWidth;

    public static int infoHeight = 380;
    public static int infoWidth = 150;
    public static int infoBegX = Board.WIDTH / 2 - infoHeight / 2;
    public static int infoBegY = Board.HEIGHT / 3 - infoWidth;

    public static int volumeLowerHeight = 120;
    public static int volumeLowerWidth = 120;
    public static int volumeLowerBegX = 4 * Board.WIDTH / 6;
    public static int volumeLowerBegY = Board.HEIGHT / 20;

    public static int volumeHigherHeight = 120;
    public static int volumeHigherWidth = 120;
    public static int volumeHigherBegX = 6 * Board.WIDTH / 7;
    public static int volumeHigherBegY = Board.HEIGHT / 20;
}
