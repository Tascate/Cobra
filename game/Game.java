package com.mygdx.game;

import java.util.ArrayList;
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
	private TestAI ai;
	private Boolean realOpponent; //whether Player 2 is a Second Player or AI
	private Boolean paused; //check if game is paused or not
	private Boolean ended; //check if game has ended or not
	private int maxTrailLength;
	
	private Item currentItem;
	private Boolean itemQueued;
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
		rand = new Random();
		paused = false;
		ended = false;
		itemQueued = false;
		realOpponent = twoPlayers;
		maxTrailLength = 90;
		
		currentFrame = 0;
		seconds = 0;
		minutes = 0;
		
		char1 = new Character(rows * 1/8, cols * 1/2, 1,new Color(0,0,1,1), 2);
		grid[rows * 1/8][cols * 1/2] = char1;
		
		if (realOpponent) {
			char2 = new Character(rows - (rows * 1/8), cols * 1/2, 1,new Color(1,0,0,1), 4);
		}
		else {
			ai = new TestAI(grid, rows - (rows * 1/8), cols * 1/2, 1,new Color(1,0,0,1), 4);
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
					ai.runAI();
				}
				moveChar(char2);
			}
		}
		if (ended()) {
			endGame();
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
            char1.setDirection(4);
			gotInput = true;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !gotInput){
            char1.setDirection(2);
			gotInput = true;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.UP) && !gotInput){
			char1.setDirection(1);
			gotInput = true;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && !gotInput){
			char1.setDirection(3);
			gotInput = true;
        }
		
		//Player 2's Input if not a CPU
		if (realOpponent) {
			gotInput = false;
			if(Gdx.input.isKeyPressed(Input.Keys.A) && !gotInput){
				char2.setDirection(4);
				gotInput = true;
			}
			if(Gdx.input.isKeyPressed(Input.Keys.D) && !gotInput){
				char2.setDirection(2);
				gotInput = true;
			}
			if(Gdx.input.isKeyPressed(Input.Keys.W) && !gotInput){
				char2.setDirection(1);
				gotInput = true;
			}
			if(Gdx.input.isKeyPressed(Input.Keys.S) && !gotInput){
				char2.setDirection(3);
				gotInput = true;
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
	Checks to see of the game has ended
	*@return true if the game has ended
	*/
	private Boolean ended() {
		if (char1.isAlive() && char2.isAlive()) {
			return false;
		}
		return true;
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
		// System.out.print(" Collision: " + collision);
		if (!collided(character)) {		
			for(int i = 0; i < char1.getSpeed(); i++) {
				switch (character.getDirection()) {
				case 1:
					grid[row][col+i] = character.leaveTrail(row, col+i);
					break;
				case 2:
					grid[row+i][col] = character.leaveTrail(row+i, col);
					break;
				case 3:
					grid[row][col-i] = character.leaveTrail(row, col-i);
					break;
				case 4:
					grid[row-i][col] = character.leaveTrail(row-i, col);
					break;
				}
			}
			
			int speed = character.getSpeed();
			switch (character.getDirection()) {
				case 1:
					character.setCol(col+speed);
					break;
				case 2:
					character.setRow(row+speed);
					break;
				case 3:
					character.setCol(col-speed);
					break;
				case 4:
					character.setRow(row-speed);
					break;
			}
			grid[character.getRow()][character.getCol()] = character;
			checkForExcessTrail();
		}
		else {
			character.die();
		}
	}
	
	/**
	 * Check to see each spot that the player will pass in the game. If the spot that the player is going to is occupied,
	 * the method will return true, meaning that the player has collided and will end the game, false otherwise.
	 * @param character - player
	 * @return true if spot player went is occupied, false otherwise
	 */
	private boolean collided(Character character) {
		//check each spot the player will pass
		// if occupied return true
		for(int i = 1; i <= character.getSpeed(); i++) {
			int projectedRow = character.getRow();
			int projectedCol = character.getCol();
			switch (character.getDirection()) {
				case 1:
					projectedCol += i;
					break;
				case 2:
					projectedRow += i;
					break;
				case 3:
					projectedCol -= i;
					break;
				case 4:
					projectedRow -= i;
					break;
			}
			boolean outOfHorizontalBounds = projectedRow >= grid.length || projectedRow < 0;
			boolean outOfVerticalBounds = projectedCol >= grid[0].length || projectedCol < 0;
			//is the spot out of bounds?
			if (outOfHorizontalBounds || outOfVerticalBounds) {
				character.die();
				return true;
			}
			// is the spot occupied
			else if (isOccupied(projectedRow,projectedCol)) {
				if (grid[projectedRow][projectedCol].isCharacter()) {
					if (char1.getDirection() == char2.getOppositeDirection()) {
						char1.die();
						char2.die();
						endGame();
						return true;
					}
				}
				character.die();
				return true;
			}
		}
		//area is in bounds and unoccupied
		return false;
	}
	/**
	*Checks to see whether the coordinate on the grid is occupied
	*@return true if point on grid is occupied
	*/
	private boolean isOccupied (int row, int col) {
		return grid[row][col] != null;
	}
	
	/**
	*Checks to see if the tail is getting too long.
	*/
	private void checkForExcessTrail() {
		if (char1.getTrailLength() > (int)maxTrailLength * char1.getTrailMultiplier()) {
			removeExcessTrail(char1);
		}
		if (char2.getTrailLength() > (int)maxTrailLength * char2.getTrailMultiplier()) {
			removeExcessTrail(char2);
		}
	}
	/**
	*Removes any excess tail on the sprite if the tail gets too long
	*/
	private void removeExcessTrail(Character character) {
		//Get Tail Details
		int row = character.getTailRow();
		int col = character.getTailCol();
		int direction = character.tailDirection;
		
		grid[row][col] = null;
		//Find the new Tail
		switch (direction) {
			case 1:
				character.setTailCol(col+1);
				break;
			case 2:
				character.setTailRow(row+1);
				break;
			case 3:
				character.setTailCol(col-1);
				break;
			case 4:
				character.setTailRow(row-1);
				break;
		}
		character.setTailDirection(grid[character.getTailRow()][character.getTailCol()].getDirection());
		//Remove the old Tail
		character.decrementTrailLength();
	}
	
	/**
	*Checks to see if an item is in queue
	*@return true if tem is queued
	*/
	public boolean queueItem() {
		if (!itemQueued) {
			int row = rand.nextInt(grid.length);
			int col = rand.nextInt(grid[0].length);
			if (rand.nextInt(2) == 1) {
				currentItem= new Item(rand.nextInt(4), row, col);
				return true;
			}
			return false;
		}
		return false;
	}
		
	/**
	*Checks to see if an item has been spawned.
	*@return true if item has been spawned
	*/
	public boolean spawnItem() {
		if (grid[currentItem.getRow()][currentItem.getCol()] == null) {
			grid[currentItem.getRow()][currentItem.getCol()] = currentItem;
			if (!realOpponent) {
				ai.prioritizeItem(currentItem.getRow(), currentItem.getCol());
			}
			return true;
		}
		return false;
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
	/*
	*Returns whether or not the game has ended.
	*@return the state of whether or not the game has ended
	*/
	public Boolean isGameEnded() {
		return ended;
	}
	/**
	*Returns whether or not the game is paused
	*@return the state of whether or not the game is paused
	*/
	public Boolean isPaused() {
		return paused;
	}
	/**
	*Checks to see of the player sprite is blinking if the game is pausd
	*@return true of the modulus of the seconds by 2 is equal to 0
	*/
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
