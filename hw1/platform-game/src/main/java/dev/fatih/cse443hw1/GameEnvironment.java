package dev.fatih.cse443hw1;

import dev.fatih.cse443hw1.common.Util;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameEnvironment {
  private final double WIDTH, HEIGHT;
  private double ground;
  private Scene scene;
  private AnchorPane gamePane;
  private ParallaxBackground background;
  //  private Background background;
  private Character character;
  private HashMap<KeyCode, Boolean> keyPressed;
  private Log log;
  private List<Obstacle> obstacleList;

  public GameEnvironment(double width, double height) {
    this.WIDTH = width;
    this.HEIGHT = height;
    this.ground = height * 0.6 * Util.GAME_AREA; // TODO test

    gamePane = new AnchorPane();
    log = new Log();
    ListView listView = new ListView(log.getList());
    VBox vBox = new VBox(gamePane, listView);
//    VBox vBox = new VBox(gamePane);

    listView.setFocusTraversable(false);

    scene = new Scene(vBox, WIDTH, HEIGHT);

    // register keys
    keyPressed = new HashMap<>();
    registerKeyEvent(KeyCode.SPACE); // jumping
    registerKeyEvent(KeyCode.D); // moving to the right

    // create background
//    background = new Background(Util.BACKGROUND_IMAGE, Util.getGameWidth(), Util.getGameHeight());
//    background.setMaxVelocity(Util.HORIZONTAL_VELOCITY);
//    for (ImageView backgroundView : background.getViews())
//      gamePane.getChildren().add(backgroundView);

    background = new ParallaxBackground(Util.BACKGROUNDS);
    background.addToPane(gamePane);

    // create character
    character = new Character(Util.CHARACTER_FILENAME, Util.CHARACTER_HORIZONTAL_POSITION, ground);
    gamePane.getChildren().add(character.getView());

    obstacleList = new ArrayList<>();
    obstacleList.add(new Obstacle("flower.png", Util.getGameWidth(), ground));
    obstacleList.add(new Obstacle("flower2.png", Util.getGameWidth() + 400, ground));
    obstacleList.add(new Obstacle("book.png", Util.getGameWidth() + 800, ground));
    obstacleList.add(new Obstacle("firecracker.png", Util.getGameWidth() + 1200, ground));
    obstacleList.add(new Obstacle("firework.png", Util.getGameWidth() + 1600, ground));
    obstacleList.add(new Obstacle("gud-papdi.png", Util.getGameWidth() + 2000, ground));

    for (Obstacle o : obstacleList)
      gamePane.getChildren().add(o.getView());

    int noBackgrounds = background.getBackgrounds().size();
    for (int i = noBackgrounds - Util.CHARACTER_LEVEL; i < noBackgrounds; i++) {
      background.getBackgrounds().get(i).getViews()[0].toFront();
      background.getBackgrounds().get(i).getViews()[1].toFront();
    }


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
      long delta;
      long lastFrameTime;


      @Override
      public void handle(long now) {
        delta = now - lastFrameTime;
        lastFrameTime = now;

//        double frameRate = 1d / delta;
//        double fps = frameRate * 1e9;
//        System.out.println(fps);

        if (keyPressed.get(KeyCode.D) && background.getVelocity() != background.getMaxVelocity()) {
          background.setVelocity(background.getMaxVelocity());
          for (Obstacle o : obstacleList) o.setVelocity(Util.HORIZONTAL_VELOCITY * 2);
          log.add("Character: moving right");
        }
        if (!keyPressed.get(KeyCode.D) && background.getVelocity() != 0) {
          background.setVelocity(0);
          for (Obstacle o : obstacleList) o.setVelocity(0);
          log.add("Character: stopped");
        }
        if (keyPressed.get(KeyCode.SPACE) && !character.isJumping()) {
          character.jump();
          log.add("Character: jump initiated");
        }
        character.updatePosition(now);
        background.updateBackgroundPosition();
        for (Obstacle o : obstacleList) o.updateObstaclePosition();
//        moveObstacles();
//        checkCollision();
      }
    }.start();
  }


}
