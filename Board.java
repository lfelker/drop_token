import java.util.*;

// The Board class maintains the state of a game of drop token.
public class Board {
	private int boardSize;
	private int playerNumber;
	private int[][] board;
	private List<Integer> successfulPutColumns;
	private boolean gameIsOver;

	// Accepts: int size, and initializes a game of drop token
	//			with a board of that width and height.
	public Board(int size) {
		boardSize = size;
		playerNumber = 1;
		board = new int[size][size];
		successfulPutColumns = new LinkedList<Integer>();
		gameIsOver = false;
	}

	// Accepts: int column
	// Returns: int indicating the result of placing a token in the given column.
	//          0 = error, 1 = ok, 2 = win, 3 = draw
	public int put(int column) {
		int result = placeToken(column);
		if (result == -1) {
			return 0;
		} else if (isVictory()) {
			gameIsOver = true;
			return 2;
		} else if (boardIsFull()) {
			gameIsOver = true;
			return 3;
 		} else {  // result == 1 and game is not over
 			switchPlayers();
 			return 1;
 		}
	}

	// Returns: List<Integer> representing the column numbers of successful
	//			token placements in the order they occurred.
	public List<Integer> get() {
		return new ArrayList<Integer>(successfulPutColumns);
	}

	// Returns: int[][] representing the current state of the game board.
	public int[][] getBoard() {
		final int[][] copy = new int[boardSize][];
		for (int i = 0; i < boardSize; i++) {
			copy[i] = Arrays.copyOf(board[i], board[i].length);
		}
		return copy;
	}

	// Returns: true if the game has been won or resulted in a draw, otherwise returns false.
	public boolean gameIsOver() {
		return gameIsOver;
	}

	// Accepts: int column representing the column to place a token in.
	// Returns: int indicating result of placement. 1 if successful, -1 if error occurred.
	private int placeToken(int column) {
		for (int i = board.length - 1; i >= 0; i--) {
			if (board[i][column - 1] == 0) {
				board[i][column - 1] = playerNumber;
				successfulPutColumns.add(column);
				return 1;
			}
		}
		return -1;
	}

	// Switches the state of the game to be the next players turn.
	private void switchPlayers() {
		if (playerNumber == 1) {
			playerNumber = 2;
		} else {
			playerNumber = 1;
		}
	}

	// Returns: true the board is in a state where the current player won, otherwise returns false.
	private boolean isVictory() {
		return rowFull() || columnFull() || diagonalFull();
	}

	// Returns: true if the board is completely full of tokens, otherwise false.
	private boolean boardIsFull() {
		return (boardSize * boardSize) == successfulPutColumns.size();
	}

	// Returns: true if any row is full of the current player's tokens, otherwise returns false.
	private boolean rowFull() {
		for (int r = 0; r < boardSize; r++) {
			for (int c = 0; c < boardSize; c++) {
				if (board[r][c] == playerNumber && c == boardSize - 1) {
					return true;
				} else if (board[r][c] != playerNumber) {
					break;
				}
			}
		}
		return false;
	}

	// Returns: true if any column is full fo the current player's tokens, otherwise returns false.
	private boolean columnFull() {
		for (int c = 0; c < boardSize; c++) {
			for (int r = 0; r < boardSize; r++) {
				if (board[r][c] == playerNumber && r == boardSize - 1) {
					return true;
				} else if (board[r][c] != playerNumber) {
					break;
				}
			}
		}
		return false;
	}

	// Returns: true if either diagonal is full of the current player's tokens, otherwise returns false.
	private boolean diagonalFull() {
		boolean bottomLeftDiagonal = true;
		boolean topLeftDiagonal = true;
		for (int i = 0; i < boardSize; i++) {
			if (board[i][i] != playerNumber) {
				bottomLeftDiagonal = false;
			}
			if (board[i][boardSize - 1 - i] != playerNumber) {
				topLeftDiagonal = false;
			}
		}
		return bottomLeftDiagonal || topLeftDiagonal;
	}
}