import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;

// The Drop Token class initializes the game and controls the results of the user input
// To start a game you run "java DropToken arg1"
// arg1 is either the word "Console" to recive input from System.in or the path of some file.txt to read input from
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
			View.printGameStartMessage(BOARD_SIZE);
		} else {
			try {
				userInput = new Scanner(new File(args[0]));
			} catch (Exception e) {  // File Not Found or Null Pointer Exception
				throw new IllegalArgumentException("file input is not valid");
			}
		}

		Board dtBoard = new Board(BOARD_SIZE);

		String[] commandTokens = getUserCommand(userInput);
		while (!commandTokens[0].equals("EXIT")) {
			switch (commandTokens[0]) {
				case "PUT":
					if (dtBoard.gameIsOver()) {
						View.printGameOverError();
					} else {
						int resultValue = dtBoard.put(Integer.parseInt(commandTokens[1]));
						View.printResultMessage(resultValue);
					}
					break;
				case "GET":
					List<Integer> successfulPutColumns = dtBoard.get();
					View.printList(successfulPutColumns);
					break;
				case "BOARD":
					int[][] boardState = dtBoard.getBoard();
					View.printBoard(boardState);
					break;
			}
			commandTokens = getUserCommand(userInput);
		}
	}

	// Accepts: Scanner to receive input from and prompts user for input until valid input is received.
	// Returns: String[] representing the tokens of the user's input.
	//			The length of the returned array is 1 or 2 depending on the command, where the 0th index is
	//			the command and if the command is "PUT" the 1st index is the column value.
	private static String[] getUserCommand(Scanner userInput) {
		String[] commandTokens = null;
		Boolean goodUserInput = false;
		while (!goodUserInput) {
			View.promptUser();
			String command = userInput.nextLine().trim();
			if (command.length() == 0) {
				continue;
			}
			commandTokens = command.split("\\s+");
			if (isCorrectFormat(commandTokens)) {
				goodUserInput = true;
			} else {
				View.printCommandErrorMessage(BOARD_SIZE);
			}
		}
		return commandTokens;
	}

	// Accepts: String[] representing the tokens of the user input separated by white space.
	// Returns: true if user input is valid, otherwise returns false.
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
}