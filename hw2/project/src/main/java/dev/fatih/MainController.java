package dev.fatih;

import dev.fatih.common.Util;
import dev.fatih.model.hero.Hero;
import dev.fatih.view.Grid;
import dev.fatih.model.Tile;
import dev.fatih.view.HeroGroup;
import dev.fatih.view.HeroView;
import dev.fatih.view.TileView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
  @FXML
  public GridPane gridPane;
  public ListView statusBar;
  public HBox characterHBox;
  public HBox enemyHBox;

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    HeroGroup characters = new HeroGroup();
    HeroGroup enemies = new HeroGroup();
    Grid g = new Grid(Util.COLS, Util.ROWS);
    g.setCharacters(characters);
    g.setEnemies(enemies);
    g.setStatusBar(statusBar);
    initGridPane(g);
    initHeroPane(characterHBox, characters);
    initHeroPane(enemyHBox, enemies);
  }

  private void initGridPane(Grid g) {
    for (int i = 0; i < g.getWidth(); i++) {
      for (int j = 0; j < g.getHeight(); j++) {
        Tile t = new Tile(i, j);
        GridPane.setConstraints(g.getTileViewNode(t), i, j);
      }
    }
    for (TileView t : g.getTileViews())
      gridPane.getChildren().add(t.getLabel());
  }

  private void initHeroPane(HBox hBox, HeroGroup group) {
    List<HeroView> views = group.asList();
    for (HeroView view : views) {
      VBox vBox = new VBox();
      vBox.getStyleClass().add("hero-container");
      vBox.getChildren().addAll(
              view.getNameLabel(),
              view.getStrengthLabel(),
              view.getAgilityLabel(),
              view.getHealthLabel(),
              view.getHealthBar());
      hBox.getChildren().add(vBox);
    }
  }
}
