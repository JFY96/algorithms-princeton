import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * First attempt at solution. It works correctly as required by the solution but the isFull method fails when "backwash" is involved
 * E.g. isFull(3,1) in the below grid should be false, but is true as it percolates and (3,1) is union'd to the bottom virtual site.
 * x x -
 * x x -
 * - x -
 */
public class Percolation_v1 {
	
	private final int gridSize;
	private boolean[] grid;
	private int openSites;

	private final WeightedQuickUnionUF unionFind;
	private final int ufTopIndex;
	private final int ufBottomIndex;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation_v1(int n) {
		if (n <= 0) throw new IllegalArgumentException("Invalid argument passed to Percolation constructor - out of range");

		gridSize = n;
		grid = new boolean[n * n];
		openSites = 0;

		unionFind = new WeightedQuickUnionUF((n * n) + 2); // 2 additional elements to simulate fake top/bottom
		ufTopIndex = n * n;
		ufBottomIndex = (n * n) + 1;
	}

	private int getIndex(int row, int col) {
		return (row - 1) * gridSize + (col - 1);
	}

	private boolean validIndexRange(int i) {
		return (i > 0 && i <= gridSize);
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
	
			// check for open sites in each direction, and if so, union
			openUnion(row, col, row - 1, col); // north
			openUnion(row, col, row, col + 1); // east
			openUnion(row, col, row + 1, col); // south
			openUnion(row, col, row, col - 1); // west
	
			// if this site is on the top or bottom row, add a union to 'fake' top or bottom sites
			if (row == 1) unionFind.union(index, ufTopIndex); // top row
			if (row == gridSize) unionFind.union(index, ufBottomIndex); // bottom row
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
		// WRONG since once it percoaltes, a site may be unioned to virtual bottom which links to virtual top
		return unionFind.find(getIndex(row, col)) == unionFind.find(ufTopIndex);
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
