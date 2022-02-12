package modal.powerup;

public class PowerUpA extends PowerUpDecorator {
  Multiplier multiplier;

  public PowerUpA(Multiplier multiplier) {
    this.multiplier = multiplier;
  }

  @Override
  public long unitPoints() {
    return 2 * multiplier.unitPoints();
  }
}
