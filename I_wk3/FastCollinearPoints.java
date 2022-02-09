import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {

	private final Point[] pts;

	// finds all line segments containing 4 or more points
   public FastCollinearPoints(Point[] points) {
		if (points == null) throw new IllegalArgumentException("Argument must not be null.");
		int n = points.length;
		pts = new Point[n];
		for (int i = 0; i < n; i++) {
			if (points[i] == null) throw new IllegalArgumentException("Argument must not contain null elements.");
			pts[i] = points[i];
		}
		Arrays.sort(pts, Collections.reverseOrder()); // Sort in reverse so largest points are first - we only ass line segments during looping if the origin matches the largest on the line
		for (int i = 0; i < n; i++) {
			if (i > 0 && pts[i].compareTo(pts[i-1]) == 0) throw new IllegalArgumentException("Argument must not contain duplicate elements.");
		}
	}

	// the number of line segments
   public int numberOfSegments() {
		LineSegment[] s = segments();
		return s.length;
	}

	// the line segments
   public LineSegment[] segments() {
		ArrayList<LineSegment> s = new ArrayList<LineSegment>();
		int n = pts.length;
		Point[] p = Arrays.copyOf(pts, n);
		for (int i = 0; i < n; i++) {
			Point a = pts[i];
			Arrays.sort(p, a.slopeOrder());
			Double slope = null;
			int count = 0;
			for (int j = 0; j < n; j++) {
				double tempSlope = a.slopeTo(p[j]);
				if (slope == null || slope == tempSlope) {
					count++;
				} else {
					checkAndAddSegment(s, p, a, j, count);
					count = 1;
				}
				slope = tempSlope;
			}
			checkAndAddSegment(s, p, a, n, count);
		}

		int noSegments = s.size();
		LineSegment[] ls = new LineSegment[noSegments];
		for (int i = 0; i < noSegments; i++) ls[i] = s.get(i);
		return ls;
	}

	private void checkAndAddSegment(ArrayList<LineSegment> s, Point[] p, Point origin, int index, int count) {
		if (count >= 3) {
			Point[] subPoints = new Point[count+1];
			for (int i = 0; i < count; i++) subPoints[i] = p[index-count+i];
			subPoints[count] = origin;
			Arrays.sort(subPoints);
			if (origin == subPoints[count]) { // to prevent duplicates, only add if the point we are checking matches the largest point on line
				s.add(new LineSegment(subPoints[0], subPoints[count]));
			}
		}
	}

	public static void main(String[] args) {

		// read the n points from a file
		In in = new In(args[0]);
		int n = in.readInt();
		Point[] points = new Point[n];
		for (int i = 0; i < n; i++) {
			int x = in.readInt();
			int y = in.readInt();
			points[i] = new Point(x, y);
		}

		// draw the points
		StdDraw.enableDoubleBuffering();
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		for (Point p : points) {
			p.draw();
		}
		StdDraw.show();

		// print and draw the line segments
		FastCollinearPoints collinear = new FastCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();
	}

}