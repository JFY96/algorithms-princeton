import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private final int trialCount;
	private final double[] thresholdEstimates;
	
	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Invalid argument passed to PercolationStats constructor - out of range");

		trialCount = trials;
		thresholdEstimates = new double[trials];

		for (int i = 0; i < trials; i++) {
			Percolation percolation = new Percolation(n);
			while (!percolation.percolates()) {
				percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
			}
			thresholdEstimates[i] = (double) percolation.numberOfOpenSites() / (n * n);
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(thresholdEstimates);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {	
		return StdStats.stddev(thresholdEstimates);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - confidenceLevel();
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + confidenceLevel();
	}

	private double confidenceLevel() {
		return (1.96 * stddev()) / Math.sqrt(trialCount);
	}

	// test client
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);
		PercolationStats stats = new PercolationStats(n, t);
		System.out.println("mean                     = " + stats.mean());
		System.out.println("stddev                   = " + stats.stddev());
		System.out.println("95%% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
	}

}
