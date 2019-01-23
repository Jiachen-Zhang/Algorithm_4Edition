import edu.princeton.cs.algs4.Insertion;
import java.util.Comparator;

public class FastCollinearPoints {

  private Point[] points;
  private double[][] pointSlope;
  private int size;

  /**
   * finds all line segments containing 4 or more points.
   * @param points array which element is Point type
   */
  public FastCollinearPoints(Point[] points) {
    if (points == null) {
      throw new java.lang.IllegalArgumentException();
    }
    this.points = points;
    Insertion.sort(this.points, new Comparator<Point>() {
      @Override
      public int compare(Point o1, Point o2) {
        if (o1 == null || o2 == null || o1.compareTo(o2) == 0) {
          throw new java.lang.IllegalArgumentException();
        }
        return o1.compareTo(o2);
      }
    });
    this.size = points.length;
    pointSlope = new double[size][size];
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (i == j) {
          continue;
        }
        pointSlope[i][j] = points[i].slopeTo(points[j]);
      }
    }
  }

  /**
   * the number of line segments
   * @return
   */
  public int numberOfSegments() {
    return 0;
  }

  /**
   * the line segments.
   * @return
   */
  public LineSegment[] segments() {
    return null;
  }

}
