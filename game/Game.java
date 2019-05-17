package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;

/**
 * Game class to run the game.
 */
public class Game {
	private FieldObject[][] grid; //grid of game
	private Character char1; //player 1
	private Character char2; //player 2
	private AI ai;
	private Boolean realOpponent; //whether Player 2 is a Second Player or AI
	private Boolean paused; //check if game is paused or not
	private Boolean ended; //check if game has ended or not
	private int maxTrailLength;
	
	
	
	private Random rand; 
	
	int currentFrame;
	private int seconds;
	private int minutes;
	
	/**
	 * Game constructor to create the game with the rows and columns; the grid of the game. It
	 * also sets paused and ended to false, indicating that the game has just started. It also creates 2 players to play
	 * the game.
	 * @param rows - rows of grid of game
	 * @param cols - cols of grid of game
	 * @param twoPlayer - if the 2nd player is an AI or not
	 */
	public Game(int rows, int cols, Boolean twoPlayers) {
		grid = new FieldObject[rows][cols];
		paused = false;
		ended = false;
		realOpponent = twoPlayers;
		maxTrailLength = 90;
		
		currentFrame = 0;
		seconds = 0;
		minutes = 0;
		
		char1 = new Player(rows * 1/8, cols * 1/2, 1,new Color(0,0,1,1), 0);
		grid[rows * 1/8][cols * 1/2] = char1;
		
		if (realOpponent) {
			char2 = new Player(rows - (rows * 1/8), cols * 1/2, 1,new Color(1,0,0,1), 180);
		}
		else {
			ai = new AI(rows - (rows * 1/8), cols * 1/2, 1,new Color(1,0,0,1), 0);
			char2 = ai;
		}
		
		grid[rows - (rows * 1/8)][cols * 1/2] = char2;
	}
	
	/**
	 * Method to get the progress of the game. If the game hasn't paused or ended yet, you get the current player speed
	 * and just continue to move the player.
	 */
	public void progressGame() {
		currentFrame++;
		if (currentFrame > 60) {
			currentFrame = 0;
			seconds++;
			if (seconds > 60) {
				seconds = 0;
				minutes++;
				if (minutes > 99) {
					minutes = 0;
				}
			}
		}
		checkForOtherInput();
		if (!paused && !ended) {
			checkForGameInput();
			if (seconds % char1.getNextActionableFrame() == 0 && char1.nextActionableFrame != -1) {
				moveChar(char1);
			}
			if (seconds % char2.getNextActionableFrame() == 0 && char1.nextActionableFrame != -1) {
				if (!realOpponent) {
					ai.init(grid);
					ai.tick();
				}
				moveChar(char2);
			}
		}
	}
	
	/**
	 * Method to check for where the player wants the character in the game to go, where to turn. The only thing
	 * is that the character is not allowed to automatically turn backwards. If the player tries to turn backwards,
	 * it will not work and the character will just keep going that way.
	 */
	private void checkForGameInput() {
		//Player 1's Input
		boolean gotInput = false;
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && !gotInput){
			if (!(char1.getAngle() == 0))
            char1.setAngle(180);
			gotInput = true;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !gotInput){
			if (!(char1.getAngle() == 180))
            char1.setAngle(0);
			gotInput = true;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.UP) && !gotInput){
			if (!(char1.getAngle() == 270)) 
            char1.setAngle(90);
			gotInput = true;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && !gotInput){
			if (!(char1.getAngle() == 90)) {
				char1.setAngle(270);
				gotInput = true;
			}
        }
		
		//Player 2's Input if not a CPU
		if (realOpponent) {
			gotInput = false;
			if(Gdx.input.isKeyPressed(Input.Keys.A) && !gotInput){
				if (!(char2.getAngle() == 0))
					char2.setAngle(180);
					gotInput = true;
        		}
			if(Gdx.input.isKeyPressed(Input.Keys.D) && !gotInput){
				if (!(char2.getAngle() == 180))
					char2.setAngle(0);
					gotInput = true;
        		}
			if(Gdx.input.isKeyPressed(Input.Keys.W) && !gotInput){
				if (!(char2.getAngle() == 270)) 
					char2.setAngle(90);
					gotInput = true;
        		}
			if(Gdx.input.isKeyPressed(Input.Keys.S) && !gotInput){
				if (!(char2.getAngle() == 90)) {
					char2.setAngle(270);
					gotInput = true;
				}
        	}
		}
	}
	
	private void checkForOtherInput() {
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			pauseGame();
		}
	}
	
	/**
	 * Method to pause the game.
	 */
	private void pauseGame() {
		paused = !paused;
	}
	
	/**
	 * Method to end the game.
	 */
	private void endGame() {
		System.out.println("\n Game ended.");
		ended = true;
		
		//Should display it on Game Screen later
		switch (findWinner()) {
			case 0:
				System.out.println(" Both Players tied!");
				break;
			case 1:
				System.out.println(" Player One wins!");
				break;
			case 2:
				if (realOpponent) {
					System.out.println(" Player Two wins!");
				}
				else {
					System.out.println(" AI wins!");
				}
				break;
			case -1:
				System.out.println("Error! Something went wrong in finding the Winner.");
				break;
		}
		System.out.println("\n Press the Enter Key to Restart");
	}
	
	/**
	 * Method to move the character during the game. If the space they are moving into is unoccupied, they keep moving. Otherwise,
	 * if they player is going to a place that is occupied and is collided with anything, the game ends and they die.
	 */
	private void moveChar(Character character) {
		// System.out.print("Start:");
		
		int row = character.getRow();
		int col = character.getCol();
		
		boolean collision = collided(character, row, col);
		// System.out.print(" Collision: " + collision);
		
		if (!collision) {
			// System.out.print(" Movement!");
			int angle = character.getAngle();
			int movedHorizontalSpots = calcHorizontalMultiplier(angle)*character.getSpeed();
			int movedVerticalSpots = calcVerticalMultiplier(angle)*character.getSpeed();
			
			grid[row+movedHorizontalSpots][col+movedVerticalSpots] = character;
			
			for(int i = 0; i < char1.getSpeed(); i++) {
				int horizontalSpot = row+(calcHorizontalMultiplier(angle)*i);
				int verticalSpot = col+(calcVerticalMultiplier(angle)*i);
				grid[horizontalSpot][verticalSpot] = character.leaveTrail(horizontalSpot, verticalSpot);
			}
			
			character.setRow(row += movedHorizontalSpots);
			character.setCol(col += movedVerticalSpots);
			checkForExcessTrail();
			System.out.println();
		}
		else {
			character.die();
			endGame();
		}
	}
	
	/**
	 * Check to see each spot that the player will pass in the game. If the spot that the player is going to is occupied,
	 * the method will return true, meaning that the player has collided and will end the game, false otherwise.
	 * @param character - player
	 * @param playerRow - row value of player
	 * @param playerCol - column value of player
	 * @return true if spot player went is occupied, false otherwise
	 */
	private boolean collided(Character character, int playerRow, int playerCol) {
		//check each spot the player will pass
		// if occupied return true
		for(int i = 1; i <= character.getSpeed(); i++) {
			int horizontalSpot = playerRow+(calcHorizontalMultiplier(character.getAngle())*i);
			int verticalSpot = playerCol+(calcVerticalMultiplier(character.getAngle())*i);
			boolean outOfHorizontalBounds = horizontalSpot >= grid.length || horizontalSpot < 0;
			boolean outOfVerticalBounds = verticalSpot >= grid[0].length || verticalSpot < 0;
			//System.out.print("Collision Detection:" + horizontalSpot + "," + verticalSpot + " ");
			//System.out.print("Horizontal Bounded:" + (!outOfHorizontalBounds) + " ");
			//System.out.print("Vertical Bounded:" + (!outOfVerticalBounds) + " ");
			
			//is the spot out of bounds?
			if (outOfHorizontalBounds || outOfVerticalBounds) {
				character.die();
				return true;
			}
			// is the spot occupied
			else if (grid[horizontalSpot][verticalSpot] != null) {
				if (grid[horizontalSpot][verticalSpot].isCharacter()) {
					if (Math.abs(180 - char1.getAngle()) == (double)char2.getAngle() || Math.abs(180 - char2.getAngle()) == (double)char1.getAngle()) {
						char1.die();
						char2.die();
						return true;
					}
				}
				
				System.out.println(" Collided!");
				return true;
			}
		}
		
		//area is in bounds and unoccupied
		System.out.print(" Unoccupied! ");
		return false;
	}
	
	public void checkForExcessTrail() {
		if (char1.getTrailLength() > (int)maxTrailLength * char1.getTrailMultiplier()) {
			removeExcessTrail(char1);
		}
		if (char2.getTrailLength() > (int)maxTrailLength * char2.getTrailMultiplier()) {
			removeExcessTrail(char2);
		}
	}
	
	public void removeExcessTrail(Character character) {
		//Get Tail Details
		int row = character.getTailRow();
		int col = character.getTailCol();
		int angle = grid[row][col].getAngle();
		
		//Find the new Tail
		character.setTailRow(row+(calcHorizontalMultiplier(angle)));
		character.setTailCol(col+(calcVerticalMultiplier(angle)));
		
		//Remove the old Tail
		grid[row][col] = null;
		character.decrementTrailLength();
	}
		
	/**
	 * Method to calculate and return the vertical directions the player is moving in.
	 * @param character - character in the game.
	 * @return 1 if angle is 90, -1 if angle is 270, and default is 0.
	 */
	private int calcVerticalMultiplier(int angle) {
		//Calculates the vertical direction the player is moving in
		switch (angle) {
			case 90:
				return 1;
			case 270:
				return -1;
			default:
				return 0;
		}
	}
	
	/**
	 * Method to calculate and return the horizontal direction the player is moving in.
	 * @param character - character in the game
	 * @return 1 if angle is 0, -1 if angle is 180, and default is 0.
	 */
	private int calcHorizontalMultiplier(int angle) {
		//Returns the horizontal direction the player is moving in
		switch (angle) {
			case 0:
				return 1;
			case 180:
				return -1;
			default:
				return 0;
		}
	}
	
	/**
	 * Method to get the grid of the game to be played on
	 * @return grid of game
	 */
	public FieldObject[][] getGrid() {
		return grid;
	}
	
	/**
	 * Method to find and return the winner.
	 * @return the winner of game.
	 */
	public int findWinner() {
		// return who won
		// 0 = tie
		// 1 = Character 1
		// 2 = Character 2
		if (!char1.isAlive() && !char2.isAlive()) {
			return 0; // tie
		}
		if (!char1.isAlive()) {
			return 2;
		}
		if (!char2.isAlive()) {
			return 1;
		}
		return -1; // should not be reached if this method is called whenever the game ends
	}
	
	public Boolean isGameEnded() {
		return ended;
	}
	
	public Boolean isPaused() {
		return paused;
	}
	
	public Boolean blinkPlayerWhenPaused() {
		return seconds % 2 == 0;
	}
	
	/**
	 * Method to get the row value of the first character.
	 * @return row of first character
	 */
	public int getChar1Row() {
		return char1.getRow();
	}
	
	/**
	 * Method to get the column value of the first character.
	 * @return column of first character
	 */
	public int getChar1Col() {
		return char1.getCol();
	}
	
	/**
	 * Method to get the row value of the second character.
	 * @return row of first character
	 */
	public int getChar2Row() {
		return char2.getRow();
	}
	
	/**
	 * Method to get the column value of the second character.
	 * @return column of second character
	 */
	public int getChar2Col() {
		return char2.getCol();
	}
}
