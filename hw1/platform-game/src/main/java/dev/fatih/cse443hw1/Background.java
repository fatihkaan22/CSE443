package dev.fatih.cse443hw1;

import dev.fatih.cse443hw1.common.Util;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Background {
  private double width, height;
  private ImageView backgroundViews[];
  private TranslateTransition transitions[];
  private double velocity; // m/s
  private double maxVelocity; // m/s

  public Background(String imageResourcePath, double width, double height) {
    this.width = width;
    this.height = height;
    this.maxVelocity = Util.HORIZONTAL_VELOCITY;
    this.velocity = 0;
    this.backgroundViews = new ImageView[2];
    this.transitions = new TranslateTransition[2];
    Image img = new Image(PlatformGame.class.getResource(imageResourcePath).toString(), width, height, false, false);

    // TODO: loop
    backgroundViews[0] = new ImageView(img);
    backgroundViews[1] = new ImageView(img);
    backgroundViews[0].setCache(true);
    backgroundViews[0].setCacheHint(CacheHint.QUALITY);
    backgroundViews[0].setCacheHint(CacheHint.SPEED);
    backgroundViews[1].setCache(true);
    backgroundViews[1].setCacheHint(CacheHint.QUALITY);
    backgroundViews[1].setCacheHint(CacheHint.SPEED);

    // TODO: loop
    backgroundViews[0].setLayoutX(0);
    backgroundViews[1].setLayoutX(width);
//    transitions[0] = new TranslateTransition(Duration.seconds(3), backgroundViews[0]);
//    transitions[0].setFromX(0);
//    transitions[0].setToX(-width);
//    transitions[0].setInterpolator(Interpolator.LINEAR);
//    transitions[0].setCycleCount(Animation.INDEFINITE);

//    transitions[1] = new TranslateTransition(Duration.seconds(3), backgroundViews[1]);
//    transitions[1].setFromX(width);
//    transitions[1].setToX(0);
//    transitions[1].setInterpolator(Interpolator.LINEAR);
//    transitions[1].setCycleCount(Animation.INDEFINITE);
  }

  public TranslateTransition[] getTransitions() {
    return transitions;
  }

  public void setMaxVelocity(double maxVelocity) {
    this.maxVelocity = maxVelocity;
  }

  public double getMaxVelocity() {
    return maxVelocity;
  }

  public double getVelocity() {
    return velocity;
  }

  public void setVelocity(double velocity) {
    this.velocity = velocity;
  }

  public ImageView[] getViews() {
    return backgroundViews;
  }

  public void updateBackgroundPosition() {
    // TODO: loop
    backgroundViews[0].setLayoutX(backgroundViews[0].getLayoutX() - velocity);
    backgroundViews[1].setLayoutX(backgroundViews[1].getLayoutX() - velocity);
    if (backgroundViews[0].getLayoutX() <= -width)
      backgroundViews[0].setLayoutX(backgroundViews[1].getLayoutX() + width);
    if (backgroundViews[1].getLayoutX() <= -width)
      backgroundViews[1].setLayoutX(backgroundViews[0].getLayoutX() + width);
  }

}
