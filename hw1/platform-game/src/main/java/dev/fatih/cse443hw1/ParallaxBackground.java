package dev.fatih.cse443hw1;

import dev.fatih.cse443hw1.common.Util;
import javafx.animation.ParallelTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class ParallaxBackground {
  List<Background> backgrounds;
  private double velocity; // m/s
  private double maxVelocity; // m/s

  public void setVelocity(double velocity) {
    this.velocity = velocity;
    for (Background b : backgrounds) {
      if (velocity == 0)
        b.setVelocity(velocity);
      else
        b.setVelocity(b.getMaxVelocity());
    }
  }

  public double getMaxVelocity() {
    return maxVelocity;
  }

  public double getVelocity() {
    return velocity;
  }

  public List<Background> getBackgrounds() {
    return backgrounds;
  }

  public ParallaxBackground(String[] backgroundPath) {
    // create background
    backgrounds = new ArrayList<Background>();
    this.maxVelocity = Util.HORIZONTAL_VELOCITY;
    double v = Util.HORIZONTAL_VELOCITY;
    for (int i = backgroundPath.length - 1; i >= 0; i--) {
      Background b = new Background(Util.BACKGROUNDS_PATH + backgroundPath[i], Util.getGameWidth(), Util.getGameHeight());
      backgrounds.add(b);
      b.setMaxVelocity(v);
      v += 2;
    }
//    ParallelTransition parTrans = new ParallelTransition();
//    for (Background b : backgrounds) {
//      b.getTransitions()[0].play();
//      b.getTransitions()[1].play();
//      parTrans.getChildren().add(b.getTransitions()[0]);
//      parTrans.getChildren().add(b.getTransitions()[1]);
//    }
//    parTrans.play();
  }

  public void addToPane(Pane gamePane) {
    for (Background b : backgrounds)
      for (ImageView backgroundView : b.getViews())
        gamePane.getChildren().add(backgroundView);
  }

  public void updateBackgroundPosition() {
    for (Background b : backgrounds)
      b.updateBackgroundPosition();
  }
}
