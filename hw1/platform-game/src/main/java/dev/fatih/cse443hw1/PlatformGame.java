package dev.fatih.cse443hw1;

import dev.fatih.cse443hw1.common.Util;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class PlatformGame extends Application {


  @Override
  public void start(Stage stage) throws IOException {

    GameEnvironment kangarooGame = new GameEnvironment(Util.WIDTH, Util.HEIGHT);
    kangarooGame.setGround(Util.GROUND);

    stage.setScene(kangarooGame.getScene());
    stage.setTitle("Floating Enable"); // TODO: change title
    stage.show();

    Rectangle2D primScreenBounds = Screen.getScreens().get(0).getVisualBounds();
    // TODO: left monitor +
    stage.setX(1920 + (2560 - 1920) / 2 + (primScreenBounds.getWidth() - stage.getWidth()) / 2);
//    stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
    stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
  }

  public static void main(String[] args) {
    launch();
  }
}