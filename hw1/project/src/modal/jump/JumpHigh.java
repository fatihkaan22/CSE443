package modal.jump;

import common.JumpPhysics;
import common.Util;
import view.CharacterView;

public class JumpHigh implements JumpBehaviour {
  private final int initialVelocity;
  boolean jumping;
  private CharacterView characterView;
  boolean changeJumpMode;

  public JumpHigh(CharacterView characterView) {
    this.characterView = characterView;
    this.initialVelocity = (int) (Util.VERTICAL_VELOCITY * 1.4);
    this.jumping = false;
    this.changeJumpMode = true;
  }

  @Override
  public void jump() {
    this.characterView.jumpStartTime = System.nanoTime();
    this.jumping = true;
  }

  @Override
  public boolean isJumping() {
    return jumping;
  }

  @Override
  public void updatePosition(long time) {
    final double timeInAir = JumpPhysics.getTimeInAir(initialVelocity);
    if (jumping) {
      double deltaT = JumpPhysics.getDeltaTInSeconds(time, characterView.jumpStartTime);
      if (deltaT <= timeInAir) {
        characterView.setY((int) JumpPhysics.getNextPosByElapsedTime(initialVelocity, deltaT, characterView.getGround()));
      } else {
        characterView.setY(characterView.getGround());
        jumping = false;
      }
    }
  }
}
