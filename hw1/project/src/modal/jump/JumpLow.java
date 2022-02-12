package modal.jump;

import common.JumpPhysics;
import common.Util;
import view.CharacterView;

public class JumpLow implements JumpBehaviour {
  private final int initialVelocity;
  boolean jumping;
  private CharacterView characterView;
  boolean changeJumpMode;

  public JumpLow(CharacterView characterView) {
    this.characterView = characterView;
    this.initialVelocity = Util.VERTICAL_VELOCITY;
    this.jumping = false;
    this.changeJumpMode = true;
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

  @Override
  public boolean isJumping() {
    return jumping;
  }

  @Override
  public void jump() {
    this.characterView.jumpStartTime = System.nanoTime();
    this.jumping = true;
  }

}
