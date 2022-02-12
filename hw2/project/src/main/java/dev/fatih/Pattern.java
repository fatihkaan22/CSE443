package dev.fatih;

import dev.fatih.model.Tile;

import java.util.ArrayList;
import java.util.List;

public class Pattern {
  boolean[][] pattern;
  Tile.Direction direction;
  Tile source;

  public static final List<Pattern> PATTERNS = new ArrayList<>() {{
    add(new Pattern(new boolean[][]{
            {true, false},
            {false, true},
            {false, true},
    }, Tile.Direction.EAST, new Tile(0, 0)));
    add(new Pattern(new boolean[][]{
            {false, true},
            {true, false},
            {true, false},
    }, Tile.Direction.WEST, new Tile(1, 0)));
    add(new Pattern(new boolean[][]{
            {true, false},
            {false, true},
            {true, false},
    }, Tile.Direction.WEST, new Tile(1, 1)));
    add(new Pattern(new boolean[][]{
            {false, true},
            {true, false},
            {false, true},
    }, Tile.Direction.EAST, new Tile(0, 1)));
  }};

  public Pattern(boolean[][] pattern, Tile.Direction direction, Tile source) {
    this.pattern = pattern;
    this.direction = direction;
    this.source = source;
  }

  public boolean[][] getPattern() {
    return pattern;
  }

  public Tile.Direction getDirection() {
    return direction;
  }

  public Tile getSource() {
    return source;
  }

}
