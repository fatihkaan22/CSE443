package dev.fatih.model.hero;

public class AtlantisFactory implements HeroFactory {
  static final double strengthFactor = 0.8;
  static final double agilityFactor = 1.2;
  static final double healthFactor = 1.2;

  @Override
  public Hero createFireHero() {
    return new FireHero(strengthFactor, agilityFactor, healthFactor, "Atlantis Fire");
  }

  @Override
  public Hero createIceHero() {
    return new IceHero(strengthFactor, agilityFactor, healthFactor, "Atlantis Ice");
  }

  @Override
  public Hero createNatureHero() {
    return new NatureHero(strengthFactor, agilityFactor, healthFactor, "Atlantis Nature");
  }
}
