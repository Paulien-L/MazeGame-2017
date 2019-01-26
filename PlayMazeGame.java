import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

/**
 * Class used to play the Maze game based on user input.
 * @author Paulien Leten
 * @version 11
 *
 */
public class PlayMazeGame {
	
	/**
	 * Method to create in instance of the Maze class based on the data source provided by the user.
	 * @return Maze
	 * @note The scanner is not closed in this method as the input stream is reused in the playMaze() method.
	 * 		 A warning is added for clarity.
	 */
	@SuppressWarnings("resource")
	public static Maze createMaze() {
		String dataSource;
		Maze maze;
		Scanner consoleInput = new Scanner(System.in);
		System.out.println("Please specify the path to your maze file:");
		dataSource = consoleInput.nextLine();
		return maze = new Maze(dataSource);
	}
	
	/**
	 * Method to play the maze game.
	 * The maze is created, then read and a Player is created.
	 * Some text lines are printed to explain the premise and basic functionalities of the player in the maze.
	 * As well as confirmations of actions the player takes.
	 * If a movement action is not possible, nothing is printed and also nothing happens.
	 * If an action like breaking a wall, opening a door or using a map is not possible, the reason is printed.
	 * 
	 * @note	It is the deliberate choice of the author to display the player movements as text options rather than a drawn version of the maze.
	 * 			If the text file specifies it the player can, however, find and use a map, which functions more or less the same.
	 * 			The Player also has to actively look for hidden objects in the maze to find them.
	 * 			Special walls can also be uncovered by looking around.
	 * 		 	It is, however, possible to change this game set up with minimal impact on the code.
	 * @throws IOException
	 * 			Error is thrown when the file cannot be resolved.
	 */
	public static void playMaze() throws IOException {
		Maze maze = createMaze();
		maze.readData();
		Player player = maze.createPlayer();
		
		//Tutorial instructions
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
		
		//Prints movement options of the player
		player.printPlayerMovementOptions();
		
		Scanner consoleInput = new Scanner(System.in);
		
		//Game continues until the player reaches the end.
		while(! player.isOnEndTile()) {
			
			String action = consoleInput.nextLine();
			
			//LOOKING AROUND
			if(action.equals("look")) {
				System.out.println();
				player.scanSurroundings();
			}
			
			//After a player has moved the hasPerfomedAction variable is set back to false, this is to ensure a player only performs one action at a time.
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
		
		//Lines printed upon completion of the maze
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
		
		//Sets player name to the console input
		String playerName = consoleInput.nextLine();
		player.setName(playerName);
		
		System.out.println();
		System.out.format("I'll remember that, cadet %s.\n", player.getName());
		System.out.println();
		
		//Ask user for file path to the provided high score file
		System.out.println("Please specify the filepath to your high score file:");
		String filePath = consoleInput.nextLine();
		
		consoleInput.close();
		
		//Writes high score to the file
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
		//Print confirmation line
		System.out.println();
		System.out.println("Your scores have been added to a the highscore file.");
	}
	
	//Main method
	public static void main(String[] args) throws IOException {
		playMaze();
	}
}
