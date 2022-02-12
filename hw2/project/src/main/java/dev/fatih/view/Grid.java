package dev.fatih.view;

import dev.fatih.Pattern;
import dev.fatih.common.Util;
import dev.fatih.model.Tile;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.util.Duration;
import javafx.util.Pair;

import java.util.*;
import java.util.function.Predicate;

public class Grid {
  List<TileView> tileViews;
  List<Tile> tiles;
  public final int width, height;
  Tile marked;
  HashMap<Tile, Integer> shiftAmount = new HashMap<>();
  boolean highlighted;
  Pair<Tile, Tile> swapPair;
  ListView statusBar;
  boolean isNextTurnComputer;
  boolean userInputEnabled;
  boolean end;
  HeroGroup enemies, characters;

  public Grid(int width, int height) {
    this.width = width;
    this.height = height;
    this.tileViews = new ArrayList<>();
    this.tiles = new ArrayList<>();
    this.marked = null;
    this.isNextTurnComputer = true;
    this.userInputEnabled = true;
    this.end = false;

    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
//        int random = (int) (Math.random() * Tile.TileColor.values().length);
        Tile t = new Tile(i, j, getRandomColor());
        tiles.add(t);
        tileViews.add(new TileView(t, this::setMarked));
      }
    }

    // initial game state (none lined up tiles)
    while (removeMatchingTiles())
      spawnNewTiles();
  }

  private Tile getTileInDirection(Tile t, Tile.Direction d) {
    switch (d) {
      case EAST:
        return getTile(t.getX() + 1, t.getY());
      case WEST:
        return getTile(t.getX() - 1, t.getY());
      case NORTH:
        return getTile(t.getX(), t.getY() + 1);
      case SOUTH:
        return getTile(t.getX(), t.getY() - 1);
    }
    return null;
  }

  private void autoMove() {
    Tile found = null;
    Pattern p = null;
    for (int i = 0; i < Pattern.PATTERNS.size(); i++) {
      p = Pattern.PATTERNS.get(i);
      found = searchPattern(p);
      if (found != null) break;
    }
    if (found != null) {
      found = getTile(found.getX() + p.getSource().getX(), found.getY() + p.getSource().getY());
      userInputEnabled = true;
      setMarked(found);
      setMarked(getTileInDirection(found, p.getDirection()));
    } else {
      System.out.println("MOVE NOT FOUND");
      // TODO: shuffle
    }
  }

  private Tile.TileColor getRandomColor() {
    final List<Tile.TileColor> COLORS = Arrays.asList(Tile.TileColor.values());
    int random = (int) (Math.random() * Tile.TileColor.values().length - 1);
    return COLORS.get(random);
  }

  /**
   * recursive move calls until the grid is cleared from lined up tiles
   *
   * @param isSwap whether wait for swap animation or not
   */
  private void cascadedMove(boolean isSwap) {
    Thread thread = move(null, isSwap);
    waitAndRun(thread, () -> {
      if (highlighted) {
        boolean isThereAnyLinedUpTiles = matchingTiles(t -> true);
        if (isThereAnyLinedUpTiles) {
          cascadedMove(false);
        }
      } else {
        // move not successful: reverse
        if (swapPair != null) {
          Tile.swapTileColors(swapPair.getKey(), swapPair.getValue());
          animateSwap(swapPair.getKey(), swapPair.getValue());
        }
      }
    }, 0).start();
  }

  /**
   * Creates a thread that
   * waits for given thread,
   * runs the given runnable,
   * sleeps for sleepDuration
   *
   * @param wait          for thread to finish
   * @param r             runs the method
   * @param sleepDuration sleeps after running the method, in ms
   * @return the thread
   */
  public Thread waitAndRun(Thread wait, Runnable r, long sleepDuration) {
    return new Thread(() -> {
      try {
        if (wait != null) {
          // TODO: is busy waiting?
          do {
            wait.join();
          } while (wait.getState() != Thread.State.TERMINATED);
        }
        Thread.sleep(20);
        r.run();
        Thread.sleep(sleepDuration);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
  }


  /**
   * @param waitFor thread to wait for termination
   * @param isSwap  whether wait for the swap animation or not
   * @return should return the last thread in order to enable cascading move calls
   */
  public Thread move(Thread waitFor, boolean isSwap) {
    boolean noMatch = !matchingTiles(t -> true);
    Thread swapWait = waitAndRun(null, () -> {
    }, Util.SWAP_DURATION);
    Thread highlight = waitAndRun(isSwap ? swapWait : null, () -> {
      highlighted = matchingTiles(TileView::highlight);
    }, noMatch ? 0 : Util.HIGHLIGHT_DURATION);

    Thread scaleDown = waitAndRun(highlight, () -> {
      // remove status
      Platform.runLater(() -> statusBar.getItems().clear());
      if (highlighted) {
        performDamage(characters, enemies);
        removeHighlight();
        removeMatchingTiles();
        // update ui
        Platform.runLater(() -> enemies.asList().forEach(HeroView::updateView));
      }
    }, noMatch ? 0 : Util.SCALE_DURATION);

    Thread fillGaps = waitAndRun(scaleDown, () -> {
      fillGaps();
    }, noMatch ? 0 : Util.FILL_GAPS_DURATION);

    Thread spawnNewTiles = waitAndRun(fillGaps, () -> {
      spawnNewTiles();
      if (highlighted) {
        changeTurn();
      } else {
        userInputEnabled = true;
      }
    }, noMatch ? 0 : Util.FILL_GAPS_DURATION);

    waitAndRun(waitFor, () -> {
      swapWait.start();
      highlight.start();
      scaleDown.start();
      fillGaps.start();
      spawnNewTiles.start();
    }, 0).start();
    return spawnNewTiles;
  }

  /**
   * Change the turn by swapping characters and enemies references and scheduling computers move
   */
  private void changeTurn() {
    if (!matchingTiles(t -> true)) {
      if (enemies.lost() && isNextTurnComputer) {
        System.out.println("WIN");
        addStatus("WIN");
        characters.asList().forEach(c -> c.getProperties().setHealth(c.getProperties().getMaxHealth()));
        enemies.asList().forEach(c -> c.getProperties().setHealth(c.getProperties().getMaxHealth()));
//        enemies = new HeroGroup();
        Platform.runLater(() -> characters.asList().forEach(HeroView::updateView));
        Platform.runLater(() -> enemies.asList().forEach(HeroView::updateView));
        userInputEnabled = true;
        return;
      }
      if (enemies.lost() && !isNextTurnComputer) {
        System.out.println("LOST");
        addStatus("LOST");
        end = true;
      }
      Platform.runLater(() -> characters.asList().forEach(HeroView::updateView));
      Platform.runLater(() -> enemies.asList().forEach(HeroView::updateView));
      swapHeroes();
      if (isNextTurnComputer) {
        schedule(this::autoMove, Util.COMPUTER_THINKING_DURATION);
        isNextTurnComputer = false;
      } else {
        isNextTurnComputer = true;
        userInputEnabled = true;
      }
    }
  }

  /**
   * Swap hero references
   */
  private void swapHeroes() {
    HeroGroup tmp = characters;
    characters = enemies;
    enemies = tmp;
  }

  private void performDamage(HeroGroup from, HeroGroup to) {
    matchingTiles(t -> {
      HeroView character;
      switch (t.tile.getColor()) {
        case RED:
          character = from.getFireHero();
          break;
        case GREEN:
          character = from.getNatureHero();
          break;
        case BLUE:
          character = from.getIceHero();
          break;
        default:
          throw new IllegalStateException("Unexpected value: " + t.tile.getColor());
      }
      HeroView enemy = getIntersectedHero(to, t.tile.getX());
      int damage = enemy.getProperties().takeDamage(character.getProperties());
      addStatus(character.hero.getName() + " > " + enemy.hero.getName() + " :: Damage: " + damage);
      return true;
    });
  }

  /**
   * Adds new row to status bar
   *
   * @param s string to be added as status
   */
  private void addStatus(String s) {
    Platform.runLater(() -> {
      statusBar.getItems().add(s);
    });
  }

  /**
   * @param hg hero group
   * @param x  coordinate
   * @return the HeroView according to given coordinate and hero group
   */
  private HeroView getIntersectedHero(HeroGroup hg, int x) {
    // TODO: order important
    if (x < 3) return hg.getFireHero();
    if (x < 6) return hg.getIceHero();
    return hg.getNatureHero();
  }

  /**
   * Removes matching tiles
   *
   * @return true if any cell is removed, false otherwise
   */
  private boolean removeMatchingTiles() {
    // separate list needed to be able to remove both horizontal and vertical matches of same tile
    List<TileView> remove = new ArrayList<>();
    matchingTiles(remove::add);
    remove.forEach(t -> {
      TileView.setNone(t);
      Platform.runLater(() -> TileView.animateScaleDown(t, Util.SCALE_DURATION));
    });
    return remove.size() != 0;
  }

  /**
   * Change tiles with {@link dev.fatih.model.Tile.TileColor.NONE} to random color
   */
  private void spawnNewTiles() {
    tileViews.stream()
            .filter(tv -> tv.tile.getColor() == Tile.TileColor.NONE)
            .forEach(tv -> {
              tv.tile.setColor(getRandomColor());
              Platform.runLater(tv::updateColor);
              TileView.animateScaleUp(tv, Util.SCALE_DURATION);
            });
  }

  // TODO: move to util
  private static void schedule(Runnable r, long delay) {
    new Timer().schedule(new TimerTask() {
      @Override
      public void run() {
        r.run();
      }
    }, delay);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  private boolean isFirstClick() {
    return this.marked == null;
  }

  /**
   * Sets source tile to perform swap on the next click
   *
   * @param t tile to be selected
   */
  private void setSource(Tile t) {
    if (marked == t) {
      getTileView(marked).setSelected(false);
      marked = null;
      return;
    }
    if (marked != null) {
      getTileView(marked).setSelected(false);
    }
    this.marked = t;
    getTileView(marked).setSelected(true);
  }

  /**
   * After swap animation tiles are translated
   * Resets the positions of tile to its starting point
   *
   * @param t tile to be translated back
   */
  private void resetTranslation(Tile t) {
    getTileView(t).getLabel().setTranslateX(0);
    getTileView(t).getLabel().setTranslateY(0);
  }

  /**
   * Animates the swap
   *
   * @param t1
   * @param t2
   */
  public void animateSwap(Tile t1, Tile t2) {
    TranslateTransition animationSource =
            new TranslateTransition(Duration.millis(Util.SWAP_DURATION), getTileView(t1).getLabel());
    TranslateTransition animationTarget =
            new TranslateTransition(Duration.millis(Util.SWAP_DURATION), getTileView(t2).getLabel());
    Tile.Direction direction = Tile.getSwapDirection(t1, t2);
    // TODO: consider splitting into another function
    switch (direction) {
      case EAST:
        animationSource.setByX(Util.TRANSLATION_DISTANCE);
        animationTarget.setByX(-Util.TRANSLATION_DISTANCE);
        break;
      case WEST:
        animationSource.setByX(-Util.TRANSLATION_DISTANCE);
        animationTarget.setByX(Util.TRANSLATION_DISTANCE);
        break;
      case NORTH:
        animationSource.setByY(-Util.TRANSLATION_DISTANCE);
        animationTarget.setByY(Util.TRANSLATION_DISTANCE);
        break;
      case SOUTH:
        animationSource.setByY(Util.TRANSLATION_DISTANCE);
        animationTarget.setByY(-Util.TRANSLATION_DISTANCE);
        break;
    }
    animationSource.setOnFinished(e -> {
      resetTranslation(t1);
      getTileView(t1).getLabel().getStyleClass().removeIf(s -> Objects.equals(s, "tile-selected"));
      getTileView(t1).updateColor();
      getTileView(t2).updateColor();
    });
    animationTarget.setOnFinished(e -> resetTranslation(t2));
    animationSource.play();
    animationTarget.play();
  }

  /**
   * This method will be called with corresponding {@link Tile} whenever the {@link TileView} is clicked.
   * Either sets the source or swaps the tiles
   *
   * @param t tile to be marked
   */
  public void setMarked(Tile t) {
    if (end) return;
    if (!userInputEnabled) return;
    if (isFirstClick()) { // source
      setSource(t);
    } else { // second click (target)
      if (!Tile.isTilesAdjacent(t, marked)) {
        setSource(t);
        return;
      }
      userInputEnabled = false;
      Tile.swapTileColors(marked, t);
      animateSwap(marked, t);
      onSwap();
      swapPair = new Pair<>(marked, t);
      this.marked = null;
    }
  }

  /**
   * Removes "tile-lined" class from the applied classes to disable highlight effect.
   */
  private void removeHighlight() {
    Platform.runLater(() ->
            tileViews.forEach(t -> t.getLabel().getStyleClass().removeIf(s -> s.equals("tile-lined"))));
  }

  /**
   * iterates through empty tiles and swaps with the northern tile, if northern tile is not empty swap with current tile
   */
  private void shiftUpwardsOneStep() {
    for (int i = width - 1; i >= 0; i--)
      for (int j = height - 1; j >= 0; j--) {
        TileView current = getTileView(i, j);
        TileView next = getTileView(i, j + 1);
        if (current.tile.getColor() == Tile.TileColor.NONE
                && next != null
                && next.tile.getColor() != Tile.TileColor.NONE)
          Tile.swapTileColors(current.tile, next.tile);
      }
  }

  /**
   * Fills the gaps by swapping tiles from bottom to up
   * Animates the swap by calculating the shift amount for each tile
   */
  private void fillGaps() {
    calculateShiftAmount();
    //  loop height times to make sure no gap between tiles // TODO: find a better way
    for (int i = 0; i < height; i++)
      shiftUpwardsOneStep();
    // shift animation that fill the gaps
    shiftAmount.forEach((tile, shift) -> {
      TranslateTransition animation =
              new TranslateTransition(Duration.millis(Util.SWAP_DURATION), getTileView(tile).getLabel());
      animation.setByY(-Util.TRANSLATION_DISTANCE * shift);
      animation.setOnFinished(e -> {
        Tile updated = getTile(tile.getX(), tile.getY() - shift);
        Platform.runLater(() -> {
          resetTranslation(tile);
          getTileView(updated).updateColor();
          getTileView(tile).updateColor();
        });
      });
      animation.play();
    });
    shiftAmount.clear();
  }

  private void onSwap() {
    cascadedMove(true);
  }

  /**
   * Calculates shift amount in order to fill gaps. Needed to perform the fill gaps animation
   */
  private void calculateShiftAmount() {
    for (int i = 0; i < width; i++) {
      int gaps = 0;
      for (int j = 0; j < height; j++)
        if (getTile(i, j).getColor() == Tile.TileColor.NONE)
          gaps++;
        else if (gaps != 0)
          shiftAmount.put(getTile(i, j), gaps);
    }
  }


  /**
   * Runs given operation on every lined up {@link TileView}
   *
   * @param predicate
   * @return returns false, if all the operations results in false, true otherwise
   */
  private boolean matchingTiles(Predicate<TileView> predicate) {
    boolean applied = false;
    removeHighlight();
    for (int i = 0; i < width; i++)
      for (int j = 0; j < height; j++) {
        List<Tile> groupEast = getLinedUpTiles(getTile(i, j), Tile.Direction.EAST);
        if (groupEast.size() >= Util.MIN_MATCH)
          for (Tile tile : groupEast)
            if (predicate.test(getTileView(tile)))
              applied = true;
        List<Tile> groupSouth = getLinedUpTiles(getTile(i, j), Tile.Direction.SOUTH);
        if (groupSouth.size() >= Util.MIN_MATCH)
          for (Tile tile : groupSouth)
            if (predicate.test(getTileView(tile)))
              applied = true;
      }
    return applied;
  }

  /**
   * Checks whether the tile is in bounds or not
   *
   * @param x tile x
   * @param y tile y
   * @return true if the tile is in bounds
   */
  public boolean isTileInBounds(int x, int y) {
    return x >= 0 && x < width && y >= 0 && y < height;
  }

  /**
   * @param start
   * @return number of tiles lined up after given tile
   */
  private List<Tile> getLinedUpTiles(Tile start, Tile.Direction direction) {
    List<Tile> list = new ArrayList<>();
    if (start.getColor() == Tile.TileColor.NONE) return list;
    list.add(start);
    Tile next;
    int nextCount = 1;

    do {
      switch (direction) {
        case EAST:
          next = getTile(start.getX() + nextCount, start.getY());
          break;
        case WEST:
          next = getTile(start.getX() - nextCount, start.getY());
          break;
        case SOUTH:
          next = getTile(start.getX(), start.getY() + nextCount);
          break;
        case NORTH:
          next = getTile(start.getX(), start.getY() - nextCount);
          break;
        default:
          return list;
      }
      if (next == null) break;
      if (next.getColor() != start.getColor()) break;
      list.add(next);
      nextCount++;
    } while (true);
    return list;
  }

  /**
   * In the area reference color will selected as true, other colors selected as false
   *
   * @param offset offset in the grid
   * @param ref    color of this tile will be selected as true
   * @return returns a boolean matrix according to given offset
   */
  private boolean[][] convertColorsToPattern(Tile offset, Tile ref) {
    int w = 3; // TODO: dynamic
    int h = 2;
    Tile.TileColor reference = getTile(offset.getX() + ref.getX(), offset.getY() + ref.getY()).getColor();
    boolean[][] pattern = new boolean[w][h];
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        pattern[i][j] = (reference == getTileView(j + offset.getX(), i + offset.getY()).tile.getColor());
      }
    }
    return pattern;
  }

  /**
   * Searches given pattern in the grid
   *
   * @param p pattern to be searched for
   * @return the initial (0,0) tile of the matched pattern
   */
  private Tile searchPattern(Pattern p) {
    List<Tile> possibleMoves = new ArrayList<>();

    for (int i = 0; i < width - 3; i++) {
      for (int j = 0; j < height - 3; j++) {
        boolean[][] area = convertColorsToPattern(new Tile(i, j), p.getSource());
        if (matchPattern(p.getPattern(), area)) {
          possibleMoves.add(getTile(i, j));
        }
      }
    }

    if (possibleMoves.isEmpty()) return null;
    Tile s = possibleMoves.get(0);
    return s;
  }

  /**
   * @param mask pattern
   * @param area searched region
   * @return true if the area matches with given pattern, false otherwise
   */
  private boolean matchPattern(boolean[][] mask, boolean[][] area) {
    for (int i = 0; i < mask.length; i++) {
      for (int j = 0; j < mask[0].length; j++) {
        if (mask[i][j]) {
          if (!area[i][j]) {
            return false;
          }
        }
      }
    }
    return true;
  }

  public List<TileView> getTileViews() {
    return tileViews;
  }

  public Tile getTile(int x, int y) {
    TileView tv = getTileView(x, y);
    if (tv == null) return null;
    return tv.tile;
  }

  /**
   * @param x coordinate of tile
   * @param y coordinate of tile
   * @return the {@link TileView} corresponds the given tile
   */
  public TileView getTileView(int x, int y) {
    if (!isTileInBounds(x, y)) {
      return null;
    }
    return tileViews.get(x * height + y);
  }

  /**
   * @param t
   * @return the {@link TileView} corresponds the given tile
   */
  public TileView getTileView(Tile t) {
    return getTileView(t.getX(), t.getY());
  }

  public Node getTileViewNode(Tile t) {
    return getTileView(t).getLabel();
  }

  public void setEnemies(HeroGroup enemies) {
    this.enemies = enemies;
  }

  public void setCharacters(HeroGroup characters) {
    this.characters = characters;
  }

  public void setStatusBar(ListView statusBar) {
    this.statusBar = statusBar;
  }
}
