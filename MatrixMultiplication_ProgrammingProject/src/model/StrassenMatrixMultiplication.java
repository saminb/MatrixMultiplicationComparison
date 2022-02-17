package model;

public class StrassenMatrixMultiplication {

    public static int[][] multiply(int[][] a, int[][] b)
    {
        int size = a.length;
        int[][] result = new int[size][size];

        if(size==1){
            result[0][0] = a[0][0]*b[0][0];
        }
        else
        { int newSize = size/2;
            int[][] a11 = seperate(a,0,0);
            int[][] a12 = seperate(a,0,newSize);
            int[][] a21 = seperate(a,newSize,0);
            int[][] a22 = seperate(a,newSize,newSize);
            int[][] b11 = seperate(b,0,0);
            int[][] b12 = seperate(b,0,newSize);
            int[][] b21 = seperate(b,newSize,0);
            int[][] b22 = seperate(b,newSize,newSize);


            int[][] M1 = multiply(add(a11, a22), add(b11, b22));
            int[][] M2 = multiply(add(a21, a22), b11);
            int[][] M3 = multiply(a11, subtract(b12, b22));
            int[][] M4 = multiply(a22, subtract(b21, b11));
            int[][] M5 = multiply(add(a11, a12), b22);
            int[][] M6 = multiply(subtract(a21, a11), add(b11, b12));
            int[][] M7 = multiply(subtract(a12, a22), add(b21, b22));

            int [][] C11 = add(subtract(add(M1, M4), M5), M7);
            int [][] C12 = add(M3, M5);
            int [][] C21 = add(M2, M4);
            int [][] C22 = add(subtract(add(M1, M3), M2), M6);

            merge(C11, result, 0 , 0);
            merge(C12, result, 0 , newSize);
            merge(C21, result, newSize, 0);
            merge(C22, result, newSize, newSize);
        }
        return result;
    }
    
    private static int[][] seperate(int[][] M, int row, int col)
    {
        int newSize = M.length/2;
        int[][] C = new int[newSize][newSize];
        for(int i = 0;i<newSize;i++)
        {
            for(int j = 0;j<newSize; j++)
            {
                C[i][j] = M[i+row][j+col];
            }
        }
        return C;
    }

    private static void merge(int[][]A,int[][]C,int row,int col)
    {
        int newRow = row;
        for(int i=0;i< A.length;i++)
        {
            int newCol = col;
            for(int j=0; j< A.length;j++)
            {
                C[newRow][newCol] = A[i][j];
                newCol++;
            }
            newRow++;
        }
    }

    private static int[][] add(int[][] a,int[][] b)
    {
        int n = a.length;
        int[][] c = new int[n][n];
        for(int i = 0;i<n;i++)
        {
            for(int j=0;j<n;j++)
                c[i][j] = a[i][j] + b[i][j];
        }
        return c;
    }

    private static int[][] subtract(int[][] a,int[][] b)
    {
        int n = a.length;
        int[][] c = new int[n][n];
        for(int i = 0;i<n;i++)
        {
            for(int j=0;j<n;j++)
                c[i][j] = a[i][j] - b[i][j];
        }
        return c;
    }
}