package dev.fatih.view;

import dev.fatih.model.hero.Hero;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class HeroView {
  Hero hero;
  Label name;
  Label strength;
  Label agility;
  Label health;
  ProgressBar healthBar;

  public HeroView(Hero h) {
    this.hero = h;
    name = new Label(h.getName());
    name.getStyleClass().add("hero-name");
    strength = new Label("Strength: " + h.getStrength());
    agility = new Label("Agility: " + h.getAgility());
    health = new Label();
    healthBar = new ProgressBar(1);
    updateView();
  }

  public Hero getProperties() {
    return hero;
  }

  public Label getNameLabel() {
    return name;
  }

  public ProgressBar getHealthBar() {
    return healthBar;
  }

  public Label getStrengthLabel() {
    return strength;
  }

  public Label getAgilityLabel() {
    return agility;
  }

  public Label getHealthLabel() {
    return health;
  }

  public void setHealth(int value) {
    this.getHealthLabel().setText("Health: " + value);
  }

  public void updateView() {
    setHealth(hero.getHealth());
    healthBar.setProgress(hero.getHealth() / 162.0);
  }
}
