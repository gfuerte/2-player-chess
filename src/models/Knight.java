package models;

public class Knight extends Tile {

	int[][] moves = {  {2,1},
	                   {2,-1},
	                   {-2,1},
	                   {-2,-1},
	          
	                   {1,-2},
	                   {1,2},
	                   {-1,-2},
	                   {-1,2},

			                  };
	
	public Knight(String name, String team, boolean occupied) {
		super(name, team, occupied);
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
