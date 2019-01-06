import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private int numberOfOpenSites;
  private WeightedQuickUnionUF unionFind;
  private int size;
  private int top;
  private int bottom;
  private boolean[] open; //1: open 0:blocked

  /**create n-by-n grid, with all sites blocked.
   * @param n the size of a quadratic sites
   */
  public Percolation(int n) {
    if (n <= 0) {
      throw new java.lang.IllegalArgumentException();
    }
    unionFind = new WeightedQuickUnionUF(n * n + 2); // 0 as the upper bound, and N+1 as the bottom
    open = new boolean[n * n + 2];
    size = n;
    top = 0;
    bottom = n * n + 1;
    numberOfOpenSites = 0;
    for (int i = 1; i <= size; i++) {
      unionFind.union(top, i);
    }
    for (int i = size * (size - 1) + 1; i <= size * size; i++) {
      unionFind.union(bottom, i);
    }
  }


  /**open site (row, col) if it is not open already.
   * @param row the row of a site
   * @param col the column of a site
   */
  public void open(int row, int col) {
    if (!((1 <= row && row <= size) && (1 <= col && col <= size))) {
      throw new java.lang.IllegalArgumentException();
    }
    if (isOpen(row, col)) {
      return;
    }
    numberOfOpenSites++;
    int curIndex = (row - 1) * size + col;
    open[curIndex] = true;
    if (1 <= row - 1 && isOpen(row - 1, col)) { // up
      unionFind.union(curIndex, (row - 2) * size + col);
    }
    if (row + 1 <= size && isOpen(row + 1, col)) { // down
      unionFind.union(curIndex, (row) * size + col);
    }
    if (1 <= col - 1 && isOpen(row, col - 1)) { // left
      unionFind.union(curIndex, (row - 1) * size + col - 1);
    }
    if (col + 1 <= size && isOpen(row, col + 1)) { // right
      unionFind.union(curIndex, (row - 1) * size + col + 1);
    }
  }


  /**is site (row, col) open?.
   * @param row the row of a site
   * @param col the column of a site
   * @return true if the site has been opened
   */
  public boolean isOpen(int row, int col) {
    if (!((1 <= row && row <= size) && (1 <= col && col <= size))) {
      throw new java.lang.IllegalArgumentException();
    }
    return open[(row - 1) * size + col];
  }


  /**is site (row, col) full?.
   * @param row the row of a site
   * @param col the column of a site
   * @return true if the site is connected to the top
   */
  public boolean isFull(int row, int col) {
    if (!((1 <= row && row <= size) && (1 <= col && col <= size))) {
      throw new java.lang.IllegalArgumentException();
    }
    return unionFind.connected(top, (row - 1) * size + col);
  }

  /**number of open sites<br>.
   * @return number of open sites
   */
  public int numberOfOpenSites() {
    return numberOfOpenSites;
  }

  //

  /**does the system percolate?.
   * @return true if the the system percolates
   */
  public boolean percolates() {
    if (this.size == 1) {
      return isOpen(1, 1);
    }
    return unionFind.connected(top, bottom);
  }

  /**test client (optional).
   * @param args default
   */
  public static void main(String[] args) {
    Percolation percolation = new Percolation(1);
    percolation.open(1, 1);
    System.out.println(percolation.percolates());

  }

}
