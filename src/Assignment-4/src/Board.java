import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Arrays;

public class Board {

  private final char[] blocks;
  private final int N;
  private int blankIdx;
  private Board twin;
  private boolean hasTwin;

  /**
   * construct a board from an n-by-n array of blocks. (where blocks[i][j] = block in row i, column
   * j)
   *
   * @param iniBlocks the set of board
   */
  public Board(int[][] iniBlocks) {
    if (iniBlocks == null) {
      throw new IllegalArgumentException("null pointer of iniBlocks");
    }
    this.N = iniBlocks.length;
    this.blocks = get1DArray(iniBlocks);
    this.twin = null;
    this.hasTwin = false;
  }

  /**
   * board N n.
   *
   * @return the N of the board
   */
  public int dimension() {
    return N;
  }

  /**
   * number of blocks out of place.
   *
   * @return the number of blocks out of place
   */
  public int hamming() {
    int hamming = 0;
    for (int i = 0, length = blocks.length; i < length; i++) {
      if (blocks[i] != 0 && blocks[i] != i + 1) {
        hamming++;
      }
    }
    return hamming;
  }

  /**
   * sum of Manhattan distances between blocks and goal. Manhattan distances: sum of the vertical
   * and horizontal distance
   *
   * @return Manhattan distance
   */
  public int manhattan() {
    int manhattan = 0;
    for (int i = 0, length = blocks.length; i < length; i++) {
      if (blocks[i] != 0 && blocks[i] != i + 1) {
        int col = Math.abs(i % N - (blocks[i] - 1) % N); // Math.abs(cur - goal)
        int row = Math.abs(i / N - (blocks[i] - 1) / N);
//        System.out.println("i = " + (i+1) + ", row = " + row + ", col = " + col);
//        System.out.println("col = " + i % N + "-" + (blocks[i]-1) % N );
//        System.out.println("row = " + i / N + "-" + (blocks[i]-1) / N );
        manhattan += col + row;
      }
    }
    return manhattan;
  }

  /**
   * is this board the goal board?
   *
   * @return true if this board is on the right position
   */
  public boolean isGoal() {
    for (int i = 0, length = blocks.length - 1; i < length; i++) {
      if (blocks[i] != i + 1) {
        return false;
      }
    }
    return true;
  }

  /**
   * a board that is obtained by exchanging any pair of blocks.
   *
   * @return a board that is obtained by exchanging any pair of blocks.
   */
  public Board twin() {
    if (!hasTwin) {
      twin = getTwin();
      hasTwin = true;
    }
    return twin;
  }

  private Board getTwin() {
    char[] blocksClone = blocks.clone();
    /* choose a block (which is not 0) */
    int idx;
    do {
      idx = StdRandom.uniform(N * N);
    } while (blocks[idx] == 0);
    /* exchange to a random direction */
    boolean swapSuccess = false;
    do {
      /* choose an exchange direction */
      int choice = StdRandom.uniform(4);
      switch (choice) {
        case 0: // swapUp
          if (getRow(idx) != 0 && blocksClone[idx - N] != 0) {
            swapUp(blocksClone, idx);
            swapSuccess = true;
          }
          break;
        case 1: // swapDown
          if (getRow(idx) != N - 1 && blocksClone[idx + N] != 0) {
            swapDown(blocksClone, idx);
            swapSuccess = true;
          }
          break;
        case 2: // swapLeft
          if (getCol(idx) != 0 && blocksClone[idx - 1] != 0) {
            swapLeft(blocksClone, idx);
            swapSuccess = true;
          }
          break;
        case 3: // swapRight
          if (getCol(idx) != N - 1 && blocksClone[idx + 1] != 0) {
            swapRight(blocksClone, idx);
            swapSuccess = true;
          }
          break;
      }
    } while (!swapSuccess);

    return new Board(get2DArray(blocksClone));
  }

  /**
   * does this board equal y?
   *
   * @param y that board
   * @return true if this board equals that board.
   */
  public boolean equals(Object y) {
    if (y == null) {
      return false;
    }
    if (y == this) {
      return true;
    }
    if (y.getClass() != this.getClass()) {
      return false;
    }
    return Arrays.equals(blocks, ((Board) y).blocks);
  }

  /**
   * all neighboring boards.
   *
   * @return all neighboring boards
   */
  public Iterable<Board> neighbors() {
    Queue<Board> bq = new Queue<>();
    char[] neighbor;
    if (getRow(blankIdx) != 0) {
      neighbor = blocks.clone();
      swapUp(neighbor, blankIdx);
      bq.enqueue(new Board(get2DArray(neighbor)));
    }
    if (getRow(blankIdx) != N - 1) {
      neighbor = blocks.clone();
      swapDown(neighbor, blankIdx);
      bq.enqueue(new Board(get2DArray(neighbor)));
    }
    if (getCol(blankIdx) != 0) {
      neighbor = blocks.clone();
      swapLeft(neighbor, blankIdx);
      bq.enqueue(new Board(get2DArray(neighbor)));
    }
    if (getCol(blankIdx) != N - 1) {
      neighbor = blocks.clone();
      swapRight(neighbor, blankIdx);
      bq.enqueue(new Board(get2DArray(neighbor)));
    }
    return bq;
  }

  /**
   * string representation of this board. output format: The input and output format for a board is
   * the board N n followed by the n-by-n initial board, using 0 to represent the blank square
   *
   * @return string representation of this board
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(String.format("%d\n", N));
    int k = 0;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        s.append(String.format("%2d ", (int) blocks[k++]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  /* auxiliary functions */
  private int getCol(int idx) {
    return idx % N;
  }

  private int getRow(int idx) {
    return idx / N;
  }

  private void swapUp(char[] twinBlocks, int idx) {
    char temp = twinBlocks[idx];
    twinBlocks[idx] = twinBlocks[idx - N];
    twinBlocks[idx - N] = temp;
  }

  private void swapDown(char[] twinBlocks, int idx) {
    char temp = twinBlocks[idx];
    twinBlocks[idx] = twinBlocks[idx + N];
    twinBlocks[idx + N] = temp;
  }

  private void swapLeft(char[] twinBlocks, int idx) {
    char temp = twinBlocks[idx];
    twinBlocks[idx] = twinBlocks[idx - 1];
    twinBlocks[idx - 1] = temp;
  }

  private void swapRight(char[] twinBlocks, int idx) {
    char temp = twinBlocks[idx];
    twinBlocks[idx] = twinBlocks[idx + 1];
    twinBlocks[idx + 1] = temp;
  }

  private int[][] get2DArray(char[] blocks1D) {
    int idx = 0;
    int[][] blocks2D = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        blocks2D[i][j] = blocks1D[idx++];
      }
    }
    return blocks2D;
  }

  private char[] get1DArray(int[][] blocks2D) {
    int idx = 0;
    char[] blocks1D = new char[N * N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        blocks1D[idx] = (char)blocks2D[i][j];
        if (blocks1D[idx] == 0) {
          blankIdx = idx;
        }
        idx++;
      }
    }
    return blocks1D;
  }

  /**
   * unit tests (not graded).
   *
   * @param args default
   */
  public static void main(String[] args) {
    In in = new In("test.txt");
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        blocks[i][j] = in.readInt();
      }
    }
    Board initial = new Board(blocks);

    StdOut.print(initial.toString());
    StdOut.print(initial.twin().toString());
    StdOut.println(initial.hamming());
    StdOut.println(initial.manhattan());
    StdOut.println(initial.dimension());
    StdOut.println(initial.isGoal());

    for (Board b : initial.neighbors()) {
      StdOut.println(b.toString());
      for (Board d : b.neighbors()) {
        StdOut.println("===========");
        StdOut.println(d.toString());
        StdOut.println("===========");
      }
    }
  }
}
