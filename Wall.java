
public class Wall {
	
	Tile tile1;
	Tile tile2;
	
	public Wall() {	
	}
	
	public Tile getTile1() {
		return this.tile1;
	}
	
	public Tile getTile2() {
		return this.tile2;
	}
	
	public void setTile1(Tile tile) throws IllegalArgumentException {
		if(isValidTile(tile) == true)
			this.tile1 = tile;
		else
			throw new IllegalArgumentException();
	}
	
	public void setTile2(Tile tile) throws IllegalArgumentException {
		if(isValidTile(tile) == true)
			this.tile2 = tile;
		else
			throw new IllegalArgumentException();
	}
	
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
	
	public boolean isShared() {
		if((tile1 != null) && (tile2 != null))
			return true;
		else
			return false;
	}
	
	public static void main(String[] args) {
		Tile myTile = new Tile(0,0,"breakable","wall","no","no","no", null);
		Tile myNorthTile = new Tile(1,0,"wall","breakable","no","no","no", null);
		Wall wallNS = new Wall();
		Wall wallSouth = new Wall();
		
		myTile.setWallNorth(wallNS);
		myNorthTile.setWallSouth(wallNS);
		
		wallNS.setTile1(myTile);
		wallNS.setTile2(myNorthTile);

		System.out.println(wallNS.isBreakable());
		System.out.println(wallSouth.isShared());
	}
	
}
