package view;

import common.Util;
import modal.Object2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameObject extends Object2D {
  private BufferedImage image;

  public GameObject(String imageResourcePath, int x, int y, int width, int height) {
    super(x, y, width, height);
    setImage(imageResourcePath);
  }

  public void setImage(String imageResourcePath) {
    try {
      this.image = ImageIO.read(getClass().getResource(imageResourcePath));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public GameObject(String imageResourcePath, int x, int y, int size) {
    this(imageResourcePath, x, y, (int) (size * Util.GAME_AREA), size);
  }

  public void draw(Graphics2D g) {
    g.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);
  }

}
