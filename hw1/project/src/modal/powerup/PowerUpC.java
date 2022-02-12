package modal.powerup;

public class PowerUpC extends PowerUpDecorator {
  Multiplier multiplier;

  public PowerUpC(Multiplier multiplier) {
    this.multiplier = multiplier;
  }

  @Override
  public long unitPoints() {
    return 10 * multiplier.unitPoints();
  }
}
