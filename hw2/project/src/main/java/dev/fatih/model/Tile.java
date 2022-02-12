package dev.fatih.model;


import javafx.scene.paint.Color;


public class Tile {
  int x, y;
  TileColor color;

  public enum TileColor {
    RED(Color.valueOf("#e4564a")),
    BLUE(Color.valueOf("#4078f2")),
    GREEN(Color.valueOf("#50a14e")),
    NONE(Color.valueOf("#f2f2f2"));

    public final Color fxColor;

    TileColor(Color fxColor) {
      this.fxColor = fxColor;
    }
  }

  public enum Direction {
    NORTH, SOUTH, EAST, WEST
  }

  public Tile(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public Tile(int x, int y, TileColor color) {
    this(x, y);
    this.color = color;
  }

  public Tile(Tile t) {
    this.x = t.x;
    this.y = t.y;
  }

  public static void swapTileColors(Tile t1, Tile t2) {
    TileColor tmp = t1.getColor();
    t1.setColor(t2.getColor());
    t2.setColor(tmp);
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public TileColor getColor() {
    return color;
  }

  public void setColor(TileColor color) {
    this.color = color;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  /**
   * @param source tile
   * @param target tile
   * @return direction of the target {@link Tile} with respect to source Tile
   * @throws IllegalArgumentException in case swap failed
   */
  public static Direction getSwapDirection(Tile source, Tile target) {
    if (source.getX() < target.getX())
      return Direction.EAST;
    if (source.getX() > target.getX())
      return Direction.WEST;
    if (source.getY() > target.getY())
      return Direction.NORTH;
    if (source.getY() < target.getY())
      return Direction.SOUTH;
    throw new IllegalArgumentException("Swap failed: (source,target):" + source + target);
  }

  /**
   * @param t1
   * @param t2
   * @return whether two tiles are swappable (adjacent) or not
   */
  public static boolean isTilesAdjacent(Tile t1, Tile t2) {
    if (t1.getX() == t2.getX())
      return Math.abs(t1.getY() - t2.getY()) == 1;
    if (t1.getY() == t2.getY())
      return Math.abs(t1.getX() - t2.getX()) == 1;
    return false;
  }

  @Override
  public String toString() {
//    return String.valueOf(color.toString().toCharArray()[0]);
    return "Tile{" +
            "x=" + x +
            ", y=" + y +
            ", color=" + color +
            '}';
  }

//  @Override
//  public boolean equals(Object o) {
//    if (this == o) return true;
//    if (!(o instanceof Tile)) return false;
//
//    Tile tile = (Tile) o;
//
//    if (x != tile.x) return false;
//    return y == tile.y;
//  }
//
//  @Override
//  public int hashCode() {
//    return 31 * x + y;
//  }
}
