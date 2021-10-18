package dev.fatih.cse443hw1;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class PlatformGame extends Application {
  private static final double WIDTH = 1920 * 0.9;
  private static final double HEIGHT = 1080 * 0.9;
  private static final int GROUND = 700;
  private static int CHARACTER_HORIZONTAL_POSITION = 80;
  private static final double GRAVITATIONAL_ACCELERATION = 9.8; // m/s^2
  private static final String BACKGROUND_IMAGE = "planet.png";
  private static final String CHARACTER_FILENAME = "kangaroo.png";

  // TODO: constructor
  private double initialVerticalVelocity = 5; // m/s
  private double horizontalVelocity = 0.0;

  double jumpPower = 100;
  private ImageView backgroundView1;
  private ImageView backgroundView2;
  private ImageView characterView;
  private AnchorPane gamePane;
  private AnimationTimer gameTimer;
  private Scene scene;
  private boolean spacePressed;
  private boolean dPressed;

  private long jumpStartTime;
  private boolean jumping;

  static double getTimeInAir(double verticalVelocity, double acceleration) {
    return 2 * verticalVelocity / acceleration;
  }

  private double getNextPosByElapsedTime(double deltaT) {
    // delta_x = v_0 * t - 1/2 * g * t^2
    double deltaX = initialVerticalVelocity * deltaT - 0.5 * GRAVITATIONAL_ACCELERATION * Math.pow(deltaT, 2);
    System.out.println(deltaX);
    double position = GROUND - deltaX * jumpPower;
    return (position < GROUND) ? position : GROUND;
  }

  private void setCharacterPosition(double time) {
    final double timeInAir = getTimeInAir(initialVerticalVelocity, GRAVITATIONAL_ACCELERATION);
    if (spacePressed && !jumping) {
      jumping = true;
      jumpStartTime = System.nanoTime();
    }
    if (jumping) {
      double deltaT = (time - jumpStartTime) / 1000000000.0; // in seconds
      if (deltaT <= timeInAir) {
        characterView.setY(getNextPosByElapsedTime(deltaT));
      } else {
        jumping = false;
      }
    }
  }

  private void createGameLoop() {

    gameTimer = new AnimationTimer() {
      @Override
      public void handle(long now) {
        moveBackground();
        setCharacterPosition(now);
//        moveObstacles();
//        checkCollision();
      }
    };
    gameTimer.start();
  }

  private void createKeyListeners() {
    scene.setOnKeyPressed(event -> {
      if (event.getCode() == KeyCode.SPACE) {
        spacePressed = true;
      } else if (event.getCode() == KeyCode.D) {
        horizontalVelocity = 2;
      }

    });
    scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
          spacePressed = false;
        } else if (event.getCode() == KeyCode.D) {
          horizontalVelocity = 0;
        }
      }
    });
  }

  private void moveBackground() {
    backgroundView1.setLayoutX(backgroundView1.getLayoutX() - horizontalVelocity);
    backgroundView2.setLayoutX(backgroundView2.getLayoutX() - horizontalVelocity);
    if (backgroundView1.getLayoutX() <= -WIDTH)
      backgroundView1.setLayoutX(WIDTH);
    if (backgroundView2.getLayoutX() <= -WIDTH)
      backgroundView2.setLayoutX(WIDTH);
  }


  private void createBackground() {
    Image img = new Image(PlatformGame.class.getResource(BACKGROUND_IMAGE).toString(), WIDTH, HEIGHT, false, false);
    backgroundView1 = new ImageView(img);
    backgroundView2 = new ImageView(img);
    backgroundView1.setLayoutX(0);
    backgroundView2.setLayoutX(WIDTH);
    gamePane.getChildren().add(backgroundView1);
    gamePane.getChildren().add(backgroundView2);
  }

  private void createCharacter() {
    Image characterImage = new Image(PlatformGame.class.getResource(CHARACTER_FILENAME).toString(), 200, 200, false, false);
    characterView = new ImageView(characterImage);
    characterView.setX(CHARACTER_HORIZONTAL_POSITION);
    characterView.setY(GROUND);
    gamePane.getChildren().add(characterView);
  }


  @Override
  public void start(Stage stage) throws IOException {
    gamePane = new AnchorPane();
    scene = new Scene(gamePane, WIDTH, HEIGHT);
    stage.setScene(scene);
    stage.setTitle("Floating Enable"); // TODO: change title

    createBackground();
    createKeyListeners();
    createCharacter();
    createGameLoop();

    stage.show();

    Rectangle2D primScreenBounds = Screen.getScreens().get(0).getVisualBounds();
    // TODO: left monitor +
    stage.setX(1920 + (2560 - 1920) / 2 + (primScreenBounds.getWidth() - stage.getWidth()) / 2);
    stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
  }

  public static void main(String[] args) {
    launch();
  }
}