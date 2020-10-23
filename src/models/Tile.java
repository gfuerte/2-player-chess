package models;

public class Tile {
	String name;
	String team;
	boolean occupied;
	boolean firstMove;
	
	public Tile(String name, String team, boolean occupied) {
		this.name = name;
		this.team = team;
		this.occupied = occupied;
		this.firstMove = (occupied) ? true : false;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getTeam() {
		return this.team;
	}
	
	public boolean getOccupation() {
		return this.occupied;
	}
	
	public boolean getFirstMove() {
		return this.getFirstMove();
	}
	
	public void setFirstMove(boolean firstMove) {
		this.firstMove = false;
	}
	
	public boolean validateMove(int[] origin, int[] destination) {
		return false;
	}
}
