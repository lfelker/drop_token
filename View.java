import java.util.List;
import java.util.Arrays;

// The View class handles the presentation of what the user sees on the command line.
public class View {
	private static final String[] PUT_RESULTS = {"ERROR", "OK", "WIN", "DRAW"};

	public static void printGameStartMessage(int boardSize) {
		System.out.println("Welcome to 98Point6 Drop Token");
		printCommandsMessage(boardSize);
		System.out.println("Player 1 starts");
	}

	public static void promptUser() {
		System.out.print("> ");
	}

	public static void printCommandErrorMessage(int boardSize) {
		System.out.println("ERROR, WRONG COMMAND FORMAT");
		printCommandsMessage(boardSize);
	}

	private static void printCommandsMessage(int boardSize) {
		System.out.println("Accepted commands are:");
		System.out.println("Command: \"PUT <column number 1 - " + boardSize + ">\" Response: " + Arrays.toString(PUT_RESULTS));
		System.out.println("Command: \"GET\" Response: List of columns that have been successfully put to.");
		System.out.println("Command: \"BOARD\" Response: a " + boardSize + "x" + boardSize + " matrix that shows the game state.");
		System.out.println("Command: \"EXIT\" Response: Ends the program");
	}

	// Accepts: List<Integer>
	// Displays: Each element of the list on a new line 
	public static void printList(List<Integer> list) {
		for (Integer i : list) {
			System.out.println(i);
		}
	}

	// Accepts: int[][] of equal width and height.
	// Displays: The two dimensional array where the 0th row is assumed to be on the top.
	public static void printBoard(int[][] board) {
		for (int r = 0; r < board.length; r++) {
			System.out.print("|");
			for (int c = 0; c < board[r].length; c++) {
				System.out.print(" " + board[r][c]);
			}
			System.out.println();
		}
		System.out.print("+");
		for (int i = 0; i < board.length; i++) {
			System.out.print("--");
		}
		System.out.println();
		System.out.print(" ");
		for (int i = 0; i < board.length; i++) {
 			System.out.print(" " + (i + 1));
 		}
 		System.out.println();
	}

	public static void printGameOverError() {
		System.out.println("The game is over, no more token placing allowed");
	}

	public static void printResultMessage(int resultValue) {
		System.out.println(PUT_RESULTS[resultValue]);
	}
}