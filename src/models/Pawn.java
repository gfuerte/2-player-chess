package models;

public class Pawn extends Tile {
	int[][] moves = {{-1, 0}, {-1,1}, {-1,-1}, {1, 0}, {1,1}, {1,-1}};
	
	public Pawn(String boardName) {
		super(boardName);
		super.pieceName = "Pawn";
		super.moves = this.moves;
	}
	
	/*
	public boolean validateMove(int[] origin, int[] destination) {
		
		if(this.getTeam().equals("white") && this.firstMove == true){
			int moves2W[][] = {{-2,0}, {-1,0}};
			for(int i = 0; i < moves2W.length; i++) {
				if (origin[0] + moves2W[i][0] == destination[0] && origin[1] + moves2W[i][1] == destination[1]) {
					return true;
				}
			}
		}
		
		if(this.getTeam().equals("black") && this.firstMove == true){
			int moves2B[][] = {{2,0}, {1,0}};
			for(int i = 0; i < moves2B.length; i++) {
				if (origin[0] + moves2B[i][0] == destination[0] && origin[1] + moves2B[i][1] == destination[1]) {
					return true;
				}
			}
		}
		
		
		
		if(this.getTeam().equals("white")) {
			for(int i = 0; i < movesW.length; i++) {
				if (origin[0] + movesW[i][0] == destination[0] && origin[1] + movesW[i][1] == destination[1]) {
					return true;
				}
			}
		} 
		
		else if(this.getTeam().equals("black")) {
			for(int i = 0; i < movesB.length; i++) {
				if (origin[0] + movesB[i][0] == destination[0] && origin[1] + movesB[i][1] == destination[1]) {
					return true;
				}
			}
		}
		return false;
	}
	*/
}
