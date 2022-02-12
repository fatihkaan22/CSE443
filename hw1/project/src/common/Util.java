package common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Util {

  public static class Obstacle {
    private String filename;
    private double size;

    public Obstacle(String filename, double size) {
      this.filename = filename;
      this.size = size;
    }

    public String getFilename() {
      return filename;
    }

    public double getSize() {
      return size;
    }
  }

  public static final int WIDTH = 1920;
  public static final int HEIGHT = 1080;
  public static final int GROUND = 800;
  public static final int OBSTACLE_SIZE = 330;
  public static final int POWERUP_SIZE = 100;
  public static final int POWERUP_Y = GROUND - 250;
  public static final double GRAVITATIONAL_ACCELERATION = 9.8; // m/s^2
  public static final String BACKGROUND_FILENAME = "/forest.png";
  public static final String CHARACTER_FILENAME = "/kangaroo.png";
  public static final String CHARACTER_WITH_WINGS_FILENAME = "/dragon.png";
  public static final int CHARACTER_SIZE = 200;
  public static final int HORIZONTAL_VELOCITY = 4;
  public static final int VERTICAL_VELOCITY = 7;
  public static final double GAME_AREA = 0.75; // < 1
  public static final String POWERUP_PATH = "/power-up/";
  public static final HashMap<Character, String> POWER_UPS = new HashMap<>() {
    {
      put('a', "a.png");
      put('b', "b.png");
      put('c', "c.png");
      put('d', "d.png");
    }
  };
  public static final String OBSTACLE_PATH = "/obstacle/";
  public static final List<Obstacle> OBSTACLES = Collections.unmodifiableList(new ArrayList<>() {
    {
      add(new Obstacle("camel.png", 0.7));
      add(new Obstacle("chicken.png", 0.2));
      add(new Obstacle("dog.png", 0.4));
      add(new Obstacle("elephant.png", 0.9));
      add(new Obstacle("giraffe.png", 0.7));
      add(new Obstacle("penguin.png", 0.2));
      add(new Obstacle("pig.png", 0.5));
      add(new Obstacle("rabbit.png", 0.2));
      add(new Obstacle("rat.png", 0.2));
      add(new Obstacle("walrus.png", 0.3));
    }
  });

  public static int getGameWidth() {
//    return WIDTH * GAME_AREA;
    return WIDTH;
  }

  public static int getGameHeight() {
    return (int) (HEIGHT * GAME_AREA);
  }
}
