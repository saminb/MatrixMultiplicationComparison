
public class StrassenMatrixMultiplication {
		/**
	     * Multiplies two matrices A and B with the
	     * assumption that A and B are both n*n matrices
	     * using Strassens method.
	     * 
	     * @param a The first matrix in the operation.
	     * @param b The second matrix in the operation.
	     * @return The resulting matrix.
	     */
	    public static int[][] multiply(int[][] a, int[][] b)
	    {
	        int size = a.length;
	        int[][] result = new int[size][size];
	        mul(a, 0, 0,
	            b, 0, 0,
	            result);
	        return result;
	    }

	    /**
	     * Multiplies portions of two matrices using Strassens method,
	     * storing the result in a third provided matrix.
	     * 
	     * C = Arc*Brc
	     * 
	     * Note: No size parameter is taken because the method assumes
	     * that the provided C matrix matches the size of the result.
	     * 
	     * @param a       Matrix A
	     * @param aRowPtr Matrix A Starting Row Index
	     * @param aColPtr Matrix A Starting Column Index
	     * @param b       Matrix B
	     * @param bRowPtr Matrix B Starting Row Index
	     * @param bColPtr Matrix B Starting Column Index
	     * @param c       Matrix C, Result Matrix
	     */
	    private static void mul(int[][] a, int aRowPtr, int aColPtr,
	                     int[][] b, int bRowPtr, int bColPtr,
	                     int[][] c)
	    {
	        /**
	         * Base Case
	         */
	        if (c.length <= 2)
	        {
	            for (int rPos = 0; rPos < 2; rPos++)
	            {
	                for (int cPos = 0; cPos < 2; cPos++)
	                {
	                    int sum = 0;
	                    for (int i = 0; i < 2; i++)
	                    {
	                        sum += a[aRowPtr + rPos][aColPtr + i]
	                               * b[bRowPtr + i][bColPtr + cPos]; 
	                    }
	                    c[rPos][cPos] = sum;
	                }
	            }
	            return;
	        }
	        /**
	         * Not Base Case, Need Recursive Calls
	         * Array Provided To Store Result May Have
	         * Existing Contents, Must Be Cleared
	         * Before Adding P, Q, R, S, T, U, V
	         */
	        for (int rPos = 0; rPos < c.length; rPos++)
	        {
	            for (int cPos = 0; cPos < c[rPos].length; cPos++)
	            {
	                c[rPos][cPos] = 0;
	            }
	        }
	        int quadrantSize = c.length / 2;
	        /**
	         * Need 3 Temporary Arrays For Operations
	         * Note: To save memory, instead of creating
	         *       new arrays for every operation (P, Q, R, etc)
	         *       these temporary arrays are used to store intermediate
	         *       results which are immediately added to the result matrix
	         *       so that subsequent operations may use the same memory.
	         */
	        int[][] t1 = new int[quadrantSize][quadrantSize];
	        int[][] t2 = new int[quadrantSize][quadrantSize];
	        int[][] t3 = new int[quadrantSize][quadrantSize];

	        /**
	         * Calculate P
	         */
	        // t1 = A11 + A22
	        add(a,  aRowPtr,                aColPtr,                // A11 +
	            a,  aRowPtr + quadrantSize, aColPtr + quadrantSize, // A22
	            t1, 0,                      0,                      // -> t1
	            quadrantSize, false);
	        // t2 = B11 + B22
	        add(b,  bRowPtr,                bColPtr,                // B11 +
	            b,  bRowPtr + quadrantSize, bColPtr + quadrantSize, // B22
	            t2, 0,                      0,                      // -> t2
	            quadrantSize, false);
	        // P = t3 = (A11 + A22)(B11 + B22)
	        mul(t1, 0,                      0,                      // (A11 + A22) *
	            t2, 0,                      0,                      // (B11 + B22)
	            t3);                                                // -> t3
	        // Add P To C11 & C22 Immediately So Value Can Be Discarded
	        add(c,  0,                      0,                      // C11 +
	            t3, 0,                      0,                      // P
	            c,  0,                      0,                      // -> C11
	            quadrantSize, false);
	        add(c,  quadrantSize,           quadrantSize,           // C22 +
	            t3, 0,                      0,                      // P
	            c,  quadrantSize,           quadrantSize,           // -> C22
	            quadrantSize, false);
	        
	        /**
	         * Calculate Q
	         */
	        // t1 = A21 + A22
	        add(a,  aRowPtr + quadrantSize, aColPtr,                // A21 +
	            a,  aRowPtr + quadrantSize, aColPtr + quadrantSize, // A22
	            t1, 0,                      0,                      // -> t1
	            quadrantSize, false);
	        // Q = t3 = (A21 + A22)B11
	        mul(t1, 0,                      0,                      // (A21 + A22) *
	            b,  bRowPtr,                bColPtr,                // B11
	            t3);                                                // -> Q
	        // Add Q To C21 & Subtract From C22 Immediately So Value Can Be Discarded
	        add(c,  quadrantSize,           0,                      // C21 +
	            t3, 0,                      0,                      // Q
	            c,  quadrantSize,           0,                      // -> C21
	            quadrantSize, false);
	        add(c,  quadrantSize,           quadrantSize,           // C22 -
	            t3, 0,                      0,                      // Q
	            c,  quadrantSize,           quadrantSize,           // -> C22
	            quadrantSize, true);
	        
	        /**
	         * Calculate R
	         */
	        // t2 = (B12 - B22)
	        add(b,  bRowPtr,                bColPtr + quadrantSize, // B12 -
	            b,  bRowPtr + quadrantSize, bColPtr + quadrantSize, // B22
	            t2, 0,                      0,                      // -> t2
	            quadrantSize, true);
	        // R = t3 = A11(B12 - B22)
	        mul(a,  aRowPtr,                aColPtr,               // A11 *
	            t2, 0,                      0,                     // (B12 - B22)
	            t3);                                               // -> R
	        // Add R To C12 & C22 Immediately So Value Can Be Discarded
	        add(c,  0,                      quadrantSize,          // C12 +
	            t3, 0,                      0,                     // R
	            c,  0,                      quadrantSize,          // -> C12
	            quadrantSize, false);
	        add(c,  quadrantSize,           quadrantSize,          // C22 +
	            t3, 0,                      0,                     // R
	            c,  quadrantSize,           quadrantSize,          // -> C22
	            quadrantSize, false);
	        
	        /**
	         * Calculate S
	         */
	        // t2 = (B21 - B11)
	        add(b,  bRowPtr + quadrantSize, bColPtr,                // B21 -
	            b,  bRowPtr,                bColPtr,                // B11
	            t2, 0,                      0,                      // -> t2
	            quadrantSize, true);
	        // S = t3 = A22(B21 - B11)
	        mul(a,  aRowPtr + quadrantSize, aColPtr + quadrantSize, // A22 *
	            t2, 0,                      0,                      // (B21 - B11)
	            t3);                                                // -> S
	        // Add S To C11 & C21 Immediately So Value Can Be Discarded
	        add(c,  0,                      0,                      // C11 +
	            t3, 0,                      0,                      // S
	            c,  0,                      0,                      // -> C11
	            quadrantSize, false);
	        add(c,  quadrantSize,           0,                      // C21 +
	            t3, 0,                      0,                      // S
	            c,  quadrantSize,           0,                      // -> C21
	            quadrantSize, false);
	        
	        /**
	         * Calculate T
	         */
	        // t1 = (A11 + A12)
	        add(a,  aRowPtr,                aColPtr,                // A11 +
	            a,  aRowPtr,                aColPtr + quadrantSize, // A12
	            t1, 0,                      0,                      // -> t1
	            quadrantSize, false);
	        // T = t3 = (A11 + A12)B22
	        mul(t1, 0,                      0,                      // (A11 + A12) *
	            b,  bRowPtr + quadrantSize, bColPtr + quadrantSize, // B22
	            t3);                                                // -> T
	        // Subtract T From C11 & Add To C12 Immediately So Value Can Be Discarded
	        add(c,  0,                      0,                      // C11 -
	            t3, 0,                      0,                      // T
	            c,  0,                      0,                      // -> C11
	            quadrantSize, true);
	        add(c,  0,                      quadrantSize,           // C12 +
	            t3, 0,                      0,                      // T
	            c,  0,                      quadrantSize,           // -> C12
	            quadrantSize, false);
	        
	        /**
	         * Calculate U
	         */
	        // t1 = (A21 - A11)
	        add(a,  aRowPtr + quadrantSize, aColPtr,                // A21 +
	            a,  aRowPtr,                aColPtr,                // A11
	            t1, 0,                      0,                      // -> t1
	            quadrantSize, true);
	        // t2 = (B11 + B12)
	        add(b,  bRowPtr,                bColPtr,                // B11 +
	            b,  bRowPtr,                bColPtr + quadrantSize, // B12
	            t2, 0,                      0,                      // -> t2
	            quadrantSize, false);
	        // U = t3 = (A21 - A11)(B11 + B12)
	        mul(t1, 0,                      0,                      // (A21 - A11) *
	            t2, 0,                      0,                      // (B11 + B12)
	            t3);                                                // -> U
	        // Add U To C22 Immediately So Value Can Be Discarded
	        add(c,  quadrantSize,           quadrantSize,           // C22 +
	            t3, 0,                      0,                      // U
	            c,  quadrantSize,           quadrantSize,           // -> C22
	            quadrantSize, false);

	        /**
	         * Calculate V
	         */
	        // t1 = (A12 - A22)
	        add(a,  aRowPtr,                aColPtr + quadrantSize, // A12 -
	            a,  aRowPtr + quadrantSize, aColPtr + quadrantSize, // A22
	            t1, 0,                      0,                      // -> t1
	            quadrantSize, true);
	        // t2 = (B21 + B22)
	        add(b,  bRowPtr + quadrantSize, bColPtr,                // B21 +
	            b,  bRowPtr + quadrantSize, bColPtr + quadrantSize, // B22
	            t2, 0,                      0,                      // -> t2
	            quadrantSize, false);
	        // V = t3 = (A12 - A22)(B21 + B22)
	        mul(t1, 0,                      0,                      // (A12 - A22) *
	            t2, 0,                      0,                      // (B21 + B22)
	            t3);                                                // -> V
	        // Add V To C11 Immediately So Value Can Be Discarded
	        add(c,  0,                      0,                      // C11 +
	            t3, 0,                      0,                      // V
	            c,  0,                      0,                      // -> C11
	            quadrantSize, false);
	    }

	    /**
	     * Adds two matrices, storing the result in
	     * a third matrix provided as a parameter.
	     * 
	     * @param a        1st Matrix
	     * @param aRowPtr  1st Matrix Row To Start From
	     * @param aColPtr  1st Matrix Column To Start From
	     * @param b        2nd Matrix
	     * @param bRowPtr  2nd Matrix Row To Start From
	     * @param bColPtr  2nd Matrix Column To Start From
	     * @param c        Matrix To Store Result
	     * @param cRowPtr  Row To Begin Storing Result
	     * @param cColPtr  Column To Begin Storing Result
	     * @param size     The size of the arrays being added.
	     * @param subtract Perform A-B If True
	     */
	    private static void add(int[][] a, int aRowPtr, int aColPtr,
	                     int[][] b, int bRowPtr, int bColPtr,
	                     int[][] c, int cRowPtr, int cColPtr,
	                     int size, boolean subtract)
	    {
	        for (int row = 0; row < size; row++)
	        {
	            for (int col = 0; col < size; col++)
	            {
	                c[cRowPtr + row][cColPtr + col] = a[aRowPtr + row][aColPtr + col]
	                    + (subtract ? -b[bRowPtr + row][bColPtr + col] : b[bRowPtr + row][bColPtr + col]);
	            }
	        }
	    }
	}

