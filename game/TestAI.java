package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

// Attempt to Survive
// If in Danger of Dieing
// Find a "Safe Spot"
// Get to that Safe Spot
// --- Match X first and Y First
// Always check if destination compromised for new safe spot
public class TestAI extends Character {
	private int spotsToCheck;
	private int state;
	// The AI's current Behavior State
	// 0 - go forward
	// 1 - match safe spot coordinates
	// 2 - prioritize items
	// 3 - look for coords
	private Random rand;
	private boolean itemOnGrid;
	private int itemRow;
	private int itemCol;
	
	private boolean inDanger;
	private int safeRow;
	private int safeCol; //Will try to match Safe Spot Coordinates if in danger
	private FieldObject[][] map;
	private int wins; // number of wins
	
	/**
	 * AI constructor to create the AI, which is the opponent of the other player, and sets their wins to 0.
	 * @param s - speed of AI
	 * @param c - color of AI
	 * @param a - angle of AI
	 */
	public TestAI(FieldObject[][]  grid, int row, int col, int speed, Color color, int d) {
		super(row, col, speed, color, d);
		map = grid;
		wins = 0;
		
		rand = new Random();
		spotsToCheck = 30;
		safeRow = 0;
		safeCol = 0;
		state = 3;
	}
	
	public void prioritizeItem(int itemRow, int itemCol) {
		itemOnGrid = true;
		state = 2;
		this.itemRow = itemRow;
		this.itemCol = itemCol;
	}
	
	public void runAI() {
		if (searchForDanger(direction, spotsToCheck)) {
			inDanger = true;
		}
		else {
			inDanger = false;
		}
		
		System.out.println(state);
		switch (state) {
			//go forward
			case 0:
				steer();
				break;
			//reach designated spot
			case 1:
				reachCoords(safeRow, safeCol);
				break;
			//reach item location
			case 2:
				reachCoords(itemRow, itemCol);
				break;
			//search for a direction
			case 3:
				wander();
				break;
		}
	}
	
	private void steer() {
		//If still in danger
		if (inDanger) {
			lookForNewDirection(spotsToCheck);
		}
		else {
			if (itemOnGrid) {
				state = 2;
			}
			else {
				state = 1;
			}
		}
	}
	
	private void wander() {
		if (inDanger) {
			state = 1;
			lookForNewDirection(spotsToCheck);
			generateSafePlace();
		}
		if (Math.random() * 10 < 0.1) {
			int d = randomDirection();
			if (searchForDanger(d, spotsToCheck)) {
				System.out.println("Danger is happening");
				state = 1;
				lookForNewDirection(spotsToCheck);
				generateSafePlace();
			}
			else {
				System.out.println("Direction is happening");
				setDirection(d);
			}
		}
		// else continue forward
	}
	
	public boolean searchForDanger(int direction, int spots) {
		try {
			for (int i = 1; i <= spots; i++) {
				switch (direction) {
					case 1:
						if (map[row][col+i] != null)
							return true;
						break;
					case 2:
						if (map[row+i][col] != null)
							return true;
						break;
					case 3:
						if (map[row][col-i] != null)
							return true;
						break;
					case 4:
						if (map[row-i][col] != null)
							return true;
						break;
				}
			}
		}
		catch (Exception e) {
			return true;
		}
		return false;
	}
	
	private void lookForNewDirection(int spots) {
		//Look Right or Left
			int left = getLeftDirection();
			int right = getRightDirection();
			boolean canGoLeft = searchForDanger(getLeftDirection(), spots);
			boolean canGoRight = searchForDanger(getRightDirection(), spots);
			if (canGoLeft && canGoRight) {
				if(System.currentTimeMillis() % 2 == 0) {
					setDirection(left);
				}
				else {
					setDirection(right);
				}
			}
			else if (canGoLeft && !canGoRight) {
				setDirection(left);
			}
			else if (!canGoLeft && canGoRight) {
				setDirection(right);
			}
	}
	
	private void reachCoords(int row, int col) {
		if (this.row != row) {
			matchRow(row, col);
		}
		else if (this.col != col) {
			matchCol(row, col);
		}
		else {
			itemOnGrid = false;
			//Reached Location, now Wander
			state = 3;
		}
	}
	
	private void matchRow(int row, int col) {
		int distance = this.row-row;
		if (distance > 0) {
			if(!setDirection(4)) 
				matchCol(row, col);
		}
		else {
			if (!setDirection(2))
				matchCol(row, col);
		}
	}
	
	private void matchCol(int row, int col) {
		int distance = this.col-col;
		if (distance > 0) {
			if(!setDirection(3))
				matchRow(row,col);
		}
		else {
			if(!setDirection(1))
			matchRow(row,col);
		}
	}
	
	private int randomDirection() {
		return rand.nextInt(4);
	}
	
	private void generateSafePlace() {
		int row = rand.nextInt(map.length);
		int col = rand.nextInt(map[0].length);
		safeRow = row;
		safeCol = col;
		state = 1;
	}
	
}
