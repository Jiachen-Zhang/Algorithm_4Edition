# PriorityQueues

- Collection: Insert and delete items
- Stack: Remove the item most recently added
- Queue: Remove the item least recently added 
- Randomized queue: Remove a random item

- Priority queue: Remove the **largest** (or **smallest**) item.

## APIs and Elementary Implementations

> Requirement: Generic items are Comparable

```java
public class MaxPQ<Key extend Comparable<Key>>{
  MaxPQ()				// creat an empty priority queue
    
  MaxPQ(Key[] a)	// creat a priority queue with given keys
  
  void insert(Key v)	// insert a key into the priority queue
  
  Key delMax()				// return and remove the largest key
    
  boolean isEmpty()		// is the priority queue empty
    
  Key max()						// return the largest key
    
  int size()					// number of entries in the priority queue

}
```

### Priority queue applications

- Event-driven simulation.	[customers in a line, coliding particles]
- Numerical computaion.      [reducing roundoff error]
- Data compression.              [Huffman code]
- Graph searching.                  [Number theory]
- Artificial intelligence.            [A* search]
- Statistics.                                [maintain largest M values in a sequence]
- Operating system.                 [load balancing, interrupt handling]
- Discrete optimization.          [bin packing, scheduling]
- Spam filtering.                        [Bayesian spam filter]

> Generalizes: stack, queue, randomized queue

> Challenge. Find the largest M items in a stream of N items. (M << N)
>
> Constraint. Not enough memory to store N items

## Binary Heaps

### Complete binary tree

- Binary tree

  Empty or node with links to left and right binary trees.

- Complete tree

  Perfect balanced, except for bottom level

> Poperty. Height of complete tree with N nodes is [lgN]
>
> Pf. Height only increases when N is a power of 2.

### Binary heap representations

- Binary heap. Array representation of a heap-ordered complete binary tree.
- Heap-ordered binary tree.
  - Keys in nodes
  - Parent's key no smaller than children's keys
- Array representation
  - Indices start at 1
  - Take nodes in level order
  - No explicit links needed.

#### Binary heap properties
- Proposition
  - Largest key is a[1], which is root of binary tree
  - Can use a array indices to move through tree
    - Parent of node at $k$ is at $k/2$
    - Children of node at $k$ are $2k$ and $2k+1​$

### Promotion in a heap

> Scenario.	Child's key becomes larger key than its parent's key

To eliminate the violation:

- Exchange key in child with key in parent
- Repeat until heap order restored

### Demotion in a heap

> Scenario.	Parent's key becomes smaller than one (or both) of its children's.

To eliminate the violation:

- Exchange key in parent with key in larger child.
- Repeat until heap order restored.

### Java Implementation

```java
public class MaxPQ<Key extends Comparable<Key>>{
  private Key[] pq;
  private int N;
  
  public MaxPQ(int capacity){
    pq = (Key[]) new Comparable[capacity+1];
  }
  
  public boolean isEmpty(){
    return N == 0;
  }
  
  public void insert(Key key){
    pq[++N] = key;
    swim(N;)
  }
    
  public Key delMax(){
    Key max = pq[1];
    exch(1, N--);
    sink(1);
    pq[N+1] = null;
    return max;
  }
  
  private void swim(int k){
    while (k > 1 && less(k/2, k)) {
      exch(k, k/2);
      k = k/2;
    }
  }
  
  private void sink(int k){
    while (2*k <= N) {
      int j = 2*k;
    	if (j < N && less(j, j+1))	j++;
      if (!less(k, j))	break;
      exch(k, j);
      k = j;
    }
  }
  
  private boolean less(int i, int j){
    return pq[i].compareTo(pq[j]) < 0;
  }
  
  private void exch(int i, int j){
    Key t = pq[i];
    pq[i] = pq[j];
    pq[j] = t;
  }
}
```

### Binary heap cpnsiderations

- Immutability of keys
  - Assumption: client does not change keys while they're on the PQ.
  - Best practice: use immutable keys.
- Underflow and overflow
  - Underflow: throw exception if deleting from empty PQ.
  - Overflow: add no-arg constructor and use resizing array.
- Minimum-oriented priority queue
  - Replace `less()` with `greater()`
  - Implement `greater()`

#### Immutability: implementing in Java

- Data type: Set of values and operations on those values
- Immutable data type: Can't change the data type value once created.

```java
public final class Vector{
  private final int N;
  private final double[] data;
  
  public Vector(double[] data){
    this.N = data.length;
    this.data = new double[N];
    for (int i = 0; i < N; i++)
      this.data[i] = data[i];
  }
}
```

- Immutable: String, Integer, Double, Color, Vector, Transaction, Point2D
- Mutable: StringBuilder,  Stack, Counter, Java array

#### Immutability: Properties

- Data type: Set of values and operations on those values
- Immutable data type: Can't change the data type value once created
- Advantages
  - Simplifies debugging
  - Safer in presence of hostile code
  - Simplifies concurrent programming
  - Safe to use as key in priority queue or symbol table
- Disadvantages
  - Must create ew object for each data type value.