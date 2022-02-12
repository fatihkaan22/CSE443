import java.util.Iterator;

/**
 * Address book entry of a single person
 * Leaf of composite design pattern
 */
public class Email extends EmailComponent { // leaf
  /**
   * Name of the person
   */
  private String name;

  public Email(String name, String address) {
    super(address);
    this.name = name;
  }

  /**
   * Single email address followed by a name
   *
   * @return string includes address and name
   */
  @Override
  public String toString() {
    return getAddress() + " " + name;
  }

  /**
   * toString of this
   */
  @Override
  public void print() {
    System.out.println(this);
  }

  /**
   * @return null iterator
   */
  public Iterator<EmailComponent> createIterator() {
    return new NullIterator();
  }
}
