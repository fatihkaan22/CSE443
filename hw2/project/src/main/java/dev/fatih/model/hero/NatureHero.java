package dev.fatih.model.hero;

public class NatureHero extends Hero {
  public NatureHero(String name) {
    super(name);
    this.strength = 75;
    this.agility = 100;
    this.health = 125;
  }

  public NatureHero(double strengthFactor, double agilityFactor, double healthFactor, String name) {
    this(name);
    this.strength *= strengthFactor;
    this.agility *= agilityFactor;
    this.health *= healthFactor;
    this.maxHealth = health;
  }

  @Override
  public int calculateDamage(Hero from) {
    if (from instanceof IceHero)
      return 2 * super.calculateDamage(from);
    if (from instanceof NatureHero)
      return (int) (super.calculateDamage(from) / 2.0);
    return super.calculateDamage(from);
  }

}
