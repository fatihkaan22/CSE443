package modal.jump;

public interface JumpBehaviour {
  void jump();
  boolean isJumping();
  void updatePosition(long start);
}
