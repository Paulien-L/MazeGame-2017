/**
 * A class specifying the walls of the maze involving whether a wall is breakable, has a door or is shared by two tiles.
 * @invar	A wall references 0, 1 or 2 tiles.
 * 			If a wall references 0 tiles, this corresponds with "no" wall in the text file.
 * 			If a wall references 1 tile, this corresponds to a wall that isn't shared between two tiles.
 * 			If a wall references 2 tiles, this corresponds to a wall that is shared between two tiles.
 * @invar	A wall can only be set to a tile that already references this wall.
 * @author 	Paulien Leten
 * @version 11
 *
 */
public class Wall {
	
	/**
	 * Variable for the first tile a wall can reference.
	 */
	Tile tile1;
	/**
	 * Variable for the second tile a wall can reference.
	 */
	Tile tile2;
	
	/**
	 * Initialises a wall with no properties.
	 */
	public Wall() {	
	}
	
	/**
	 * Returns the first tile a wall references.
	 * @return tile1
	 */
	public Tile getTile1() {
		return this.tile1;
	}
	
	/**
	 * Returns the second tile a wall references.
	 * @return
	 */
	public Tile getTile2() {
		return this.tile2;
	}
	/**
	 * Sets the first tile a wall references to a new tile.
	 * @param tile
	 * @post If the given tile is a valid tile, the current first tile is set to the new tile.
	 * @throws IllegalArgumentException
	 * 			If the given tile is not a valid tile.
	 */
	public void setTile1(Tile tile) throws IllegalArgumentException {
		if(isValidTile(tile) == true)
			this.tile1 = tile;
		else
			throw new IllegalArgumentException();
	}
	/**
	 * Sets the second tile a wall references to a new tile.
	 * @param tile
	 * @post If the given tile is a valid tile, the current second tile is set to the new tile.
	 * @throws IllegalArgumentException
	 * 			If the given tile is not a valid tile.
	 */
	public void setTile2(Tile tile) throws IllegalArgumentException {
		if(isValidTile(tile) == true)
			this.tile2 = tile;
		else
			throw new IllegalArgumentException();
	}
	
	/**
	 * Checks whether the given tile is a valid tile.
	 * @param tile
	 * @return 	True if the north wall of the given tile equals this wall.
	 * 			True if the south wall of the given tile equals this wall.
	 * 			True if the east wall of the given tile equals this wall.
	 * 			True if the west wall of the given tile equals this wall.
	 * 			False if any of the walls of the given tile do not equal this wall.
	 * 
	 */
	public boolean isValidTile(Tile tile) {
		if(tile.getWallNorth() == this)
			return true;
		else if(tile.getWallSouth() == this)
			return true;
		else if(tile.getWallEast() == this)
			return true;
		else if(tile.getWallWest() == this)
			return true;
		else
			return false;
	}
	
	/**
	 * Checks whether or not a wall is breakable.
	 * @return	True if the tile a wall references is not null,
	 * 			if the north/south/east/west wall of the first tile is this wall,
	 * 			if the north/south/east/west wall is described as "breakable" in the text file.
	 * @note	Only one of the tiles is checked here, since wall will either only reference one tile or wall is the same for both tiles.
	 */
	public boolean isBreakable() {
		if(tile1 != null) {
			if(tile1.getWallNorth() == this) {
				if(tile1.getNorthWall().equals("breakable"))
					return true;
				else
					return false;
			} else if(tile1.getWallSouth() == this) {
				if(tile1.getSouthWall().equals("breakable"))
					return true;
				else
					return false;
			} else if(tile1.getWallEast() == this) {
				if(tile1.getEastWall().equals("breakable"))
					return true;
				else
					return false;
			} else if(tile1.getWallWest() == this) {
				if(tile1.getWestWall().equals("breakable"))
					return true;
				else
					return false;
			} else
				return false;
		}else
			return false;
	}
	
	/**
	 * Checks whether a wall has a door or not.
	 * @return	True if the tile a wall references is not null,
	 * 			if the north/south/east/west wall of the first tile is this wall,
	 * 			if the north/south/east/west wall is described as "door" in the text file.
	 * @note	Only one of the tiles is checked here, since wall will either only reference one tile or wall is the same for both tiles.
	 */
	public boolean hasDoor() {
		if(tile1 != null) {
			if(tile1.getWallNorth() == this) {
				if(tile1.getNorthWall().equals("door"))
					return true;
				else
					return false;
			} else if(tile1.getWallSouth() == this) {
				if(tile1.getSouthWall().equals("door"))
					return true;
				else
					return false;
			} else if(tile1.getWallEast() == this) {
				if(tile1.getEastWall().equals("door"))
					return true;
				else
					return false;
			} else if(tile1.getWallWest() == this) {
				if(tile1.getWestWall().equals("door"))
					return true;
				else
					return false;
			} else
				return false;
		} else
			return false;
	}
	
	/**
	 * Checks whether a wall is shared by two tiles or not.
	 * @return True if the wall references two tiles.
	 * @note Not currently used.
	 */
	public boolean isShared() {
		if((tile1 != null) && (tile2 != null))
			return true;
		else
			return false;
	}	
	
	public static void main(String[] args) {

	}
}
