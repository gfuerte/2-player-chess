package models;
/**
 * @author Greg Fuerte
 * @author Aries Regalado
 */
public class Tile {
	String boardName;
	String pieceName;
	String team;
	boolean occupied;
	boolean firstMove;
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
	}

	/**
	 * Returns name of this piece on the board
	 * @return String value of this board name.
	 */
	public String getBoardName() {
		return this.boardName;
	}
	
	/**
	 * Returns the type of piece this piece is.
	 * @return String value of this type of piece.
	 */
	public String getPieceName() {
		return this.pieceName;
	}
	
	/**
	 * Returns what team this piece is residing with.
	 * @return String value of either "black", "white", or "".
	 */
	public String getTeam() {
		return this.team;
	}
	
	/**
	 * Returns whether this tile is currently occupied.
	 * @return Returns true if this tile is currently occupied, false otherwise.
	 */
	public boolean getOccupation() {
		return this.occupied;
	}
	
	/**
	 * Returns whether this piece's move is its first.
	 * @return Returns true if it's first move, false otherwise.
	 */
	public boolean getFirstMove() {
		return this.firstMove;
	}
	
	/**
	 * Returns the set of moves this piece can do.
	 * @return Returns 2d array of every possible move this piece can do.
	 */
	public int[][] getMoves() {
		return this.moves;
	}
	
	/**
	 * Sets this pieces' firstMove field.
	 * @param firstMove Sets this pieces' firstMove to this value.
	 */
	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}
}
