import java.util.Arrays;
import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {

	private final Point[] p;

	// finds all line segments containing 4 points
   public BruteCollinearPoints(Point[] points) {
		if (points == null) throw new IllegalArgumentException("Argument must not be null");
		int n = points.length;
		p = new Point[n];
		for (int i = 0; i < n; i++) {
			if (points[i] == null) throw new IllegalArgumentException("Argument must not contain null elements.");
			p[i] = points[i];
		}
		Arrays.sort(p);
		for (int i = 0; i < n; i++) {
			if (i > 0 && p[i].compareTo(p[i-1]) == 0) throw new IllegalArgumentException("Argument must not contain duplicate elements.");
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
		int n = p.length;
		for (int i = 0; i < n; i++) {
			for (int j = i+1; j < n; j++) {
				for (int k = j+1; k < n; k++) {
					if (!checkPointsCollinear(i, j, k)) continue;
					for (int l = k+1; l < n; l++) {
						if (checkPointsCollinear(i, j, l)) {
							Point[] tempPoints = { p[i], p[j], p[k], p[l] };
							Arrays.sort(tempPoints);
							if (p[l] == tempPoints[3]) {
								s.add(new LineSegment(tempPoints[0], tempPoints[3]));
							}
						}
					}
				}
			}
		}

		int noSegments = s.size();
		LineSegment[] ls = new LineSegment[noSegments];
		for (int i = 0; i < noSegments; i++) ls[i] = s.get(i);
		return ls;
	}

	private boolean checkPointsCollinear(int i, int j, int k) {
		return p[i].slopeTo(p[j]) == p[i].slopeTo(p[k]);
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
		BruteCollinearPoints collinear = new BruteCollinearPoints(points);
		for (LineSegment segment : collinear.segments()) {
			StdOut.println(segment);
			segment.draw();
		}
		StdDraw.show();
	}
}