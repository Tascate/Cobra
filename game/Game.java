package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;

/**
 * Game class to run the game.
 */
public class Game {
	FieldObject[][] grid;
	Character char1;
	Character char2;
	int char1Row, char1Col;
	int char2Row, char2Col;
	Boolean paused;
	Boolean ended;
	
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
		if (!paused || !ended) {
			int speed = char1.getSpeed();
			checkForInput(speed);
			moveChar1();
		}
	}
	
	private void checkForInput(int speed) {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            char1.setAngle(180);
            System.out.println("Left!");
            char1Row -= speed;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            char1.setAngle(0);
            System.out.println("Right!");
            char1Row += speed;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            char1.setAngle(90);
            System.out.println("Up!");
            char1Col += speed;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            char1.setAngle(270);
            System.out.println("Down!");
            char1Col -= speed;
        }
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
			pauseGame();
		}
	}
	
	private void pauseGame() {
		paused = !paused;
	}
	
	private void endGame() {
		ended = true;
	}
	
	private void moveChar1() {
		if (!collided(char2, char1Row, char1Col)) {
			grid[char1Row+calcHorizontal(char1)*char1.getSpeed()][char1Col+calcVertical(char1)*char1.getSpeed()] = char1;
			for(int i = 1; i <= char1.getSpeed(); i++) {
				grid[char1Row+calcHorizontal(char1)*i][char1Col+calcVertical(char1)*i] = char1.leaveTrail();
			}
			char1Row += calcHorizontal(char1)*char1.getSpeed();
			char1Col += calcVertical(char1)*char1.getSpeed();
		}
		else {
			endGame();
		}
	}
	
	private void moveChar2() {
		if (collided(char1, char2Row, char2Col)) {
			grid[char1Row+calcHorizontal(char1)*char1.getSpeed()][char1Col+calcVertical(char1)*char1.getSpeed()] = char1;
			for(int i = 1; i <= char1.getSpeed(); i++) {
				grid[char1Row+calcHorizontal(char1)*i][char1Col+calcVertical(char1)*i] = char1.leaveTrail();
			}
			char1Row += calcHorizontal(char1)*char1.getSpeed();
			char1Col += calcVertical(char1)*char1.getSpeed();
		}
		else {
			endGame();
		}
	}
	
	private boolean collided(Character character, int playerRow, int playerCol) {
		//check each spot the player will pass
		//amount of spots the player will pass = the speed
		for(int i = 1; i <= character.getSpeed(); i++) {
			if (playerRow+calcHorizontal(character)*i < grid.length && 
				playerRow+calcHorizontal(character)*i >= 0 &&
				playerRow+calcVertical(character)*i < grid[0].length &&
				playerRow+calcVertical(character)*i >= 0 &&
				grid[playerRow+calcHorizontal(character)*i][playerCol+calcVertical(character)*i] != null) {
				return false;
			}
		}
		return true;
	}
	
	private int calcVertical(FieldObject character) {
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
		switch (character.getAngle()) {
			case 0:
				return 1;
			case 180:
				return -1;
			default:
				return 0;
		}
	}
}
