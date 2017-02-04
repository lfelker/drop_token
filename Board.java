import java.util.*;

public class Board {
	private int boardSize;
	private int playerNumber;
	private int[][] board;
	private List<Integer> successfulPutColumns;
	private boolean gameIsOver;

	public Board(int size) {
		boardSize = size;
		playerNumber = 1;
		board = new int[4][4];
		successfulPutColumns = new LinkedList<Integer>();
		gameIsOver = false;
	}

	public String put(int column) {
		String result = placeToken(column);
		if (result.equals("ERROR")) {
			return "ERROR";
		} else if (isVictory()) {
			gameIsOver = true;
			return "WIN";
		} else if (boardIsFull()) {
			gameIsOver = true;
			return "DRAW";
 		} else {
 			switchPlayers();
 			return result;
 		}
	}

	public List<Integer> get() {
		return new ArrayList<Integer>(successfulPutColumns);
	}

	public int[][] getBoard() {
		final int[][] copy = new int[4][];
		for (int i = 0; i < boardSize; i++) {
			copy[i] = Arrays.copyOf(board[i], board[i].length);
		}
		return copy;
	}

	public boolean gameIsOver() {
		return gameIsOver;
	}

	private String placeToken(int column) {
		for (int i = board.length - 1; i >= 0; i--) {
			if (board[i][column - 1] == 0) {
				board[i][column - 1] = playerNumber;
				successfulPutColumns.add(column);
				return "OK";
			}
		}
		return "ERROR";
	}

	private boolean isVictory() {
		return rowFull() || columnFull() || diagnalFull();
	}

	private boolean boardIsFull() {
		return (boardSize * boardSize) == successfulPutColumns.size();
	}

	private void switchPlayers() {
		if (playerNumber == 1) {
			playerNumber = 2;
		} else {
			playerNumber = 1;
		}
	}

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

	private boolean diagnalFull() {
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