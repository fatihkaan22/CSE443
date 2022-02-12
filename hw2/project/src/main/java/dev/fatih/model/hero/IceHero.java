package dev.fatih.model.hero;

public class IceHero extends Hero {
  public IceHero(String name) {
    super(name);
    this.strength = 125;
    this.agility = 75;
    this.health = 100;
  }

  public IceHero(double strengthFactor, double agilityFactor, double healthFactor, String name) {
    this(name);
    this.strength *= strengthFactor;
    this.agility *= agilityFactor;
    this.health *= healthFactor;
    this.maxHealth = health;
  }

  @Override
  public int calculateDamage(Hero from) {
    if (from instanceof FireHero)
      return 2 * super.calculateDamage(from);
    if (from instanceof NatureHero)
      return (int) (super.calculateDamage(from) / 2.0);
    return super.calculateDamage(from);
  }

}
