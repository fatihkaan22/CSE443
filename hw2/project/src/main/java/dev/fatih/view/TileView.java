package dev.fatih.view;

import dev.fatih.model.Tile;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.function.Consumer;

public class TileView {
  Tile tile;
  Label label;

  // CSS classes
  public final static String TILE_LINED = "tile-lined";

  public TileView(Tile t, Consumer<Tile> tileMarker) {
    this.tile = t;
    this.label = new Label();
    setProperties(t);
    this.label.getStyleClass().add("tile");

    label.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> tileMarker.accept(tile));
  }

  public TileView(TileView t) {
    this.label = new Label(t.label.getText());
    this.label.setTextFill(t.label.getTextFill());
  }

  public Label getLabel() {
    return label;
  }

  public void setProperties(Tile t) {
    this.label.setText("⯀");
    setColor(t.getColor().fxColor);
  }

  public void setColor(Color c) {
    Platform.runLater(() -> label.setTextFill(c));
  }

  public void updateColor() {
    setColor(tile.getColor().fxColor);
  }

  /**
   * @param t tile
   * @return false if already highlighted, true if the class just highlighted
   */
  public static boolean highlight(TileView t) {
    boolean highlighted = isHighlighted(t);
    if (!highlighted)
      Platform.runLater(() -> t.getLabel().getStyleClass().add(TILE_LINED));
    return !highlighted;
  }

  public static boolean isHighlighted(TileView t) {
    return t.getLabel().getStyleClass().contains(TILE_LINED);
  }

  /**
   * @param t tile
   * @return true if the color is changed, false otherwise
   */
  public static boolean setNone(TileView t) {
    boolean result = t.tile.getColor() != Tile.TileColor.NONE;
    t.tile.setColor(Tile.TileColor.NONE);
    return result;
  }

  public static void animateScaleDown(TileView t, long duration) {
    ScaleTransition st = new ScaleTransition(Duration.millis(duration), t.label);
    st.setFromX(1);
    st.setFromY(1);
    st.setToX(0);
    st.setToY(0);
    st.setOnFinished(e -> {
      t.label.setScaleX(1);
      t.label.setScaleY(1);
      t.updateColor();
    });
    st.play();
  }

  public static void animateScaleUp(TileView t, long duration) {
    ScaleTransition st = new ScaleTransition(Duration.millis(duration), t.label);
    st.setFromX(0);
    st.setFromY(0);
    st.setToX(1);
    st.setToY(1);
    st.setOnFinished(e -> {
      t.label.setScaleX(1);
      t.label.setScaleY(1);
      t.updateColor();
    });
    st.play();
  }


  public void setSelected(boolean b) {
    if (b)
      getLabel().getStyleClass().add("tile-selected");
    else
      getLabel().getStyleClass().removeIf(s -> s.equals("tile-selected"));

  }
}
