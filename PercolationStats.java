/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static double mean;
    private static double temp;
    private final double[] perThs;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("dimension " + n + " ≤ 0");
        }

        perThs = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                percolation.open(row, col);
            }
            perThs[i] = percolation.numberOfOpenSites() / Math.pow(n, 2);
        }
    }

    // sample mean of percolation threshold
    //среднее значение выборки порогов перколяции
    public double mean() {
        return StdStats.mean(perThs);
    }

    // sample standard deviation of percolation threshold
    //стандартное отклонение выборки порогов перколции
    public double stddev() {
        return StdStats.stddev(perThs);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - temp;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + temp;
    }

    // test client (see below)
    public static void main(String[] args) {
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]),
                                                                 trials);
        mean = percolationStats.mean();
        System.out.println("mean\t\t\t\t\t= " + mean);
        double stddev = percolationStats.stddev();
        System.out.println("stddev\t\t\t\t\t= " + stddev);
        temp = 1.96 * Math.sqrt(stddev) / Math.sqrt(trials);
        System.out.println(
                "95% confidence interval\t= [" + percolationStats.confidenceLo() + ", "
                        + percolationStats.confidenceHi() + "]");
    }
}