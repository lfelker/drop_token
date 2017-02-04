import java.util.*;
import java.io.File;

public class DropToken {
	private static final int BOARD_SIZE = 4;
	private static final Set<String> POSSIBLE_COMMANDS = new HashSet<String>(Arrays.asList(
			new String[] {"PUT", "EXIT", "GET", "BOARD"} ));

	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("you must have one argument,\neither \"Console\" or some file.txt to read input from");
		}

		Scanner userInput = null;
		if (args[0].equals("Console")) {
			userInput = new Scanner(System.in);
		} else {
			try {
				userInput = new Scanner(new File(args[0]));
			} catch (Exception e) { // File Not Found or Null Pointer
				throw new IllegalArgumentException("file cannot be found");
			}
		}

		Board dtBoard = new Board(BOARD_SIZE);

		String[] commandTokens = getUserCommand(userInput);
		while (!commandTokens[0].equals("EXIT")) {
			switch (commandTokens[0]) {
				case "PUT":
					if (dtBoard.gameIsOver()) {
						System.out.println("The game is over, no more token placing allowed");
					} else {
						String result = dtBoard.put(Integer.parseInt(commandTokens[1]));
						System.out.println(result);
					}
					break;
				case "GET":
					List<Integer> successfulPutColumns = dtBoard.get();
					printList(successfulPutColumns);
					break;
				case "BOARD":
					int[][] boardState = dtBoard.getBoard();
					printBoard(boardState);
					break;
			}
			commandTokens = getUserCommand(userInput);
		}
	}

	private static String[] getUserCommand(Scanner userInput) {
		String[] commandTokens = null;
		Boolean goodUserInput = false;
		while (!goodUserInput) { 
			System.out.print("> ");
			String command = userInput.nextLine();
			if (command.trim().length() == 0) {
				continue;
			}
			commandTokens = command.trim().split("\\s+");
			if (isCorrectFormat(commandTokens)) {
				goodUserInput = true;
			} else {
				System.out.println("ERROR, WRONG COMMAND FORMAT");
				System.out.println("Accepted commands are: \"PUT <column number 1 - " +
						BOARD_SIZE + ">\", \"GET\", \"BOARD\", or \"EXIT\"");
			}
		}
		return commandTokens;
	}

	private static boolean isCorrectFormat(String[] commandTokens) {
		if (commandTokens.length == 0 || commandTokens.length > 2) return false;
		if (!POSSIBLE_COMMANDS.contains(commandTokens[0])) return false;
		if (commandTokens[0].equals("PUT")) {
			if (commandTokens.length != 2) return false;
			try {
				int column = Integer.parseInt(commandTokens[1]);
				if (column < 1 || column > BOARD_SIZE) return false;
			} catch (NumberFormatException nfe) {
				return false;
			}
		} else if (commandTokens.length > 1) {
			return false;
		}
		return true;
	}

	private static void printList(List<Integer> list) {
		for (Integer i : list) {
			System.out.println(i);
		}
	}

	// assumes row 1 of board is on top
	private static void printBoard(int[][] board) {
		for (int r = 0; r < BOARD_SIZE; r++) {
			System.out.print("|");
			for (int c = 0; c < BOARD_SIZE; c++) {
				System.out.print(" " + board[r][c]);
			}
			System.out.println();
		}
		System.out.println("+--------");
		System.out.println("  1 2 3 4");
	}
}