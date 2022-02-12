import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Synchronization Proxy of DataStructure
 * Synchronization is handled as in readers writers problem with Java's ReentrantReadWriteLock
 */
public class SynchronizationProxy implements DataStructure {
  /**
   * Data Structure to be synchronized
   */
  private DataStructure dataStructure;
  private ReentrantReadWriteLock lock;

  public SynchronizationProxy(DataStructure dataStructure) {
    this.dataStructure = dataStructure;
    this.lock = new ReentrantReadWriteLock();
  }

  /**
   * thread-safe call to dataStructure.insert()
   *
   * @param o object to be inserted
   */
  @Override
  public void insert(Object o) {
    lock.writeLock().lock();
    dataStructure.insert(o);
    lock.writeLock().unlock();
  }

  /**
   * thread-safe call to dataStructure.remove()
   *
   * @param o object to be removed
   */
  @Override
  public void remove(Object o) {
    lock.writeLock().lock();
    dataStructure.remove(o);
    lock.writeLock().unlock();
  }

  /**
   * thread-safe call to dataStructure.get()
   *
   * @param index
   */
  @Override
  public Object get(int index) {
    Object o;
    lock.readLock().lock();
    o = dataStructure.get(index);
    lock.readLock().unlock();
    return o;
  }
}
