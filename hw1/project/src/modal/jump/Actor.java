package modal.jump;

public abstract class Actor {
  public JumpBehaviour jumpBehaviour;

  public void performJump() {
    jumpBehaviour.jump();
  }

  public JumpBehaviour getJumpBehaviour() {
    return this.jumpBehaviour;
  }

  public void setJumpBehaviour(JumpBehaviour jumpBehaviour) {
    this.jumpBehaviour = jumpBehaviour;
  }

  public boolean isJumping() {
    return jumpBehaviour.isJumping();
  }

  public abstract void updatePosition(long start);
}
