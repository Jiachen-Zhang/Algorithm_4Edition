import edu.princeton.cs.algs4.StdRandom;
import java.util.Arrays;
import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

  private int size;
  private int length;
  private Item[] queue;

  /** construct an empty randomized queue.
   *
   */
  public RandomizedQueue() {
    size = 0;
    length = 1;

    queue = (Item[]) new Object[1];
  }

  /** is the randomized queue empty?.
   *
   * @return true if the randomized queue is empty
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /** return the number of items on the randomized queue.
   *
   * @return the number of items on the randomized queue.
   */
  public int size() {
    return size;
  }

  /** Changes the queue size to the specified size.
   * @param newSize the new queue size.
   */
  private void resize(int newSize) {
//    System.out.println("Resizing from " + length + " to " + newSize);
    queue = Arrays.copyOfRange(queue, 0, newSize);
    length = newSize;
  }

  /** add the item.
   *
   * @param item which will be added into the randomized queue
   */
  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    if (size == length) {
      resize(length * 2);
    }
    queue[size++] = item;
  }

  /** remove and return a random item.
   *
   * @return which has been removed
   */
  public Item dequeue() {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }

    if (size <= length / 4) {
      resize(length / 2);
    }

    int randomIndex = StdRandom.uniform(size);
    Item item = queue[randomIndex];
    queue[randomIndex] = queue[--size]; // fill the hole which is generated be randomly accessed
    queue[size] = null; // avoid loitering
    return item;
  }

  /** return a random item (but do not remove it).
   *
   * @return a random item (but do not remove it).
   */
  public Item sample() {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }
    int randomIndex = StdRandom.uniform(size);
    return queue[randomIndex];
  }

  /** return an independent iterator over items in random order.
   *
   * @return an independent iterator over items in random order.
   */
  public Iterator<Item> iterator() {
    return new RandomQueueIterator();
  }

  private class RandomQueueIterator implements Iterator<Item> {

    private Item[] queueIterator;
    private int current;

    RandomQueueIterator() {
      queueIterator = queue.clone();
      shuffle(queueIterator);
      current = 0;
    }

    private void shuffle(Item[] shuffle) {
      Item tmp;
      for (int i = 0; i < size; i++) {
        int randomIndex = StdRandom.uniform(i, size);
        tmp = shuffle[randomIndex];
        shuffle[randomIndex] = shuffle[i];
        shuffle[i] = tmp;
      }
    }

    public boolean hasNext() {
      return current < size;
    }

    public void remove() {
      /* not supported */
      throw new java.lang.UnsupportedOperationException();
    }

    public Item next() {

      if (current > size - 1) {
        throw new java.util.NoSuchElementException();
      }
      return queueIterator[current++];
    }
  }

  /** .
   *
   * @param args default
   */
  public static void main(String[] args) {
    // unit testing (optional)
    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
    randomizedQueue.enqueue(1);
    randomizedQueue.enqueue(2);
    randomizedQueue.enqueue(3);
    Iterator<Integer> iterator = randomizedQueue.iterator();
    System.out.println(iterator.next());
    System.out.println(iterator.next());
    System.out.println(iterator.next());
    System.out.println(iterator.next());
  }
}

