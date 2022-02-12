import common.Util;
import common.Log;
import modal.Object2D;
import modal.jump.JumpLow;
import modal.powerup.*;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener {
  public int width;
  public int height;
  private int ground;
  private Thread thread;
  private boolean running;
  private int targetFPS = 60;
  private long targetTime = 1000 / targetFPS;
  private BufferedImage image;
  private Graphics2D g;
  private Log log;
  private HashMap<Integer, Boolean> keyPressed;
  private ArrayList<PowerUpView> powerUpList;
  private GameCharacter character;
  private Multiplier multiplier;
  private ArrayList<Obstacle> obstacleList;
  private Obstacle lastPassed, lastCollided;
  private long score;
  private int life;
  private boolean paused;
  private PauseButton pauseButton;
  private boolean justResumed;
  private long timeInAirWhenPaused;
  private Background background;

  public GamePanel() {
    super();
    this.width = Util.WIDTH;
    this.height = Util.HEIGHT;
    this.ground = Util.GROUND;
    this.paused = false;
    this.justResumed = false;
    keyPressed = new HashMap<>();
    registerKeyEvent(KeyEvent.VK_SPACE); // jumping
    registerKeyEvent(KeyEvent.VK_D); // moving to the right
    setPreferredSize(new Dimension(width, height));
    setLayout(null);
    this.log = new Log();
    this.multiplier = new BaseMultiplier();
    JList<String> list = new JList<>(log.getList());
    JScrollPane scrollPane = new JScrollPane(list);
    scrollPane.setBounds(0, Util.getGameHeight(), Util.WIDTH, Util.HEIGHT - Util.getGameHeight());
    add(scrollPane);
    setFocusable(true);
    requestFocus();
    addMouseListener(this);
  }

  public void addNotify() {
    super.addNotify();
    if (thread == null) {
      thread = new Thread(this);
      addKeyListener(this);
      thread.start();
    }
  }

  public void init() {
    score = 0;
    life = 3;
    lastCollided = null;
    lastPassed = null;

    obstacleList = new ArrayList<>();
    int x = 0;
    for (Util.Obstacle o : Util.OBSTACLES) {
      obstacleList.add(new Obstacle(Util.OBSTACLE_PATH + o.getFilename(), Util.getGameWidth() + x, ground, o.getSize()));
      x += 400;
    }

    this.powerUpList = new ArrayList<>();
    powerUpList.add(new PowerUpView(PowerUpView.TYPE.A, Util.POWERUP_PATH + Util.POWER_UPS.get('a'), width, Util.POWERUP_Y));
    powerUpList.add(new PowerUpView(PowerUpView.TYPE.B, Util.POWERUP_PATH + Util.POWER_UPS.get('b'), width * 2, Util.POWERUP_Y));
    powerUpList.add(new PowerUpView(PowerUpView.TYPE.C, Util.POWERUP_PATH + Util.POWER_UPS.get('c'), width * 3, Util.POWERUP_Y));
    powerUpList.add(new PowerUpView(PowerUpView.TYPE.D, Util.POWERUP_PATH + Util.POWER_UPS.get('d'), width * 4, Util.POWERUP_Y));

    this.character = new GameCharacter(new CharacterView(Util.CHARACTER_FILENAME, 100, ground));
//    log.getList().removeAllElements();
    background = new Background(Util.BACKGROUND_FILENAME, width, height);
    log.add("game initialized");
  }

  public void run() {
    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    g = (Graphics2D) image.getGraphics();
    running = true;
    pauseButton = new PauseButton("/pause.png");

    long start, elapsed, wait;
    int fps = targetFPS;

    init();

    while (running) {
      start = System.nanoTime();
      if (!paused) {
        if (justResumed) {
          character.getView().jumpStartTime = start - timeInAirWhenPaused;
          justResumed = false;
        }
        background.updateBackgroundPosition();
        background.draw(g);
        character.updatePosition(start);
        character.getView().draw(g);
        for (Obstacle o : obstacleList) {
          o.updateObstaclePosition(1);
          o.draw(g);
        }
        for (PowerUpView p : powerUpList) {
          p.updatePosition();
          p.draw(g);
        }
        // HUD
        Font font = g.getFont().deriveFont(24.0f);
        g.setFont(font);
        g.setColor(Color.RED);
        g.drawString("FPS: " + fps, 20, 50);
        g.drawString("Score: " + score, 20, 100);
        g.drawString("Life: " + life, 20, 150);

        pauseButton.draw(g);

        checkIfAnyPointsEarned();
        checkCollision();
        checkIfPowerUpAcquired();
        drawToScreen();


        if (keyPressed.get(KeyEvent.VK_D) && background.getVelocity() != background.getMaxVelocity()) {
          background.setVelocity(background.getMaxVelocity());
          for (Obstacle o : obstacleList) o.setVelocity(Util.HORIZONTAL_VELOCITY * 2);
          for (PowerUpView p : powerUpList) p.setVelocity(Util.HORIZONTAL_VELOCITY * 2);
          log.add("Character: moving right");
        }
        if (!keyPressed.get(KeyEvent.VK_D) && background.getVelocity() != 0) {
          background.setVelocity(0);
          for (Obstacle o : obstacleList) o.setVelocity(0);
          for (PowerUpView p : powerUpList) p.setVelocity(0);
          log.add("Character: stopped");
        }

        if (keyPressed.get(KeyEvent.VK_SPACE) && !character.isJumping()) {
          character.performJump();
          log.add("Character: jump initiated");
        }
      }


      elapsed = System.nanoTime() - start;
      wait = targetTime - elapsed / 1000000;
      fps = (wait >= 0) ? targetFPS : (int) (1000000000 / elapsed);
      if (wait < 0) wait = 5;
      try {
        Thread.sleep(wait);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private void checkCollision() {
    for (Obstacle o : obstacleList) {
      if (lastCollided != o && character.getView().collides(o, 0.5)) {
        lastCollided = o;
        life--;
        if (life <= 0) {
          log.add("game over");
          g.setFont(g.getFont().deriveFont(38.0f));
          g.drawString("Game Over. Press PLAY button to restart", width / 2 - 400, height / 2);
          pause();
          init();
        }
        log.add("Character: collided");
      }
    }
  }

  private void checkIfAnyPointsEarned() {
    Obstacle closest = obstacleList.get(0);
    for (Obstacle o : obstacleList) {
      if (closest.getX() > o.getX()) {
        closest = o;
      }
    }
    if (lastPassed != closest && character.getView().getX() > closest.getX()) {
      lastPassed = closest;
      score += multiplier.unitPoints();
      log.add(multiplier.unitPoints() + " points earned.");
    }
  }

  private void checkIfPowerUpAcquired() {
    for (PowerUpView p : powerUpList) {
      if (character.getView().collides(p, 0.6)) {
        switch (p.type) {
          case A:
            multiplier = new PowerUpA(multiplier);
            p.setX(width);
            break;
          case B:
            multiplier = new PowerUpB(multiplier);
            p.setX(width * 2);
            break;
          case C:
            p.setX(width * 3);
            multiplier = new PowerUpC(multiplier);
            break;
          case D:
            p.setX(width * 4);
            character.toggleJumpMode();
            log.add("Character: power up " + p.type + " acquired. Jump mode toggled.");
        }
        if (p.type != PowerUpView.TYPE.D)
          log.add("Character: power up " + p.type + " acquired. Current multiplier's unit points are " + multiplier.unitPoints());
      }
    }
  }

  private void registerKeyEvent(int keyCode) {
    keyPressed.put(keyCode, false);
  }

  private void drawToScreen() {
    Graphics2D g2 = (Graphics2D) getGraphics();
    g2.drawImage(image, 0, 0, width, Util.getGameHeight(), null);
    g2.dispose();
  }

  private void pause() {
    paused = true;
    timeInAirWhenPaused = System.nanoTime() - character.getView().jumpStartTime;
    pauseButton.setImage("/play.png");
    log.add("game paused");
    pauseButton.draw(g);
    drawToScreen();
  }

  private void play() {
    paused = false;
    justResumed = true;
    pauseButton.setImage("/pause.png");
    log.add("game resumed");
    pauseButton.draw(g);
    drawToScreen();
  }


  @Override
  public void keyTyped(KeyEvent keyEvent) {
    // empty
  }

  @Override
  public void keyPressed(KeyEvent keyEvent) {
    for (Integer keyCode : keyPressed.keySet()) {
      if (keyEvent.getKeyCode() == keyCode)
        keyPressed.put(keyCode, true);
    }
  }

  @Override
  public void keyReleased(KeyEvent keyEvent) {
    for (Integer keyCode : keyPressed.keySet()) {
      if (keyEvent.getKeyCode() == keyCode)
        keyPressed.put(keyCode, false);
    }
  }

  @Override
  public void mouseClicked(MouseEvent mouseEvent) {
    Point p = mouseEvent.getPoint();
    if (!pauseButton.collides(new Object2D(p.x, p.y, 10, 10))) return;
    if (paused)
      play();
    else
      pause();
  }

  @Override
  public void mousePressed(MouseEvent mouseEvent) {
    // empty
  }

  @Override
  public void mouseReleased(MouseEvent mouseEvent) {
    // empty
  }

  @Override
  public void mouseEntered(MouseEvent mouseEvent) {
    // empty
  }

  @Override
  public void mouseExited(MouseEvent mouseEvent) {
    // empty
  }
}