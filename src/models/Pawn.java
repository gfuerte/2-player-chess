package models;

public class Pawn extends Tile {
	int[][] movesW = {{-1, 0}};
	int[][] movesB = {{1, 0}};
	boolean firstMove;
	
	public Pawn(String name, String team, boolean occupied) {
		super(name, team, occupied);
		this.firstMove = true;
	}
	
	public boolean getFirstMove() {
		return this.firstMove;
	}
	
	public boolean validateMove(int[] origin, int[] destination) {
		if(this.getTeam().equals("white")) {
			for(int i = 0; i < movesW.length; i++) {
				if(origin[0] + movesW[0][0] == destination[0]) {
					return true;
				}
			}
		} else {
			
		}
		return false;
	}
}
