package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;

/**
 * Game class to run the game.
 */
public class Game {
	FieldObject[][] grid; //grid of game
	Character char1; //player 1
	Character char2; //player 2
	int char1Row, char1Col; //player 1 row and column
	int char2Row, char2Col; //player 2 row and column
	Boolean paused; //check if game is paused or not
	Boolean ended; //check if game has ended or not
	
	/**
	 * Game constructor to create the game with the rows and columns; the grid of the game. It
	 * also sets paused and ended to false, indicating that the game has just started. It also creates 2 players to play
	 * the game.
	 * @param rows - rows of grid of game
	 * @param cols - cols of grid of game
	 */
	public Game(int rows, int cols) {
		grid = new FieldObject[rows][cols];
		paused = false;
		ended = false;
		
		char1 = new Player(1,new Color(0,0,1,1), 90); //creates a new player
		grid[3][6] = char1;
		char1Row = 3;
		char1Col = 6;
		
		char2 = new Player(1,new Color(1,0,0,1), 90); //creates a new player
		grid[9][6] = char2;
		char2Row = 9;
		char2Col = 6;
	}
	
	/**
	 * Method to get the progress of the game. If the game hasn't paused or ended yet, you get the current player speed
	 * and just continue to move the player.
	 */
	public void progressGame() {
		checkForInput();
		if (!paused && !ended) {
			moveChar1();
		}
	}
	
	/**
	 * Method to check for where the player wants the character in the game to go, where to turn. The only thing
	 * is that the character is not allowed to automatically turn backwards. If the player tries to turn backwards,
	 * it will not work and the character will just keep going that way.
	 */
	private void checkForInput() {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			if (!(char1.getAngle() == 0))
            char1.setAngle(180);;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			if (!(char1.getAngle() == 180))
            char1.setAngle(0);
        }
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			if (!(char1.getAngle() == 270)) 
            char1.setAngle(90);
        }
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			if (!(char1.getAngle() == 90)) {
				char1.setAngle(270);
			}
        }
		
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
	}
	
	/**
	 * Method to move the character during the game. If the space they are moving into is unoccupied, they keep moving. Otherwise,
	 * if they player is going to a place that is occupied and is collided with anything, the game ends and they die.
	 */
	private void moveChar1() {
		System.out.print("Start:");
		boolean collision = collided(char1, char1Row, char1Col);
		System.out.print(" Collision: " + collision);
		if (!collision) {
			System.out.print(" Movement!");
			int movedHorizontalSpots = calcHorizontal(char1)*char1.getSpeed();
			int movedVerticalSpots = calcVertical(char1)*char1.getSpeed();
			grid[char1Row+movedHorizontalSpots][char1Col+movedVerticalSpots] = char1;
			for(int i = 1; i <= char1.getSpeed(); i++) {
				int horizontalSpot = char1Row+(calcHorizontal(char1)*i);
				int verticalSpot = char1Col+(calcVertical(char1)*i);
				grid[horizontalSpot][verticalSpot] = char1.leaveTrail();
			}
			char1Row += movedHorizontalSpots;
			char1Col += movedVerticalSpots;
			System.out.println();
		}
		else {
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
			int horizontalSpot = playerRow+(calcHorizontal(character)*i);
			int verticalSpot = playerCol+(calcVertical(character)*i);
			boolean outOfHorizontalBounds = horizontalSpot >= grid.length || horizontalSpot < 0;
			boolean outOfVerticalBounds = verticalSpot >= grid[0].length || verticalSpot < 0;
			//System.out.print("Collision Detection:" + horizontalSpot + "," + verticalSpot + " ");
			//System.out.print("Horizontal Bounded:" + (!outOfHorizontalBounds) + " ");
			//System.out.print("Vertical Bounded:" + (!outOfVerticalBounds) + " ");
			
			//is the spot out of bounds?
			if (outOfHorizontalBounds || outOfVerticalBounds) {
				return true;
			}
			// is the spot occupied
			else if (grid[horizontalSpot][verticalSpot] != null) {
				System.out.println(" Collided!");
				return true;
			}
		}
		
		//area is in bounds and unoccupied
		System.out.print(" Unoccupied! ");
		return false;
	}
		
	/**
	 * Method to calculate and return the vertical directions the player is moving in.
	 * @param character - character in the game.
	 * @return 1 if angle is 90, -1 if angle is 270, and default is 0.
	 */
	private int calcVertical(FieldObject character) {
		//Calculates the vertical direction the player is moving in
		switch (character.getAngle()) {
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
	private int calcHorizontal(FieldObject character) {
		//Returns the horizontal direction the player is moving in
		switch (character.getAngle()) {
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
	
	public int findWinner() {
		if (char1.returnLifeStatus()) {
			return 1;
		}
		else if (char2.returnLifeStatus()) {
			return 2;
		}
		return 0;
	}
	
	//should move these methods into Character.java later
	
	/**
	 * Method to get the row value of the first character.
	 * @return row of first character
	 */
	public int getChar1Row() {
		return char1Row;
	}
	
	/**
	 * Method to get the column value of the first character.
	 * @return column of first character
	 */
	public int getChar1Col() {
		return char1Col;
	}
	
	/**
	 * Method to get the row value of the second character.
	 * @return row of first character
	 */
	public int getChar2Row() {
		return char2Row;
	}
	
	/**
	 * Method to get the column value of the second character.
	 * @return column of second character
	 */
	public int getChar2Col() {
		return char2Col;
	}
}
