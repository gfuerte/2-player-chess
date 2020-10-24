package models;

public class King extends Tile {
	int[][] moves = {  {1,0},
			           {-1,0},
			           {0,1},
			           {0,-1},
			          
			           {1,1},
			           {1,-1},
			           {-1,1},
			           {-1,1} };
	
	public King(String boardName) {
		super(boardName);
		super.pieceName = "King";
		super.moves = this.moves;
	}
	
	public boolean validateMove(int[] origin, int[] destination) {
		for (int i = 0; i < moves.length; i++) {
			if (origin[0] + moves[i][0] == destination[0] && origin[1] + moves[i][1] == destination[1]) {
				return true;
			}
		}
		return false;
	}
}
