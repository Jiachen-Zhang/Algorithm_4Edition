import edu.princeton.cs.algs4.Insertion;
import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {

  private Point[] points;
  private double[][] pointSlope;
  private int size;

  /**
   * finds all line segments containing 4 points.
   *
   * @param points array which element is Point type
   */
  public BruteCollinearPoints(Point[] points) {
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
  }

  /**
   * the number of line segments.
   *
   * @return the number of line segments
   */
  public int numberOfSegments() {

    return 0;
  }

  /**
   * the line segments.
   *
   * @return a array which element is LineSegment type
   */
  public LineSegment[] segments() {
    for (int i = 0; i < size; i++) {
      for (int j = i + 1; j < size; j++) {
        for (int k = j + 1; k < size; k++) {
          for (int l = k + 1; l < size; l++) {
            /* Make a compare */

          }
        }

      }
    }
    return null;
  }

}
