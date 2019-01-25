import edu.princeton.cs.algs4.Stack;
import java.util.Arrays;

public class BruteCollinearPoints {

  private Stack<LineSegment> stack;
  private Point[] points;

  /**
   * finds all line segments containing 4 points.
   *
   * @param points array which element is Point type
   */
  public BruteCollinearPoints(Point[] points) {
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
    for (int i = 0; i < size; i++) {
      for (int j = i + 1; j < size; j++) {
        double slopeIandJ = this.points[i].slopeTo(this.points[j]);
        for (int k = j + 1; k < size; k++) {
          double slopeJandK = this.points[j].slopeTo(this.points[k]);
          if (Math.abs(slopeIandJ - slopeJandK) > 1e-6) {
            continue;
          }
          for (int l = k + 1; l < size; l++) {
            double slopeKandL = this.points[k].slopeTo(this.points[l]);
            if (Math.abs(slopeJandK - slopeKandL) > 1e-6) {
              continue;
            }
            stack.push(new LineSegment(this.points[i], this.points[l]));
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
   * the line segments containing 4 points exactly once
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
