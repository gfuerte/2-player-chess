package models;

public class Rook extends Tile {
	int[][] moves = {  
			{1,0},
            {2,0},
            {3,0},
            {4,0},
            {5,0},
            {6,0},
            {7,0},
            {8,0},
   
        	{-1,0},
            {-2,0},
            {-3,0},
            {-4,0},
            {-5,0},
            {-6,0},
            {-7,0},
            {-8,0},
            
        	{0,1},
            {0,2},
            {0,3},
            {0,4},
            {0,5},
            {0,6},
            {0,7},
            {0,8},
            
        	{0,-1},
            {0,-2},
            {0,-3},
            {0,-4},
            {0,-5},
            {0,-6},
            {0,-7},
            {0,-8},
            
            
	                  };
	
	public Rook(String name, String team, boolean occupied) {
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
