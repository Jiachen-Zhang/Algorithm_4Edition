import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

  private final int trials;
  private final double[] probabilities;
  private double mean;
  private double stddev;
  private boolean hasMean;
  private boolean hasStddev;
  // TODO Note that there are no specific restraints on memory usage,
  // therefore try to solve this problem with additional memory.
  // Do keep in mind that constant time does not mean a single API call.
  // This means that regardless of input size,
  // API will be invoked fixed number of times, one, two or whatever << N.
  /** perform trials independent experiments on an n-by-n grids.
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
    hasMean = false;
    hasStddev = false;
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

  }

  /** sample mean of percolation threshold.
   *
   * @return mean
   */
  public double mean() {
    mean = StdStats.mean(probabilities);
    hasMean = true;
    return mean;
  }

  /** sample standard deviation of percolation threshold.
   *
   * @return stddev
   */
  public double stddev() {
    stddev = StdStats.stddev(probabilities);
    hasStddev = true;
    return stddev;
  }

  /** low endpoint of 95% confidence interval.
   *
   * @return confidenceLo
   */
  public double confidenceLo() {
    if (!hasMean) {
      mean();
    }
    if (!hasStddev) {
      stddev();
    }
    return mean - 1.96 * stddev / Math.sqrt(trials);
  }

  /** high endpoint of 95% confidence interval.
   *
   * @return confidenceHi
   */
  public double confidenceHi() {
    if (!hasMean) {
      mean();
    }
    if (!hasStddev) {
      stddev();
    }
    return mean + 1.96 * stddev / Math.sqrt(trials);
  }

  /** test client (described below).
   *
   * @param args default
   */
  public static void main(String[] args) {
    PercolationStats percolationStats = new PercolationStats(200, 10);
    System.out.println("mean                    = " + percolationStats.mean());
    System.out.println("stddev                  = " + percolationStats.stddev());
    System.out.println(
        "95% confidence interval = [" + percolationStats.confidenceLo()
            + ", " + percolationStats.confidenceHi() + "]");
  }

}

