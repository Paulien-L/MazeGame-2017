import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class specifying a Maze based on a text file from a data source.
 * A maze is comprised of rows and has a name based on the text file filepath.
 * @invar Mazes can only be generated based on a text file with a specific format.
 * @author Paulien Leten
 * @version 11
 *
 */
public class Maze {
	
	/**
	 * Variable referencing the rows of a maze.
	 */
	private ArrayList<Row> rows;
	
	/**
	 * Variable referencing the data source of the text file for a maze.
	 */
	private String dataSource;
	
	/**
	 * Variable referencing the name of a maze.
	 */
	private String mazeName;
	
	/**
	 * Initialises the maze based on the given file path and with an empty list of rows.
	 * @param filePath
	 * @post  An empty list or Rows is created
	 * 		  The data source of the maze is set to the given file path.
	 */
	public Maze(String filePath) {
		this.rows = new ArrayList<Row>();
		this.dataSource = filePath;
	}
	
	/**
	 * Returns the list of Rows in a maze.
	 * @return rows
	 */
	public ArrayList<Row> getRows() {
		return this.rows;
	}
	
	/**
	 * Reverses the list of rows and saves this list to a new list.
	 * @return reveseRows
	 * @note used to draw maze from bottom to top.
	 */
	public ArrayList<Row> getReverseRows() {
		ArrayList<Row> reverseRows = new ArrayList<Row>(rows);
		Collections.reverse(reverseRows);
		return reverseRows;
	}
	
	/**
	 * Returns maze name based on the file path the maze was initialised with.
	 * @return mazeName
	 */
	public String getMazeName() {
		int index = this.dataSource.lastIndexOf("\\");
		return this.mazeName = this.dataSource.substring(index +1);
	}
	
	/**
	 * Reads data from the text file the maze was initialised with.
	 * Creates a number of Tiles based on this data
	 * Creates a number of Rows containing the Tiles based on the number of Tiles created
	 * Creates a number of shared and unshared Walls based on the wall descriptions in the data and number of Tiles created
	 * @throws IOException
	 * @note Shared walls are walls that are in between two tiles, unshared walls are outer walls of the maze
	 */
	public void readData() throws IOException {

		//List to put all the read tiles
		ArrayList<Tile> tiles = new ArrayList<Tile>();
		
		//Opening a buffered reader
		InputStream input = new FileInputStream(dataSource);
		BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
		
		//Reads first line
		buffer.readLine();
		
		//Iterate over each line in the text file
		for(String line = buffer.readLine(); line != null; line = buffer.readLine()) {
			String[] data = line.split(",");

			//Cast data types to match the input
			int x = Integer.parseInt(data[0]);
			int y = Integer.parseInt(data[1]);
			String north = String.valueOf(data[2]);
			String south = String.valueOf(data[3]);
			String east = String.valueOf(data[4]);
			String west = String.valueOf(data[5]);
			String object = String.valueOf(data[6]);
			
			//Create new instances of Tile class with the retrieved data as constructor input and no player
			Tile newTile = new Tile(x, y, north, south, east, west, object, null);
			
			//Add created Tiles to list of Tiles
			tiles.add(newTile);
		}
		buffer.close();
		
		//Create instances of Wall class based on Tiles and data retrieved
		Wall wallBetweenNS;
		Wall wallBetweenEW;
		Wall wallSouth;
		Wall wallNorth;
		Wall wallEast;
		Wall wallWest;
		
		/*Creating shared walls
		 * If the tiles are directly next to each other (north-south or east-west) and there is a wall between them that is not a fake wall,
		 * a new Wall is created referencing both tiles and both tiles are set to reference this Wall.
		 * Note: the order is important!
		 */
		for(int i=0; i < tiles.size(); i++) {
			for(int j = tiles.size()-1; j >= 0; j--) {
				if((tiles.get(i).isSouthOf(tiles.get(j))) && (! tiles.get(i).getNorthWall().equals("no")) && (! tiles.get(i).getNorthWall().equals("fake"))) {
					wallBetweenNS = new Wall();
					tiles.get(i).setWallNorth(wallBetweenNS);
					tiles.get(j).setWallSouth(wallBetweenNS);
					wallBetweenNS.setTile1(tiles.get(i));
					wallBetweenNS.setTile2(tiles.get(j));
				}
				else if((tiles.get(i).isWestOf(tiles.get(j))) && (! tiles.get(i).getEastWall().equals("no")) && (! tiles.get(i).getEastWall().equals("fake"))) {
					wallBetweenEW = new Wall();
					tiles.get(i).setWallEast(wallBetweenEW);
					tiles.get(j).setWallWest(wallBetweenEW);
					wallBetweenEW.setTile1(tiles.get(i));
					wallBetweenEW.setTile2(tiles.get(j));
				}
			}
		}
		
		/*Creating unshared walls
		 * If the Tiles do not reference a Wall yet, but the text file specifies that they should (description is not "no" or "fake")
		 * A new wall is created and for the correct direction.
		 */
		for(Tile tile: tiles) {
			if((tile.getWallNorth() == null) && (! tile.getNorthWall().equals("no")) && (! tile.getNorthWall().equals("fake"))) {
				wallNorth = new Wall();
				tile.setWallNorth(wallNorth);
				wallNorth.setTile1(tile);
			}
			
			if((tile.getWallSouth() == null) && (! tile.getSouthWall().equals("no")) && (! tile.getSouthWall().equals("fake"))) {
				wallSouth = new Wall();
				tile.setWallSouth(wallSouth);
				wallSouth.setTile1(tile);
			} 
			
			if((tile.getWallEast() == null) && (! tile.getEastWall().equals("no")) && (! tile.getEastWall().equals("fake"))) {
				wallEast = new Wall();
				tile.setWallEast(wallEast);
				wallEast.setTile1(tile);
			} 
			
			if((tile.getWallWest() == null) && (! tile.getWestWall().equals("no")) && (! tile.getWestWall().equals("fake"))) {
				wallWest = new Wall();
				tile.setWallWest(wallWest);
				wallWest.setTile1(tile);
			}
		}
		
		/*Calculates how many tiles a row should have based on the given dimensions
		 * A list is created to hold the y-positions of the generated Tiles
		 */
		int tilesPerRow;
		ArrayList<Integer> yPosTiles = new ArrayList<Integer>();
		for(Tile tile: tiles) {
			yPosTiles.add(tile.getYPos());
		}
		
		//Then the maximum value of the y-position list is used to specify the tiles per row
		tilesPerRow = yPosTiles.get(yPosTiles.size() - 1) + 1;

		//Creates rows based on this number of tiles per row
		Row row = new Row();
		int rowCounter = 0;

		for(Tile tile: tiles) {
			//Start new row when the row is full (and resets counter)
			if (rowCounter == tilesPerRow)
			{
				rows.add(row);
				row = new Row();
				rowCounter = 0;
			}
			
			//Adds each tile to a row
			row.getTiles().add(tile);
			rowCounter++;
		}
		//Adds last row
		rows.add(row);
	}
	
	/**
	 * Creates an instance of Player on the starting tile
	 * @return Player
	 */
	public Player createPlayer() {
		Player player = null;
		for(Row row: rows) {
			for(Tile tile: row.getTiles()) {
				if(tile.isStartingTile()) {
					player = new Player(tile);
					tile.setPlayer(player);
				}
			}
		}
		return player;
	}
	
	/**
	 * Method for drawing the maze.
	 * Draws the maze by calling the drawRow() method in Row for every row, based on a reversed list of rows.
	 * The list of rows is reversed to ensure the maze is drawn the right way up.
	 * A bottom line for the maze is generated based on the amount of tiles in the first row (and consequently all other rows).
	 */
	public void drawMaze() {
		String bottomLine ="";
		for(Row row: this.getReverseRows()) {
			row.drawRow();
			if(row.isStartingRow()) {
				for(Tile tile: row.getTiles()) {
					bottomLine += "---+";
				}
			}
		}
		//Prints first + and bottom line
		System.out.println("+" + bottomLine);
	}
	
	public static void main(String[] args) {
		
	}
}


