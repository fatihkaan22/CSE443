import java.util.ArrayList;
import java.util.Iterator;

/**
 * Email group entry of address book
 * Composite
 */
public class EmailGroup extends EmailComponent { // composite
  /**
   * List of emails or email groups
   */
  private ArrayList<EmailComponent> emailComponents;

  public EmailGroup(String address) {
    super(address);
    this.emailComponents = new ArrayList<>();
  }

  /**
   * Adds new email or email group to the list
   *
   * @param emailComponent email component to be added
   * @return true if operation is successful, false otherwise
   */
  @Override
  public boolean add(EmailComponent emailComponent) {
    return emailComponents.add(emailComponent);
  }

  /**
   * Removes specified email or email group from the list
   *
   * @param emailComponent email component to be removed
   * @return true if operation is successful, false otherwise
   */
  @Override
  public boolean remove(EmailComponent emailComponent) {
    return emailComponents.remove(emailComponent);
  }

  /**
   * Returns email component at given index
   *
   * @param index of email component to be returned
   * @return email component at given index
   */
  @Override
  public EmailComponent getChild(int index) {
    return emailComponents.get(index);
  }

  /**
   * Returns string includes the email group, and the elements of email group
   *
   * @return string in a format of "example@mail.com:{ex1@mail.com, ex2@mail.com ...}"
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(getAddress());
    sb.append(":{");

    Iterator<EmailComponent> iterator = createIterator();
    while (iterator.hasNext()) {
      sb.append(iterator.next().toString());
      if (iterator.hasNext())
        sb.append(", ");
    }
    sb.append("}");
    return sb.toString();
  }

  /**
   * Prints the email group, and the elements of email group
   */
  @Override
  public void print() {
    Iterator<EmailComponent> iterator = createIterator();
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
    }
  }

  /**
   * @return CompositeIterator of email component
   */
  public Iterator<EmailComponent> createIterator() {
    return new CompositeIterator(emailComponents.iterator());
  }

}
