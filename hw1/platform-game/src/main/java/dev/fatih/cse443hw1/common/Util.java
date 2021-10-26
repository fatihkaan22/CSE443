package dev.fatih.cse443hw1.common;

import java.util.ArrayList;

public class Util {
  public static final double WIDTH = 1920 * 0.9;
  public static final double HEIGHT = 1080 * 0.9;
  public static final double GROUND = 600.0;
  public static final double OBSTACLE_SIZE = 70;
  public static final double CHARACTER_HORIZONTAL_POSITION = 80.0;
  public static final double GRAVITATIONAL_ACCELERATION = 9.8; // m/s^2
  public static final String BACKGROUND_IMAGE = "planet.png";
  public static final String CHARACTER_FILENAME = "kangaroo.png";
  public static final double HORIZONTAL_VELOCITY = 4.0; // m/s
  public static final double GAME_AREA = 0.75; // < 1
  public static final int CHARACTER_LEVEL = 3; // level of the character among the parallax backgrounds
  public static final String BACKGROUNDS_PATH = "forest/";
  public static final String BACKGROUNDS[] = { // multiple backgrounds with different velocities for parallax effect
          "01_Mist.png",
          "02_Bushes.png",
          "03_Particles.png",
          "04_Forest.png",
          "05_Particles.png",
          "06_Forest.png",
          "07_Forest.png",
          "08_Forest.png",
          "09_Forest.png",
          "10_Sky.png"
  };

  public static double getGameWidth() {
    return WIDTH * GAME_AREA;
  }

  public static double getGameHeight() {
    return HEIGHT * GAME_AREA;
  }

}
