import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;

public class PointSET {
  private TreeSet<Point2D> treeSet;

  /**
   * construct an empty set of points.
   */
  public PointSET(){
    this.treeSet = new TreeSet<>();
  }

  /**
   * is the set empty?.
   * @return true if empty
   */
  public boolean isEmpty() {
    return treeSet.isEmpty();
  }

  /**
   * number of points in the set.
   * @return number of points in the set
   */
  public int size() {
    return treeSet.size();
  }


  /**
   * add the point to the set (if it is not already in the set).
   * @param p the point which will be added
   */
  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    treeSet.add(p);
  }

  /**
   * does the set contain point p?.
   * @param p the point which is being checking.
   * @return true if p is in the set
   */
  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    return treeSet.contains(p);
  }

  /**
   * draw all points to standard draw.
   */
  public void draw(){

  }

  /**
   * all points that are inside the rectangle (or on the boundary).
   * @param rect the rectangle Object
   * @return an iterator that contains all points that are inside the rectangle (or on the boundary)
   */
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException();
    }
    Queue<Point2D> queue = new Queue<>();
    for (Point2D point2D : treeSet) { //TODO: 可能引发指针异常的bug
      if (rect.contains(point2D)) {
        queue.enqueue(point2D);
      }
    }
    return queue;
  }

  /**
   * a nearest neighbor in the set to point p; null if the set is empty.
   * @param p the check point
   * @return a nearest neighbor in the set to point p; null if the set is empty.
   */
  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    Point2D result = null;
    double distance = Double.POSITIVE_INFINITY;
    for (Point2D point2D : treeSet) {
      double tmpDistance = p.distanceSquaredTo(point2D);
      if (tmpDistance < distance) {
        distance = tmpDistance;
        result = point2D;
      }
    }
    return result;
  }


  /**
   * unit testing of the methods (optional).
   * @param args default
   */
  public static void main(String[] args){

  }
}
