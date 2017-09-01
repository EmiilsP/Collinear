import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
        if (points == null || points[0] == null) {// better do with try and catch but unittest won't let me.
            throw new IllegalArgumentException("Argumet can't be null.");
        }
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {

                if (points[j] == null || points[i] == null || points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Points can't be null or dublicated.");
                }
            }
        }
        ArrayList<LineSegment> actualSegments = new ArrayList<>();
        Point[] pointArray = Arrays.copyOf(points, points.length);
        Arrays.sort(pointArray);
        for (int p = 0; p < pointArray.length - 3; p++) {
            for (int q = p + 1; q < pointArray.length - 2; q++) {
                double firstSlope = pointArray[p].slopeTo(pointArray[q]);
                for (int r = q + 1; r < pointArray.length - 1; r++) {
                    double secondSlope = pointArray[q].slopeTo(pointArray[r]);
                    for (int s = r + 1; s < pointArray.length; s++) {
                        double thirdSlope = pointArray[r].slopeTo(pointArray[s]);
                        if (firstSlope == secondSlope && secondSlope == thirdSlope) {
                            actualSegments.add(new LineSegment(pointArray[p], pointArray[s]));
                        }
                    }
                }
            }
        }
        segments = new LineSegment[actualSegments.size()];
        segments = actualSegments.toArray(segments);

    }

    public int numberOfSegments() {    // the number of line segments
        return segments.length;
    }

    public LineSegment[] segments() {    // the line segments
        return Arrays.copyOf(segments, numberOfSegments());
    }

}
