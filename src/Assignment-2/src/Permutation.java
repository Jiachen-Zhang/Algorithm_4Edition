import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

  /**.
   *
   * @param args default
   */
  public static void main(String[] args) {
    RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
    int k =Integer.parseInt(args[0]);

    int maxSize = k;
    while (!StdIn.isEmpty()) {
      String str = StdIn.readString();
      if (randomizedQueue.size() == maxSize) {
        randomizedQueue.dequeue();
      }
      randomizedQueue.enqueue(str);
    }
    while (k > 0) {
      StdOut.print(randomizedQueue.dequeue());
      k--;
    }


  }

}
