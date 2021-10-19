package dev.fatih.cse443hw1;

import dev.fatih.cse443hw1.common.Util;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Character {
  private String characterFilename;
  private ImageView imageView;
  private boolean jumping;
  long jumpStartTime;
  private double ground;

  // TODO: rename
  double jumpPower = 100;
  private double defaultVerticalVelocity = 5; // m/s

  public Character(String imageResourcePath, double x, double y) {
    this.characterFilename = characterFilename;
    Image img = new Image(PlatformGame.class.getResource(imageResourcePath).toString(), 200, 200, false, false);
    imageView = new ImageView(img);
    imageView.setX(x);
    imageView.setY(y);
    this.ground = y;
    this.jumping = false;
  }

  public ImageView getView() {
    return imageView;
  }

  public void setX(double x) {
    imageView.setX(x);
  }

  public void setY(double y) {
    imageView.setY(y);
  }

  static double getTimeInAir(double verticalVelocity, double acceleration) {
    return 2 * verticalVelocity / acceleration;
  }

  private double getNextPosByElapsedTime(double deltaT) {
    // delta_x = v_0 * t - 1/2 * g * t^2
    double deltaX = defaultVerticalVelocity * deltaT - 0.5 * Util.GRAVITATIONAL_ACCELERATION * Math.pow(deltaT, 2);
    System.out.println(deltaX);
    double position = ground - deltaX * jumpPower;
    return Math.min(position, ground);
  }

  public boolean isJumping() {
    return jumping;
  }

  public void jump() {
    jumping = true;
    jumpStartTime = System.nanoTime();
  }

  public void updatePosition(double time) {
    final double timeInAir = getTimeInAir(defaultVerticalVelocity, Util.GRAVITATIONAL_ACCELERATION);
    if (jumping) {
      double deltaT = (time - jumpStartTime) / 1000000000.0; // in seconds
      if (deltaT <= timeInAir) {
        setY(getNextPosByElapsedTime(deltaT));
      } else {
        jumping = false;
      }
    }
  }


}
