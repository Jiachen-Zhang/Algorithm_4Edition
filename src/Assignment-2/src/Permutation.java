import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

  /**.
   *
   * @param args default
   */
  public static void main(String[] args) {
    RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
    int k = Integer.parseInt(args[0]);

    for (int i = 1; !StdIn.isEmpty(); i++) {
      String str = StdIn.readString();
      if (i <= k) {
        randomizedQueue.enqueue(str);
      } else if (Math.random() < (double) k/i) {
        randomizedQueue.dequeue();
        randomizedQueue.enqueue(str);
      }
    }

    while (k > 0) {
      StdOut.println(randomizedQueue.dequeue());
      k--;
    }

  }


}
