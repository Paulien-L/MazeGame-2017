import java.util.ArrayList;

/**
 * A class of Rows specifying the rows of the maze, each row in the maze holds a number of Tiles (Tile class).
 * @author Paulien Leten
 * @version 11
 *
 */
public class Row {
	/**
	 * Variable referencing the tiles a row contains.
	 */
	private ArrayList<Tile> tiles;
	
	/**
	 * Initialises a Row with an empty list of tiles.
	 */
	public Row() {
		this.tiles = new ArrayList<Tile>();
	}
	
	/**
	 * Returns the list of Tiles a row contains.
	 * @return tiles
	 */
	public ArrayList<Tile> getTiles() {
		return this.tiles;
	}
	
	/**
	 * Checks whether a contains the starting tile or not.
	 * @return True if the row contains a tile with object S or start
	 */
	public boolean isStartingRow() {
		for(Tile tile: tiles) {
			while((tile.getObject().equals("S")) || (tile.getObject().equals("start"))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method to draw a textual representation of a row in the console.
	 * Checks the description of the north and east walls of the Tiles in a row and draws them accordingly.
	 * Also checks if the tile in the row has a player and prints them accordingly.
	 * @note Only north and east wall descriptions are used since each wall is only printed once (while they're described twice in the text file)
	 * @note Fake walls are printed the same ways as regular walls, but are printed separately to account for future changes in this feature
	 */
	public void drawRow() {
		//String to store vertical elements of the row (east/west walls)
		String vertical ="";
		//String to store horizontal elements of a row (north/south walls)
		String horizontal ="";
		
		//Based on the description of the wall in the text file a different element is added to the horizontal or vertical string
		for(Tile tile: tiles) {
			if(tile.getNorthWall().equals("wall"))
				horizontal += "---+";
			if(tile.getNorthWall().equals("no"))
				horizontal += "   +";
			if(tile.getNorthWall().equals("breakable"))
				horizontal += "-b-+";
			if(tile.getNorthWall().equals("door"))
				horizontal += "-d-+";
			if(tile.getNorthWall().equals("fake"))
				horizontal += "---+";
			
			if((tile.getEastWall().equals("wall")) && ((tile.getObject().equals("S")) || (tile.getObject().equals("start"))))
				vertical += " S |";
			else if((tile.getEastWall().equals("wall")) && ((tile.getObject().equals("E")) || (tile.getObject().equals("end"))))
				vertical += " E |";
			else if((tile.getEastWall().equals("wall")) && (tile.hasPlayer()))
				vertical += " P |";
			else if(tile.getEastWall().equals("wall"))
				vertical += "   |";
			
			if((tile.getEastWall().equals("no")) && ((tile.getObject().equals("S")) || (tile.getObject().equals("start"))))
				vertical += " S  ";
			else if((tile.getEastWall().equals("no")) && ((tile.getObject().equals("E")) || (tile.getObject().equals("end"))))
				vertical += " E  ";
			else if((tile.getEastWall().equals("no")) && (tile.hasPlayer()))
				vertical += " P  ";
			else if(tile.getEastWall().equals("no"))
				vertical += "    ";
			
			if((tile.getEastWall().equals("breakable")) && ((tile.getObject().equals("S")) || (tile.getObject().equals("start"))))
				vertical += " S b";
			else if((tile.getEastWall().equals("breakable")) && ((tile.getObject().equals("E")) || (tile.getObject().equals("end"))))
				vertical += " E b";
			else if((tile.getEastWall().equals("breakable")) && (tile.hasPlayer()))
				vertical += " P b";
			else if(tile.getEastWall().equals("breakable"))
				vertical += "   b";
			
			if((tile.getEastWall().equals("door")) && ((tile.getObject().equals("S")) || (tile.getObject().equals("start"))))
				vertical += " S d";
			else if((tile.getEastWall().equals("door")) && ((tile.getObject().equals("E")) || (tile.getObject().equals("end"))))
				vertical += " E d";
			else if((tile.getEastWall().equals("door")) && (tile.hasPlayer()))
				vertical += " P d";
			else if(tile.getEastWall().equals("door"))
				vertical += "   d";
			
			if((tile.getEastWall().equals("fake")) && ((tile.getObject().equals("S")) || (tile.getObject().equals("start"))))
				vertical += " S |";
			else if((tile.getEastWall().equals("fake")) && ((tile.getObject().equals("E")) || (tile.getObject().equals("end"))))
				vertical += " E |";
			else if((tile.getEastWall().equals("fake")) && (tile.hasPlayer()))
				vertical += " P |";
			else if(tile.getEastWall().equals("fake"))
				vertical += "   |";
		}
		//Prints first + and | sign of a new row and the horizontal elements
		System.out.println("+" + horizontal);
		//Prints first + and | sign of a new row and the vertical elements
		System.out.println("|" + vertical);
	}
	
	public static void main(String[] args) {
		
	}
}
