package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Random;

public class Main {
	public static List<Double> iterativeTimes= new ArrayList();
	public static List<Double> DivideConquerTimes= new ArrayList();
	public static List<Double> StrassenTimes= new ArrayList();
	
	public static void main(String[] args){
		runner();
	}
	
	private static void runner() {
		int [] n= {4,8,16,32,64,128,256,512,1024};
		
		for( int i=0; i<n.length;i++){
			int[][] matrixA= new int[n[i]][n[i]];
			int[][] matrixB= new int[n[i]][n[i]];
			matrixA= generateNumbers(matrixA);
			matrixB= generateNumbers(matrixB);
			test(matrixA,matrixB,3);
		}
	}

	private static void test(int[][] matrixA, int[][] matrixB, int count) {
		long totalTime, startTime,finishTime = 0;
		int size= matrixA.length;
		System.out.println("Size of Matrix:"+size);
		for(int i=0; i<count; i++) {
			Date startDate = new Date();
			startTime= startDate.getTime();
			IterativeMatrixMultiplication.multiply(matrixA,matrixB);
			Date finishDate = new Date();
            finishTime = finishDate.getTime();
            totalTime = (finishTime - startTime);
            iterativeTimes.add((double) totalTime);
		}
		System.out.println("Iterative");
		averageTime(iterativeTimes);
		for(int i=0; i<count; i++) {

         Date startDate2 = new Date();
			startTime= startDate2.getTime();
			DivideNConquerMatrixMultiplication.multiply(matrixA,matrixB);
			Date finishDate2 = new Date();
         finishTime = finishDate2.getTime();
         totalTime = (finishTime - startTime);
         DivideConquerTimes.add((double) totalTime);
		}
		System.out.println("Divide N Conquer");
		averageTime(DivideConquerTimes);
		for(int i=0; i<count; i++) {
         Date startDate3 = new Date();
			startTime= startDate3.getTime();
			StrassenMatrixMultiplication.multiply(matrixA,matrixB);
			Date finishDate3 = new Date();
         finishTime = finishDate3.getTime();
         totalTime = (finishTime - startTime);
         StrassenTimes.add((double) totalTime);
		}
		System.out.println("Strassen");
		averageTime(StrassenTimes);
	}

	private static void averageTime(List<Double> timeList) {
		DoubleSummaryStatistics summaryStats = timeList.stream()
			      .mapToDouble((a) -> a)
			      .summaryStatistics();
			      System.out.println(summaryStats.getAverage());
		
	}

	private static int[][] generateNumbers(int[][] matrix) {
		Random rand = new Random(); 
		for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++)
                matrix[i][j] = rand.nextInt(2); 
        return matrix;
	}


}
