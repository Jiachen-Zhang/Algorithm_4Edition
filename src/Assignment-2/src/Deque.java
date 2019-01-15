import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

  private Node first;
  private Node last;
  private int size;

  private class Node {
    Item item;
    Node pre;
    Node next;
  }

  /** construct an empty deque.
   *
   */
  public Deque() {
    first = null;
    last = null;
    size = 0;
  }

  /** is the deque empty?.
   *
   * @return true if the queue is empty
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /** get the size of the queue.
   *
   * @return the number of items on the deque
   */
  public int size() {
    return size;
  }

  /** .
   *  add the item to the front
   * @param item the item which is going to be added
   */
  public void addFirst(Item item) {
    if (item == null) {
      throw new java.lang.IllegalArgumentException();
    }

    Node oldFirst = first;
    first = new Node();
    first.item = item;
    if (isEmpty()) {
      last = first;
      first.next = null;
    } else {
      oldFirst.pre = first;
      first.next = oldFirst;
    }
    size++;
  }

  /** add the item to the end.
   *
   * @param item the item which is going to be added
   */
  public void addLast(Item item) {
    if (item == null) {
      throw new java.lang.IllegalArgumentException();
    }

    Node oldlast = last;
    last = new Node();
    last.item = item;
    if (isEmpty()) {
      first = last;
      last.next = null;
    } else {
      oldlast.next = last;
      last.pre = oldlast;
    }
    size++;
  }

  /** remove and return the item from the front.
   *
   * @return remove the item at the front
   */
  public Item removeFirst() {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }
    Item item = first.item;
    size--;
    if (isEmpty()) {
      first = null;
      last = null;
    } else {
      first = first.next;
      first.pre = null;
    }
    return item;
  }

  /** remove and return the item from the end.
   *
   * @return remove the item at the last
   */
  public Item removeLast() {
    if (isEmpty()) {
      throw new java.util.NoSuchElementException();
    }
    Item item = last.item;
    size--;
    if (isEmpty()) {
      first = null;
      last = null;
    } else {
      last = last.pre;
      last.next = null;
    }

    return item;
  }

  /** .
   *
   * @return an iterator over items in order from front to end
   */
  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {

    private Node current = first;

    public boolean hasNext() {
      return current != null;
    }

    public void remove() {
      /* not supported */
      throw new java.lang.UnsupportedOperationException();
    }

    public Item next() {
      if (current == null) {
        throw new java.util.NoSuchElementException();
      }
      Item item = current.item;
      current = current.next;
      return item;
    }
  }

  /** unit testing (optional).
   *
   * @param args default
   */
  public static void main(String[] args) {
    Deque<Integer> deque = new Deque<>();
    deque.addFirst(1);
    deque.addFirst(2);
    deque.addFirst(3);
    print(deque);
    System.out.println(deque.removeLast());
    print(deque);
    System.out.println(deque.removeLast());
    print(deque);
    System.out.println(deque.removeLast());
    print(deque);
    deque.addFirst(3);
    print(deque);
    System.out.println(deque.removeLast());
    deque.addLast(3);
    print(deque);
    System.out.println(deque.removeFirst());
  }

  private static void print(Deque<Integer> deque) {
    for (int i : deque) {
      System.out.print(i + ", ");
    }
    System.out.println(", size = " + deque.size());
  }

}
