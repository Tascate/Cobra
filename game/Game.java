package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;

public class Game {
	FieldObject[][] grid;
	Character char1;
	Character char2;
	int char1Row, char1Col;
	int char2Row, char2Col;
	Boolean paused;
	
	public Game(int rows, int cols) {
		grid = new FieldObject[rows][cols];
		paused = false;
		
		char1 = new Player(5,new Color(0,0,1,1), 90);
		grid[3][6] = char1;
		char1Row = 3;
		char1Col = 6;
		
		char2 = new Player(5,new Color(1,0,0,1), 90);
		grid[9][6] = char2;
		char2Row = 9;
		char2Col = 6;
	}
	
	public void progressGame() {
		int speed = grid[char1Row][char1Col].getSpeed();
		checkForInput(speed);
	}
	
	private void checkForInput(int speed) {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            grid[char1Row][char1Col].setAngle(180);
            System.out.println("Left!");
            char1Row -= speed;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            grid[char1Row][char1Col].setAngle(0);
            System.out.println("Right!");
            char1Row += speed;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            grid[char1Row][char1Col].setAngle(90);
            System.out.println("Up!");
            char1Col += speed;
        }
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            grid[char1Row][char1Col].setAngle(270);
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
		
	}
	
	private void moveChar1() {
		if (collision(char1Row, char1Col)) {
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
		
	}
	
	private boolean collision(int playerRow, int playerCol) {
		FieldObject character = grid[playerRow][playerCol];
		//check each spot the player will pass
		//amount of spots the player will pass = the speed
		for(int i = 1; i <= character.getSpeed(); i++) {
			if ((playerRow+calcHorizontal(character)*i < grid.length) && (playerRow+calcVertical(character)*i) < grid[0].length &&
				grid[playerRow+calcHorizontal(character)*i][playerCol+calcVertical(character)*i] != null) {
				return true;
			}
		}
		return false;
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
