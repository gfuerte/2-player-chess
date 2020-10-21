package models;

public class Piece {
	String name;
	String team;
	
	public Piece(String name, String team) {
		this.name = name;
		this.team = team;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getTeam() {
		return this.team;
	}
}
