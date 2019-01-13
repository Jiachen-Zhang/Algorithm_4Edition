## Fundamental Data Types

- Value: collection of objects

- Operations: `insert`, `remove`, `iterate`, test if empty

- Intent is clear when we insert.

<img src="../pic/2-1.png" width="600px" align=center />

# Stack

## Implementation

### Linked-list implementation (space)

```java
public class LinkedStackOfStrings{
    private Node first = null;
    
    private class Node{
        String item;
        Node next;
    }
    
    public boolean isEmpty(){
        return first == null;
    }
    
    public void push(String item){
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
    }
    
    public String pop(){
        String item = first.item;
        first = first.next;
        return item;
    }     
}
```



### Array implementation (stack overflow)

```java
public class FixedCapacityOfStrings{
    private String[] s;
    private int N = 0;
    
    public FixedCapacityOfStrings(int capacity){
        s = new String[capacity];
    }
    
    public boolean isEmpty(){
        return N == 0;
    }
    
    public void push(String item){
        s[N++] = item;
    }
    
    public String pop(){
        /* avoid "loitering", garbage collector can reclaim memory only if no outstanding references */
        String item = s[--N];
        s[N] = null;
        return item;
    }
}
```

## Resizing Arrays

- If array is full, create a new array of **twice** the size, and copy items.

```java
public ResizingArrayStackOfStrings(){
    s = new String[1];
}

public void push(String item){
    if (N == s.length)
        resize(2 * s.length);
    s[N++] = item;
}

private void resizing(int capacity){
    String[] copy = new String[capacity];
    for (int i = 0; i < N; i++)
        copy[i] = s[i];
    s = copy;
}
```

Cost of inserting first N items:

N + (2 + 4 + 8 + ... + N) ~ 3N

- halve size of array s[] when array is 1/4 full (1/2 cause shrinking)

```java
public String pop(){
    String item = s[--N];
    s[N] = null;
    if(N > 0 && N = s.length/4)
    	resize(s.length/2);
    return item;
}
```

Invariant. Array is between 25% and 100% full.

## Tradeoff

- Linked-list Implementation
  - Every operation takes constant time in the **worst case**
  - Use extra time and space to deal with the links
- Resizing-array Implementation
  - Every operation takes constant **amortized** time
  - Less wasted space

## Memory Usage

- Linked-list Stack:
  - A stack with N items uses ~ 40N bytes. (reference slides page 11)
- Array-based Stack
  - ~ 8N (when full)    ~ 32N (when one-quarter full) (reference slides page 24)



# Queue

## Implementation

### Linked-list

```java
class LinkeQueueOfStrings{
    private Node first, last;
    
    private class Node{
        String item;
        Node next;
    }
    
    public boolean isEmpty(){
        return first == null;
    }
    
    public void enqueue(String item){
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if(isEmpty()) first = last;
        else          oldlast.next = last;
    }
    
    public String dequeue(){
        String item = first.item;
        first = first.next;
        if(isEmpty()) last = null;
        return item;
    }
}
```

### Array

```java

```

## Iterators

- What is an `Iterable`?

  - Has a method that returns an `Iterator`.

    Iterable Interface
    ```java
    public interface Iterable<Item>{
        Iterator<Item> iterator();
    }
    ```


  - Has methods `hasNext()` and `next()`.

    ```java
    public interface Iterator<Item>{
        boolean hasNext();
        Item next();
        void remove(); // optional: use at your own risk
    }
    ```

- Why make data structures `Iterable`?

  - Java support elegant client code.

    - `foreach` statement (shorthand)

      ```java
      for (String s : stack) {
          StdOut.println(s);
      }
      ```

    - equivalent code (longhand)

      ```java
      Iterator<String> i = stack.iterator();
      while (i.hasNext()) {
          String s = i.next();
          StdOut.println(s);
      }
      ```

### Linked-list Implementation

```java
public class Stack<Item> implements Iterable<Item>{
    // ...
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item>{
        
        private Node current = first;
        
        public boolean hasNext(){
            return current != null;
        }
        
        public void remove() {
            /* not supported */
        }
        
        public Item next(){
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}
```

### Array Implementation

```java
public class Stack<Item> implements Iterable<Item>{
    public Iterator<Item> iterator(){
        return new ReverseArrayIterator();
    }
    
    private class ReverseArrayIterator implements Iterator<Item>{
        private int i = N;
        
        public boolean hasNext(){
            return i > 0;
        }
        
        public void remove(){
            /* not supported */
        }
        
        public Item next(){
            return s[--i];
        }
    }
}
```

## Bag

> Do not care about the order

implementation: Stack (without `pop`) or Queue (without `dequeue`).

```java
public class Bag<Item> implements Iterable<Item>
	Bag() // creat an empty bag
	void add(Item x) // insert a new item onto bag
	int size() // number of items in bag
	Iterable<Item> iterator() // iterator for all items in bag
```



# Application

Implementations:

- `java.util.ArrayList` uses resizing array
- `java.util.LinkedList` uses linked list
- `java.util.Queue`: An interface, not an implementation of queue
- **Best Practice**: use our implementation of Stack, Queue and Bag.

Stack applications:

- Parsing in a compiler
- Java virtual machine
- Undo in a word processor
- Back button in a web browser
- PostScript language for printers

- Implementing function calls in a complier
  - Function call: `push` local environment and return address
  - Return: `pop` return address and local environment
- ...

Queue applications:

- 



## Arithmetic expression evaluation

- Goal: Evaluate infix expressions
- Two-stack algorithm: [E. W. Dijkstra]
  - Value: push onto the value stack
  - Operator: push on to the operator stack
  - left parenthesis: ignore
  - Right parenthesis:
    - pop operator and two value
    - push the result of applying that operator to those values onto the value stack.

![1547381032737](..\pic\2-2.png)

## Postfix

![1547381169724](..\pic\2.3.png)

Bottom line. **Postfix** or "reverse Polish" notation.

Applications:

- Postscript
- Forth
- calculators
- Java virtual machine
- …

