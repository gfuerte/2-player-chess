package models;

public class Queen extends Tile{
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
            
            {1, 1},
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
			{-7, 7} 

	                  };
	
	public Queen(String name, String team, boolean occupied) {
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
