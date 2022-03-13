package model;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * In this class we are implementing Strassen algorithm using ForK Join Pool.
 * This way the Strassen method will run faster and reduces wait time.
 * @author samin
 *
 */
public class StrassenMatrixMultiplication {
	/**
	 * @param Matrix a
	 * @param  Matrix b
	 * @param n is size
	 * @return result
	 */
	public static int[][] forkJoinMultiply(int[][] a, int[][] b, int n) {

        int m = setSize(n);
        int[][] am = new int[m][m];
        int[][] bm = new int[m][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                am[i][j] = a[i][j];
                bm[i][j] = b[i][j];
            }
        }

        Tasks task = new Tasks(am, bm);
        ForkJoinPool pool = new ForkJoinPool();

        int[][] strassen = pool.invoke(task);

        int[][] result = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = strassen[i][j];
            }
        }
        return result;
    }

    /**
     * @param n
     * @return new dimensions for the matrices
     */
    private static int setSize(int n) {
        int log2 = (int) Math.ceil(Math.log(n) / Math.log(2));
        return (int) Math.pow(2, log2);
    }
    /**
     * @author samin
     *
     */
    private static class Tasks extends RecursiveTask<int[][]> {

		private static final long serialVersionUID = 6970008569688173292L;
		private int size;
        private int[][] matrixA;
        private int[][] matrixB;

        Tasks(int[][] a, int[][] b) {
            this.matrixA = a;
            this.matrixB = b;
            this.size = a[0].length;
        }

        /**
         *
         */
        @Override
        public int[][] compute() {
            if (size <= 64) {
                return IterativeMatrixMultiplication.multiply(matrixA, matrixB, size);
            }

            size = size / 2;

            int[][] a11 = new int[size][size];
            int[][] a12 = new int[size][size];
            int[][] a21 = new int[size][size];
            int[][] a22 = new int[size][size];

            int[][] b11 = new int[size][size];
            int[][] b12 = new int[size][size];
            int[][] b21 = new int[size][size];
            int[][] b22 = new int[size][size];

            seperate(matrixA, a11, a12, a21, a22, size);
            seperate(matrixB, b11, b12, b21, b22, size);
            //Dividing the tasks into 7 which is typical in Strassen
            Tasks task1 = new Tasks(add(a11, a22), add(b11, b22));
            Tasks task2 = new Tasks(add(a21, a22), b11);
            Tasks task3 = new Tasks(a11, subtract(b12, b22));
            Tasks task4 = new Tasks(a22, subtract(b21, b11));
            Tasks task5 = new Tasks(add(a11, a12), b22);
            Tasks task6 = new Tasks(subtract(a21, a11), add(b11, b12));
            Tasks task7 = new Tasks(subtract(a12, a22), add(b21, b22));

            task1.fork();
            task2.fork();
            task3.fork();
            task4.fork();
            task5.fork();
            task6.fork();
            task7.fork();

            int[][] p1 = task1.join();
            int[][] p2 = task2.join();
            int[][] p3 = task3.join();
            int[][] p4 = task4.join();
            int[][] p5 = task5.join();
            int[][] p6 = task6.join();
            int[][] p7 = task7.join();

            int[][] c11 = add(add(p1, p4), subtract(p7, p5));
            int[][] c12 = add(p3, p5);
            int[][] c21 = add(p2, p4);
            int[][] c22 = add(subtract(p1, p2), add(p3, p6));

            return merge(c11, c12, c21, c22);
        }

    }


    /**
     * @param A
     * @param a11
     * @param a12
     * @param a21
     * @param a22
     * @param n
     */
    private static void seperate(int[][] A, int[][] a11, int[][] a12, int[][] a21, int[][] a22, int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a11[i][j] = A[i][j];
                a12[i][j] = A[i][j + n];
                a21[i][j] = A[i + n][j];
                a22[i][j] = A[i + n][j + n];
            }
        }
    }
    
    /**
     * @param a11
     * @param a12
     * @param a21
     * @param a22
     * @return C
     */
    private static int[][] merge(int[][] a11, int[][] a12, int[][] a21, int[][] a22) {
        int n = a11.length;
        int[][] A = new int[n * 2][n * 2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = a11[i][j];
                A[i][j + n] = a12[i][j];
                A[i + n][j] = a21[i][j];
                A[i + n][j + n] = a22[i][j];
            }
        }
        return A;
    }

    /**
     * @param A
     * @param B
     * @return C
     */
    private static int[][] add(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B[i][j];
            }
        }
        return C;
    }

    /**
     * @param A
     * @param B
     * @return C
     */
    private static int[][] subtract(int[][] A, int[][] B) {
        int n = A.length;
        int[][] C = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - B[i][j];
            }
        }
        return C;
    }

}