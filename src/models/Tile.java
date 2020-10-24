package models;

public class Tile {
	String boardName;
	String pieceName;
	String team;
	boolean occupied;
	boolean firstMove;
	boolean doubleMove;
	int[][] moves;
	
	public Tile() {
		this.boardName = "";
		this.pieceName = "";
		this.team = "";
		this.occupied = false;
		this.firstMove = false;
		this.moves = null;
	}
	
	public Tile(String boardName) {
		this.boardName = boardName;
		this.team = (boardName.charAt(0) == 'w') ? "white" : "black";
		this.occupied = true;
		this.firstMove = true;
		this.doubleMove = false;
	}

	public String getBoardName() {
		return this.boardName;
	}
	
	public String getPieceName() {
		return this.pieceName;
	}
	
	public String getTeam() {
		return this.team;
	}
	
	public boolean getOccupation() {
		return this.occupied;
	}
	
	public boolean getFirstMove() {
		return this.firstMove;
	}
	
	public boolean getDoubleMove() {
		return this.doubleMove;
	}
	
	public int[][] getMoves() {
		return this.moves;
	}
	
	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}
	
	public void setDoubleMove(boolean doubleMove) {
		this.doubleMove = doubleMove;
	}
}
