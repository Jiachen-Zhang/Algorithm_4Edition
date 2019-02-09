import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

  private final Board initial;
  private SearchNode minNode;

  /**
   * find a solution to the initial board (using the A* algorithm).
   *
   * @param initial the original board
   */
  public Solver(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException("null pointer of Board in the constructor");
    }
    this.initial = initial;
    MinPQ<SearchNode> pq = new MinPQ<>();
    pq.insert(new SearchNode(initial, 0, null, false));
    pq.insert(new SearchNode(initial.twin(), 0, null, true));
    minNode = pq.min();
    while (!minNode.board.isGoal()) {
      minNode = pq.delMin();
      for (Board neighbor : minNode.board.neighbors()) {
        if (minNode.moves == 0) {
          pq.insert(new SearchNode(neighbor, minNode.moves + 1, minNode, minNode.isTwin));
        } else if (!neighbor.equals(minNode.previousNode.board)) {
          /* A critical optimization:
            don't enqueue a neighbor if its board is the same as the board of the predecessor search node */
          pq.insert(new SearchNode(neighbor, minNode.moves + 1, minNode, minNode.isTwin));
        }
      }
    }
  }

  private class SearchNode implements Comparable<SearchNode> {

    private SearchNode previousNode;
    private Board board;
    private int priority;
    private boolean isTwin;
    private int moves;
    /* A second optimization. To avoid recomputing the Manhattan priority of a search node from scratch each
     time during various priority queue operations, pre-compute its value when you construct the search node;
     save it in an instance variable; and return the saved value as needed.*/

    public SearchNode(Board board, int moves, SearchNode previousNode, boolean isTwin) {
      this.board = board;
      this.moves = moves;
      this.priority = moves + board.manhattan();
      this.previousNode = previousNode;
      this.isTwin = isTwin;
    }

    public int compareTo(SearchNode that) {
      return (this.priority - that.priority);
    }
  }

  /**
   * is the initial board solvable?
   *
   * @return true if solvable
   */
  public boolean isSolvable() {
    if (minNode.isTwin) {
      return false;
    }
    return minNode.board.isGoal();
  }

  /**
   * min number of moves to solve initial board; -1 if unsolvable.
   *
   * @return min number of moves to solve initial board; -1 if unsolvable
   */
  public int moves() {
    if (!isSolvable()) {
      return -1;
    }
    return minNode.moves;
  }

  /**
   * sequence of boards in a shortest solution; null if unsolvable.
   *
   * @return sequence of boards in a shortest solution; null if unsolvable
   */
  public Iterable<Board> solution() {
    if ( !isSolvable() ) {
      return null;
    }
    Stack<Board> stackSolution = new Stack<>();
    SearchNode current = minNode;
    while (current.previousNode != null) {
      stackSolution.push(current.board);
      current = current.previousNode;
    }
    stackSolution.push(initial);
    return stackSolution;
  }

  /**
   * solve a slider puzzle (given below).
   *
   * @param args default
   */
  public static void main(String[] args) {
    // create initial board from file
    In in = new In("test.txt");
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        blocks[i][j] = in.readInt();
      }
    }
    Board initial = new Board(blocks);
//    StdOut.println(initial.toString());
    // solve the puzzle
    Solver solver = new Solver(initial);
    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution()) {
        StdOut.println(board);
      }
    }

  }

}
