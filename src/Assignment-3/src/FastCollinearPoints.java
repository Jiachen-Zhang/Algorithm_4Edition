import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;

public class FastCollinearPoints {

  private Stack<LineSegment> stack;
  private Point[] points;

  /**
   * finds all line segments containing 4 or more points.
   * @param points array which element is Point type
   */
  public FastCollinearPoints(Point[] points) {
    /* IllegalArgumentException */
    if (points == null) {
      throw new java.lang.IllegalArgumentException("There is no point.");
    }
    this.points = points;
    int size = this.points.length;
    for (int i = 0; i < size; i++) {
      if (this.points[i] == null) {
        throw new java.lang.IllegalArgumentException("There exits a null point.");
      }
    }
    this.stack = new Stack<>();
    Arrays.sort(this.points);
    for (int i = 1; i < size; i++) {
      if (this.points[i - 1].compareTo(this.points[i]) == 0) {
        throw new java.lang.IllegalArgumentException("There exits a repeated point.");
      }
    }

    /* Not handling the 5-or-more case */
    for (int i = 0; i < size - 3; i++) {
      /* only compare the points which is on the right or top of it */
      Point[] tmpPoints = Arrays.copyOfRange(this.points, i + 1, size);
      Arrays.sort(tmpPoints, this.points[i].slopeOrder());
      double tmpSlope = this.points[i].slopeTo(tmpPoints[0]);
      int count = 2;
      for (int j = 0, length = tmpPoints.length; j < length; j++) {
        double curSlope = this.points[i].slopeTo(tmpPoints[j]);
        if (Math.abs(tmpSlope - curSlope) < 1e-6) {
          count++;
          if (count == 4) {
            stack.push(new LineSegment(this.points[i], tmpPoints[j]));
          }
        } else {
          tmpSlope = curSlope;
          count = 2;
        }
      }
    }

  }

  /**
   * the number of line segments
   * @return
   */
  public int numberOfSegments() {
    return stack.size();
  }

  /**
   * the line segments.
   * @return
   */
  public LineSegment[] segments() {
    LineSegment[] result = new LineSegment[stack.size()];
    int i = 0;
    for (LineSegment lineSegment : stack) {
      result[i++] = lineSegment;
    }
    return result;
  }

}
