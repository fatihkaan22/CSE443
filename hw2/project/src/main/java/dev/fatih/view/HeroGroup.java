package dev.fatih.view;

import dev.fatih.Main;
import dev.fatih.model.hero.Hero;
import dev.fatih.model.hero.HeroFactory;

import java.util.ArrayList;
import java.util.List;

public class HeroGroup {
  HeroView fireHero, natureHero, iceHero;

  HeroFactory getRandomFactory() {
    int randomFactory = (int) (Math.random() * Main.factories.size());
    return Main.factories.get(randomFactory);
  }

  public HeroGroup() {
    Hero fireHero, natureHero, iceHero;
    fireHero = getRandomFactory().createFireHero();
    iceHero = getRandomFactory().createIceHero();
    natureHero = getRandomFactory().createNatureHero();
    this.fireHero = new HeroView(fireHero);
    this.natureHero = new HeroView(natureHero);
    this.iceHero = new HeroView(iceHero);
  }

  public HeroView getFireHero() {
    return fireHero;
  }

  public HeroView getNatureHero() {
    return natureHero;
  }

  public HeroView getIceHero() {
    return iceHero;
  }

  public List<HeroView> asList() {
    List<HeroView> list = new ArrayList<>();
    list.add(fireHero);
    list.add(iceHero);
    list.add(natureHero);
    return list;
  }

  public boolean lost() {
    for (HeroView h : asList())
      if (h.getProperties().getHealth() != 0) return false;
    return true;
  }

}
