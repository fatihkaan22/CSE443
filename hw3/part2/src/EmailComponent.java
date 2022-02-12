import java.util.Iterator;

/**
 * Component abstract class
 */
public abstract class EmailComponent {
  /**
   * Email address of the person
   */
  private String address;

  public EmailComponent(String address) {
    this.address = address;
  }

  /**
   * @return the email address
   */
  public String getAddress() {
    return address;
  }

  public void print() {
    throw new UnsupportedOperationException();
  }

  /**
   * Throws UnsupportedOperationException
   *
   * @param emailComponent
   * @throws UnsupportedOperationException
   */
  public boolean add(EmailComponent emailComponent) {
    throw new UnsupportedOperationException();
  }

  /**
   * Throws UnsupportedOperationException
   *
   * @param emailComponent
   * @throws UnsupportedOperationException
   */
  public boolean remove(EmailComponent emailComponent) {
    throw new UnsupportedOperationException();
  }

  /**
   * Throws UnsupportedOperationException
   *
   * @param nthChild
   * @throws UnsupportedOperationException
   */
  public EmailComponent getChild(int nthChild) {
    throw new UnsupportedOperationException();
  }

  /**
   * Throws UnsupportedOperationException
   *
   * @throws UnsupportedOperationException
   */
  public Iterator<EmailComponent> createIterator() {
    throw new UnsupportedOperationException();
  }
}
