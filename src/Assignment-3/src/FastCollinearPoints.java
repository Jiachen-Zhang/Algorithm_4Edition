import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

  /**
   * @lineSegments the result set of LineSegments
   */
  private final ArrayList<LineSegment> lineSegments;


  /**
   * finds all line segments containing 4 or more tmpPoints.
   * Time Complexity: O(N*NlgN)
   * @param points array which element is Point type
   */
  public FastCollinearPoints(Point[] points) {
    /* check if the data if legal and return a sorted copy */
    Point[] pointsClone = isLegal(points);
    int N = pointsClone.length;
    lineSegments = new ArrayList<>();
    /* We only consider the points and others which are on the right or above the start point */
    for (int i = 0, size = N - 3; i < size; i++) {
      Point startPoint = pointsClone[i];
      /* the slope between the startPoint and points which are on the left or below the start point */
      double[] preSlopes = new double[i];
      for (int j = 0; j < i; j++) {
        preSlopes[j] = startPoint.slopeTo(pointsClone[j]);
      }
      /* points which are on the right or above the start point */
      Point[] nextPoints = new Point[N - i - 1];
      for (int j = 0, length = nextPoints.length; j < length; j++) {
        nextPoints[j] = pointsClone[i + j + 1];
      }
      /* sort the nextPoints[] by the slopeOrder related to the startPoint */
      Arrays.sort(nextPoints, startPoint.slopeOrder());  //Time Complexity: O(NlgN)
      Arrays.sort(preSlopes); // sort for binary search  //Time Complexity: O(NlgN)
      addLineSegemnt(preSlopes, startPoint, nextPoints); //Time Complexity: O(NlgN)
    }
  }

  /**
   * Time Complexity: O(NlgN)
   * @param preSlopes the slope between the startPoint and points which are on the left or below the start point
   * @param startPoint one of the points
   * @param nextPoints points which are on the right or above the start point
   */
  private void addLineSegemnt(double[] preSlopes, Point startPoint, Point[] nextPoints) {
    int count = 1; // store the number of collinear points
    double preSlope = startPoint.slopeTo(nextPoints[0]);
    int length = nextPoints.length;
    for (int i = 1; i < length; i++) {
      double curSlope = startPoint.slopeTo(nextPoints[i]);
      if (preSlope == curSlope) {
        count++;
      } else {
        if (count >= 3 && !isSubline(preSlope, preSlopes)) {
          lineSegments.add(new LineSegment(startPoint, nextPoints[i - 1]));
        }
        count = 1;
      }
      preSlope = curSlope;
    }
    if (count >= 3 && !isSubline(preSlope, preSlopes)) {
      lineSegments.add(new LineSegment(startPoint, nextPoints[length - 1]));
    }
  }

  /**
   * Based on Binary Search
   * Time Complexity: O(lgN)
   * @param curSlope key
   * @param preSlopes set
   * @return true if exists
   */
  private boolean isSubline(double curSlope, double[] preSlopes){
    int lo = 0;
    int hi = preSlopes.length - 1;
    while (lo <= hi) {
      int mid = (lo + hi) / 2;
      if (curSlope > preSlopes[mid]) {
        lo = mid + 1;
      } else if (curSlope < preSlopes[mid]) {
        hi = mid - 1;
      } else {
        return true;
      }
    }
    return false;
  }

  private Point[] isLegal(Point[] points) {
    if (points == null) {
      throw new java.lang.IllegalArgumentException("There is no point.");
    }
    int size = points.length;
    for (Point point : points) {
      if (point == null) {
        throw new java.lang.IllegalArgumentException("There exits a null point.");
      }
    }
    Point[] pointsClone = points.clone();
    Arrays.sort(pointsClone);
    for (int i = 1; i < size; i++) {
      if (pointsClone[i - 1].compareTo(pointsClone[i]) == 0) {
        throw new java.lang.IllegalArgumentException("There exits a repeated point.");
      }
    }
    return pointsClone;
  }

  /**
   * the number of line segments
   *
   * @return the number of line segments
   */
  public int numberOfSegments() {
    return lineSegments.size();
  }

  /**
   * the line segments.
   *
   * @return the line segments.
   */
  public LineSegment[] segments() {
    return lineSegments.toArray(new LineSegment[numberOfSegments()]);
  }

}
