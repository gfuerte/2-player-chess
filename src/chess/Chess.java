package chess;

import models.Bishop;
import models.King;
import models.Knight;
import models.Pawn;
import models.Piece;
import models.Queen;
import models.Rook;

public class Chess {

	static Piece[][] board = new Piece[8][8];
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		initBoard();
		printBoard();
	}
	
	public static void initBoard() {
		board[0][0] = new Rook("bR", "black");
		board[0][1] = new Knight("bN", "black");
		board[0][2] = new Bishop("bB", "black");
		board[0][3] = new Queen("bQ", "black");
		board[0][4] = new King("bK", "black");
		board[0][5] = new Bishop("bB", "black");
		board[0][6] = new Knight("bN", "black");
		board[0][7] = new Rook("bR", "black");
		board[1][0] = new Pawn("bp", "black");
		board[1][1] = new Pawn("bp", "black");
		board[1][2] = new Pawn("bp", "black");
		board[1][3] = new Pawn("bp", "black");
		board[1][4] = new Pawn("bp", "black");
		board[1][5] = new Pawn("bp", "black");
		board[1][6] = new Pawn("bp", "black");
		board[1][7] = new Pawn("bp", "black");
		
		board[6][0] = new Pawn("wp", "black");
		board[6][1] = new Pawn("wp", "black");
		board[6][2] = new Pawn("wp", "black");
		board[6][3] = new Pawn("wp", "black");
		board[6][4] = new Pawn("wp", "black");
		board[6][5] = new Pawn("wp", "black");
		board[6][6] = new Pawn("wp", "black");
		board[6][7] = new Pawn("wp", "black");
		board[7][0] = new Rook("wR", "black");
		board[7][1] = new Knight("wN", "black");
		board[7][2] = new Bishop("wB", "black");
		board[7][3] = new Queen("wQ", "black");
		board[7][4] = new King("wK", "black");
		board[7][5] = new Bishop("wB", "black");
		board[7][6] = new Knight("wN", "black");
		board[7][7] = new Rook("wR", "black");
	}
	
	public static void printBoard() {
		boolean whiteTile = true;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j] != null) {
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
