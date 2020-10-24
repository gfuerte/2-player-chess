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
					
					if (validateMove(origin, destination, input, whitesMove) == true) {
							whitesMove = ((whitesMove == true) ? false : true);
							System.out.println();
							printBoard();
							
							drawCheck = (input.length() == 11 && input.substring(5).equals(" draw?")) ? true : false;
						
					} else {
						System.out.println("Illegal move, try again");
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
	
	public static boolean validateMove(int[] origin, int[] destination, String input, boolean whitesMove) {
		if (destination[0] < 0 || destination[0] > 7 || destination[1] < 0 || destination[0] > 7) {
			//Case: Input destination is out of bounds
			return false;
		}

		Tile piece = board[origin[0]][origin[1]];
		Tile target = board[destination[0]][destination[1]];
		int[][] moves = piece.getMoves();

		if ((whitesMove == true && piece.getTeam().equals("black")) || (whitesMove == false && piece.getTeam().equals("white"))) {
			//Case: User is trying to move a piece not theirs
			return false;
		}
		
		if (piece.getTeam().equals(target.getTeam())) {
			//Case: Destination is occupied by a team mate
			return false;
		}

		if (piece.getPieceName().equals("Pawn")) { //Pawn
			boolean validMove = false;		
			if(piece.getTeam().equals("white")) {
				int movesW[][] = {{-1, 0}};
				int moves2W[][] = {{-2,0}};
				int attackW[][] = {{-1,1}, {-1,-1}};
				
				if(checkCollisions(origin, destination, moves2W) && piece.getFirstMove()) { //double move
					validMove = true;
					piece.setDoubleMove(true);
				} else if(checkMove(origin, destination, movesW)) { //normal move
					if(!target.getOccupation()) {
						validMove = true;
					}
				} else if(checkMove(origin, destination, attackW)) { //attack move
					if(target.getOccupation()) {
						validMove = true;
					}
				}
			} else if(piece.getTeam().equals("black")) {
				int movesB[][] = {{1, 0}};
				int moves2B[][] = {{2,0}};
				int attackB[][] = {{1,1}, {1,-1}};
				
				if(checkCollisions(origin, destination, moves2B) && piece.getFirstMove()) { //double move
					validMove = true;
					piece.setDoubleMove(true);
				} else if(checkMove(origin, destination, movesB)) { //normal move
					if(!target.getOccupation()) {
						validMove = true;
					}
				} else if(checkMove(origin, destination, attackB)) { //attack move
					if(target.getOccupation()) {
						validMove = true;
					}
				}
			}
			if(validMove) {
				if(destination[0] == 0 || destination[0] == 7) { // promotion
					piece = (piece.getTeam().equals("white")) ? pawnPromotion(input, "white") : pawnPromotion(input, "black");
					movePiece(origin, destination, piece, target);
				} else {
					movePiece(origin, destination, piece, target);
				}			
			}
			if (piece.getFirstMove()) piece.setFirstMove(false);
			
		} else if(piece.getPieceName().equals("Knight")) {
			//Case: Knight jump over pieces, no need for collision check
			if(checkMove(origin, destination, moves)) {
				movePiece(origin, destination, piece, target);
			}	
		} else if(piece.getPieceName().equals("King")) { //King
			movePiece(origin, destination, piece, target); //for now
		} else { //Queen, Bishop, Rook no special rules to mind
			if(checkCollisions(origin, destination, moves)) {
				movePiece(origin, destination, piece, target);
				piece.setFirstMove(true);
			}				
		}
		
		return true;
	}
	
	public static boolean checkCollisions(int[] origin, int[] destination, int[][] moves) {
		int count = -1;
		for(int i = 0; i < moves.length; i++) {
			if (origin[0] + moves[i][0] == destination[0] && origin[1] + moves[i][1] == destination[1]) {
				return true;
			}
			
			count++;
			try {	
				Tile temp = board[origin[0] + moves[i][0]][origin[1] + moves[i][1]];
				if(temp.getOccupation() == true) { //collision has occurred
					i = i + 6 - count;
					count = -1;
				}
				
				if(count == 6) count = 0;
			} catch (ArrayIndexOutOfBoundsException e) {}
		}
		
		return false;
	}
	
	public static boolean checkMove(int[] origin, int[] destination, int[][] moves) {
		for(int i = 0; i < moves.length; i++) {
			if (origin[0] + moves[i][0] == destination[0] && origin[1] + moves[i][1] == destination[1]) {
				return true;
			}
		}

		return false;
	}
	
	public static void movePiece(int[] origin, int[] destination, Tile piece, Tile target) {
		if (target.getTeam().equals("")) {
			board[destination[0]][destination[1]] = piece;
			board[origin[0]][origin[1]] = target;
		} else {
			board[destination[0]][destination[1]] = piece;
			board[origin[0]][origin[1]] = new Tile();
		}
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
		if(input.length() != 7) return new Queen(team.charAt(0) + "" + input.charAt(6));
		
		Tile piece = null;
		switch(input.charAt(6)) {
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
	
	public static void updateZones() {
		
	}
}
