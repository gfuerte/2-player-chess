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
		
		boolean whitesMove = true;
		String input = "";
		while(!input.equals("resign")) {
			printBoard();
			if(whitesMove == true) {
				System.out.print("White's move: ");
			} else {
				System.out.print("Black's move: ");
			}
			whitesMove = ((whitesMove == true) ? false : true);
			input = scanner.nextLine();
			
			int[] origin = getIndex(input.substring(0, 2));
			int[] destination = getIndex(input.substring(3));
						
			Tile piece = board[origin[0]][origin[1]];
			
			if(piece.validateMove(origin, destination) == true) {
				movePiece(origin, destination);
			} else {
				
			}
		}
		
		scanner.close();
	}
	
	public static void movePiece(int[] origin, int[] destination) {
		Tile temp = board[destination[0]][destination[1]];
		board[destination[0]][destination[1]] = board[origin[0]][origin[1]];
		board[origin[0]][origin[1]] = temp;
	}
	
	public static int[] getIndex(String input) {
		if(input.length() != 2) return null;
		int[] index = new int[2];
		
		index[0] = 8 - (input.charAt(1) - '0');
		index[1] = input.charAt(0) - '0' - 49;
		
		return index;
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
		
		for(int i = 2; i < 6; i++) {
			for(int j = 0; j < 8; j++) {
				board[i][j] = new Tile("", "", false);
			}
		}
	}
	
	public static void printBoard() {
		boolean whiteTile = true;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j].getOccupation() == true) {
					System.out.print(board[i][j].getName() + " ");
				} else {
					if(whiteTile) {
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
	}
}
