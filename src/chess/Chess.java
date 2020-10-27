package chess;

import java.awt.desktop.SystemSleepEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import models.Bishop;
import models.King;
import models.Knight;
import models.Pawn;
import models.Queen;
import models.Rook;
import models.Tile;

/**
 * @author Greg Fuerte
 * @author Aries Regalado
 */
public class Chess {
	static Tile[][] board = new Tile[8][8];

	static int[] wKing = { 7, 4 };
	static int[] bKing = { 0, 4 };
	static int whiteCheck = 0;
	static int blackCheck = 0;

	static String previousMethod;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		initBoard();
		printBoard();

		boolean gameContinues = true;

		String input;
		boolean whitesMove = true;
		boolean drawCheck = false;		
		
		while (gameContinues) {
			if (whitesMove == true) {
				System.out.print("White's move: ");
			} else {
				System.out.print("Black's move: ");
			}

			input = scanner.nextLine();
			if (input.equals("resign")) { // resign
				if (whitesMove) {
					System.out.println("Black wins");
				} else {
					System.out.println("White wins");
				}
				break;
			} else if (drawCheck) {
				if (input.equals("draw")) {
					System.out.println("draw");
					break;
				}
			} else if (input.length() >= 5) { // normal turn
				if (checkInput(input) == true) {

					int[] origin = getIndex(input.substring(0, 2));
					int[] destination = getIndex(input.substring(3, 5));

					if (validateMove(origin, destination, input, whitesMove)) {
						check();
						if (whitesMove && blackCheck >= 1) {

							if (checkmate("black")) {
								System.out.println("Checkmate\n");
								printBoard();
								System.out.println("White wins");
								break;
							}
							System.out.println("Check");
						} else if (!whitesMove && whiteCheck >= 1) {

							if (checkmate("white")) {
								System.out.println("Checkmate\n");
								printBoard();
								System.out.println("Black wins");
								break;
							}
							System.out.println("Check");
						}

						System.out.println();
						printBoard();

						previousMethod = input.substring(0, 5);
						drawCheck = ((input.length() == 11 && input.substring(5).equals(" draw?"))
								|| (input.length() == 13 && input.substring(7).equals(" draw?"))) ? true : false;
						whitesMove = ((whitesMove) ? false : true);
					} else {
						System.out.println("Illegal move, try again");
					}
				}
			}
		}
		scanner.close();
	}

	/**
	 * Determines if a the previous input correspond to a pawn double move.
	 * @param prev The previous input from the current one.
	 * @return boolean Returns true if the previous input correspond to a pawn double move, false otherwise.
	 */
	public static boolean validPrevious(String prev) {
		String[] arr = { "a2 a4", "b2 b4", "c2 c4", "d2 d4", "e2 e4", "f2 f4", "g2 g4", "h2 h4", "a7 a5", "b7 b5",
				"c7 c5", "d7 d5", "e7 e5", "f7 f5", "g7 g5", "h7 h5" };

		for (int i = 0; i < arr.length; i++) {
			if (prev.equals(arr[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines if a given move is valid by checking if that particular piece can move at that location, the given situation supports it, and it does not leave their king in check.
	 * @param origin Where the piece is currently located in the board via board index.
	 * @param destination Where the piece wants to go in the board via board index.
	 * @param input The user input used for pawn promotion.
	 * @param whitesMove True if the current move is white's, false otherwise
	 * @return True if the given move is valid, false otherwise.
	 */
	public static boolean validateMove(int[] origin, int[] destination, String input, boolean whitesMove) {
		if (destination[0] < 0 || destination[0] > 7 || destination[1] < 0 || destination[0] > 7) {
			return false;
		}

		Tile piece = board[origin[0]][origin[1]];
		Tile target = board[destination[0]][destination[1]];
		int[][] moves = piece.getMoves();

		if (piece.getOccupation() == false) {
			return false;
		}

		if ((whitesMove == true && piece.getTeam().equals("black"))
				|| (whitesMove == false && piece.getTeam().equals("white"))) {
			return false;
		}

		if (piece.getTeam().equals(target.getTeam())) {
			return false;
		}

		if (piece.getPieceName().equals("Pawn")) { // Pawn
			boolean validMove = false;
			if (piece.getTeam().equals("white")) {				
				int movesW[][] = { { -1, 0 } };
				int moves2W[][] = { { -2, 0 } };
				int attackW[][] = { { -1, 1 }, { -1, -1 } };
				if (checkCollisions(origin, destination, moves2W) && piece.getFirstMove()) { // double move
					if (!target.getOccupation()) {
						validMove = true;
					}
				} else if (checkMove(origin, destination, movesW)) { // normal move
					if (!target.getOccupation()) {
						validMove = true;
					}
				} else if (checkMove(origin, destination, attackW)) { // attack move
					if (target.getOccupation()) {
						validMove = true;
					} else { // en passant
						if (validPrevious(previousMethod)) {
							int[] prevIndex = getIndex(previousMethod.substring(3, 5));
							if ((prevIndex[0] - destination[0] == 1) && (prevIndex[1] == destination[1])) {
								Tile temp = board[prevIndex[0]][prevIndex[1]];
								movePiece(origin, destination, piece, target);
								board[prevIndex[0]][prevIndex[1]] = new Tile();
								if (checksOwnKing(piece.getTeam())) {
									reverseMove(origin, destination, temp, target);
									board[prevIndex[0]][prevIndex[1]] = temp;
									return false;
								} else {
									if (piece.getFirstMove())
										piece.setFirstMove(false);
									return true;
								}
							}
						}
					}
				}
			} else if (piece.getTeam().equals("black")) {
				int movesB[][] = { { 1, 0 } };
				int moves2B[][] = { { 2, 0 } };
				int attackB[][] = { { 1, 1 }, { 1, -1 } };

				if (checkCollisions(origin, destination, moves2B) && piece.getFirstMove()) { // double move
					if (!target.getOccupation()) {
						validMove = true;
					}
				} else if (checkMove(origin, destination, movesB)) { // normal move
					if (!target.getOccupation()) {
						validMove = true;
					}
				} else if (checkMove(origin, destination, attackB)) { // attack move
					if (target.getOccupation()) {
						validMove = true;
					} else { // en passant
						if (validPrevious(previousMethod)) {
							int[] prevIndex = getIndex(previousMethod.substring(3, 5));
							if ((destination[0] - prevIndex[0] == 1) && (destination[1] == prevIndex[1])) {
								Tile temp = board[prevIndex[0]][prevIndex[1]];
								movePiece(origin, destination, piece, target);
								board[prevIndex[0]][prevIndex[1]] = new Tile();
								if (checksOwnKing(piece.getTeam())) {
									reverseMove(origin, destination, temp, target);
									board[prevIndex[0]][prevIndex[1]] = temp;
									return false;
								} else {
									if (piece.getFirstMove())
										piece.setFirstMove(false);
									return true;
								}
							}
						}
					}
				}
			}

			if (validMove) {
				Tile temp = piece;
				if (destination[0] == 0 || destination[0] == 7) { // promotion
					piece = (piece.getTeam().equals("white")) ? pawnPromotion(input, "white")
							: pawnPromotion(input, "black");
					movePiece(origin, destination, piece, target);
					if (checksOwnKing(piece.getTeam())) {
						reverseMove(origin, destination, temp, target);
						return false;
					}
				} else {
					movePiece(origin, destination, piece, target);
					if (checksOwnKing(piece.getTeam())) {
						reverseMove(origin, destination, piece, target);
						return false;
					}
				}
			} else {
				return false;
			}
			if (piece.getFirstMove())
				piece.setFirstMove(false);

		} else if (piece.getPieceName().equals("Knight")) {
			// Case: Knight jump over pieces, no need for collision check
			if (checkMove(origin, destination, moves)) {
				movePiece(origin, destination, piece, target);
				if (checksOwnKing(piece.getTeam())) {
					reverseMove(origin, destination, piece, target);
					return false;
				} else {
					piece.setFirstMove(false);
				}
			} else {
				return false;
			}
		} else if (piece.getPieceName().equals("King")) { // King
			if (piece.getTeam().equals("white") && validCastling()) {
				// if its King is white and going to right side towards rook
				if (destination[0] == 7 && destination[1] == 6) {
					movePiece(origin, destination, piece, target);
					board[7][5] = board[7][7];
					board[7][7] = new Tile();

					check();
					if (whiteCheck > 0) {
						reverseCastle(destination, "white");
						return false;
					}
					board[7][5].setFirstMove(false);
				} else if (destination[0] == 7 && destination[1] == 2) { // if king is white and going to left side
					movePiece(origin, destination, piece, target);
					board[7][3] = board[7][0];
					board[7][0] = new Tile();

					check();
					if (whiteCheck > 0) {
						reverseCastle(destination, "white");
						return false;
					}
					board[7][3].setFirstMove(false);
				} else {
					if(!checkMove(origin, destination, moves)) return false;
					movePiece(origin, destination, piece, target);
					if (piece.getTeam().equals("white") && checksOwnKing("white")) {
						reverseMove(origin, destination, piece, target);
						return false;
					}
				}
			} else if (piece.getTeam().equals("black") && validCastling()) {
				// if its King is black and going to right side towards rook
				if (destination[0] == 0 && destination[1] == 6) {
					movePiece(origin, destination, piece, target);
					board[0][5] = board[0][7];
					board[0][7] = new Tile();

					check();
					if (blackCheck > 0) {
						reverseCastle(destination, "black");
						return false;
					}
					board[0][5].setFirstMove(false);
				} else if (destination[0] == 0 && destination[1] == 2) { // if king is black and going to left side
					movePiece(origin, destination, piece, target);
					board[0][3] = board[0][0];
					board[0][0] = new Tile();

					check();
					if (blackCheck > 0) {
						reverseCastle(destination, "black");
						return false;
					}
					board[0][3].setFirstMove(false);
				} else {
					if(!checkMove(origin, destination, moves)) return false;
					movePiece(origin, destination, piece, target); // for now
					if (piece.getTeam().equals("black") && checksOwnKing("black")) {
						reverseMove(origin, destination, piece, target);
						return false;
					}
				}
			} else { // normal move
				if(!checkMove(origin, destination, moves)) return false;
				movePiece(origin, destination, piece, target);
				if (piece.getTeam().equals("white") && checksOwnKing("white")) {
					reverseMove(origin, destination, piece, target);
					return false;
				} else if (piece.getTeam().equals("black") && checksOwnKing("black")) {
					reverseMove(origin, destination, piece, target);
					return false;
				}
			}
			piece.setFirstMove(false);

		} else { // Queen, Bishop, Rook no special rules to mind
			if (checkCollisions(origin, destination, moves)) {
				movePiece(origin, destination, piece, target);
				if (checksOwnKing(piece.getTeam())) {
					reverseMove(origin, destination, piece, target);
					return false;
				} else {
					piece.setFirstMove(false);
				}
			} else {
				return false;
			}
		}

		// secondary protection, this if-else statement should never be reached
		if (piece.getTeam().equals("white") && whiteCheck >= 1) {
			System.out.println("This should not be happening");
			return false;
		} else if (piece.getTeam().equals("black") && blackCheck >= 1) {
			System.out.println("This should not be happening");
			return false;
		}

		return true;
	}

	/**
	 * Determines if there is a piece in the way of a given move.
	 * @param origin Where the piece is currently located in the board via board index.
	 * @param destination Where the piece wants to go in the board via board index.
	 * @param moves A 2d array of every possible move a Queen/Bishop/Rook can make.
	 * @return Returns true if there is a collision on the way of a move, false otherwise.
	 */
	public static boolean checkCollisions(int[] origin, int[] destination, int[][] moves) {
		int count = -1;
		for (int i = 0; i < moves.length; i++) {
			if (origin[0] + moves[i][0] == destination[0] && origin[1] + moves[i][1] == destination[1]) {
				return true;
			}
			count++;
			try {
				Tile temp = board[origin[0] + moves[i][0]][origin[1] + moves[i][1]];
				if (temp.getOccupation() == true) { // collision has occurred
					i = i + 6 - count;
					count = -1;
				}

				if (count == 6)
					count = 0;
			} catch (ArrayIndexOutOfBoundsException e) {
			}
		}
		return false;
	}

	/**
	 * Determines if a given move is valid by checking matching the destination with the origin plus a particular move.
	 * @param origin Where the piece is currently located in the board via board index.
	 * @param destination Where the piece wants to go in the board via board index.
	 * @param moves A 2d array of every possible move that piece can make.
	 * @return Returns true if the given move is reachable and valid, false otherwise.
	 */
	public static boolean checkMove(int[] origin, int[] destination, int[][] moves) {
		for (int i = 0; i < moves.length; i++) {
			if (origin[0] + moves[i][0] == destination[0] && origin[1] + moves[i][1] == destination[1]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Given where the piece that wants to move is located, it swaps tiles with tile located at the destination. If an enemy piece is located there, captures it.
	 * @param origin Where the piece is currently located in the board via board index.
	 * @param destination Where the piece wants to go in the board via board index.
	 * @param piece The piece that wants to move.
	 * @param target The enemy piece that piece wants to attack or the tile that the piece wants to move to.
	 */
	public static void movePiece(int[] origin, int[] destination, Tile piece, Tile target) {
		if (target.getTeam().equals("")) {
			board[destination[0]][destination[1]] = piece;
			board[origin[0]][origin[1]] = target;
		} else {
			board[destination[0]][destination[1]] = piece;
			board[origin[0]][origin[1]] = new Tile();
		}

		if (piece.getPieceName().equals("King")) {
			if (piece.getTeam().equals("white")) {
				wKing[0] = destination[0];
				wKing[1] = destination[1];
			} else if (piece.getTeam().equals("black")) {
				bKing[0] = destination[0];
				bKing[1] = destination[1];
			}
		}
	}

	/**
	 * Reverses the move a piece most recently made on the board.
	 * @param origin Where the piece was located in the board via board index.
	 * @param destination Where the piece is currently located in the board via board index.
	 * @param piece The piece that recently moved.
	 * @param target The enemy piece that the piece attacked or the tile object that the piece swapped places with.
	 */
	public static void reverseMove(int[] origin, int[] destination, Tile piece, Tile target) {
		board[destination[0]][destination[1]] = target;
		board[origin[0]][origin[1]] = piece;

		if (piece.getPieceName().equals("King")) {
			if (piece.getTeam().equals("white")) {
				wKing[0] = origin[0];
				wKing[1] = origin[1];
			} else if (piece.getTeam().equals("black")) {
				bKing[0] = origin[0];
				bKing[1] = origin[1];
			}
		}
	}

	/**
	 * Reverse the castle a king most recently made on the board.
	 * @param origin Where the king was located in the board via board index.
	 * @param team What team the king sides with.
	 */
	public static void reverseCastle(int[] origin, String team) {
		if (team.equals("white")) {
			if (origin[0] == 7 && origin[1] == 2) {
				board[7][0] = board[7][3];
				board[7][3] = new Tile();
				board[7][4] = board[7][2];
				board[7][2] = new Tile();
			} else if (origin[0] == 7 && origin[1] == 6) {
				board[7][7] = board[7][5];
				board[7][5] = new Tile();
				board[7][4] = board[7][6];
				board[7][6] = new Tile();
			}
			wKing[0] = 7;
			wKing[1] = 4;
		} else if (team.equals("black")) {
			if (origin[0] == 0 && origin[1] == 2) {
				board[0][0] = board[0][3];
				board[0][3] = new Tile();
				board[0][4] = board[0][2];
				board[0][2] = new Tile();
			} else if (origin[0] == 0 && origin[1] == 6) {
				board[0][7] = board[0][5];
				board[0][5] = new Tile();
				board[0][4] = board[0][6];
				board[0][6] = new Tile();
			}
			bKing[0] = 0;
			bKing[1] = 4;
		}
	}

	/**
	 * Given the most recent user input and team of the piece, returns the piece object of what the user wants the pawn to promote into.
	 * @param input The user input.
	 * @param team What team the user resides with.
	 * @return Returns the new Tile object that pawn promoted to.
	 */
	public static Tile pawnPromotion(String input, String team) {
		if (input.length() != 7 && input.length() != 13)
			return new Queen(team.charAt(0) + "" + "Q");

		Tile piece = null;
		switch (input.charAt(6)) {
		case 'B':
			piece = new Bishop(team.charAt(0) + "" + input.charAt(6));
			break;
		case 'N':
			piece = new Knight(team.charAt(0) + "" + input.charAt(6));
			break;
		case 'Q':
			piece = new Queen(team.charAt(0) + "" + input.charAt(6));
			break;
		case 'R':
			piece = new Rook(team.charAt(0) + "" + input.charAt(6));
			break;
		default:
			piece = new Queen(team.charAt(0) + "" + input.charAt(6));
		}
		piece.setFirstMove(false);
		return piece;
	}

	/**
	 * Determines if a King is currently in the right situation to perform a castle.
	 * @return Returns true if a King can do a castle, otherwise false.
	 */
	public static boolean validCastling() {
		// checks valid of right side of white king
		if ((board[7][4].getPieceName().equals("King") && board[7][7].getPieceName().equals("Rook"))
				&& board[7][4].getTeam().equals(board[7][7].getTeam()) && board[7][4].getFirstMove()
				&& board[7][7].getFirstMove() &&
				// check if tiles are empty
				board[7][5].getPieceName().equals("") && board[7][6].getPieceName().equals("")) {
			return true;
		}

		// checks valid of left side of white king
		else if ((board[7][4].getPieceName().equals("King") && board[7][0].getPieceName().equals("Rook"))
				&& board[7][4].getTeam().equals(board[7][0].getTeam()) && board[7][4].getFirstMove()
				&& board[7][0].getFirstMove() &&
				// check if tiles are empty
				board[7][1].getPieceName().equals("") && board[7][2].getPieceName().equals("")
				&& board[7][3].getPieceName().equals("")) {
			return true;
		}

		// checks valid of right side of black king
		else if ((board[0][4].getPieceName().equals("King") && board[0][7].getPieceName().equals("Rook"))
				&& board[0][4].getTeam().equals(board[0][7].getTeam()) && board[0][4].getFirstMove()
				&& board[0][7].getFirstMove() &&
				// check if tiles are empty
				board[0][5].getPieceName().equals("") && board[0][6].getPieceName().equals("")) {
			return true;
		}

		// checks valid of left side of black king
		else if ((board[0][4].getPieceName().equals("King") && board[0][0].getPieceName().equals("Rook"))
				&& board[0][4].getTeam().equals(board[0][0].getTeam()) && board[0][4].getFirstMove()
				&& board[0][0].getFirstMove() &&
				// check if tiles are empty
				board[0][1].getPieceName().equals("") && board[0][2].getPieceName().equals("")
				&& board[0][3].getPieceName().equals("")) {
			return true;
		}

		return false;
	}

	/**
	 * Determines if the most recent move leaves the given team's King in check.
	 * @param team Either "white" or "black" depending on what team the current user resides with.
	 * @return Returns true if the recent move leaves their own King in check, false otherwise.
	 */
	public static boolean checksOwnKing(String team) {
		int wTemp = whiteCheck;
		int bTemp = blackCheck;
		check();
		if (team.equals("white") && whiteCheck >= 1) {
			whiteCheck = wTemp;
			blackCheck = bTemp;
			return true;
		} else if (team.equals("black") && blackCheck >= 1) {
			whiteCheck = wTemp;
			blackCheck = bTemp;
			return true;
		}
		return false;
	}

	/**
	 * Updates global variables whiteCheck and blackCheck on how many pieces is currently checking the White King or Black King respectively.
	 */
	public static void check() {
		int[][][] moves = { { { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 }, { 5, 0 }, { 6, 0 }, { 7, 0 } }, // S
				{ { -1, 0 }, { -2, 0 }, { -3, 0 }, { -4, 0 }, { -5, 0 }, { -6, 0 }, { -7, 0 } }, // N
				{ { 0, 1 }, { 0, 2 }, { 0, 3 }, { 0, 4 }, { 0, 5 }, { 0, 6 }, { 0, 7 } }, // E
				{ { 0, -1 }, { 0, -2 }, { 0, -3 }, { 0, -4 }, { 0, -5 }, { 0, -6 }, { 0, -7 } }, // W

				{ { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }, { 6, 6 }, { 7, 7 } }, // SE
				{ { -1, -1 }, { -2, -2 }, { -3, -3 }, { -4, -4 }, { -5, -5 }, { -6, -6 }, { -7, -7 } }, // NW
				{ { -1, 1 }, { -2, 2 }, { -3, 3 }, { -4, 4 }, { -5, 5 }, { -6, 6 }, { -7, 7 } }, // NE
				{ { 1, -1 }, { 2, -2 }, { 3, -3 }, { 4, -4 }, { 5, -5 }, { 6, -6 }, { 7, -7 } }, // SW
				{ { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 }, { 1, -2 }, { 1, 2 }, { -1, -2 }, { -1, 2 } } // Knight
		};

		whiteCheck = 0;
		blackCheck = 0;
		// Count how many pieces is the white king being checked by
		for (int i = 0; i < moves.length; i++) {
			for (int j = 0; j < moves[i].length; j++) {
				try {
					Tile temp = board[wKing[0] + moves[i][j][0]][wKing[1] + moves[i][j][1]];
					if (temp.getOccupation()) {
						if (temp.getTeam().equals("black")) {
							if (((i == 6 && j == 0) || (i == 5 && j == 0)) && temp.getPieceName().equals("Pawn")) {
								whiteCheck++;
							} else if ((i >= 0 && i <= 3)
									&& (temp.getPieceName().equals("Rook") || temp.getPieceName().equals("Queen"))) {
								whiteCheck++;
							} else if ((i >= 4 && i <= 7)
									&& (temp.getPieceName().equals("Bishop") || temp.getPieceName().equals("Queen"))) {
								whiteCheck++;
							} else if (i == 8 && temp.getPieceName().equals("Knight")) {
								whiteCheck++;
							}
						}
						if(i == 8) continue;
						break;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					if(i == 8) continue;
					break;
				}
			}
		}

		// Count how many pieces is the black king being checked by
		for (int i = 0; i < moves.length; i++) {
			for (int j = 0; j < moves[i].length; j++) {
				try {
					Tile temp = board[bKing[0] + moves[i][j][0]][bKing[1] + moves[i][j][1]];
					if (temp.getOccupation()) {
						if (temp.getTeam().equals("white")) {
							if (((i == 4 && j == 0) || (i == 7 && j == 0)) && temp.getPieceName().equals("Pawn")) {
								blackCheck++;
							} else if ((i >= 0 && i <= 3)
									&& (temp.getPieceName().equals("Rook") || temp.getPieceName().equals("Queen"))) {
								blackCheck++;
							} else if ((i >= 4 && i <= 7)
									&& (temp.getPieceName().equals("Bishop") || temp.getPieceName().equals("Queen"))) {
								blackCheck++;
							} else if (i == 8 && temp.getPieceName().equals("Knight")) {
								blackCheck++;
							}
						}
						if(i == 8) continue;
						break;
					}
				} catch (ArrayIndexOutOfBoundsException e) {				
					if(i == 8) continue;
					break;
				}
			}
		}
	}

	/**
	 * Determines if a certain team's king is in check mate.
	 * @param team Either "white" or "black" corresponding to that color's king
	 * @return boolean Returns true if a team's king is in check mate, false otherwise.
	 */
	public static boolean checkmate(String team) {
		if ((team.equals("white") && whiteCheck == 0) || (team.equals("black") && blackCheck == 0))
			return false;

		if (team.equals("white")) { // white king that is in check
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					Tile piece = board[i][j];
					boolean firstMove = piece.getFirstMove();
					if (piece.getTeam().equals("white")) {
						int[] origin = { i, j };
						int[][] moves = piece.getMoves();
						if (piece.getPieceName().equals("Pawn")) {
							int pawnMoves[][] = { { -1, 0 }, { -1, 1 }, { -1, -1 }, { -2, 0 } };
							for (int k = 0; k < pawnMoves.length; k++) {
								int[] destination = { i + pawnMoves[k][0], j + pawnMoves[k][1] };
								try {
									Tile target = board[destination[0]][destination[1]];
									if (!target.getTeam().equals("white")
											&& validateMove(origin, destination, "", true)) {
										reverseMove(origin, destination, piece, target);
										piece.setFirstMove(firstMove);
										return false;
									}
								} catch (ArrayIndexOutOfBoundsException e) {
								}
							}
						} else if (piece.getPieceName().equals("King")) {
							int kingMoves[][] = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { 1, -1 },
									{ -1, 1 }, { -1, -1 }, { 0, -2 }, { 0, 2 } };
							for (int k = 0; k < kingMoves.length; k++) {
								int[] destination = { i + kingMoves[k][0], j + kingMoves[k][1] };
								try {
									Tile target = board[destination[0]][destination[1]];
									if (!target.getTeam().equals("white")
											&& validateMove(origin, destination, "", true)) {
										if (k == 8 || k == 9) {
											reverseCastle(destination, "white");
										} else {
											reverseMove(origin, destination, piece, target);
										}
										piece.setFirstMove(firstMove);
										return false;
									}
								} catch (ArrayIndexOutOfBoundsException e) {
								}
							}
						} else {
							for (int k = 0; k < moves.length; k++) {
								int[] destination = { i + moves[k][0], j + moves[k][1] };
								try {
									Tile target = board[destination[0]][destination[1]];
									if (!target.getTeam().equals("white")
											&& validateMove(origin, destination, "", true)) {
										reverseMove(origin, destination, piece, target);
										piece.setFirstMove(firstMove);
										return false;
									}
								} catch (ArrayIndexOutOfBoundsException e) {
								}
							}
						}
					}
				}
			}
		} else if (team.equals("black")) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[i].length; j++) {
					Tile piece = board[i][j];
					boolean firstMove = piece.getFirstMove();
					if (piece.getTeam().equals("black")) {
						int[] origin = { i, j };
						int[][] moves = piece.getMoves();
						if (piece.getPieceName().equals("Pawn")) {
							int pawnMoves[][] = { { 1, 0 }, { 1, 1 }, { 1, -1 }, { 2, 0 } };
							for (int k = 0; k < pawnMoves.length; k++) {
								int[] destination = { i + pawnMoves[k][0], j + pawnMoves[k][1] };
								try {
									Tile target = board[destination[0]][destination[1]];
									if (!target.getTeam().equals("black")
											&& validateMove(origin, destination, "", false)) {
										reverseMove(origin, destination, piece, target);
										piece.setFirstMove(firstMove);
										return false;
									}
								} catch (ArrayIndexOutOfBoundsException e) {
								}
							}
						} else if (piece.getPieceName().equals("King")) {
							int kingMoves[][] = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { 1, -1 },
									{ -1, 1 }, { -1, 1 }, { 0, -2 }, { 0, 2 } };
							for (int k = 0; k < kingMoves.length; k++) {
								int[] destination = { i + kingMoves[k][0], j + kingMoves[k][1] };
								try {
									Tile target = board[destination[0]][destination[1]];
									if (!target.getTeam().equals("black")
											&& validateMove(origin, destination, "", false)) {
										if (k == 8 || k == 9) {
											reverseCastle(destination, "white");
										} else {
											reverseMove(origin, destination, piece, target);
										}
										piece.setFirstMove(firstMove);
										return false;
									}
								} catch (ArrayIndexOutOfBoundsException e) {
								}
							}
						} else {
							for (int k = 0; k < moves.length; k++) {
								int[] destination = { i + moves[k][0], j + moves[k][1] };
								try {
									Tile target = board[destination[0]][destination[1]];
									if (!target.getTeam().equals("black")
											&& validateMove(origin, destination, "", false)) {
										reverseMove(origin, destination, piece, target);
										piece.setFirstMove(firstMove);
										return false;
									}
								} catch (ArrayIndexOutOfBoundsException e) {
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Checks if a certain input is properly formatted.
	 * @param input User input.
	 * @return boolean Returns true if input if properly formatted, false otherwise.
	 */
	public static boolean checkInput(String input) {
		if (input.length() != 5 && input.length() != 7 && input.length() != 13)
			return false;
		if (input.substring(0, 2).equals(input.substring(3, 5)))
			return false;
		if ((input.charAt(0) - '0' >= 49 && input.charAt(0) - '0' <= 56)
				&& (input.charAt(1) - '0' >= 1 && input.charAt(1) - '0' <= 8)
				&& (input.charAt(3) - '0' >= 49 && input.charAt(3) - '0' <= 56)
				&& (input.charAt(4) - '0' >= 1 && input.charAt(4) - '0' <= 8)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the board index of a given input.
	 * @param input Substring of the original user input of length 2.
	 * @return int[] Index of the input in terms of the board.
	 */
	public static int[] getIndex(String input) {
		if (input.length() != 2)
			return null;
		int[] index = new int[2];

		index[0] = 8 - (input.charAt(1) - '0');
		index[1] = input.charAt(0) - '0' - 49;

		return index;
	}

	/**
	 * Initializes the board at the start of the program.
	 */
	public static void initBoard() {
		board[0][0] = new Rook("bR");
		board[0][1] = new Knight("bN");
		board[0][2] = new Bishop("bB");
		board[0][3] = new Queen("bQ");
		board[0][4] = new King("bK");
		board[0][5] = new Bishop("bB");
		board[0][6] = new Knight("bN");
		board[0][7] = new Rook("bR");
		board[1][0] = new Pawn("bp");
		board[1][1] = new Pawn("bp");
		board[1][2] = new Pawn("bp");
		board[1][3] = new Pawn("bp");
		board[1][4] = new Pawn("bp");
		board[1][5] = new Pawn("bp");
		board[1][6] = new Pawn("bp");
		board[1][7] = new Pawn("bp");

		board[6][0] = new Pawn("wp");
		board[6][1] = new Pawn("wp");
		board[6][2] = new Pawn("wp");
		board[6][3] = new Pawn("wp");
		board[6][4] = new Pawn("wp");
		board[6][5] = new Pawn("wp");
		board[6][6] = new Pawn("wp");
		board[6][7] = new Pawn("wp");
		board[7][0] = new Rook("wR");
		board[7][1] = new Knight("wN");
		board[7][2] = new Bishop("wB");
		board[7][3] = new Queen("wQ");
		board[7][4] = new King("wK");
		board[7][5] = new Bishop("wB");
		board[7][6] = new Knight("wN");
		board[7][7] = new Rook("wR");

		for (int i = 2; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = new Tile();
			}
		}
	}

	/**
	 * Prints the board at the current state.
	 */
	public static void printBoard() {
		boolean whiteTile = true;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].getOccupation() == true) {
					System.out.print(board[i][j].getBoardName() + " ");
				} else {
					if (whiteTile) {
						System.out.print("   ");
					} else {
						System.out.print("## ");
					}
				}
				whiteTile = ((whiteTile == true) ? false : true);
			}
			whiteTile = ((whiteTile == true) ? false : true);
			System.out.println(8 - i);
		}
		System.out.println(" a  b  c  d  e  f  g  h");
		System.out.println();
	}
}