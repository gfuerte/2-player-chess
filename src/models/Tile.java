package models;

public class Tile {
	String name;
	String team;
	boolean occupied;
	
	public Tile(String name, String team, boolean occupied) {
		this.name = name;
		this.team = team;
		this.occupied = occupied;
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
	
	public boolean validateMove(int[] origin, int[] destination) {
		return false;
	}
}
