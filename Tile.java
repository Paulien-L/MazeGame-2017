/**
 * Class of tiles present in the maze involving input from the text file (x-position, y-position, north/south/east/west/ wall),
 * a class of Players and a Wall class corresponding the walls specified in the text file.
 * @invar	A tile can have either one or no players
 * @invar	The walls a tile references are generated based on the text file specifying the maze.
 * @invar	The x-y position of a tile cannot be changed after construction.
 * @author Paulien Leten
 * @version 
 *
 */
public class Tile {
	
	/**
	 * Variable referencing the x-position of a tile (from the maze text file).
	 */
	private final int xPos;
	
	/**
	 * Variable referencing the y-position of a tile (from the maze text file).
	 */
	private final int yPos;
	
	/**
	 * Variable referencing the north wall of a tile (from the maze text file).
	 */
	private String northWall;
	
	/**
	 * Variable referencing the south wall of a tile (from the maze text file).
	 */
	private String southWall;
	
	/**
	 * Variable referencing the east wall of a tile (from the maze text file).
	 */
	private String eastWall;
	
	/**
	 * Variable referencing the west wall of a tile (from the maze text file).
	 */
	private String westWall;
	
	/**
	 * Variable referencing the object on a tile (from the maze text file).
	 */
	private String object;
	
	/**
	 * Variable referencing the Player of a tile (from the maze text file).
	 */
	private Player player;
	
	/**
	 * Variable referencing the north Wall of a tile.
	 */
	private Wall wallNorth;
	
	/**
	 * Variable referencing the south Wall of a tile.
	 */
	private Wall wallSouth;
	
	/**
	 * Variable referencing the east Wall of a tile.
	 */
	private Wall wallEast;
	
	/**
	 * Variable referencing the west Wall of a tile.
	 */
	private Wall wallWest;
	
	
	/**
	 * Initialises the Tile with the following parameters.
	 * @param x
	 * @param y
	 * @param northWall
	 * @param southWall
	 * @param eastWall
	 * @param westWall
	 * @param object
	 * @param player
	 */
	public Tile(int x, int y, String northWall, String southWall, String eastWall, String westWall, String object, Player player) {
		this.xPos = x;
		this.yPos = y;
		this.northWall = northWall;
		this.southWall = southWall;
		this.eastWall = eastWall;
		this.westWall = westWall;
		this.object = object;
		this.player = player;
	}
	
	//TEXT FILE POSITION PARAMETERS
	/**
	 * Returns x-position of a tile.
	 * @return xPos
	 */
	public int getXPos() {
		return this.xPos;
	}
	
	/**
	 * Returns y-position of a tile.
	 * @return yPos
	 */
	public int getYPos() {
		return this.yPos;
	}
	
	//TEXT FILE WALL DESCRIPTIONS
	/**
	 * Returns description of north wall of a tile.
	 * @return northWall
	 */
	public String getNorthWall() {
		return this.northWall;
	}
	
	/**
	 * Returns description of south wall of a tile.
	 * @return southWall
	 */
	public String getSouthWall() {
		return this.southWall;
	}
	
	/**
	 * Returns description of east wall of a tile.
	 * @return eastWall
	 */
	public String getEastWall() {
		return this.eastWall;
	}
	
	/**
	 * Returns description of west wall of a tile.
	 * @return westWall
	 */
	public String getWestWall() {
		return this.westWall;
	}
	
	//GENERATED WALLS
	/**
	 * Returns north Wall referenced by the tile.
	 * @return wallNorth
	 */
	public Wall getWallNorth() {
		return this.wallNorth;
	}
	
	/**
	 * Returns south Wall referenced by the tile.
	 * @return wallSouth
	 */
	public Wall getWallSouth() {
		return this.wallSouth;
	}
	
	/**
	 * Returns east Wall referenced by the tile.
	 * @return wallEast
	 */
	public Wall getWallEast() {
		return this.wallEast;
	}
	
	/**
	 * Returns west Wall referenced by the tile.
	 * @return wallWest
	 */
	public Wall getWallWest() {
		return this.wallWest;
	}
	
	/**
	 * Sets north Wall referenced by the tile to a new Wall.
	 * @param wall
	 * @post The north Wall this tile references will be set to a new Wall.
	 */
	public void setWallNorth(Wall wall) {
		this.wallNorth = wall;
	}
	
	/**
	 * Sets south Wall referenced by the tile to a new Wall.
	 * @param wall
	 * @post The south Wall this tile references will be set to a new Wall.
	 */
	public void setWallSouth(Wall wall) {
		this.wallSouth = wall;
	}
	
	/**
	 * Sets east Wall referenced by the tile to a new Wall.
	 * @param wall
	 * @post The east Wall this tile references will be set to a new Wall.
	 */
	public void setWallEast(Wall wall) {
		this.wallEast = wall;
	}
	
	/**
	 * Sets west Wall referenced by the tile to a new Wall.
	 * @param wall
	 * @post The west Wall this tile references will be set to a new Wall.
	 */
	public void setWallWest(Wall wall) {
		this.wallWest = wall;
	}
	
	//OBJECTS
	/**
	 * Returns object of a tile.
	 * @return object
	 */
	public String getObject() {
		return this.object;
	}
	
	/**
	 * Checks whether a tile has an object collectible by the Player.
	 * @return	True if the object on the tile is not "no", "S", "start", "E", and "end".
	 */
	public boolean hasCollectibleObject() {
		return((! this.getObject().equals("no")) 
				&& (! this.getObject().equals("S")) && (! this.getObject().equals("start")) 
				&& (! this.getObject().equals("E")) && (! this.getObject().equals("end")));
	}
	
	/**
	 * Removes an object from the Tile.
	 * @post	If the object of the tile equals trophy, key, hammer or map.
	 * 			The object of this tile is set to "no"
	 * @throws UnsupportedOperationException
	 * 			Error is thrown when it is attempted to remove an object that is not a trophy, key, hammer, map.
	 */
	public void removeObject() throws UnsupportedOperationException {
		if((this.object.equals("trophy")) 
			|| (this.object.equals("key")) 
			|| (this.object.equals("hammer")) 
			|| (this.object.equals("map")))
			this.object = "no";
		else
			throw new UnsupportedOperationException();
	}
	
	//PLAYER MOVEMENT OPTIONS: methods used to generate player movement options
	/**
	 * Checks whether a tile is unobstructed to the north.
	 * @return	True if the north Wall referenced by this tile is null and is not described as fake
	 */
	public boolean isUnobstructedNorth() {
		return ((this.getWallNorth() == null) && (!this.getNorthWall().equals("fake")));
	}
	
	/**
	 * Checks whether a tile is unobstructed to the south.
	 * @return	True if the south Wall referenced by this tile is null and is not described as fake
	 */
	public boolean isUnobstructedSouth() {
		return ((this.getWallSouth() == null) && (!this.getSouthWall().equals("fake")));
	}
	
	/**
	 * Checks whether a tile is unobstructed to the east.
	 * @return	True if the east Wall referenced by this tile is null and is not described as fake
	 */
	public boolean isUnobstructedEast() {
		return ((this.getWallEast() == null) && (!this.getEastWall().equals("fake")));
	}
	
	/**
	 * Checks whether a tile is unobstructed to the west.
	 * @return	True if the west Wall referenced by this tile is null and is not described as fake
	 */
	public boolean isUnobstructedWest() {
		return ((this.getWallWest() == null) && (!this.getWestWall().equals("fake")));
	}
	
	//STARTING TILE
	/**
	 * Checks whether the tile is the starting tile of the maze.
	 * @return True if the object of the tile is described as S or start.
	 */
	public boolean isStartingTile() {
		return((this.getObject().equals("S")) || (this.getObject().equals("start")));
	}
	
	//MOVING PLAYER & GENERATING WALLS: methods used in the move methods of class Player and wall generation in class Maze
	/**
	 * Checks if a Tile is directly north of another Tile.
	 * @param tile
	 * @return	True if the x-position of this tile is 1 higher than the x-position of the given tile
	 * 			and the y-positions of the two tiles are the same.
	 */
	public boolean isNorthOf(Tile tile) {
		return ((this.getXPos() == tile.getXPos() + 1) && (this.getYPos() == tile.getYPos()));
	}
	
	/**
	 * Checks if a Tile is directly south of another Tile.
	 * @param tile
	 * @return	True if the x-position of this tile is 1 lower than the x-position of the given tile
	 * 			and the y-positions of the two tiles are the same.
	 */
	public boolean isSouthOf(Tile tile) {
		return ((this.getXPos() == tile.getXPos() - 1) && (this.getYPos() == tile.getYPos()));
	}
	
	/**
	 * Checks if a Tile is directly east of another Tile.
	 * @param tile
	 * @return	True if the y-position of this tile is 1 higher than the y-position of the given tile
	 * 			and the x-positions of the two tiles are the same.
	 */
	public boolean isEastOf(Tile tile) {
		return ((this.getYPos() == tile.getYPos() + 1) && (this.getXPos() == tile.getXPos()));
	}
	
	/**
	 * Checks if a Tile is directly west of another Tile.
	 * @param tile
	 * @return	True if the y-position of this tile is 1 lower than the y-position of the given tile
	 * 			and the x-positions of the two tiles are the same.
	 */
	public boolean isWestOf(Tile tile) {
		return ((this.getYPos() == tile.getYPos() - 1) && (this.getXPos() == tile.getXPos()));
	}
	
	//PLAYER: methods referring to the player and used in movement methods in the Player class
	
	/**
	 * Returns current player of a Tile.
	 * @return player
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * Checks whether or not a tile has a player on it.
	 * @return True of the player of this tile is not null.
	 */
	public boolean hasPlayer() {
		return (this.getPlayer() != null);
	}
	
	/**
	 * Sets player of this tile to the given player.
	 * @param 	player
	 * @post	If the given player references this tile as their tile,
	 * 			the player of this tile is set to the given player.
	 * @throws 	IllegalArgumentException
	 * 			Error thrown when the given player does not reference this tile as their tile.
	 * @note	Formulated like this to prevent player teleportation.
	 */
	public void setPlayer(Player player) throws IllegalArgumentException {
		if(player.getTile() == this)
			this.player = player;
		else
			throw new IllegalArgumentException();
	}
	
	/**
	 * Removes player from this tile.
	 * @post Player of this tile is null.
	 */
	public void removePlayer() {
		this.player = null;
	}
	
	/**
	 * Checks whether this tile has the given player as a player.
	 * @param player
	 * @return	True if this tile is the same as the tile the Player references
	 */
	public boolean hasAsPlayer(Player player) {
		return (this == player.getTile());
	}
	
	public static void main(String[] args) {
		
	}
}
