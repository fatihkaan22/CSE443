package dev.fatih.model.hero;

public abstract class Hero {
  String name;
  int strength;
  int agility;
  int health;
  int maxHealth;

  public Hero() {
  }

  public Hero(String name) {
    this.name = name;
  }

  public int getStrength() {
    return strength;
  }

  public int getAgility() {
    return agility;
  }

  public int getHealth() {
    return health;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setStrength(int strength) {
    this.strength = strength;
  }

  public void setAgility(int agility) {
    this.agility = agility;
  }

  public void setHealth(int health) {
    this.health = health;
  }

  @Override
  public String toString() {
    return "Hero{" +
            "name='" + name + '\'' +
            ", strength=" + strength +
            ", agility=" + agility +
            ", health=" + health +
            '}';
  }

  /**
   * Returns damage with respect to given hero
   *
   * @param from taking damage from this hero
   * @return damage amount
   */
  public int calculateDamage(Hero from) {
    return (int) (100 * Math.pow(((double) from.strength / this.agility), 1.35));
  }

  /**
   * Reduces health with respect to given hero
   *
   * @param from taking damage from this hero
   * @return damage amount
   */
  public int takeDamage(Hero from) {
    int damage = calculateDamage(from);
    int updatedHealth = this.health - damage;
    if (updatedHealth < 0) updatedHealth = 0;
    this.setHealth(updatedHealth);
    return damage;
  }

  public int getMaxHealth() {
    return maxHealth;
  }
}
