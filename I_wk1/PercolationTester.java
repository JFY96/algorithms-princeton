import edu.princeton.cs.algs4.StdIn;

public class PercolationTester {

	private int n;
	private Percolation p;

	public PercolationTester(int gridSize) {
		n = gridSize;
		p = new Percolation(n);
	}

	public void printGrid() {
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				System.out.print(p.isOpen(i, j) ? "-" : "x");
				System.out.print(" ");
			}
			System.out.println("");
		}
	}

	public void openAndPrint(int row, int col) {
		p.open(row, col);
		System.out.println("Open ("+row+", "+col+") - "+(p.isFull(row, col) ? "full" : "not full")+" (openSites: " + p.numberOfOpenSites() + (p.percolates() ? ". Percolates: true" : "") + ")");
		printGrid();
	}

	// test client
	public static void main(String[] args) {
		int gridSize = StdIn.readInt();
		PercolationTester tester = new PercolationTester(gridSize);
		while (!StdIn.isEmpty()) {
			int i = StdIn.readInt();
			int j = StdIn.readInt();
			tester.openAndPrint(i, j);
		}
	}
}
