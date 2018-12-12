import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
	
	private Tile currentTile;
	private ArrayList<String> objects;
	private int nbSteps;
	private boolean hasPerformedAction = false;
	private boolean hasTriedAction = false;
	private String name;
	
	public Player(Tile start) {
		this.currentTile = start;
		this.objects = new ArrayList<String>();
		this.nbSteps = 0;
		this.name ="";
	}
	
	public Tile getTile() {
		return this.currentTile;
	}
	
	public boolean getHasPerformedAction() {
		return this.hasPerformedAction;
	}
	
	public void setHasPerformedAction(boolean bool) {
		this.hasPerformedAction = bool;
	}
	
	public boolean getHasTriedAction() {
		return this.hasTriedAction;
	}
	
	public void setHasTriedAction(boolean bool) {
		this.hasTriedAction = bool;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	//OBJECTS
	public ArrayList<String> getObjects() {
		return this.objects;
	}
	
	public void pickUpObject() {		
		if(this.currentTile.hasCollectibleObject()) {
			this.objects.add(this.currentTile.getObject());
			this.currentTile.removeObject();
		} else
			System.out.println("There's nothing to pick up.");
	}
	
	public boolean hasMap() {
		return (this.objects.contains("map"));
	}
	
	//Looking around
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
	
	//MOVING
	public int getNbSteps() {
		return this.nbSteps;
	}
	
	//tile != null is here to indeed say that we're throwing the null pointer exception, which we are
	public void moveNorthTo(Tile tile) throws NullPointerException {
		if(tile != null) {
			if((tile.isNorthOf(this.currentTile)) && (this.getTile().getWallNorth() == null)) { 
				this.currentTile.removePlayer();
				this.currentTile = tile;
				tile.setPlayer(this);
				this.nbSteps += 1;
				this.hasPerformedAction = true;
				System.out.println("You moved a room up.");
				System.out.println();
			}
		} else
			throw new NullPointerException();
	}
	
	public void moveSouthTo(Tile tile) throws NullPointerException {
		if(tile != null) {
			if((tile.isSouthOf(this.getTile())) && (this.getTile().getWallSouth() == null)) {
				this.currentTile.removePlayer();
				this.currentTile = tile;
				tile.setPlayer(this);
				this.nbSteps += 1;
				this.hasPerformedAction = true;
				System.out.println("You moved a room down.");
				System.out.println();
			}
		} else
			throw new NullPointerException();
	}
	
	public void moveEastTo(Tile tile) throws NullPointerException {
		if(tile != null) {
			if((tile.isEastOf(this.getTile())) && (this.getTile().getWallEast() == null)) {
				this.currentTile.removePlayer();
				this.currentTile = tile;
				tile.setPlayer(this);
				this.nbSteps += 1;
				this.hasPerformedAction = true;
				System.out.println("You moved a room right.");
				System.out.println();
			}
		} else
			throw new NullPointerException();
	}
	
	public void moveWestTo(Tile tile) throws NullPointerException {
		if(tile != null) {
			if((tile.isWestOf(this.getTile())) && (this.getTile().getWallWest() == null)) {
				this.currentTile.removePlayer();
				this.currentTile = tile;
				tile.setPlayer(this);
				this.nbSteps += 1;
				this.hasPerformedAction = true;
				System.out.println("You moved a room left.");
				System.out.println();
			}
		} else
			throw new NullPointerException();
	}
		
	public boolean isOnEndTile() {
		return ((this.getTile().getObject().equals("E") || (this.getTile().getObject().equals("end"))));
	}
	
	//BREAKING WALLS
	public void breakWall(Tile tile) throws NullPointerException {
		if(tile != null) {
			if(this.getObjects().contains("hammer")) {
				this.hasTriedAction = true;
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
	
	//OPENING DOORS (still prints way too many "you don't have a key" if you try to open a door without one)
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
	public int calculateScore() {
		int score;
		score = (1000 - this.getNbSteps()) + (this.getObjects().size() * 2);
		return score;
	}
	
}
