public class BestDSEver implements DataStructure {

  /**
   * Sleeps for 1 seconds to simulate operation
   */
  private void simulateOperation() {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void insert(Object o) {
    System.out.println("inserting");
    simulateOperation();
  }

  public void remove(Object o) {
    System.out.println("removing");
    simulateOperation();
  }

  public Object get(int index) {
    System.out.println("getting");
    simulateOperation();
    return null;
  }
}
