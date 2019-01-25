import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;

public class BruteCollinearPoints {

  private Stack<LineSegment> stack;

  /**
   * finds all line segments containing 4 tmpPoints.
   *
   * @param points array which element is Point type
   */
  public BruteCollinearPoints(Point[] points) {
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
    for (int i = 0; i < size; i++) {
      for (int j = i + 1; j < size; j++) {
        double slopeIandJ = tmpPoints[i].slopeTo(tmpPoints[j]);
        for (int k = j + 1; k < size; k++) {
          double slopeJandK = tmpPoints[j].slopeTo(tmpPoints[k]);
          if (Math.abs(slopeIandJ - slopeJandK) > 1e-6) {
            continue;
          }
          for (int l = k + 1; l < size; l++) {
            double slopeKandL = tmpPoints[k].slopeTo(tmpPoints[l]);
            if (Math.abs(slopeJandK - slopeKandL) > 1e-6) {
              continue;
            }
            stack.push(new LineSegment(tmpPoints[i], tmpPoints[l]));
          }
        }
      }
    }
  }

  /**
   * the number of line segments.
   *
   * @return the number of line segments
   */
  public int numberOfSegments() {
    return stack.size();
  }

  /**
   * the line segments containing 4 tmpPoints exactly once
   * @return a array which element is LineSegment type
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
