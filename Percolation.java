/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int countOp;
    private final int bottom;
    private final int top = 0;
    private boolean[][] grid;
    private final WeightedQuickUnionUF weightedQuickUnionUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        weightedQuickUnionUF = new WeightedQuickUnionUF(
                (int) Math.pow(n, 2) + 2);
        bottom = (int) Math.pow(n, 2) + 1;
        grid = new boolean[n][n];

        // can I use Default Value (false) ?
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        // if (row >= 1 && row <= grid.length && col >= 1 && col <= grid[0].length) {

        validate(row, col);

        if (row == 1) {
            weightedQuickUnionUF.union(col, top);
        }
        if (row == grid[0].length) {
            weightedQuickUnionUF.union(grid.length * (row - 1) + col, bottom);
        }

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            countOp++;

            // union with neighbours
            int[][] adjacent = {
                    { row - 1, col }, { row + 1, col }, { row, col - 1 }, { row, col + 1 }
            };
            for (int i = 0; i < adjacent.length; i++) {
                if ((adjacent[i][0] >= 1 && adjacent[i][0] <= grid.length) && (
                        adjacent[i][1] >= 1
                                && adjacent[i][1] <= grid[0].length)) {
                    if (isOpen(adjacent[i][0],
                               adjacent[i][1])) {
                        weightedQuickUnionUF
                                .union(grid.length * (row - 1) + col,
                                       grid.length * (adjacent[i][0] - 1) + adjacent[i][1]);
                    }
                }
            }
        }
        //}
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);

        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    // not used...
    public boolean isFull(int row, int col) {
        validate(row, col);

        return weightedQuickUnionUF.find(grid.length * (row - 1) + col) == weightedQuickUnionUF
                .find(top);
    }

    private void validate(int row, int col) {
        int n = grid.length;
        if (row < 1 || row > n) {
            throw new IllegalArgumentException(
                    "index of row " + row + " is not between 1 and " + n);
        }
        n = grid[0].length;
        if (col < 1 || col > n) {
            throw new IllegalArgumentException(
                    "index of col " + col + " is not between 1 and " + n);
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOp;
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.find(top) == weightedQuickUnionUF.find(bottom);
    }

    // test client (optional)
    public static void main(String[] args) {
        // some test, evidently
    }

}
