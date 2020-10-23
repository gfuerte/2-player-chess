package chess;

import java.util.Scanner;

import models.Bishop;
import models.King;
import models.Knight;
import models.Pawn;
import models.Queen;
import models.Rook;
import models.Tile;

public class Chess {

	static Tile[][] board = new Tile[8][8];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		initBoard();
		printBoard();

		boolean gameContinues = true;
		boolean whitesMove = true;

		String input;
		boolean drawCheck = false;
		while (gameContinues) {
			if (whitesMove == true) {
				System.out.print("White's move: ");
			} else {
				System.out.print("Black's move: ");
			}

			input = scanner.nextLine();

			if (input.equals("resign")) { // resign
				if (whitesMove == true) {
					System.out.println("Black wins");
				} else {
					System.out.println("White wins");
				}
				break;
			} else if (input.length() >= 5) { // normal turn
				if (checkInput(input) == true) {
					int[] origin = getIndex(input.substring(0, 2));
					int[] destination = getIndex(input.substring(3, 5));

					Tile piece = board[origin[0]][origin[1]];
					if (piece.validateMove(origin, destination) == true) {
						if (movePiece(origin, destination, input, whitesMove)) {
							whitesMove = ((whitesMove == true) ? false : true);
							System.out.println();
							printBoard();
							drawCheck = (input.length() == 11 && input.substring(5).equals(" draw?")) ? true : false;
						} else {
							System.out.println("Illegal move, try again\n");
						}
					} else {
						System.out.println("Illegal move, try again\n");
					}
				}
			} else if (drawCheck) {
				if (input.equals("draw")) {
					System.out.println("draw");
					break;
				}
			}
		}
		scanner.close();
	}

	public static boolean movePiece(int[] origin, int[] destination, String input, boolean whitesMove) {
		if (destination[0] < 0 || destination[0] > 7 || destination[1] < 0 || destination[0] > 7)
			return false;

		Tile piece = board[origin[0]][origin[1]];
		Tile temp = board[destination[0]][destination[1]];

		if (piece.getTeam().equals(temp.getTeam()))
			return false;

		if ((whitesMove == true && piece.getTeam().equals("black"))
				|| (whitesMove == false && piece.getTeam().equals("white")))
			return false;

		// Pawn double move start, promotion, diagonal attack, en passant
		if (piece.getName().equals("wp") || piece.getName().equals("bp")) {
			if (piece.getFirstMove() == true)
				piece.setFirstMove(false);

			//attack
			
			
			// promotion
			if (piece.getTeam().equals("white")) {
				if (destination[0] == 0) {
					piece = pawnPromotion(input, "white");
				}
			} else if (piece.getTeam().equals("black")) {
				if (destination[0] == 7) {
					piece = pawnPromotion(input, "black");
				}
			}
		}

		if (temp.getTeam().equals("")) {
			board[destination[0]][destination[1]] = piece;
			board[origin[0]][origin[1]] = temp;
		} else {
			board[destination[0]][destination[1]] = piece;
			board[origin[0]][origin[1]] = new Tile("", "", false);
		}

		return true;
	}

	public static boolean checkInput(String input) {
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

	public static int[] getIndex(String input) {
		if (input.length() != 2)
			return null;
		int[] index = new int[2];

		index[0] = 8 - (input.charAt(1) - '0');
		index[1] = input.charAt(0) - '0' - 49;

		return index;
	}
	
	public static Tile pawnPromotion(String input, String team) {
		if(input.length() != 7) return new Queen(team.charAt(0) + "" + input.charAt(6), team, true);
		
		Tile piece = null;
		switch(input.charAt(6)) {
			case 'B':
				piece = new Bishop(team.charAt(0) + "" + input.charAt(6), team, true);
				break;
			case 'N':
				piece = new Knight(team.charAt(0) + "" + input.charAt(6), team, true);
				break;
			case 'Q':
				piece = new Queen(team.charAt(0) + "" + input.charAt(6), team, true);
				break;
			case 'R':
				piece = new Rook(team.charAt(0) + "" + input.charAt(6), team, true);
				break;
			default:
				piece = new Queen(team.charAt(0) + "" + input.charAt(6), team, true);
		}
		piece.setFirstMove(false);
		return piece;
	}

	public static void initBoard() {
		board[0][0] = new Rook("bR", "black", true);
		board[0][1] = new Knight("bN", "black", true);
		board[0][2] = new Bishop("bB", "black", true);
		board[0][3] = new Queen("bQ", "black", true);
		board[0][4] = new King("bK", "black", true);
		board[0][5] = new Bishop("bB", "black", true);
		board[0][6] = new Knight("bN", "black", true);
		board[0][7] = new Rook("bR", "black", true);
		board[1][0] = new Pawn("bp", "black", true);
		board[1][1] = new Pawn("bp", "black", true);
		board[1][2] = new Pawn("bp", "black", true);
		board[1][3] = new Pawn("bp", "black", true);
		board[1][4] = new Pawn("bp", "black", true);
		board[1][5] = new Pawn("bp", "black", true);
		board[1][6] = new Pawn("bp", "black", true);
		board[1][7] = new Pawn("bp", "black", true);

		board[6][0] = new Pawn("wp", "white", true);
		board[6][1] = new Pawn("wp", "white", true);
		board[6][2] = new Pawn("wp", "white", true);
		board[6][3] = new Pawn("wp", "white", true);
		board[6][4] = new Pawn("wp", "white", true);
		board[6][5] = new Pawn("wp", "white", true);
		board[6][6] = new Pawn("wp", "white", true);
		board[6][7] = new Pawn("wp", "white", true);
		board[7][0] = new Rook("wR", "white", true);
		board[7][1] = new Knight("wN", "white", true);
		board[7][2] = new Bishop("wB", "white", true);
		board[7][3] = new Queen("wQ", "white", true);
		board[7][4] = new King("wK", "white", true);
		board[7][5] = new Bishop("wB", "white", true);
		board[7][6] = new Knight("wN", "white", true);
		board[7][7] = new Rook("wR", "white", true);

		for (int i = 2; i < 6; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = new Tile("", "", false);
			}
		}
	}

	public static void printBoard() {
		boolean whiteTile = true;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j].getOccupation() == true) {
					System.out.print(board[i][j].getName() + " ");
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
