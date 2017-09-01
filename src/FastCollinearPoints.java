import java.util.*;


public class FastCollinearPoints {

    private HashMap<Double, List<Point>> actualSegments = new HashMap<>();
    private List<LineSegment> segments = new ArrayList<>();


    public FastCollinearPoints(Point[] points) {
        if (points == null || points[0] == null) {  // better do with try and catch but unittest won't let me.
            throw new IllegalArgumentException("Argumet can't be null.");
        }
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {

                if (points[j] == null || points[i] == null || points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Points can't be null or dublicated.");
                }
            }
        }
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        for (Point startPoint : points) {
            Arrays.sort(pointsCopy, startPoint.slopeOrder());
            List<Point> slopePoints = new ArrayList<>();
            double slope = 0;
            double previousSlope = Double.NEGATIVE_INFINITY;

            for (int i = 1; i < pointsCopy.length; i++) {
                slope = startPoint.slopeTo(pointsCopy[i]);
                if (slope == previousSlope) {
                    slopePoints.add(pointsCopy[i]);
                } else {
                    if (slopePoints.size() >= 3) {
                        slopePoints.add(startPoint);
                        addSegment(slopePoints, previousSlope);
                    }
                    slopePoints.clear();
                    slopePoints.add(pointsCopy[i]);
                }
                previousSlope = slope;
            }
            if (slopePoints.size() >= 3) {
                slopePoints.add(startPoint);
                addSegment(slopePoints, slope);
            }
        }
    }

    private void addSegment(List<Point> slopePoints, double slope) {
        List<Point> endPoints = actualSegments.get(slope);
        Collections.sort(slopePoints);

        Point startPoint = slopePoints.get(0);
        Point endPoint = slopePoints.get(slopePoints.size() - 1);

        if (endPoints == null) {
            endPoints = new ArrayList<>();
            endPoints.add(endPoint);
            actualSegments.put(slope, endPoints);
            segments.add(new LineSegment(startPoint, endPoint));
        } else {
            for (Point currentEndPoint : endPoints) {
                if (currentEndPoint.compareTo(endPoint) == 0) {
                    return;
                }
            }
            endPoints.add(endPoint);
            segments.add(new LineSegment(startPoint, endPoint));
        }
    }


    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    public static void main(String[] args) {
        Point a = (null);
        Point b = new Point(1, 2);
        Point[] zx = new Point[]{null};
        FastCollinearPoints pp = new FastCollinearPoints(zx);
    }


}