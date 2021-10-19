package dev.fatih.cse443hw1;

import dev.fatih.cse443hw1.common.Util;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.util.HashMap;

public class GameEnvironment {
  private final double WIDTH, HEIGHT;
  private double ground;
  private Scene scene;
  private AnchorPane gamePane;
  private Background background;
  private Character character;
  private HashMap<KeyCode, Boolean> keyPressed;

  public GameEnvironment(double width, double height) {
    this.WIDTH = width;
    this.HEIGHT = height;
    this.ground = height * 0.6; // TODO test

    gamePane = new AnchorPane();
    scene = new Scene(gamePane, WIDTH, HEIGHT);

    // register keys
    keyPressed = new HashMap<>();
    registerKeyEvent(KeyCode.SPACE); //TODO: figure out a proper way of registering functions
    registerKeyEvent(KeyCode.D);

    // create background
    background = new Background(Util.BACKGROUND_IMAGE, Util.WIDTH, Util.HEIGHT);
    background.setMaxVelocity(Util.HORIZONTAL_VELOCITY);
    for (ImageView backgroundView : background.getViews())
      gamePane.getChildren().add(backgroundView);

    // create character
    character = new Character(Util.CHARACTER_FILENAME, Util.CHARACTER_HORIZONTAL_POSITION, ground);
    gamePane.getChildren().add(character.getView());

    createGameLoop();
  }

  Scene getScene() {
    return scene;
  }

  void setGround(double ground) {
    this.ground = ground;
  }

  private void registerKeyEvent(KeyCode key) {
    keyPressed.put(key, false);
    scene.setOnKeyPressed(event -> {
      for (KeyCode keyCode : keyPressed.keySet()) {
        if (event.getCode() == keyCode)
          keyPressed.put(keyCode, true);
      }
    });
    scene.setOnKeyReleased(event -> {
      for (KeyCode keyCode : keyPressed.keySet()) {
        if (event.getCode() == keyCode)
          keyPressed.put(keyCode, false);
      }
    });
  }

  private void createGameLoop() {
    new AnimationTimer() {
      @Override
      public void handle(long now) {
        background.setVelocity(keyPressed.get(KeyCode.D) ? background.getMaxVelocity() : 0);
        if (keyPressed.get(KeyCode.SPACE) && !character.isJumping()) {
          character.jump();
        }
        character.updatePosition(now);
        background.updateBackgroundPosition();
//        moveObstacles();
//        checkCollision();
      }
    }.start();
  }


}
