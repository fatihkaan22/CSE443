package dev.fatih.model.hero;

public class ValhallaFactory implements HeroFactory {
  static final double strengthFactor = 1.3;
  static final double agilityFactor = 0.4;
  static final double healthFactor = 1.3;

  @Override
  public Hero createFireHero() {
    return new FireHero(strengthFactor, agilityFactor, healthFactor, "Valhalla Fire");
  }

  @Override
  public Hero createIceHero() {
    return new IceHero(strengthFactor, agilityFactor, healthFactor, "Valhalla Ice");
  }

  @Override
  public Hero createNatureHero() {
    return new NatureHero(strengthFactor, agilityFactor, healthFactor, "Valhalla Nature");
  }
}
