package models;

public class Bishop extends Tile {
	int[][] moves = {   {1, 1},
						{2, 2},
						{3, 3},
						{4, 4},
						{5, 5},
						{6, 6},
						{7, 7},  
						
						{-1, -1},
						{-2, -2},
						{-3, -3},
						{-4, -4},
						{-5, -5},
						{-6, -6},
						{-7, -7},

						{1, -1},
						{2, -2},
						{3, -3},
						{4, -4},
						{5, -5},
						{6, -6},
						{7, -7},
						
						{-1, 1},
						{-2, 2},
						{-3, 3},
						{-4, 4},
						{-5, 5},
						{-6, 6},
						{-7, 7} };
	
	public Bishop(String boardName) {
		super(boardName);
		super.pieceName = "Bishop";
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
