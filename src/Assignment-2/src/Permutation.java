import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

  /**.
   *
   * @param args default
   */
  public static void main(String[] args) {
//    RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
//    int k = Integer.parseInt(args[0]);
//
//    int maxSize = k;
//    while (!StdIn.isEmpty()) {
//      String str = StdIn.readString();
////      if (randomizedQueue.size() == maxSize) {
////        randomizedQueue.dequeue();
////      }
//      randomizedQueue.enqueue(str);
//    }
//    while (k > 0) {
//      StdOut.println(randomizedQueue.dequeue());
//      k--;
//    }

    function();
  }


  private static void function() {
    int num = 1000;
    int printA = 0;
    int printB = 0;
    int printC = 0;
    int printD = 0;
    while (num > 0) {
      RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
      int k = 1;
      int maxSize = k;
      String[] strs = {"A", "B", "C", "D"};
      for (int i = 0; i < 4; i++) {
//        if (randomizedQueue.size() == maxSize) {
//          randomizedQueue.dequeue();
//        }
        randomizedQueue.enqueue(strs[i]);
      }
      while (k > 0) {
        String str = randomizedQueue.dequeue();
        switch (str) {
          case "A" : printA++; break;
          case "B" : printB++; break;
          case "C" : printC++; break;
          case "D" : printD++; break;
        }
        k--;
      }
      num--;
    }
    System.out.println("A: " + printA);
    System.out.println("B: " + printB);
    System.out.println("C: " + printC);
    System.out.println("D: " + printD);
  }

}
