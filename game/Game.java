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
	 * Game constructor to create the game with the rows and columns, which is the grid of the game. It
	 * also sets paused and ended to false, indicating that the game has just started. It also creates 2 players to play
	 * the game.
	 * @param rows - rows of grid of game
	 * @param cols - cols of grid of game
	 */
	public Game(int rows, int cols) {
		grid = new FieldObject[rows][cols];
		paused = false;
		ended = false;
		
		char1 = new Player(1,new Color(0,0,1,1), 90);
		grid[3][6] = char1;
		char1Row = 3;
		char1Col = 6;
		
		char2 = new Player(1,new Color(1,0,0,1), 90);
		grid[9][6] = char2;
		char2Row = 9;
		char2Col = 6;
	}
	
	/**
	 * Method to get the progress of the game. If the game hasn't paused or ended yet, you get the current player speed
	 * and just continue to move the player.
	 */
	public void progressGame() {
		if (!paused && !ended) {
			checkForInput();
			moveChar1();
		}
	}
	
	/**
	 * Method to check for input of what key the player pressed to move, left, right, up, or down. The player
	 * cannot be going backwards, or turning backwards, so the if statements check for that.
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
		System.out.println(" Game ended.");
		ended = true;
	}
	
	private void moveChar1() {
		System.out.print("Start:");
		boolean collision = collided(char1, char1Row, char1Col);
		System.out.print(" Collision:" + collision);
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
	 * Method to get the gird of the game to be played on.
	 * @return grid of game
	 */
	public FieldObject[][] getGrid() {
		return grid;
	}
	
	//should move these methods into Character.java later
	
	/**
	 * Method to get the first character's row value in the game
	 * @return row value of first character
	 */
	public int getChar1Row() {
		return char1Row;
	}
	
	/**
	 * Method to get the first character's column value
	 * @return column value of first character
	 */
	public int getChar1Col() {
		return char1Col;
	}
	/**
	 * Method to get the second character's row value in the game
	 * @return row value of second character
	 */
	public int getChar2Row() {
		return char2Row;
	}
	
	/**
	 * Method to get the second character's column value in the game
	 * @return column value of second character
	 */
	public int getChar2Col() {
		return char2Col;
	}
}
