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
	private boolean prioritizeRow;
	
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
		spotsToCheck = 20;
		safeRow = 0;
		safeCol = 0;
		generateSafePlace();
		state = 3;
		prioritizeRow = true;
	}
	
	public void prioritizeItem(int itemRow, int itemCol) {
		itemOnGrid = true;
		state = 2;
		this.itemRow = itemRow;
		this.itemCol = itemCol;
	}
	
	public void resetState() {
		itemOnGrid = false;
		prioritizeRow = true;
		state = 3;
		runAI();
	}
	
	public void runAI() {
		System.out.print("State: " + state + ", " + row + ", " + col);
		System.out.println(", Direction: " + direction);
		System.out.print(searchForDanger(direction, spotsToCheck));
		if (searchForDanger(direction, spotsToCheck)) {
			inDanger = true;
		}
		else {
			inDanger = false;
		}
		switch (state) {
			//go forward
			case 0:
				steer();
				break;
			//reach designated spot
			case 1:
				System.out.println("Safe Row: " + safeRow + " Safe Col: " + safeCol);
				if (inDanger) {
					deduceLeftOrRight();
				}
				else {
				reachCoords(safeRow, safeCol);
				}
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
		System.out.println(" New Direction:" + direction);
	}
	
	private void steer() {
		//If still in danger, look for best direction to be in
		deduceLeftOrRight();
		if (searchForDanger(direction, spotsToCheck)) { 
			
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
			deduceLeftOrRight();
			generateSafePlace();
		}
		if (Math.random() * 10 < 0.2) {
			int d = randomDirection();
			if (!searchForDanger(d+1, spotsToCheck)) {
				System.out.println("Direction is happening");
				setDirection(d+1);
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
	
	public int distanceUntilImpact(int direction) {
		int i = 0;
		if (direction == 1 || direction == 3) {
			while (Math.abs(i) < map[0].length) {
				switch (direction) {
				case 1:
					if (map[row][col+i] != null)
						return Math.abs(i);
					else i++;
				case 3:
					if (map[row][col-i] != null)
						return Math.abs(i);
					else i--;
				}
			}
		}
		else if (direction == 2 || direction == 4) {
			while (Math.abs(i) < map.length) {
				switch (direction) {
				case 2:
					if (map[row+i][col] != null)
						return Math.abs(i);
					else i++;
				case 4:
					if (map[row-i][col] != null)
						return Math.abs(i);
					else i--;
				}
			}
		}
		return Math.abs(i);
	}
	private void deduceLeftOrRight() {
		int left = getLeftDirection();
		int right = getRightDirection();
		if (distanceUntilImpact(left) > distanceUntilImpact(right)) {
			setDirection(left);
		}
		else {
			setDirection(right);
		}
	}
	
	private void reachCoords(int row, int col) {
			if (prioritizeRow) {
				if (this.row != row) {
					matchRow(row, col);
				}
				else if (this.col != col) {
					matchCol(row, col);
				}
				else {
					System.out.print("Reached Location.");
					//Reached Location, now go back to State 3 (Wander Around)
					resetState();
				}
			}
			else {
				if (this.col != col) {
					matchCol(row, col);
				}
				else if (this.row != row) {
					matchRow(row, col);
				}
				else {
					System.out.print("Reached Location.");
					//Reached Location, now go back to State 3 (Wander Around)
					resetState();
				}
			}
	}
	
	private void matchRow(int row, int col) {
		int distance = this.row-row;
		if (distance > 0) {
			if(!searchForDanger(4, spotsToCheck) && !setDirection(4)) {
				deduceLeftOrRight();
				prioritizeRow = false;
			}
		}
		else {
			if (!searchForDanger(2, spotsToCheck) && !setDirection(2)) {
				deduceLeftOrRight();
				prioritizeRow = false;
			}
		}
	}
	
	private void matchCol(int row, int col) {
		int distance = this.col-col;
		if (distance > 0) {
			if(!searchForDanger(3, spotsToCheck) && !setDirection(3)) {
				deduceLeftOrRight();
				prioritizeRow = true;
			}
		}
		else {
			if(!searchForDanger(1, spotsToCheck) && !setDirection(1)) {
				deduceLeftOrRight();
				prioritizeRow = true;
			}
		}
	}
	
	private int randomDirection() {
		return rand.nextInt(4);
	}
	
	private void generateSafePlace() {
		int row = rand.nextInt(map.length-140);
		int col = rand.nextInt(map[0].length-140);
		safeRow = row+70;
		safeCol = col+70;
		if (map[safeRow][safeCol] != null) {
			generateSafePlace();
		}
		state = 1;
	}
	
}
