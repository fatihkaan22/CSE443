import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalTime;

public abstract class Main {

  public static void main(String[] args) {
    DataStructure bestDSEver, synchronizedBestDSEver;
    bestDSEver = new BestDSEver();
    synchronizedBestDSEver = new SynchronizationProxy(bestDSEver);
    System.setOut(new TimestampStream(System.out));

    System.out.println("==== TEST: THREAD UNSAFE ====");
    test(bestDSEver);
    System.out.println("==== TEST: THREAD SAFE ====");
    test(synchronizedBestDSEver);
  }

  /**
   * Tests the given datastructures by adding, getting and removing element concurrently.
   *
   * @param ds data structure to test
   */
  public static void test(DataStructure ds) {
    Object object = new Object();

    Thread t1 = new Thread(() -> {
      System.out.println("Thread 1 started");
      ds.insert(object);
      ds.get(0);
      ds.remove(object);
      System.out.println("Thread 1 finished");
    });

    Thread t2 = new Thread(() -> {
      System.out.println("Thread 2 started");
      ds.insert(object);
      ds.get(0);
      ds.remove(object);
      System.out.println("Thread 2 finished");
    });

    t1.start();
    t2.start();

    try {
      t1.join();
      t2.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Custom stream to print timestamps at the beginning of each line
   */
  public static class TimestampStream extends PrintStream {
    public TimestampStream(OutputStream out) {
      super(out);
    }

    @Override
    public void println(String string) {
      super.println("[" + LocalTime.now() + "] " + string);
    }
  }
}

