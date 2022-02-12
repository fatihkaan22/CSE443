package modal.powerup;

public class PowerUpB extends PowerUpDecorator {
  Multiplier multiplier;

  public PowerUpB(Multiplier multiplier) {
    this.multiplier = multiplier;
  }

  @Override
  public long unitPoints() {
    return 5 * multiplier.unitPoints();
  }
}
