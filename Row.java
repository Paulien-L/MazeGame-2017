import java.util.ArrayList;

public class Row {
	private ArrayList<Tile> tiles;
	
	public Row() {
		this.tiles = new ArrayList<Tile>();
	}
	
	public ArrayList<Tile> getTiles() {
		return this.tiles;
	}
	
	public boolean isStartingRow() {
		for(Tile tile: tiles) {
			while((tile.getObject().equals("S")) || (tile.getObject().equals("start"))) { //The while loop is important here, if you use an if statement it just stops looking after the first tile
				return true;
			}
		}
		return false;
	}
	
	public void drawRow() {
		String vertical ="";
		String horizontal ="";

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
		System.out.println("+" + horizontal);
		System.out.println("|" + vertical);
	}
	
	
	
	public static void main(String[] args) {
		Row row1 = new Row();
		Row row2 = new Row();
		Row row3 = new Row();
		
		Tile tile1 = new Tile(0,0, "no", "wall", "wall", "wall", "S", null);
		Tile tile2 = new Tile(0,1, "wall", "no", "fake", "wall", "no", null);
		Tile tile3 = new Tile(0,2, "wall", "wall", "wall", "fake", "no", null);
		row1.getTiles().add(tile1);
		row1.getTiles().add(tile2);
		row1.getTiles().add(tile3);
		
		Tile tile4 = new Tile(1,0, "no", "no", "wall", "breakable", "no", null);
		Tile tile5 = new Tile(1,1, "fake", "no", "breakable", "wall", "no", null);
		Tile tile6 = new Tile(1,2, "no", "wall", "wall", "no", "no", null);
		row2.getTiles().add(tile4);
		row2.getTiles().add(tile5);
		row2.getTiles().add(tile6);
		
		Tile tile7 = new Tile(2,0, "wall", "no", "no", "wall", "no", null);
		Tile tile8 = new Tile(2,1, "wall", "wall", "no", "no", "no", null);
		Tile tile9 = new Tile(2,2, "wall", "no", "wall", "no", "no", null);
		row3.getTiles().add(tile7);
		row3.getTiles().add(tile8);
		row3.getTiles().add(tile9);
	
		row3.drawRow();
		row2.drawRow();
		row1.drawRow();
		
		Player player = new Player(tile1);
		player.moveNorthTo(tile4);
		
		row3.drawRow();
		row2.drawRow();
		row1.drawRow();
		
		player.moveNorthTo(tile7);
		
		row3.drawRow();
		row2.drawRow();
		row1.drawRow();
	}
}
