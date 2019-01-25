import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;

public class FastCollinearPoints {

  private Stack<LineSegment> stack;

  /**
   * finds all line segments containing 4 or more tmpPoints.
   * @param points array which element is Point type
   */
  public FastCollinearPoints(Point[] points) {
    /* IllegalArgumentException */
    if (points == null) {
      throw new java.lang.IllegalArgumentException("There is no point.");
    }
    Point[] tmpPoints = points.clone();
    int size = tmpPoints.length;
    for (int i = 0; i < size; i++) {
      if (tmpPoints[i] == null) {
        throw new java.lang.IllegalArgumentException("There exits a null point.");
      }
    }
    this.stack = new Stack<>();
    Arrays.sort(tmpPoints);
    for (int i = 1; i < size; i++) {
      if (tmpPoints[i - 1].compareTo(tmpPoints[i]) == 0) {
        throw new java.lang.IllegalArgumentException("There exits a repeated point.");
      }
    }

    /* Not handling the 5-or-more case */
    for (int i = 0; i < size - 3; i++) {
      /* only compare the nextPoints which is on the right or top of it */
      Point[] nextPoints = Arrays.copyOfRange(tmpPoints, i + 1, size);
      Arrays.sort(nextPoints, tmpPoints[i].slopeOrder());
      double tmpSlope = tmpPoints[i].slopeTo(nextPoints[0]);
      int count = 2;
      for (Point tmp : nextPoints) {
        double curSlope = tmpPoints[i].slopeTo(tmp);
        if (Math.abs(tmpSlope - curSlope) < 1e-6) {
          count++;
          if (count == 4) {
            stack.push(new LineSegment(tmpPoints[i], tmp));
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
   * @return the number of line segments
   */
  public int numberOfSegments() {
    return stack.size();
  }

  /**
   * the line segments.
   * @return the line segments.
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
