
public class IterativeMatrixMultiplication {

	public static int[][] multiply(int[][] a, int[][] b)
    {
        int size = a.length;
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
