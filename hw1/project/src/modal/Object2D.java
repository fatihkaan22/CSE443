package modal;

public class Object2D {

  private int x, y;
  private int width, height;

  public Object2D(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public Object2D(Object2D o) {
    this(o.x, o.y, o.width, o.height);
  }

  public void scale(double scaleFactor) {
    Object2D o = new Object2D(this);
    this.width = (int) (scaleFactor * o.width);
    this.height = (int) (scaleFactor * o.height);
    this.x = o.x + (o.width - this.width) / 2;
    this.y = o.y + (o.height - this.height) / 2;
  }

  public boolean collides(Object2D o) {
    return o.x < this.x + this.width &&
            o.x + o.width > this.x &&
            o.y < this.y + this.height &&
            o.height + o.y > this.y;
  }

  public boolean collides(Object2D o, double threshold) {
    Object2D thisCopy = new Object2D(this);
    Object2D oCopy = new Object2D(o);
    thisCopy.scale(threshold);
    oCopy.scale(threshold);
    return thisCopy.collides(oCopy);
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  @Override
  public String toString() {
    return "modal.Object2D{" +
            "x=" + x +
            ", y=" + y +
            ", width=" + width +
            ", height=" + height +
            '}';
  }
}

