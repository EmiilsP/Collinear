import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private ArrayList<LineSegment> actualSegments;
    private ArrayList<Point> firstPoint;
    private ArrayList<Point> lastPoint;
    private Point[] pointArray;

    public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 or more points
        for (int x = 0; x < points.length - 1; x++) {
            for (int y = x + 1; y < points.length; y++) {
                if (points[x].compareTo(points[y]) == 0) {
                    throw new IllegalArgumentException("Dublicated points");
                }
            }
        }
        actualSegments = new ArrayList<>();
        firstPoint = new ArrayList<>();
        lastPoint = new ArrayList<>();
        int size = points.length;
        pointArray = Arrays.copyOf(points, size);
        Arrays.sort(pointArray);

        for (int x = 0, coefficient = 1; x < size - 1; x++, coefficient++) {
            int newSize = size - coefficient;
            HashMap<Integer, Double> slopeMap = new HashMap<>();
            Double[] slopeDouble = new Double[size - coefficient];
            for (int id = coefficient; id < size; id++) {
                double slopy = pointArray[x].slopeTo(pointArray[id]);
                slopeMap.put(id, slopy);
                slopeDouble[id - coefficient] = slopy;
            }
            for (int i = 0; i < newSize; i++) {
                int count = 0;
                if (slopeDouble[i] != null) {
                    double compare = slopeDouble[i].doubleValue();
                    for (int j = 0; j < newSize; j++) {
                        if (slopeDouble[j] != null && compare == slopeDouble[j]) {
                            slopeDouble[j] = null;
                            count++;
                        }
                    }
                    if (count >= 3) {
                        addSegment(size, slopeMap, compare, coefficient);
                    }
                }
            }
        }
        segments = new LineSegment[actualSegments.size()];
        segments = actualSegments.toArray(segments);
    }

    private void addSegment(int size, HashMap<Integer, Double> slopeMap, double compare, int coefficient) {
        int first = -1;
        int last = -1;
        for (; coefficient < size; coefficient++) {
            if (compare == slopeMap.get(coefficient)) {
                if (first == -1) {
                    first = coefficient;
                }
                last = coefficient;
            }
        }
        if (!firstPoint.isEmpty()) { //check if it isn't subsegment
            for (int index = 0; index < firstPoint.size(); index++) {
                if (pointArray[first] == firstPoint.get(index) ||
                        pointArray[first] == lastPoint.get(index) ||
                        pointArray[first].slopeTo(firstPoint.get(index)) == pointArray[first].slopeTo(lastPoint.get(index))) {
                    return;
                }
            }
        }
        firstPoint.add(pointArray[first]);
        lastPoint.add(pointArray[last]);
        actualSegments.add(new LineSegment(pointArray[first], pointArray[last]));
    }

    public int numberOfSegments() {    // the number of line segments
        return segments.length;
    }

    public LineSegment[] segments() {    // the line segments
        return Arrays.copyOf(segments, numberOfSegments());
    }
}