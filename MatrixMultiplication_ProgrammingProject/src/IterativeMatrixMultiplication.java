
public class IterativeMatrixMultiplication {

	public static int[][] multiply(int[][] a, int[][] b)
    {
        int size = a.length;
        int[][] result = new int[size][size];
        for (int resultRow = 0; resultRow < size; resultRow++)
        {
            for (int resultCol = 0; resultCol < size; resultCol++)
            {
                int sum = 0;
                for (int i = 0; i < size; i++)
                {
                    sum += a[resultRow][i] * b[i][resultCol];
                }
                result[resultRow][resultCol] = sum;
            }
        }
        return result;
    }

}
