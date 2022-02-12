package dev.fatih.model.hero;

public class UnderwildFactory implements HeroFactory {
  static final double strengthFactor = 0.8;
  static final double agilityFactor = 1.6;
  static final double healthFactor = 0.8;

  @Override
  public Hero createFireHero() {
    return new FireHero(strengthFactor, agilityFactor, healthFactor, "Underwild Fire");
  }

  @Override
  public Hero createIceHero() {
    return new IceHero(strengthFactor, agilityFactor, healthFactor, "Underwild Ice");
  }

  @Override
  public Hero createNatureHero() {
    return new NatureHero(strengthFactor, agilityFactor, healthFactor, "Underwild Nature");
  }
}
