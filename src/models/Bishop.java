package models;
/**
 * @author Greg Fuerte
 * @author Aries Regalado
 */
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
}
