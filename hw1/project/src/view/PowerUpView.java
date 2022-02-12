package view;

import common.Util;
import view.GameObject;

public class PowerUpView extends GameObject {
  public enum TYPE {
    A(2),
    B(5),
    C(10),
    D(1);

    public final int multiplier;

    TYPE(int multiplier) {
      this.multiplier = multiplier;
    }
  }

  public TYPE type;
  private int velocity;


  public PowerUpView(TYPE t, String imageResourcePath, int x, int y) {
    super(imageResourcePath, x, y, (int) (Util.POWERUP_SIZE * Util.GAME_AREA), Util.POWERUP_SIZE);
    this.type = t;
  }

  public void updatePosition() {
    if (getX() <= -Util.OBSTACLE_SIZE) {
      setX(Util.getGameWidth() * 2);
    }
    setX(getX() - velocity);
  }

  public void setVelocity(int velocity) {
    this.velocity = velocity;
  }

}
