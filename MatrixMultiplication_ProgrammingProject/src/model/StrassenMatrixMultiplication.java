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
        {
            int[][] a11 = new int[size/2][size/2];
            int[][] a12 = new int[size/2][size/2];
            int[][] a21 = new int[size/2][size/2];
            int[][] a22 = new int[size/2][size/2];
            int[][] b11 = new int[size/2][size/2];
            int[][] b12 = new int[size/2][size/2];
            int[][] b21 = new int[size/2][size/2];
            int[][] b22 = new int[size/2][size/2];

            separate(a,a11,0,0);
            separate(a,a12,0,size/2);
            separate(a,a21,size/2,0);
            separate(a,a22,size/2,size/2);
            separate(b,b11,0,0);
            separate(b,b12,0,size/2);
            separate(b,b21,size/2,0);
            separate(b,b22,size/2,size/2);

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
            merge(C12, result, 0 , size/2);
            merge(C21, result, size/2, 0);
            merge(C22, result, size/2, size/2);
        }
        return result;
    }
    
    private static void separate(int[][]A,int[][]C,int i,int j)
    {
        for(int i1=0;i1< C.length;i1++)
        {
            for(int j1=0; j1<C.length;j1++)
            {
                C[i1][j1] = A[i][j];
                j++;
            }
            i++;
        }
    }

    private static void merge(int[][]A,int[][]C,int i,int j)
    {
        for(int i1=0;i1< A.length;i1++)
        {
            for(int j1=0; j1< A.length;j1++)
            {
                C[i][j] = A[i1][j1];
                j++;
            }
            i++;
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