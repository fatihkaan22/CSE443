package dev.fatih.common;

/**
 * Constants used throughout the application
 */
public class Util {
  public static final int WIDTH = 700;
  public static final int HEIGHT = 1020;
  public static final int COLS = 9;
  public static final int ROWS = 6;
  /**
   * Distance between two adjacent tiles in pixels, sets translation amount in the animation
   */
  public static final double TRANSLATION_DISTANCE = 64.0; // TODO: consider calculating dynamically from grid
  /**
   * Animation duration of swapping two tiles in milliseconds
   */
  public static final int SWAP_DURATION = 400;
  /**
   * Animation duration of scaling down of a tile in milliseconds
   */
  public static final long SCALE_DURATION = 200;
  /**
   * Animation duration of a tile stays highlighted in milliseconds
   */
  public static final long HIGHLIGHT_DURATION = 800;
  /**
   * Minimum number of lined up tiles necessary to make to move counted as successful move.
   */
  public static final long FILL_GAPS_DURATION = 500;
  /**
   *  Delay before the computer makes move
   */
  public static final long COMPUTER_THINKING_DURATION = 2000;
  /**
   * Minimum number of tiles for the move to be counted as successful move
   */
  public static final int MIN_MATCH = 3;
  // TODO: change before prod
  public static final String TITLE = "Floating Enable";

}
