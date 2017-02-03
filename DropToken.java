import java.util.*;

public class DropToken {
	private static final int BOARD_SIZE = 4;
	private static final Set<String> POSSIBLE_COMMANDS = new HashSet<String>(Arrays.asList(
			new String[] {"PUT", "EXIT", "GET", "BOARD"} ));

	public static void main(String[] args) {
		// Board dt_board = new Board(BOARD_SIZE);
		// TODO: command line input of System.in or a fileName for testing
		Scanner console = new Scanner(System.in);

		String[] commandTokens = getUserCommand(console);
		while (!commandTokens[0].equals("EXIT")) {
			switch (commandTokens[0]) {
				case "PUT":
					// String result = dt_board.put(tokens[1]);
					System.out.println("PUT");
					break;
				case "GET":
					//List<String> putColumnSuccess = dt_board.get();
					//printList(putColSuccess);
					System.out.println("GET");
					break;
				case "BOARD":
					// int[][] boardState = dt_board.getBoard();
					// printBoard(boardState);
					System.out.println("BOARD");
					break;
			}
			commandTokens = getUserCommand(console);
		}
	}

	private static String[] getUserCommand(Scanner console) {
		String[] commandTokens = null;
		Boolean goodUserInput = false;
		while (!goodUserInput) { 
			System.out.print("> ");
			String command = console.nextLine();
			if (command.trim().length() == 0) {
				continue;
			}
			commandTokens = command.trim().split("\\s+");
			if (isCorrectFormat(commandTokens)) {
				goodUserInput = true;
			} else {
				System.out.println("ERROR, WRONG COMMAND FORMAT");
				System.out.println("Accepted commands are: \"PUT <column number>\", \"GET\", \"BOARD\", or \"EXIT\"");
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
		}
		return true;
	}
}