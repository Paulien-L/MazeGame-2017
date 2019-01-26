import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class PlayMazeGame {
	@SuppressWarnings("resource")
	public static Maze createMaze() {
		String dataSource;
		Maze maze;
		Scanner consoleInput = new Scanner(System.in);
		System.out.println("Please specify the path to your maze file:");
		dataSource = consoleInput.nextLine();
		return maze = new Maze(dataSource);
	}
	
	public static void playMaze() throws IOException {
		Maze maze = createMaze();
		maze.readData();
		Player player = maze.createPlayer();
		
		System.out.println();
		System.out.println("Cadet! Lord Villain McEvil has you trapped in one of his contraptions.");
		System.out.println("See if you can find a way out.");
		System.out.println();
		System.out.println("Type 'up', 'down', 'right', or 'left' to move around.");
		System.out.println("Type 'look' to look around your current room. You might discover hidden things.");
		System.out.println("Type 'break' to try and break walls. You might want to try finding a hammer first, though.");
		System.out.println("Type 'open' to try and open doors. You might want to try finding a key first, though.");
		System.out.println("Type 'map' to look on the map, if you have one that is.");
		System.out.println();
		player.printPlayerMovementOptions();
		
		Scanner consoleInput = new Scanner(System.in);
		
		while(! player.isOnEndTile()) {
			String action = consoleInput.nextLine();
			
			if((action.equals("look") || action.equals("Look"))) {
				System.out.println();
				player.scanSurroundings();
			}
			
			//MOVING
			else if(action.equals("up")) {
				System.out.println();
				for(Row row: maze.getRows()) {
					for(Tile tile: row.getTiles()) {
						if(player.getHasPerformedAction() == false)
							player.moveNorthTo(tile);
					}
				}
				player.setHasPerformedAction(false);
				player.printPlayerMovementOptions();
			}
			
			else if(action.equals("down")) {
				System.out.println();
				for(Row row: maze.getRows()) {
					for(Tile tile: row.getTiles()) {
						if(player.getHasPerformedAction() == false)
							player.moveSouthTo(tile);
					}
				}
				player.setHasPerformedAction(false);
				player.printPlayerMovementOptions();
			}
			
			else if(action.equals("right")) {
				System.out.println();
				for(Row row: maze.getRows()) {
					for(Tile tile: row.getTiles()) {
						if(player.getHasPerformedAction() == false)
							player.moveEastTo(tile);
					}
				}
				player.setHasPerformedAction(false);
				player.printPlayerMovementOptions();
			}
			
			else if(action.equals("left")) {
				System.out.println();
				for(Row row: maze.getRows()) {
					for(Tile tile: row.getTiles()) {
						if(player.getHasPerformedAction() == false)
							player.moveWestTo(tile);
					}
				}
				player.setHasPerformedAction(false);
				player.printPlayerMovementOptions();
			}
			
			//BREAKING WALLS
			else if(action.equals("break")) {
				System.out.println();
				for(Row row: maze.getRows()) {
					for(Tile tile: row.getTiles()) {
						if(player.getHasPerformedAction() == false)
							player.breakWall(tile);
					}
				}
				player.setHasPerformedAction(false);
				player.printPlayerMovementOptions();
			}
			
			//OPENING DOORS
			else if(action.equals("open")) {
				System.out.println();
				for(Row row: maze.getRows()) {
					for(Tile tile: row.getTiles()) {
						if(player.getHasPerformedAction() == false)
							player.openDoor(tile);
					}
				}
				player.setHasPerformedAction(false);
				player.printPlayerMovementOptions();
			}
			
			//USING MAP
			else if(action.equals("map")) {
				if(player.hasMap()) {
					maze.drawMaze();
				} else
					System.out.println("You don't have a map.");
			}
		}
		
		System.out.println();
		System.out.println("Congratulations cadet, you've escaped Lord Villain McEvil!");
		System.out.println("Thanks for playing!");
		System.out.println();
		System.out.println("You solved the maze in: " + player.getNbSteps() + " steps.");
		System.out.format("You collected these objects along the way: %s\n", player.getObjects());
		System.out.println("Your total score is: " + player.calculateScore());
		System.out.println("Here's a map of the maze you were in. I should probably have told you I had one sooner.");
		maze.drawMaze();
		
		System.out.println("Such a feat must be recorded!");
		System.out.println("What is your name, cadet?");
		String playerName = consoleInput.nextLine();
		player.setName(playerName);
		
		System.out.println();
		System.out.format("I'll remember that, cadet %s.\n", player.getName());
		System.out.println();
		
		System.out.println("Please specify the filepath to your highscore file.");
		String filePath = consoleInput.nextLine();
		
		consoleInput.close();
		
		Writer writer = null;

		try {
		    writer = new BufferedWriter(new FileWriter(filePath, true));
		    writer.append("\r\n" +player.getName() +"," + maze.getMazeName() +","+ player.getNbSteps() +","+ player.calculateScore());
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    if (writer != null) 
		    	try { 
		    		writer.close(); 
		    	} catch (IOException ignore) {
		    	}
		}
		
		System.out.println();
		System.out.println("Your scores have been added to a the highscore file.");
	}
	
	public static void main(String[] args) throws IOException {
		playMaze();
	}
}
