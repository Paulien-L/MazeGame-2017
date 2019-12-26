import java.util.ArrayList;

/**
 * A class of players involving how they traverse a maze.
 * To this end players can look around, find objects, move, break walls and open doors.
 * A player has a name, a current tile referring to a tile in the maze and an inventory to store objects they find.
 * A player's number of steps and whether they have performed an action are tracked.
 * 
 * @invar	Each player is spawned on the starting tile of the maze.
 * @invar	Each player has an inventory to store objects they find in the maze.
 * @invar	Each player starts with 0 steps taken.
 * @invar	Whether a player has performed an action or not is false by default and will only be changed within the methods to move, break walls and open doors.
 * 
 * @author Paulien L
 * @version 11
 *
 */
public class Player {
	/**
	 * Variable referencing the current tile of a player. The tile refers to a tile in the Maze.
	 */
	private Tile currentTile;
	
	/**
	 * Variable referencing the object a player has.
	 */
	private ArrayList<String> objects;
	
	/**
	 * Variable referencing the number of steps a player has taken while traversing the maze.
	 */
	private int nbSteps;
	
	/**
	 * Variable referencing whether or not a player.
	 */
	private boolean hasPerformedAction = false;
	
	/**
	 * Variable referencing the name of a player.
	 */
	private String name;
	
	/**
	 * Initialise player on starting tile, with an empty list of objects, no steps taken and a default name.
	 * 
	 * @param start
	 * @throws IllegalArgumentException
	 * 			Throws error when the player is constructed on a tile that is not the starting tile.
	 */
	public Player(Tile start) throws IllegalArgumentException {
		if(start.isStartingTile())
			this.currentTile = start;
		else
			throw new IllegalArgumentException();
		
		this.objects = new ArrayList<String>();
		this.nbSteps = 0;
		this.name ="";
	}
	
	//PLAYER CURRENT TILE
	/**
	 * Returns current tile of the player.
	 * @return currentTile
	 */
	public Tile getTile() {
		return this.currentTile;
	}
	
	//PLAYER NAME
	/**
	 * Returns name of player.
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets player's name to new name.
	 * @param name
	 * @post A player's name will be equal to the given name if the given name is not null.
	 * @throws NullPointerException
	 * 			Throws error when given name is null.
	 */
	public void setName(String name) throws NullPointerException {
		if(name != null)
			this.name = name;
		else
			throw new NullPointerException();
	}
	
	//OBJECTS
	/**
	 * Returns objects a player is holding.
	 * @return objects
	 */
	public ArrayList<String> getObjects() {
		return this.objects;
	}
	
	/**
	 * Method to pick up objects found in the maze.
	 * @post 	If there are collectible objects on the current tile of the player:
	 * 			objects are added to the player's inventory and removed from the current tile.
	 * 			If there are no collectible objects on the current tile of the player:
	 * 			A line of text is printed
	 */
	public void pickUpObject() {		
		if(this.currentTile.hasCollectibleObject()) {
			this.objects.add(this.currentTile.getObject());
			this.currentTile.removeObject();
		} else
			System.out.println("There's nothing to pick up.");
	}
	
	/**
	 * Checks whether a player has a map object or not.
	 * @return 	True if the player has a map.
	 * 			False if the player does not have a map.
	 */
	public boolean hasMap() {
		return (this.objects.contains("map"));
	}
	
	//LOOKING AROUND
	/**
	 * Method to get information on the current tile of a player.
	 * 
	 * Checks whether or not there are objects on the current tile.
	 * If there are these objects are added to the player's inventory.
	 * 
	 * Checks whether there are special walls on the current tile.
	 * If there are information about them is printed to the console.
	 * 
	 */
	public void scanSurroundings() {		
		if((! this.currentTile.hasCollectibleObject())) {
			System.out.println("There are no objects here.");
		} else if(this.currentTile.hasCollectibleObject()) {
			System.out.format("There's a %s here! You pick it up. \n", this.currentTile.getObject());
			pickUpObject();
		}
		
		System.out.println();
		
		if((this.currentTile.getNorthWall().equals("breakable") && (this.currentTile.getWallNorth() != null)))
			System.out.println("This top wall doesn't look that sturdy.");
		else if(this.currentTile.getNorthWall().equals("fake"))
			System.out.println("This top wall looks suspicious.");
		else if(this.currentTile.getNorthWall().equals("door"))
			System.out.println("This top wall has a door.");
		
		if((this.currentTile.getSouthWall().equals("breakable") && (this.currentTile.getWallSouth() != null)))
			System.out.println("This bottom wall doesn't look that sturdy.");
		else if(this.currentTile.getSouthWall().equals("fake"))
			System.out.println("This bottom wall looks suspicious.");
		else if(this.currentTile.getSouthWall().equals("door"))
			System.out.println("This bottom wall has a door.");
		
		if((this.currentTile.getEastWall().equals("breakable") && (this.currentTile.getWallEast() != null)))
			System.out.println("This right wall doesn't look that sturdy.");
		else if(this.currentTile.getEastWall().equals("fake"))
			System.out.println("This right wall looks suspicious.");
		else if(this.currentTile.getEastWall().equals("door"))
			System.out.println("This right wall has a door.");
		
		if((this.currentTile.getWestWall().equals("breakable") && (this.currentTile.getWallWest() != null)))
			System.out.println("This left wall doesn't look that sturdy.");
		else if(this.currentTile.getWestWall().equals("fake"))
			System.out.println("This left wall looks suspicious.");
		else if(this.currentTile.getWestWall().equals("door"))
			System.out.println("This left wall has a door.");
	}
	
	//PLAYER ACTIONS
	/**
	 * Method to check if a player has performed an action or not.
	 * Possible actions are: moving, breaking a wall and opening a door.
	 * 
	 * @return  True if the player has performed an action.
	 * 			False if the player has not performed an action.
	 */
	public boolean getHasPerformedAction() {
		return this.hasPerformedAction;
	}
	
	/**
	 * Sets whether or not a player has performed an action to either true or false.
	 * @param bool
	 */
	public void setHasPerformedAction(boolean bool) {
		this.hasPerformedAction = bool;
	}
	
	//MOVING
	/**
	 * Returns number of steps a player has taken.
	 * 
	 * @return nbSteps
	 */
	public int getNbSteps() {
		return this.nbSteps;
	}
	
	/**
	 * Method to move a player from the current tile to a new destination tile.
	 * 
	 * @param 	destinationTile
	 * @post	If the given tile is not null, the given tile is directly NORTH from the current tile
	 * 			and there is no wall between the current tile and the destination tile:
	 * 			The player is removed from the current tile,
	 * 			The current tile of the player is set to the given tile,
	 * 			The player of the destination tile is set to this player.
	 * 			The number of steps a player has taken is increased by one.
	 * 			Whether or not a player has performed an action is set to true.
	 * 			Two lines are printed.
	 * 
	 * @throws 	NullPointerException
	 * 			Throws error if the tile a player tries to move to does not exist.
	 */
	public void moveNorthTo(Tile destinationTile) throws NullPointerException {
		if(destinationTile != null) {
			if((destinationTile.isNorthOf(this.currentTile)) && (this.getTile().getWallNorth() == null)) { 
				this.currentTile.removePlayer();
				this.currentTile = destinationTile;
				destinationTile.setPlayer(this);
				this.nbSteps += 1;
				this.hasPerformedAction = true;
				System.out.println("You moved a room up.");
				System.out.println();
			}
		} else
			throw new NullPointerException();
	}
	
	/**
	 * Method to move a player from the current tile to a new destination tile.
	 * 
	 * @param 	destinationTile
	 * @post	If the given tile is not null, the given tile is directly SOUTH from the current tile
	 * 			and there is no wall between the current tile and the destination tile:
	 * 			The player is removed from the current tile,
	 * 			The current tile of the player is set to the given tile,
	 * 			The player of the destination tile is set to this player.
	 * 			The number of steps a player has taken is increased by one.
	 * 			Whether or not a player has performed an action is set to true.
	 * 			Two lines are printed.
	 * 
	 * @throws 	NullPointerException
	 * 			Throws error if the tile a player tries to move to does not exist.
	 */
	public void moveSouthTo(Tile destinationTile) throws NullPointerException {
		if(destinationTile != null) {
			if((destinationTile.isSouthOf(this.getTile())) && (this.getTile().getWallSouth() == null)) {
				this.currentTile.removePlayer();
				this.currentTile = destinationTile;
				destinationTile.setPlayer(this);
				this.nbSteps += 1;
				this.hasPerformedAction = true;
				System.out.println("You moved a room down.");
				System.out.println();
			}
		} else
			throw new NullPointerException();
	}
	
	/**
	 * Method to move a player from the current tile to a new destination tile.
	 * 
	 * @param 	destinationTile
	 * @post	If the given tile is not null, the given tile is directly EAST from the current tile
	 * 			and there is no wall between the current tile and the destination tile:
	 * 			The player is removed from the current tile,
	 * 			The current tile of the player is set to the given tile,
	 * 			The player of the destination tile is set to this player.
	 * 			The number of steps a player has taken is increased by one.
	 * 			Whether or not a player has performed an action is set to true.
	 * 			Two lines are printed.
	 * 
	 * @throws 	NullPointerException
	 * 			Throws error if the tile a player tries to move to does not exist.
	 */
	public void moveEastTo(Tile destinationTile) throws NullPointerException {
		if(destinationTile != null) {
			if((destinationTile.isEastOf(this.getTile())) && (this.getTile().getWallEast() == null)) {
				this.currentTile.removePlayer();
				this.currentTile = destinationTile;
				destinationTile.setPlayer(this);
				this.nbSteps += 1;
				this.hasPerformedAction = true;
				System.out.println("You moved a room right.");
				System.out.println();
			}
		} else
			throw new NullPointerException();
	}
	
	/**
	 * Method to move a player from the current tile to a new destination tile.
	 * 
	 * @param 	destinationTile
	 * @post	If the given tile is not null, the given tile is directly WEST from the current tile
	 * 			and there is no wall between the current tile and the destination tile:
	 * 			The player is removed from the current tile,
	 * 			The current tile of the player is set to the given tile,
	 * 			The player of the destination tile is set to this player.
	 * 			The number of steps a player has taken is increased by one.
	 * 			Whether or not a player has performed an action is set to true.
	 * 			Two lines are printed.
	 * 
	 * @throws 	NullPointerException
	 * 			Throws error if the tile a player tries to move to does not exist.
	 */
	public void moveWestTo(Tile destinationTile) throws NullPointerException {
		if(destinationTile != null) {
			if((destinationTile.isWestOf(this.getTile())) && (this.getTile().getWallWest() == null)) {
				this.currentTile.removePlayer();
				this.currentTile = destinationTile;
				destinationTile.setPlayer(this);
				this.nbSteps += 1;
				this.hasPerformedAction = true;
				System.out.println("You moved a room left.");
				System.out.println();
			}
		} else
			throw new NullPointerException();
	}
	
	/**
	 * Method to check if a player is on the end tile or not.
	 * @return	True if the player's current tile corresponds to a tile whose object is "E" or "end".
	 * 			False if the player's current tile doesn't corresponds to a tile whose object is "E" or "end".
	 */
	public boolean isOnEndTile() {
		return ((this.getTile().getObject().equals("E") || (this.getTile().getObject().equals("end"))));
	}
	
	/**
	 * Method to print a player's possible movement options.
	 * Chekcs whether the current tile of a player is unobstructed by walls north/south/east/west
	 * And prints a line accordingly.
	 */
	public void printPlayerMovementOptions() {
		if(this.currentTile.isUnobstructedNorth())
			System.out.println("The way UP is free.");
		if(this.currentTile.isUnobstructedSouth())
			System.out.println("The way DOWN is free.");
		if(this.currentTile.isUnobstructedEast())
			System.out.println("The way RIGHT is free.");
		if(this.currentTile.isUnobstructedWest())
			System.out.println("The way LEFT is free.");
	}
	
	//BREAKING WALLS
	/**
	 * Method for breaking walls.
	 * @param tile
	 * @post	If the given tile is not null,
	 * 			the player has a hammer in their inventory,
	 * 			the given tile is directly north/south/east/west of the current tile,
	 * 			the current tile has a wall (corresponding to where the given tile is),
	 * 			and this wall is breakable:
	 * 
	 * 			The north/south/east/west wall of the current tile is set to null,
	 * 			the south/north/west/east wall of the given tile is set to null,
	 * 			whether or not a player has performed an action or not is changed to true,
	 * 			and a line is printed.
	 * 
	 * 			If the north/south/east/west wall of the current tile is not breakable:
	 * 			A corresponding line is printed and
	 * 			whether or not a player has performed an action or not is changed to true.
	 * 
	 * 			If the north/south/east/west wall of the current tile is null:
	 * 			A corresponding line is printed and
	 * 			whether or not a player has performed an action or not is changed to true.
	 * 
	 * 			If the player does not have a hammer:
	 * 			A corresponding line is printed and
	 * 			whether or not a player has performed an action or not is changed to true.
	 * 
	 * @throws NullPointerException
	 * 			Error is thrown when the given tile is null.
	 */
	public void breakWall(Tile tile) throws NullPointerException {
		if(tile != null) {
			if(this.getObjects().contains("hammer")) {
				if(tile.isNorthOf(this.currentTile)) {
					if(this.currentTile.getWallNorth() != null) {
						if(this.currentTile.getWallNorth().isBreakable()) {
							this.currentTile.setWallNorth(null);
							tile.setWallSouth(null);
							this.hasPerformedAction = true;
							System.out.println("You've broken down the top wall.");
							System.out.println();
						} else {
							System.out.println("You cannot break a wall here.");
							System.out.println();
							this.hasPerformedAction = true;
						}
					} else {
						System.out.println("You cannot break a wall here.");
						System.out.println();
						this.hasPerformedAction = true;
					}

				} else if(tile.isSouthOf(this.currentTile)) {
					if(this.currentTile.getWallSouth() != null) {
						if(this.currentTile.getWallSouth().isBreakable()) {
							this.currentTile.setWallSouth(null);
							tile.setWallNorth(null);
							this.hasPerformedAction = true;
							System.out.println("You've broken down the bottom wall.");
							System.out.println();
						} else {
							System.out.println("You cannot break a wall here.");
							System.out.println();
							this.hasPerformedAction = true;
						}
					}

				} else if(tile.isEastOf(this.currentTile)) {
					if(this.currentTile.getWallEast() != null) {
						if(this.currentTile.getWallEast().isBreakable()) {
							this.currentTile.setWallEast(null);
							tile.setWallWest(null);
							this.hasPerformedAction = true;
							System.out.println("You've broken down the right wall.");
							System.out.println();
						} else {
							System.out.println("You cannot break a wall here.");
							System.out.println();
							this.hasPerformedAction = true;
						}
					}

				} else if(tile.isWestOf(this.currentTile)) {
					if(this.currentTile.getWallWest() != null) {
						if(this.currentTile.getWallWest().isBreakable()) {
							this.currentTile.setWallWest(null);
							tile.setWallEast(null);
							this.hasPerformedAction = true;
							System.out.println("You've broken down the left wall.");
							System.out.println();
						} else {
							System.out.println("You cannot break a wall here.");
							System.out.println();
							this.hasPerformedAction = true;
						}
					}
				}
			} else {
				System.out.println("You need a hammer to break down a wall.");
				System.out.println();
				this.hasPerformedAction = true;
			}
		} else
			throw new NullPointerException();
	}
	
	//OPENING DOORS
	/**
	 * Method for opening doors.
	 * @param 	tile
	 * @post	If the given tile is not null,
	 * 			the player has a key in their inventory,
	 * 			the given tile is directly north/south/east/west of the current tile,
	 * 			the current tile has a wall (corresponding to where the given tile is),
	 * 			and this wall has a door:
	 * 
	 * 			The north/south/east/west wall of the current tile is set to null,
	 * 			the south/north/west/east wall of the given tile is set to null,
	 * 			whether or not a player has performed an action or not is changed to true,
	 * 			and a line is printed.
	 * 
	 * 			If the north/south/east/west wall of the current tile is does not have a door:
	 * 			A corresponding line is printed and
	 * 			whether or not a player has performed an action or not is changed to true.
	 * 
	 * 			If the north/south/east/west wall of the current tile is null:
	 * 			A corresponding line is printed and
	 * 			whether or not a player has performed an action or not is changed to true.
	 * 
	 * 			If the player does not have a key:
	 * 			A corresponding line is printed and
	 * 			whether or not a player has performed an action or not is changed to true.
	 * 
	 * @throws NullPointerException
	 * 			Error is thrown when the given tile is null.
	 */
	public void openDoor(Tile tile) throws NullPointerException {
		if(tile != null) {
			if(this.getObjects().contains("key")) {
				if(tile.isNorthOf(this.currentTile)) {
					if(this.currentTile.getWallNorth() != null) {
						if(this.currentTile.getWallNorth().hasDoor()) {
							this.currentTile.setWallNorth(null);
							tile.setWallSouth(null);
							this.hasPerformedAction = true;
							System.out.println("You've opened a door in the top wall.");
							System.out.println();
						} else {
							System.out.println("You cannot open a door here.");
							System.out.println();
							this.hasPerformedAction = true;
						}
					} else {
						System.out.println("You cannot open a door here.");
						System.out.println();
						this.hasPerformedAction = true;
					}

				} else if(tile.isSouthOf(this.currentTile)) {
					if(this.currentTile.getWallSouth() != null) {
						if(this.currentTile.getWallSouth().hasDoor()) {
							this.currentTile.setWallSouth(null);
							tile.setWallNorth(null);
							this.hasPerformedAction = true;
							System.out.println("You've opened a door in the bottom wall.");
							System.out.println();
						} else {
							System.out.println("You cannot open a door here.");
							System.out.println();
							this.hasPerformedAction = true;
						}
					}

				} else if(tile.isEastOf(this.currentTile)) {
					if(this.currentTile.getWallEast() != null) {
						if(this.currentTile.getWallEast().hasDoor()) {
							this.currentTile.setWallEast(null);
							tile.setWallWest(null);
							this.hasPerformedAction = true;
							System.out.println("You've opened a door in the right wall.");
							System.out.println();
						} else {
							System.out.println("You cannot open a door here.");
							System.out.println();
							this.hasPerformedAction = true;
						}
					}

				} else if(tile.isWestOf(this.currentTile)) {
					if(this.currentTile.getWallWest() != null) {
						if(this.currentTile.getWallWest().hasDoor()) {
							this.currentTile.setWallWest(null);
							tile.setWallEast(null);
							this.hasPerformedAction = true;
							System.out.println("You've opened a door in the left wall.");
							System.out.println();
						} else {
							System.out.println("You cannot open a door here.");
							System.out.println();
							this.hasPerformedAction = true;
						}
					}
				} 
			} else {
				System.out.println("You need a key to open a door.");
				System.out.println();
				this.hasPerformedAction = true;
			}
		} else
			throw new NullPointerException();
	}
	
	//SCORE
	/**
	 * Method for calculating the score of a player based on the number of steps they've taken and the amount of objects they've found.
	 * @return score
	 * @note The numbers 1000 and 2 are arbitrary.
	 */
	public int calculateScore() {
		int score;
		score = (1000 - this.getNbSteps()) + (this.getObjects().size() * 2);
		return score;
	}
	
	public static void main(String[] args) {
		
	}
	
}
