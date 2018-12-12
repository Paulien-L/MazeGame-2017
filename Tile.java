import java.util.ArrayList;
import java.util.List;

public class Tile {
	
	private final int xPos;
	private final int yPos;
	private String northWall;
	private String southWall;
	private String eastWall;
	private String westWall;
	private String object;
	private Player player;
	
	private Wall wallNorth;
	private Wall wallSouth;
	private Wall wallEast;
	private Wall wallWest;
	
	
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
	
	public int getXPos() {
		return this.xPos;
	}
	
	public int getYPos() {
		return this.yPos;
	}
	
	//Stuff about walls
	public Wall getWallNorth() {
		return this.wallNorth;
	}
	
	public Wall getWallSouth() {
		return this.wallSouth;
	}
	
	public Wall getWallEast() {
		return this.wallEast;
	}
	public Wall getWallWest() {
		return this.wallWest;
	}
	
	public void setWallNorth(Wall wall) {
		this.wallNorth = wall;
	}
	
	public void setWallSouth(Wall wall) {
		this.wallSouth = wall;
	}
	
	public void setWallEast(Wall wall) {
		this.wallEast = wall;
	}
	
	public void setWallWest(Wall wall) {
		this.wallWest = wall;
	}
	
	//Stuff about stringy walls
	public String getNorthWall() {
		return this.northWall;
	}
	
	public String getSouthWall() {
		return this.southWall;
	}

	public String getEastWall() {
		return this.eastWall;
	}
	
	public String getWestWall() {
		return this.westWall;
	}
	
	
	//OBJECTS
	public String getObject() {
		return this.object;
	}
	
	public boolean hasCollectibleObject() {
		return((! this.getObject().equals("no")) 
				&& (! this.getObject().equals("S")) && (! this.getObject().equals("start")) 
				&& (! this.getObject().equals("E")) && (! this.getObject().equals("end")));
	}
	
	public void removeObject() throws UnsupportedOperationException {
		if((this.object.equals("trophy")) 
			|| (this.object.equals("key")) 
			|| (this.object.equals("hammer")) 
			|| (this.object.equals("map")))
			this.object = "no";
		else
			throw new UnsupportedOperationException();
	}
	
	//Player movement options
	public boolean isUnobstructedNorth() {
		return ((this.getWallNorth() == null) && (!this.getNorthWall().equals("fake")));
	}
	
	public boolean isUnobstructedSouth() {
		return ((this.getWallSouth() == null) && (!this.getSouthWall().equals("fake")));
	}
	
	public boolean isUnobstructedEast() {
		return ((this.getWallEast() == null) && (!this.getEastWall().equals("fake")));
	}
	
	public boolean isUnobstructedWest() {
		return ((this.getWallWest() == null) && (!this.getWestWall().equals("fake")));
	}
	
	
	
	
	//MOVING PLAYER
	public boolean isStartingTile() {
		return((this.getObject().equals("S")) || (this.getObject().equals("start")));
	}
	
	public boolean isNorthOf(Tile tile) {
		return ((this.getXPos() == tile.getXPos() + 1) && (this.getYPos() == tile.getYPos()));
	}
	
	public boolean isSouthOf(Tile tile) {
		return ((this.getXPos() == tile.getXPos() - 1) && (this.getYPos() == tile.getYPos()));
	}
	
	public boolean isEastOf(Tile tile) {
		return ((this.getYPos() == tile.getYPos() + 1) && (this.getXPos() == tile.getXPos()));
	}
	
	public boolean isWestOf(Tile tile) {
		return ((this.getYPos() == tile.getYPos() - 1) && (this.getXPos() == tile.getXPos()));
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public boolean hasPlayer() {
		return (this.getPlayer() != null);
	}
	
	//Throws error if this tile is not the tile the player has in the Player class (to prevent player teleportation)
	public void setPlayer(Player player) throws IllegalArgumentException {
		if(player.getTile() == this)
			this.player = player;
		else
			throw new IllegalArgumentException();
	}
	
	public void removePlayer() {
		this.player = null;
	}
	
	public boolean hasAsPlayer(Player player) {
		return (this == player.getTile());
	}
	
	public static void main(String[] args) {
		Tile myTile = new Tile(0,0, "no", "no", "no", "no", "S", null);
		Tile northTile = new Tile(1,0, "door", "no", "no", "no", "key", null);
		Tile northnorthTile = new Tile(2,0,"no","door", "no", "no","no", null);
		
		Tile eastTile = new Tile(0,1, "no", "no", "door", "no", "no", null);
		Tile eastEastTile = new Tile(0,2,"no","no", "no", "door","no", null);
		Player player = new Player(myTile);
		
		Wall nsWall = new Wall();
		Wall ewWall = new Wall();
		
		eastTile.setWallEast(ewWall);
		eastEastTile.setWallWest(ewWall);
		ewWall.setTile1(eastEastTile);
		ewWall.setTile2(eastTile);
		
		player.moveEastTo(eastTile);
		player.scanSurroundings();
		player.openDoor(eastEastTile);
		System.out.println(player.getHasPerformedAction());

//		player.breakWall(northnorthTile);
//
//		player.scanSurroundings();

//		player.moveSouthTo(myTile);
//		player.moveEastTo(eastTile);
//		player.moveWestTo(myTile);
//		player.scanSurroundings();
//		player.breakWall();
//		player.breakWall();

//		System.out.println(myTile.getPlayer() + " " + northTile.getPlayer() + " " + eastTile.getPlayer());
//		System.out.println(player.getNbSteps());
//		System.out.println(northTile.hasCollectibleObject());
		
//		player.moveNorthTo(northTile);
//		System.out.println(myTile.getPlayer() + " " + northTile.getPlayer());

//		player.pickUpObject();
//		System.out.println(northTile.getObject());
	}
}
