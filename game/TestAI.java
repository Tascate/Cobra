package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

// Attempt to Survive
// If in Danger of Dieing
// Find a "Safe Spot"
// Get to that Safe Spot
// --- Match X first and Y First
// Always check if destination compromised for new safe spot
public class TestAI extends Character {

	private int state;
	// The AI's current Behavior State
	// 0 - go forward
	// 1 - match safe spot coordinates
	// 2 - prioritize items
	
	private boolean inDanger;
	private boolean itemOnGrid;
	private int itemRow;
	private int itemCol;
	
	private int safeRow;
	private int safeCol; //Will try to match Safe Spot Coordinates if in danger
	
	private int direction;
	// Direction to go in
	// 1 - up
	// 2 - down
	// 3 - right
	// 4 - left
	private int spotsToCheck;
	
	private int wins; // number of wins
	
	/**
	 * AI constructor to create the AI, which is the opponent of the other player, and sets their wins to 0.
	 * @param s - speed of AI
	 * @param c - color of AI
	 * @param a - angle of AI
	 */
	public TestAI(int row, int col, int speed, Color color, int angle) {
		super(row, col, speed, color, angle);
		wins = 0;
		spotsToCheck = 30;
	}
	
	public void init(FieldObject[][] grid, boolean itemSpawned, int itemRow, int itemCol) {
		int horizontalSpot;
		int verticalSpot;
		try {
			for (int i = 1; i <= spotsToCheck; i++) {
				horizontalSpot = row+calcHorizontalMultiplier(angle);
				verticalSpot = row+calcVerticalMultiplier(angle);
				if (grid[horizontalSpot][verticalSpot] != null) {
					inDanger = true;
				}
			}
		}
		catch (Exception e) {
			inDanger = true;
		}
		itemOnGrid = itemSpawned;
		this.itemRow = itemRow;
		this.itemCol = itemCol;
	}
	
	private void lookForNewDirection() {
		//Look Right or Left
		if (inDanger) {
			boolean canGoLeft;
			boolean canGoRight;
		}
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
	
}
