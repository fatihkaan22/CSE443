package view;

import common.Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Background {
  private int width;
  private int height;
  private int x;
  private int velocity;
  private int maxVelocity;
  private BufferedImage images[];

  public Background(String imageResourcePath, int width, int height) {
    this.width = width;
    this.height = height;
    this.maxVelocity = Util.HORIZONTAL_VELOCITY;
    this.velocity = 0;
    this.x = 0;
    this.images = new BufferedImage[2];
    try {
      images[0] = ImageIO.read(getClass().getResource(imageResourcePath));
      images[1] = ImageIO.read(getClass().getResource(imageResourcePath));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public int getMaxVelocity() {
    return maxVelocity;
  }

  public int getVelocity() {
    return velocity;
  }

  public void setVelocity(int velocity) {
    this.velocity = velocity;
  }

  public void draw(Graphics2D g) {
    g.drawImage(images[0], x, 0, width, height, null);
    g.drawImage(images[1], x + width, 0, width, height, null);
  }

  public void updateBackgroundPosition() {
    x -= velocity;
    if (x < -width) x = 0;
  }

  public void setMaxVelocity(int v) {
    this.maxVelocity = v;
  }
}
