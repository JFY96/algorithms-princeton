import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Second  attempt at solution. Resolves the "backwash" bug, but is a little brute force, requiring another grid and a recursive function 'full'.
 */
public class Percolation_v2 {
	
	private final int gridSize;
	private boolean[] grid;
	private boolean[] gridFull;
	private int openSites;

	private final WeightedQuickUnionUF unionFind;
	private final int ufTopIndex;
	private final int ufBottomIndex;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation_v2(int n) {
		if (n <= 0) throw new IllegalArgumentException("Invalid argument passed to Percolation constructor - out of range");

		gridSize = n;
		grid = new boolean[n * n];
		gridFull = new boolean[n * n];
		openSites = 0;

		unionFind = new WeightedQuickUnionUF((n * n) + 2); // 2 additional elements to simulate fake top/bottom
		ufTopIndex = n * n;
		ufBottomIndex = (n * n) + 1;
	}

	private boolean validIndexRange(int i) {
		return (i > 0 && i <= gridSize);
	}

	private int getIndex(int row, int col) {
		return (row - 1) * gridSize + (col - 1);
	}

	// marks a site (and any connected sites) to full
	private void full(int row, int col) {
		if (validIndexRange(row) && validIndexRange(col) && isOpen(row, col) && !isFull(row, col)) {
			gridFull[getIndex(row, col)] = true; // mark site as full
			// also do the same for all other connected sites
			full(row - 1, col); // north
			full(row, col + 1); // east
			full(row + 1, col); // south
			full(row, col - 1); // west
		}
	}

	private boolean isConnectedToFull(int row, int col) {
		boolean result = false;
		if (!result && validIndexRange(row - 1)) result = isFull(row - 1, col); // north
		if (!result && validIndexRange(col + 1)) result = isFull(row, col + 1); // east
		if (!result && validIndexRange(row + 1)) result = isFull(row + 1, col); // south
		if (!result && validIndexRange(col - 1)) result = isFull(row, col - 1); // west
		return result;
	}

	private void openUnion(int row, int col, int row2, int col2) {
		// Should only be used if site at (row, col) are valid and open (so less checks required here)
		if (validIndexRange(row2) && validIndexRange(col2) && isOpen(row2, col2)) {
			unionFind.union(getIndex(row, col), getIndex(row2, col2));
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

			// if this site is on the top/bottom row, add a union to the 'virtual' top/bottom site
			if (row == 1) unionFind.union(index, ufTopIndex);
			if (row == gridSize) unionFind.union(index, ufBottomIndex);
	
			// check for open sites in each direction, and if so, union
			openUnion(row, col, row - 1, col); // north
			openUnion(row, col, row, col + 1); // east
			openUnion(row, col, row + 1, col); // south
			openUnion(row, col, row, col - 1); // west
	
			// if this site is on top row or is connected to any full sites, mark as full
			if (row == 1 || isConnectedToFull(row, col)) full(row, col);
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
		return gridFull[getIndex(row, col)];
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
