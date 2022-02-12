package view;

import common.Util;

public class CharacterView extends GameObject {
  private int ground;
  public long jumpStartTime;

  public CharacterView(String imageResourcePath, int x, int y) {
    super(imageResourcePath, x, y, (int) (Util.CHARACTER_SIZE * Util.GAME_AREA), Util.CHARACTER_SIZE);
    this.ground = y;
  }

  public int getGround() {
    return ground;
  }
}
