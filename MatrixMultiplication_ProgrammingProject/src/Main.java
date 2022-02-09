import java.util.Date;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		testTiming();
	}
	
	private static void testTiming() {
		long totalTime, startTime,finishTime = 0;
		int [] n= {4,8,16,32,64,128,256,512,1024};
		
		for( int i=0; i<n.length;i++){
			int[][] matrixA= new int[n[i]][n[i]];
			int[][] matrixB= new int[n[i]][n[i]];
			matrixA= generateNumbers(matrixA);
			matrixB= generateNumbers(matrixB);
			for( int test= 0; test<3; test++) {
				System.out.println("Iterative");
				Date startDate = new Date();
				startTime= startDate.getTime();
				IterativeMatrixMultiplication.multiply(matrixA,matrixB);
				Date finishDate = new Date();
                finishTime = finishDate.getTime();
                totalTime = (finishTime - startTime);
                System.out.println("Size of the Matrix:"+n[i]+"\nTime: "+ totalTime+ "ms.");
                
                System.out.println("\n Divide N Conquer");
                Date startDate2 = new Date();
				startTime= startDate2.getTime();
				DivideNConquerMatrixMultiplication.multiply(matrixA,matrixB);
				Date finishDate2 = new Date();
                finishTime = finishDate2.getTime();
                totalTime = (finishTime - startTime);
                System.out.println("Size of the Matrix:"+n[i]+"\nTime: "+ totalTime+ "ms.");
                
                System.out.println("\n Strassen");
                Date startDate3 = new Date();
				startTime= startDate3.getTime();
				StrassenMatrixMultiplication.multiply(matrixA,matrixB);
				Date finishDate3 = new Date();
                finishTime = finishDate3.getTime();
                totalTime = (finishTime - startTime);
                System.out.println("Size of the Matrix:"+n[i]+"\nTime: "+ totalTime+ "ms.");
			}
		}
	}

	private static int[][] generateNumbers(int[][] matrix) {
		Random rand = new Random(); 
		for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++)
                matrix[i][j] = rand.nextInt(2); 
        return matrix;
	}


}
