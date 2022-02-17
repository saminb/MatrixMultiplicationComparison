package model;

public class DivideNConquerMatrixMultiplication {

	private static void divideConquer(int[][] a, int aRow, int aColumn,
            int[][] b, int bRow, int bColumn,
            int[][] c, int cRow, int cColumn, int size)
	{

		if (size <= 2)
		{
			for (int rPos = 0; rPos < 2; rPos++)
			{
				for (int cPos = 0; cPos < 2; cPos++)
				{
					int sum = 0;
					for (int i = 0; i < 2; i++)
					{
						sum += a[aRow + rPos][aColumn + i]
								* b[bRow + i][bColumn + cPos]; 
					}
					c[cRow + rPos][cColumn + cPos] += sum;
				}
			}
			return;
		}
		int quadSize = size / 2;
		//8 recursive calls
		divideConquer(	a, aRow,                		aColumn,                
						b, bRow,                		bColumn,             
						c, cRow,                		cColumn, quadSize);
		
		divideConquer(	a, aRow,                		aColumn + quadSize,
						b, bRow + quadSize,				bColumn,                
						c, cRow,               			cColumn, quadSize);
		
		divideConquer(	a, aRow,                		aColumn,
						b, bRow,                		bColumn + quadSize,
						c, cRow,                		cColumn + quadSize, quadSize);
		
		divideConquer(	a, aRow,                		aColumn + quadSize,
						b, bRow + quadSize, 			bColumn + quadSize,
						c, cRow,                		cColumn + quadSize, quadSize);
		
		divideConquer(	a, aRow + quadSize, 			aColumn,
						b, bRow,                		bColumn,
						c, cRow + quadSize, 			cColumn,quadSize);
		
		divideConquer(	a, aRow + quadSize, 			aColumn + quadSize, 
						b, bRow + quadSize, 			bColumn,                
						c, cRow + quadSize, 			cColumn,quadSize);

		divideConquer(	a, aRow + quadSize, 			aColumn,
						b, bRow,                bColumn + quadSize,
						c, cRow + quadSize, cColumn + quadSize, quadSize);
		
		divideConquer(	a, aRow + quadSize, aColumn + quadSize,
						b, bRow + quadSize, bColumn + quadSize,
						c, cRow + quadSize, cColumn + quadSize, quadSize);
		return;
	}
	
	public static int[][] multiply(int[][] a, int[][] b) {
		int size = a.length;
		int[][] result = new int[size][size];
		divideConquer(	a,      0, 0,
						b,      0, 0,
						result, 0, 0, result.length);
		return result;
		}
}
