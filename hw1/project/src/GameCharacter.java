import common.Util;
import modal.jump.Actor;
import modal.jump.JumpBehaviour;
import modal.jump.JumpHigh;
import modal.jump.JumpLow;
import view.CharacterView;

public class GameCharacter extends Actor {
  private CharacterView characterView;
  private JumpBehaviour nextJumpBehavior;

  public GameCharacter(CharacterView characterView) {
    jumpBehaviour = new JumpLow(characterView);
    this.characterView = characterView;
    this.nextJumpBehavior = null;
  }

  public CharacterView getView() {
    return characterView;
  }

  public void toggleJumpMode() {
    if (getJumpBehaviour() instanceof JumpLow) {
      nextJumpBehavior = new JumpHigh(this.characterView);
      characterView.setImage(Util.CHARACTER_WITH_WINGS_FILENAME);
    } else {
      nextJumpBehavior = new JumpLow(this.characterView);
      characterView.setImage(Util.CHARACTER_FILENAME);
    }
  }


  @Override
  public void updatePosition(long start) {
    jumpBehaviour.updatePosition(start);
    if (nextJumpBehavior != null && !isJumping()) {
      setJumpBehaviour(nextJumpBehavior);
    }
  }
}
