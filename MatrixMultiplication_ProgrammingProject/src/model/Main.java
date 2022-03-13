package model;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class runs every matrix multiplication algorithm 50 times with each given matrix dimension
 * and provides the average time for each algorithm
 * @author samin
 *
 */
public class Main {
	
	/**
	 * Minimum number used to fill in matrices
	 */
	private static final int MIN= 1;
	
	/**
	 * Max number used to fill in matrices
	 */
	private static final int MAX=500;
	
	/**
	 * Array of set dimensions to run the algorithms with different size matrices
	 */
	private static final int[] DIMENSIONS= {64,128,256,512,1024};
	/**
	 *  Total test number used to run each algorithm with each dimension
	 */
	private static final int TEST= 50;
	
	/**
	 * Stores the runtimes in each array can be used to print them into console and provide average time
	 */
	public static List<Double> iterativeTimes= new ArrayList<Double>();
	public static List<Double> DivideConquerTimes= new ArrayList<Double>();
	public static List<Double> StrassenTimes= new ArrayList<Double>();
	
	/**
	 * @param args
	 * Main method
	 */
	public static void main(String[] args){
		runner(DIMENSIONS);
	}
	
	/**
	 * The runner runs each starts the test with a new set of matrices with a new dimension
	 * @param n
	 */
	private static void runner(int[] n) {

		for( int i=0; i<n.length;i++){
			int[][] matrixA= generateMatrix(n[i]);
			int[][] matrixB= generateMatrix(n[i]);
			test(matrixA,matrixB,TEST, n[i]);
		}
	}

	/**
	 * @param matrixA
	 * @param matrixB
	 * @param count
	 * @param size
	 */
	private static void test(int[][] matrixA, int[][] matrixB, int count, int size) {
		long startTime,finishTime = 0;
		double totalTime=0.0;
		System.out.println("Size of Matrix:"+size);
		for(int i=0; i<count; i++) {
			startTime= System.nanoTime();
			IterativeMatrixMultiplication.multiply(matrixA,matrixB,size);
            finishTime = System.nanoTime();
            totalTime  = (double)((finishTime - startTime)/100000);
            iterativeTimes.add(totalTime);
		}

		System.out.println("Iterative");
		averageTime(iterativeTimes);
		iterativeTimes.clear();
		
		startTime= finishTime=0;
		totalTime=0.0;
		for(int i=0; i<count; i++) {

			startTime= System.nanoTime();
			DivideNConquerMatrixMultiplication.multiply(matrixA,matrixB,size);
			finishTime = System.nanoTime();
            totalTime  = (double)((finishTime - startTime)/100000);
            DivideConquerTimes.add( totalTime);
		}
		System.out.println("Divide N Conquer");
		averageTime(DivideConquerTimes);
		DivideConquerTimes.clear();
		
		startTime= finishTime=0;
		totalTime=0.0;
		for(int i=0; i<count; i++) {
			startTime= System.nanoTime();
			StrassenMatrixMultiplication.forkJoinMultiply(matrixA,matrixB,size);
			finishTime = System.nanoTime();
            totalTime  = (double)((finishTime - startTime)/100000);
			StrassenTimes.add(totalTime);
		}
		System.out.println("Strassen");
		averageTime(StrassenTimes);
		StrassenTimes.clear();
	}

	/**
	 * @param times
	 */
	private static void averageTime(List<Double> times) {
		DoubleSummaryStatistics summaryStats = times.stream()
			      .mapToDouble((a) -> a)
			      .summaryStatistics();
			      System.out.println(summaryStats.getAverage()+ "ms");
		
	}

    /**
     * @param dimension
     * @return
     */
    private static int[][] generateMatrix(int dimension) {
        int[][] matrix = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                matrix[i][j] = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
            }
        }
        return matrix;
    }


}
