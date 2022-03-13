package model;

/**
 * This class contains the Naive or Iterative Matrix Multiplication Algorithm.
 * Once called it will multiple the two matrices together and returns the resulting matrix.
 * @author samin
 *
 */
public class IterativeMatrixMultiplication {

	/**
	 * @param Matrix a
	 * @param Matrix b
	 * @param size of the matrices
	 * @return result
	 */
	public static int[][] multiply(int[][] a, int[][] b, int size)
    {
        int[][] result = new int[size][size];
        for (int row = 0; row < size; row++)
        {
            for (int column = 0; column < size; column++)
            {
                int sum = 0;
                for (int i = 0; i < size; i++)
                {
                    sum += a[row][i] * b[i][column];
                }
                result[row][column] = sum;
            }
        }
        return result;
    }

}
