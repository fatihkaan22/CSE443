package dev.fatih.model.hero;

public class FireHero extends Hero {
  public FireHero(String name) {
    super(name);
    this.strength = 100;
    this.agility = 125;
    this.health = 75;
  }

  public FireHero(double strengthFactor, double agilityFactor, double healthFactor, String name) {
    this(name);
    this.strength *= strengthFactor;
    this.agility *= agilityFactor;
    this.health *= healthFactor;
    this.maxHealth = health;
  }

  @Override
  public int calculateDamage(Hero from) {
    if (from instanceof NatureHero)
      return 2 * super.calculateDamage(from);
    if (from instanceof IceHero)
      return (int) (super.calculateDamage(from) / 2.0);
    return super.calculateDamage(from);
  }

}
