import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;


public class Maze {
	
	private ArrayList<Row> rows;
	private String dataSource;
	private String mazeName;
	
	public Maze(String pathToData) {
		this.rows = new ArrayList<Row>();
		this.dataSource = pathToData;
	}
	
	public ArrayList<Row> getRows() {
		return this.rows;
	}
	
	public ArrayList<Row> getReverseRows() {
		ArrayList<Row> reverseRows = new ArrayList<Row>(rows);
		Collections.reverse(reverseRows);
		return reverseRows;
	}
	
	public String getMazeName() {
		int index = this.dataSource.lastIndexOf("\\");
		return this.mazeName = this.dataSource.substring(index +1);
	}
	
	public void readData() throws IOException {

		//List to put all of the read tiles
		ArrayList<Tile> tiles = new ArrayList<Tile>();

		InputStream input = new FileInputStream(dataSource);
		BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
		
		//Reads first line
		buffer.readLine();
		
		//Iterate over each line in the text file
		for(String line = buffer.readLine(); line != null; line = buffer.readLine()) {
			String[] data = line.split(",");

			//Cast data types to match the tile
			int x = Integer.parseInt(data[0]);
			int y = Integer.parseInt(data[1]);
			String north = String.valueOf(data[2]);
			String south = String.valueOf(data[3]);
			String east = String.valueOf(data[4]);
			String west = String.valueOf(data[5]);
			String object = String.valueOf(data[6]);
			
			//Create new instance of Tile class with the retrieved data
			Tile newTile = new Tile(x, y, north, south, east, west, object, null);
			tiles.add(newTile);
		}
		buffer.close();
		
		//Create walls of tiles
		Wall wallBetweenNS;
		Wall wallBetweenEW;
		Wall wallSouth;
		Wall wallNorth;
		Wall wallEast;
		Wall wallWest;
		
		//Creating shared walls
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
		
		//Creating unshared walls
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
		
		//Calculates how many tiles a row should have based on the given dimensions
		int tilesPerRow;
		ArrayList<Integer> xPosTiles = new ArrayList<Integer>();
		ArrayList<Integer> yPosTiles = new ArrayList<Integer>();
		for(Tile tile: tiles) {
			xPosTiles.add(tile.getXPos());
			yPosTiles.add(tile.getYPos());
		}
		
		if(xPosTiles.get(xPosTiles.size() - 1) <= yPosTiles.get(yPosTiles.size() - 1))
			tilesPerRow = yPosTiles.get(yPosTiles.size() - 1) + 1;
		else
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
	
	public void drawMaze() {
		String bottomLine ="";
		for(Row row: this.getReverseRows()) {
			row.drawRow();
			if(row.isStartingRow()) {
				for(Tile tile: row.getTiles()) { //makes sure the correct bottom line is drawn
					bottomLine += "---+";
				}
			}
		}
		System.out.println("+" + bottomLine);
	}
}


