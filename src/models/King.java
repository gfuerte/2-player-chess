package models;

public class King extends Tile {
	int[][] moves = {{}};
	
	public King(String name, String team, boolean occupied) {
		super(name, team, occupied);
	}
}
