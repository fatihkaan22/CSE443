package dev.fatih.model.hero;

public interface HeroFactory {
//  Hero createHero(Hero.HeroType type);
  Hero createFireHero();
  Hero createIceHero();
  Hero createNatureHero();
}
