import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	
	private final int gridSize;
	private boolean[] grid;
	private int openSites;

	private final WeightedQuickUnionUF unionFind; // WQUF for open sites
	private final WeightedQuickUnionUF unionFindFull; // WQUF for full sites
	private final int ufTopIndex;
	private final int ufBottomIndex;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		if (n <= 0) throw new IllegalArgumentException("Invalid argument passed to Percolation constructor - out of range");

		gridSize = n;
		grid = new boolean[n * n];
		openSites = 0;

		// The WQUF should have 2 additional elements to simulate virtual/fake top/bottom sites
		unionFind = new WeightedQuickUnionUF((n * n) + 2);
		unionFindFull = new WeightedQuickUnionUF((n * n) + 2);
		ufTopIndex = n * n;
		ufBottomIndex = (n * n) + 1;
	}

	private boolean validIndexRange(int i) {
		return (i > 0 && i <= gridSize);
	}

	private int getIndex(int row, int col) {
		return (row - 1) * gridSize + (col - 1);
	}

	private void union(int row, int col, int row2, int col2) {
		// Should only be used if site at (row, col) are valid and open (so less checks required here)
		if (validIndexRange(row2) && validIndexRange(col2) && isOpen(row2, col2)) {
			unionFind.union(getIndex(row, col), getIndex(row2, col2));
			unionFindFull.union(getIndex(row, col), getIndex(row2, col2));
		}
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		if (!validIndexRange(row)) throw new IllegalArgumentException("Invalid range for row.");
		if (!validIndexRange(col)) throw new IllegalArgumentException("Invalid range for col.");
		
		if (!isOpen(row, col)) {
			// open site
			int index = getIndex(row, col);
			grid[index] = true;
			openSites++;

			// if this site is on top row, mark as full
			if (row == 1) unionFindFull.union(getIndex(row, col), ufTopIndex);

			// if this site is on the top/bottom row, add a union to the 'virtual' top/bottom site
			if (row == 1) unionFind.union(index, ufTopIndex);
			if (row == gridSize) unionFind.union(index, ufBottomIndex);
	
			// check for open sites in each direction, and if so, union
			union(row, col, row - 1, col); // north
			union(row, col, row, col + 1); // east
			union(row, col, row + 1, col); // south
			union(row, col, row, col - 1); // west
		}
	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		if (!validIndexRange(row)) throw new IllegalArgumentException("Invalid range for row.");
		if (!validIndexRange(col)) throw new IllegalArgumentException("Invalid range for col.");
		return grid[getIndex(row, col)];
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col) {
		if (!validIndexRange(row)) throw new IllegalArgumentException("Invalid range for row.");
		if (!validIndexRange(col)) throw new IllegalArgumentException("Invalid range for col.");
		return unionFindFull.find(getIndex(row, col)) == unionFindFull.find(ufTopIndex);
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return openSites;
	}

	// does the system percolate?
	public boolean percolates() {
		return unionFind.find(ufTopIndex) == unionFind.find(ufBottomIndex);
	}

}
