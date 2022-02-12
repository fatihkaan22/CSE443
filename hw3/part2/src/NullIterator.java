import java.util.Iterator;

/**
 * Null iterator
 */
public class NullIterator implements Iterator<EmailComponent> {
  /**
   * @return false
   */
  @Override
  public boolean hasNext() {
    return false;
  }

  /**
   * @return null
   */
  @Override
  public EmailComponent next() {
    return null;
  }

  /**
   * throws UnsupportedOperationException
   * @throws UnsupportedOperationException
   */
  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }
}
