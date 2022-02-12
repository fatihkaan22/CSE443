import java.util.Iterator;
import java.util.Stack;

/**
 * Custom iterator of EmailComponent
 */
public class CompositeIterator implements Iterator<EmailComponent> {
  private Stack<Iterator<EmailComponent>> stack;

  public CompositeIterator(Iterator<EmailComponent> iterator) {
    this.stack = new Stack<>();
    stack.push(iterator);
  }

  /**
   * @return true if iterator has next element, false otherwise
   */
  @Override
  public boolean hasNext() {
    if (stack.empty()) {
      return false;
    } else {
      Iterator<EmailComponent> iterator = stack.peek();
      if (!iterator.hasNext()) {
        stack.pop();
        return hasNext();
      } else {
        return true;
      }
    }
  }

  /**
   * @return the next element of iterator
   */
  @Override
  public EmailComponent next() {
    if (hasNext()) {
      Iterator<EmailComponent> iterator = stack.peek();
      EmailComponent component = iterator.next();
      if (component instanceof Email) {
        stack.push(component.createIterator());
      }
      return component;
    } else {
      return null;
    }
  }
}
