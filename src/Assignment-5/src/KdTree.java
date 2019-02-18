import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class KdTree {
  private static final boolean VERTICAL = true;
  private static final boolean HORIZONTAL = false;



  private int size;
  private Node root;

  private static class Node {
    private Point2D p;      // the point
    private RectHV rect;    // the axis-aligned rectangle corresponding to this node
    private Node lb;        // the left/bottom subtree
    private Node rt;        // the right/top subtree

    public Node(Point2D point2D, RectHV rectHV) {
      this.p = point2D;
      this.rect = rectHV;
    }

    @Override
    public String toString() {
      StringBuilder s = new StringBuilder();
      s.append(p.toString());
      s.append(" ");
      s.append(rect.toString());
      if (lb != null) {
        s.append("\nlb: ");
        s.append(lb.toString());
      }
      if (rt != null) {
        s.append("\nrt: ");
        s.append(rt.toString());
      }
      return s.toString();
    }
  }

  /**
   * construct an empty set of points.
   */
  public KdTree() {
    this.size = 0;
  }

  /**
   * is the set empty?.
   * @return true if empty
   */
  public boolean isEmpty() {
    return this.size == 0;
  }

  /**
   * number of points in the set.
   * @return number of points in the set
   */
  public int size() {
    return size;
  }

  /**
   * add the point to the set (if it is not already in the set).
   * @param p the point which will be added
   */
  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    root = insert(root, p, VERTICAL, 0, 0, 1, 1);
  }

  private Node insert(Node node, Point2D point2D, boolean orientation, double xmin, double ymin, double xmax, double ymax) {
    if (node == null) {
      this.size++;
      return new Node(point2D, new RectHV(xmin, ymin, xmax, ymax));
    }

    if (node.p.equals(point2D)) {
      return node;
    }

    if (orientation == VERTICAL) {
      double cmp = point2D.x() - node.p.x();
      if (cmp < 0) {
        node.lb = insert(node.lb, point2D, !orientation, node.rect.xmin(), node.rect.ymin(), node.p.x(), node.rect.ymax());
      } else {
        node.rt = insert(node.rt, point2D, !orientation, node.p.x(), node.rect.ymin(), node.rect.xmax(), node.rect.ymax());
      }
    } else {
      double cmp = point2D.y() - node.p.y();
      if (cmp < 0) {
        node.lb = insert(node.lb, point2D, !orientation, node.rect.xmin(), node.rect.ymin(), node.rect.xmax(), node.p.y());
      } else {
        node.rt = insert(node.rt, point2D, !orientation, node.rect.xmin(), node.p.y(), node.rect.xmax(), node.rect.ymax());
      }
    }

    return node;
  }


  /**
   * does the set contain point p?.
   * @param p the point which is being checking.
   * @return true if p is in the set
   */
  public boolean contains(Point2D p) {
    return contains(root, p, VERTICAL);
  }

  private boolean contains(Node x, Point2D p, boolean orientation) {
    if (x == null) {
      return false;
    }

    if (x.p.equals(p)) {
      return true;
    }

    double cmp;
    if (orientation == VERTICAL) {
      cmp = p.x() - x.p.x();
    } else {
      cmp = p.y() - x.p.y();
    }
    if (cmp < 0) {
      return contains(x.lb, p, !orientation);
    } else {
      return contains(x.rt, p, !orientation);
    }
  }


  /**
   * draw all points to standard draw.
   */
  public void draw() {
    draw(root, VERTICAL);
  }

  private void draw(Node x, boolean orientation) {
    /* set the color and size of the Pen for line-drawing */
    StdDraw.setPenRadius();
    if (orientation == VERTICAL) {
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
    } else {
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
    }

    /* set the color and size of the Pen for point-drawing */
    StdDraw.setPenRadius(0.01);
    StdDraw.setPenColor(StdDraw.BLACK);
    x.p.draw();

    if (x.lb != null) {
      draw(x.lb, !orientation);
    }
    if (x.rt != null) {
      draw(x.rt, !orientation);
    }
  }

  /**
   * all points that are inside the rectangle (or on the boundary).
   * @param rect the rectangle Object
   * @return an iterator that contains all points that are inside the rectangle (or on the boundary)
   */
  public Iterable<Point2D> range(RectHV rect) {
    Queue<Point2D> q = new Queue<Point2D>();
    range(root, rect, q);
    return q;
  }

  private void range(Node x, RectHV rect, Queue<Point2D> q) {
    if (x == null) {
      return;
    }

    if (!x.rect.intersects(rect)) {
      return;
    }

    if (rect.contains(x.p)) {
      q.enqueue(x.p);
    }

    range(x.lb, rect, q);

    range(x.rt, rect, q);
  }

  /**
   * a nearest neighbor in the set to point p; null if the set is empty.
   * @param p the check point
   * @return a nearest neighbor in the set to point p; null if the set is empty.
   */
  public Point2D nearest(Point2D p) {
    return nearest(root, p, Double.POSITIVE_INFINITY);
  }

  // Find the nearest point that is closer than distance
  private Point2D nearest(Node x, Point2D p, double distance) {
    if (x == null) {
      return null;
    }

    if (x.rect.distanceTo(p) >= distance) {
      return null;
    }

    Point2D nearestPoint = null;
    double nearestDistance = distance;
    double d;

    d = p.distanceTo(x.p);
    if (d < nearestDistance) {
      nearestPoint = x.p;
      nearestDistance = d;
    }

    // Choose subtree that is closer to point.

    Node firstNode = x.lb;
    Node secondNode = x.rt;

    if (firstNode != null && secondNode != null) {
      if (firstNode.rect.distanceSquaredTo(p) > secondNode.rect.distanceSquaredTo(p)) {
        firstNode = x.rt;
        secondNode = x.lb;
      }
    }

    Point2D firstNearestPoint = nearest(firstNode, p, nearestDistance);
    if (firstNearestPoint != null) {
      d = p.distanceSquaredTo(firstNearestPoint);
      if (d < nearestDistance) {
        nearestPoint = firstNearestPoint;
        nearestDistance = d;
      }
    }

    Point2D secondNearestPoint = nearest(secondNode, p, nearestDistance);
    if (secondNearestPoint != null) {
      d = p.distanceSquaredTo(secondNearestPoint);
      if (d < nearestDistance) {
        nearestPoint = secondNearestPoint;
        nearestDistance = d;
      }
    }

    return nearestPoint;
  }


  /**
   * unit testing of the methods (optional).
   * @param args default
   */
  public static void main(String[] args){
    KdTree kdtree = new KdTree();
    kdtree.insert(new Point2D(0.206107, 0.095492));
    kdtree.insert(new Point2D(0.975528, 0.654508));
    kdtree.insert(new Point2D(0.024472, 0.345492));
    kdtree.insert(new Point2D(0.793893, 0.095492));
    kdtree.insert(new Point2D(0.793893, 0.904508));
    kdtree.insert(new Point2D(0.975528, 0.345492));
    assert kdtree.size() == 6;
    kdtree.insert(new Point2D(0.206107, 0.904508));
    assert kdtree.size() == 7;
    kdtree.draw();
    StdOut.println("size: " + kdtree.size());
  }
}
