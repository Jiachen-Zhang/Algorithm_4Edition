import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

  private Board initial;
  private Board goalBoard;
  private int N;
  private MinPQ<SearchNode> pq;
  private MinPQ<SearchNode> pqTwin;

  /**
   * find a solution to the initial board (using the A* algorithm).
   * @param initial the original board
   */
  public Solver(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException("null pointer of Board in the constructor");
    }
    this.initial = initial;
    this.N = initial.dimension();
    this.goalBoard = getGoalBoard(N);
    this.pq = new MinPQ<SearchNode>();
    this.pqTwin = new MinPQ<SearchNode>();

    SearchNode minNode;
    SearchNode minNodeTwin;
    pq.insert(new SearchNode(initial, 0, null));
    pqTwin.insert(new SearchNode(initial.twin(), 0, null));
    while(!pq.min().board.equals(goalBoard) && !pqTwin.min().board.equals(goalBoard)) {
      minNode = pq.delMin();
      minNodeTwin = pqTwin.delMin();
      for (Board neighbor: minNode.board.neighbors()) {
        if (minNode.moves == 0) {
          pq.insert(new SearchNode(neighbor, minNode.moves+1, minNode));
        } else if (!neighbor.equals(minNode.previousNode.board)) {
          pq.insert(new SearchNode(neighbor, minNode.moves+1, minNode));
        }
      }
      for (Board neighbor: minNodeTwin.board.neighbors()) {
        if (minNodeTwin.moves == 0) {
          pqTwin.insert(new SearchNode(neighbor, minNodeTwin.moves+1, minNodeTwin));
        }
        else if (!neighbor.equals(minNodeTwin.previousNode.board)) {
          pqTwin.insert(new SearchNode(neighbor, minNodeTwin.moves+1, minNodeTwin));
        }
      }
    }
  }

  private Board getGoalBoard(int dimension) {
    int[][] blocks = new int[dimension][dimension];
    int num = 1;
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        blocks[i][j] = num++;
      }
    }
    blocks[dimension-1][dimension-1] = 0;
    return new Board(blocks);
  }

  private class SearchNode implements Comparable<SearchNode> {
    private Board board;
    private int moves;
    private int priority;
    private SearchNode previousNode;

    public SearchNode(Board board, int moves, SearchNode previousNode) {
      this.board = board;
      this.moves = moves;
      this.priority = moves + board.manhattan();
      this.previousNode = previousNode;
    }

    public int compareTo(SearchNode that) {
      return (this.priority - that.priority);
    }
  }

  /**
   * is the initial board solvable?
   * @return true if solvable
   */
  public boolean isSolvable() {
    if (pq.min().board.equals(goalBoard)) {
      return true;
    }
    if (pqTwin.min().board.equals(goalBoard)) {
      return false;
    }
    return false;
  }

  /**
   * min number of moves to solve initial board; -1 if unsolvable.
   * @return min number of moves to solve initial board; -1 if unsolvable
   */
  public int moves() {
    if(!isSolvable()) {
      return -1;
    }
    return pq.min().moves;
  }

  /**
   * sequence of boards in a shortest solution; null if unsolvable.
   * @return sequence of boards in a shortest solution; null if unsolvable
   */
  public Iterable<Board> solution() {
    if(!isSolvable()) return null;
    Stack<Board> stackSolution = new Stack<Board>();
    SearchNode current = pq.min();
    while (current.previousNode!=null) {
      stackSolution.push(current.board);
      current = current.previousNode;
    }
    stackSolution.push(initial);
    return stackSolution;
  }

  /**
   * solve a slider puzzle (given below).
   * @param args default
   */
  public static void main(String[] args) {

  }

}
