package dev.fatih.cse443hw1;

import dev.fatih.cse443hw1.common.Util;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Background {
  private double width, height;
  private ImageView backgroundViews[];
  private double velocity; // m/s
  private double maxVelocity;

  public Background(String imageResourcePath, double width, double height) {
    this.width = width;
    this.height = height;
    this.maxVelocity = Util.HORIZONTAL_VELOCITY;
    this.backgroundViews = new ImageView[2];
    Image img = new Image(PlatformGame.class.getResource(imageResourcePath).toString(), width, height, false, false);
    backgroundViews[0] = new ImageView(img);
    backgroundViews[1] = new ImageView(img);
    backgroundViews[0].setLayoutX(0);
    backgroundViews[1].setLayoutX(width);
  }

  public void setMaxVelocity(double maxVelocity) {
    this.maxVelocity = maxVelocity;
  }

  public double getMaxVelocity() {
    return maxVelocity;
  }

  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }

  public ImageView[] getViews() {
    return backgroundViews;
  }

  public void updateBackgroundPosition() {
    backgroundViews[0].setLayoutX(backgroundViews[0].getLayoutX() - velocity);
    backgroundViews[1].setLayoutX(backgroundViews[1].getLayoutX() - velocity);
    if (backgroundViews[0].getLayoutX() <= -width)
      backgroundViews[0].setLayoutX(width);
    if (backgroundViews[1].getLayoutX() <= -width)
      backgroundViews[1].setLayoutX(width);
  }

}
