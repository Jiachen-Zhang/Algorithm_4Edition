import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private int trials;
  private double[] probabilities;
  private double mean;
  private double stddev;

  /**perform trials independent experiments on an n-by-n grid.
   *
   * @param n the size of a quadratic sites
   * @param trials the number of the queries
   */
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new java.lang.IllegalArgumentException();
    }
    this.trials = trials;
    this.probabilities = new double[trials];
    int row;
    int col;
    Percolation percolation;
    for (int i = 0; i < trials; i++) {
      percolation = new Percolation(n);
      while (!percolation.percolates()) {
        row = StdRandom.uniform(1, n + 1);
        col = StdRandom.uniform(1, n + 1);
        percolation.open(row, col);
      }
      probabilities[i] = (double) percolation.numberOfOpenSites() / (n * n);
    }
    System.out.println("mean                    = " + mean());
    System.out.println("stddev                  = " + stddev());
    System.out.println(
        "95% confidence interval = [" + confidenceLo() + ", " + confidenceHi() + "]");
  }

  /**sample mean of percolation threshold.
   *
   * @return mean
   */
  public double mean() {
    mean = StdStats.mean(probabilities);
    return mean;
  }

  /** sample standard deviation of percolation threshold.
   *
   * @return stddev
   */
  public double stddev() {
    stddev = StdStats.stddev(probabilities);
    return stddev;
  }

  /** low endpoint of 95% confidence interval.
   *
   * @return confidenceLo
   */
  public double confidenceLo() {
    return mean - 1.96 * stddev / Math.sqrt(trials);
  }

  /** high endpoint of 95% confidence interval.
   *
   * @return confidenceHi
   */
  public double confidenceHi() {
    return mean + 1.96 * stddev / Math.sqrt(trials);
  }

  /** test client (described below).
   *
   * @param args default
   */
  public static void main(String[] args) {
    long startTime = System.currentTimeMillis(); // 获取开始时间
    PercolationStats percolationStats = new PercolationStats(200, 10);
    long endTime = System.currentTimeMillis(); // 获取结束时间
    System.out.println("程序运行时间：" + (endTime - startTime) + "ms"); // 输出程序运行时间
  }

}

