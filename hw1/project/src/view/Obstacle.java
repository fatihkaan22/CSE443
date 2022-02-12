package view;

import common.Util;
import view.GameObject;

import java.awt.image.BufferedImage;

public class Obstacle extends GameObject {
  private double velocity; // m/s

  public Obstacle(String imageResourcePath, int x, int y, double size) {
    super(imageResourcePath,
            x,
            (int) (y - (Util.OBSTACLE_SIZE * size)) + 180,
            (int) (Util.OBSTACLE_SIZE * Util.GAME_AREA * size),
            (int) (Util.OBSTACLE_SIZE * size));
  }

  public void updateObstaclePosition(double d) {
    if (getX() <= -Util.OBSTACLE_SIZE) {
      setX(Util.getGameWidth() * 2);
    }
    setX((int) (getX() - velocity * d));
  }


  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }
}
