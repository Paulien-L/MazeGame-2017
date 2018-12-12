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
	
	
	
	public static void main(String[] args) throws IOException {
		//The fancy test
		Maze maze1 = new Maze("C:\\Users\\Computer\\eclipse-workspace\\BC project\\src\\mediummaze.txt");
		Player player;
		maze1.readData();
		player = maze1.createPlayer();
		maze1.drawMaze();
		System.out.println(maze1.getMazeName());

		//The archaeic test
//		Maze maze2 = new Maze("here");
//
//		Row row1 = new Row();
//		Row row2 = new Row();
//		Row row3 = new Row();
//		Row row4 = new Row();

//		Tile tile1 = new Tile(0,0, "wall", "wall", "wall", "wall", "S", null);
//		Tile tile2 = new Tile(0,1, "no", "wall", "no", "wall", "no", null);
//		Tile tile3 = new Tile(0,2, "wall", "wall", "no", "no", "no", null);
//		Tile tile4 = new Tile(0,3, "no", "wall", "wall", "no", "no", null);
//		
//		Tile tile5 = new Tile(1,0, "wall", "wall", "no", "wall", "no", null);
//		Tile tile6 = new Tile(1,1, "wall", "wall", "no", "no", "no", null);		
//		Tile tile7 = new Tile(1,2, "wall", "no", "wall", "no", "no", null);
//		Tile tile8 = new Tile(1,3, "wall", "no", "wall", "wall", "no", null);
//		
//		ArrayList<Tile> tiles = new ArrayList<Tile>();
//		tiles.add(tile1);
//		tiles.add(tile2);
//		tiles.add(tile3);
//		tiles.add(tile4);
//		tiles.add(tile5);
//		tiles.add(tile6);
//		tiles.add(tile7);
//		tiles.add(tile8);
//		
//		Wall wallBetweenNS;
//		Wall wallBetweenEW;
//		Wall wallSouth;
//		Wall wallNorth;
//		Wall wallEast;
//		Wall wallWest;
//
//		for(int i=0; i < tiles.size(); i++) {
//			for(int j = tiles.size()-1; j >= 0; j--) {
//				if((tiles.get(i).isSouthOf(tiles.get(j))) && (! tiles.get(i).getNorthWall().equals("no")) && (! tiles.get(i).getSouthWall().equals("fake"))) {
//					wallBetweenNS = new Wall();
//					tiles.get(i).setWallNorth(wallBetweenNS);
//					tiles.get(j).setWallSouth(wallBetweenNS);
//					wallBetweenNS.setTile1(tiles.get(i));
//					wallBetweenNS.setTile2(tiles.get(j));
//					System.out.println(tiles.get(i).getWallNorth() + " " + tiles.get(j).getWallSouth());
//					System.out.println(tiles.get(i) + " " + wallBetweenNS.getTile1());
//					System.out.println(tiles.get(j) + " " + wallBetweenNS.getTile2());
////					System.out.println("tile"+ (i+1) + ": " + "I share my north wall with you" + ", " + "tile"+ (j+1));
//				}
//				if((tiles.get(i).isWestOf(tiles.get(j))) && (! tiles.get(i).getEastWall().equals("no")) && (! tiles.get(i).getWestWall().equals("fake"))) {
//					wallBetweenEW = new Wall();
//					tiles.get(i).setWallEast(wallBetweenEW);
//					tiles.get(j).setWallWest(wallBetweenEW);
////					System.out.println(tiles.get(i).getWallNorth() + " " + tiles.get(j).getWallSouth());
////					System.out.println("tile"+ (i+1) + ": " + "I share my east wall with you" + ", " + "tile"+ (j+1));
//				}
//			}
//		}
//		
//		for(Tile tile: tiles) {
//			if((tile.getWallNorth() == null) && (! tile.getNorthWall().equals("no")) && (! tile.getNorthWall().equals("fake"))) {
////				System.out.println("tile"+ counter + ": My north wall is my own.");
//				wallNorth = new Wall();
//				tile.setWallNorth(wallNorth);
//			}
//			
//			if((tile.getWallSouth() == null) && (! tile.getSouthWall().equals("no")) && (! tile.getSouthWall().equals("fake"))) {
////				System.out.println("tile"+ counter + ": My south wall is my own.");
//				wallSouth = new Wall();
//				tile.setWallSouth(wallSouth);
//			} 
//			
//			if((tile.getWallEast() == null) && (! tile.getEastWall().equals("no")) && (! tile.getEastWall().equals("fake"))) {
////				System.out.println("tile"+ counter + ": My east wall is my own.");
//				wallEast = new Wall();
//				tile.setWallEast(wallEast);
//			} 
//			
//			if((tile.getWallWest() == null) && (! tile.getWestWall().equals("no")) && (! tile.getWestWall().equals("fake"))) {
////				System.out.println("tile"+ counter + ": My west wall is my own.");
//				wallWest = new Wall();
//				tile.setWallWest(wallWest);
//			}
//		}
	
//		row1.getTiles().add(tile1);
//		row1.getTiles().add(tile2);
//		
//		row2.getTiles().add(tile3);
//		row2.getTiles().add(tile4);
//
//		row3.getTiles().add(tile5);
//		row3.getTiles().add(tile6);
//
//		row4.getTiles().add(tile7);
//		row4.getTiles().add(tile8);
//		
//		System.out.println(row1.isStartingRow());
//		System.out.println(row2.isStartingRow());
//
//		maze2.getRows().add(row1);
//		maze2.getRows().add(row2);
//		maze2.getRows().add(row3);
//		maze2.getRows().add(row4);
//		
//		maze2.drawMaze();
	}
	
}


