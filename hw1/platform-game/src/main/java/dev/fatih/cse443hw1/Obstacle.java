package dev.fatih.cse443hw1;

import dev.fatih.cse443hw1.common.Util;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Obstacle {
  private ImageView imageView;
  private double velocity; // m/s
  private double maxVelocity; // m/s

  public Obstacle(String imageResourcePath, double x, double y) {
    Image img = new Image(PlatformGame.class.getResource(imageResourcePath).toString(), Util.OBSTACLE_SIZE, Util.OBSTACLE_SIZE, false, false);
    imageView = new ImageView(img);
    imageView.setX(x + Util.OBSTACLE_SIZE);
    imageView.setY(y + Util.OBSTACLE_SIZE * 2);
  }


  public ImageView getView() {
    return imageView;
  }

  public void updateObstaclePosition() {
    if (imageView.getX() <= -Util.OBSTACLE_SIZE) {
      imageView.setX(Util.getGameWidth());
    }
    imageView.setX(imageView.getX() - velocity);
  }

  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }
}
