package org.kdu.jsudoku;

import java.util.Collections;
import java.util.LinkedList;


public class Sudoku {
	public static final int SIZE = 9;
	public static final int EASY = 43;
	public static final int MEDIUM = 36;
	public static final int HARD = 27;
	
	public static int[][] generateNumbers() {
		int matrix[][] = new int[SIZE][SIZE];

		clearMatrix(matrix);
		int tries = 3;
		
		while (!isFilled(matrix)) {
			int pos[] = getNextToBeFilled(matrix);

			int i = pos[0];
			int j = pos[1];
			int nextTry = -1;

			LinkedList<Integer> values = new LinkedList<Integer>();
			for (int o = 1; o <= SIZE; o++) {
				values.add(o);
			}
			
			LinkedList<Integer> possible = fit(matrix, i, j, values);

			Collections.shuffle(possible);

			if (possible.size() == 0) {
				for (int k = 0; k < j; k++) {
					matrix[i][k] = -1;
				}
				tries--;
			}
			
			if (tries == 0) {
				//try again
				clearMatrix(matrix);
				tries = 3;
			}
			
			nextTry = getNext(possible);
			
			matrix[i][j] = nextTry;
		}

		//Sudoku.printMatrix(matrix);
		
		return matrix;
	}

	private static void clearMatrix(int[][] matrix) {
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				matrix[i][j] = -1;
			}
		}
	}

	private static int getNext(LinkedList<Integer> values) {
		int pos = -1;
		for (int i = 0; i < values.size(); i++) {
			if (values.get(i) != -1) {
				pos = i;
				break;
			}
		}
		if (pos == -1) {
			return -1;
		}
		int ret = values.get(pos);
		values.set(pos, -1);
		return ret;
	}

	private static LinkedList<Integer> fit(int[][] matrix, int row, int column, LinkedList<Integer> values) {
		LinkedList<Integer> ret = new LinkedList<Integer>();
		for (Integer rand : values) {
			boolean possible = true;
			for (int i = 0; i < SIZE; i++) {
				if (matrix[i][column] == rand) {
					possible = false;
				}

				if (matrix[row][i] == rand) {
					possible = false;
				}
			}

			if (possible) {
				int sqrt = (int) Math.sqrt(SIZE);

				int iniRow = getIni(sqrt, row);
				int iniColumn = getIni(sqrt, column);

				for (int k = iniRow; k < iniRow + sqrt; k++) {
					for (int l = iniColumn; l < iniColumn + sqrt; l++) {
						if (matrix[k][l] == rand) {
							possible = false;
						}
					}
				}
				
				if (possible) {
					ret.add(rand);
				}
			}
		}
		return ret;
	}

	private static int getIni(int sqrt, int find) {
		int values[] = new int[SIZE];
		int limit = sqrt;
		for (int i = 0; i < SIZE; i++) {
			values[i] = i;
		}

		int value = 0;
		
		while (true) {
			if (find <= values[limit - 1]) {
				return value;
			}
			value += sqrt;
			limit += sqrt;
		}
	}

	private static int[] getNextToBeFilled(int[][] matrix) {
		int ret[] = new int[2];
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (matrix[i][j] == -1) {
					ret[0] = i;
					ret[1] = j;
					return ret;
				}
			}
		}
		ret[0] = -1;
		return ret;
	}

	private static boolean isFilled(int[][] matrix) {
		return getNextToBeFilled(matrix)[0] == -1;
	}

	/*private static void printMatrix(int [][] matrix) {
		for (int i = 0; i < SIZE; i++) {
			System.out.println();
			for (int j = 0; j < SIZE; j++) {
				System.out.print(matrix[i][j] + " ");
			}
		}
	}*/
}
