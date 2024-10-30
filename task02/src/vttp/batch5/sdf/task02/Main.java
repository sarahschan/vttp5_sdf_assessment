package vttp.batch5.sdf.task02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

	private static char[][] board = new char[3][3];

	public static void main(String[] args) throws Exception {

		// Check if board file was provided
		if (args.length != 1) {
			System.out.println("Usage: java -cp classes vttp.batch5.sdf.task02.Main TTT/figure1.txt");
			System.exit(-1);
		}

		File boardFile = new File(args[0]);
		if (!boardFile.exists() || !boardFile.isFile()) {
			System.out.println("Invalid file or file path");
			System.exit(-1);
		}


		// If file is valid, continue wiith program
		System.out.printf("Processing: %s\n", boardFile.getName());
		System.out.println();

		
		// Read the file and populate board
		FileReader fr = new FileReader(boardFile);
		BufferedReader br = new BufferedReader(fr);
		int y1 = 0;
		String line = "";
		while ((line = br.readLine()) != null) {
			char[] data = line.toCharArray();
			for (int x = 0; x < data.length; x++) {
				board[y1][x] = data[x];
			}
			y1++;
		}

		printBoard();


		// Check the board if it's X's turn to go
		int countX = 0;
		int countO = 0;
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				if (board[y][x] == 'X') {
					countX++;
				}
				if (board[y][x] == 'O') {
					countO++;
				}

			}
		}

		// Error and exit if it is not X's turn to go
		if (countX > countO) {
			System.out.println("-".repeat(30));
			System.out.printf("Error processing %s, it must be X's turn to go\n\n", boardFile.getName());
			System.exit(-1);
		}


		// Otherwise if it's all good....
		// Create a map to store all the results
		List<String> utilityList = new ArrayList<>();
		
		// For every empty position on the board
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				// If the space is empty
				if (board[y][x] == '.') {
					// Calculate utilityValue and store in map
					int utilityValue = checkUtility(y,x);
					// prepare data and add to the list
					String boardPosValue = String.format("y=%d, x=%d, utility=%d", y, x, utilityValue);
					utilityList.add(boardPosValue);

				}
			}

		}

		// print the utilityList
		System.out.println("-".repeat(30));
		for (String boardPosValue : utilityList) {
			System.out.println(boardPosValue);
		}

	}


	private static int checkUtility(int y, int x) {
		
		// Simulate placing the piece on the board
		board[y][x] = 'X';

		// Check for utility value at this placement
		// YOU ARE THE MAXIMIZER - next move is minimizer
		int utilityValue = minimax(0, false);

		// Undo the move
		board[y][x] = '.';

		return utilityValue;
		
	}


	private static int minimax(int depth, boolean isMaximizer) {
		// Check for base case
		if (checkWin('X')) return 1;   	// You win (maximizer)
		if (checkWin('O')) return -1;   	// Computer win (minimizer)
		if (checkTie(board)) return 0;          // Tie
		if (depth == 1) return 0;				// Only looking 1 step ahead after the simulated placing of X
												//	- neutral if 1 step ahead and no win/lose/tie
	
		int utilityValue;
	
		if (isMaximizer) {
			utilityValue = Integer.MIN_VALUE;     // Start with lowest possible value
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < 3; x++) {
					if (board[y][x] == '.') {
						board[y][x] = 'X';        // Simulate maximizer's move
						utilityValue = Math.max(utilityValue, minimax(depth + 1, false)); // Recursive call
						board[y][x] = '.';        // Undo the move
					}
				}
			}

		} else {
			utilityValue = Integer.MAX_VALUE;     // Start with highest possible value
			for (int y = 0; y < 3; y++) {
				for (int x = 0; x < 3; x++) {
					if (board[y][x] == '.') {
						board[y][x] = 'O';        // Simulate minimizer's move
						utilityValue = Math.min(utilityValue, minimax(depth + 1, true)); // Recursive call
						board[y][x] = '.';        // Undo the move
					}
				}
			}
		}
	
		return utilityValue;
	}
	

	private static boolean checkTie(char[][] board) {
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				if (board[y][x] == '.') {		// if empty space found
					return false;  				// still ongoing
				}
			}
		}

		return true;  							// No empty spaces - tie
	}


	// 0,0		0,1		0,2
	// 1,0		1,1		1,2
	// 2,0		2,1		2,2

	private static boolean checkWin(char player) {
		// if player's char in these win conditions, return true
		if ((board[0][0] == player) && (board[0][1] == player) && (board[0][2] == player) ||	// 1st row
    	    (board[1][0] == player) && (board[1][1] == player) && (board[1][2] == player) ||	// 2nd row
			(board[2][0] == player) && (board[2][1] == player) && (board[2][2] == player) ||	// 3rd row
			(board[0][0] == player) && (board[1][0] == player) && (board[2][0] == player) ||	// 1st col
			(board[0][1] == player) && (board[1][1] == player) && (board[2][1] == player) ||	// 2nd col
			(board[0][2] == player) && (board[1][2] == player) && (board[2][2] == player) ||	// 3rd col
			(board[0][0] == player) && (board[1][1] == player) && (board[2][2] == player) ||	// Diag \
			(board[0][2] == player) && (board[1][1] == player) && (board[2][0] == player)		// Diag /
		    ) {
			return true;

		} else {
			return false;
		}
	}


	public static void printBoard(){
   
        System.out.println("Board: ");
        System.out.println(""+ board[0][0] + board[0][1] + board[0][2]);
        System.out.println(""+ board[1][0] + board[1][1] + board[1][2]);
        System.out.println(""+ board[2][0] + board[2][1] + board[2][2]);
        System.out.println();

    }

}