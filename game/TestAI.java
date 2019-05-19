package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;

// Attempt to Survive
// If in Danger of Dieing
// Find a "Safe Spot"
// Get to that Safe Spot
// --- Match X first and Y First
// Always check if destination compromised for new safe spot

/**
 * AI class to make and test the AI(opponent) to make it play against the player. 
 * AI has to be uncontrollable.
 */
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
	 * @param grid - grid of game
	 * @param row - rol of AI
	 * @param col - col of AI
	 * @param speed - speed of AI
	 * @param color - color of AI
	 * @param d - direction of AI
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
	/**
	* Method that will have the AI go for the item if an item appears.
	* @param itemRow - row value for which the item is located
	* @param itemCol - column value for which the item is located
	*/
	public void prioritizeItem(int itemRow, int itemCol) {
		itemOnGrid = true;
		state = 2;
		this.itemRow = itemRow;
		this.itemCol = itemCol;
	}
	/**
	* If the item is taken by either the player or AI, then the AI
	* will resume its previous runAI behavior.
	*/
	public void resetState() {
		itemOnGrid = false;
		prioritizeRow = true;
		state = 3;
		runAI();
	}
	/**
	* Runs the AI which is the computer player. Makes the AI wander, seek out the item,
	* travel to the safe coordinate, and makes the AI avoid danger.
	*/
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
				if (inDanger) {
					deduceLeftOrRight();
				}
				else {
				reachCoords(itemRow, itemCol);
				}
				break;
			//search for a direction
			case 3:
				wander();
				break;
		}
		System.out.println(" New Direction:" + direction);
	}
	/**
	* If the AI is in danger, the AI will move in a direction that will
	* get it out of danger.
	*/
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
	/*
	* Method that will have the AI randomly wander as long as it is
	* not in danger.
	*/
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
	/**
	* Method that will recognize whether or not the AI is in danger of being killed.
	* @param direction - direction that AI is going in.
	* @param spots - checks for number of spots
	* @return true if the AI is in danger
	*/
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
	/**
	* Returns the distance the AI has before it collides with either the wall or the player
	* @param direction - direction the AI is going in
	* @return distance until impact
	*/
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
	/**
	* Deduces what direction the player is most likely to make in order to make the saftest turn.
	*/
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
	
	/**
	* Checks to see if the AI has reached the desired coordinate on the grid
	* @param row - row value to reach
	* @param col - column value to reach
	*/
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
	/*
	* Checks to see if the row the snake is in is the desired row
	* @param row - desired row value
	* @param col - column value
	*/
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
	/**
	* Checks to see if the row the snake is in is the desired colomn
	* @param row - row value
	* @param col - desired column value
	*/
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
	/**
	* Returns a random direction that the snake will move in.
	* @return random direction
	*/
	private int randomDirection() {
		return rand.nextInt(4);
	}
	/**
	* Randomly generates a safe coordinate for the AI to seek out and travel to
	*/
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
