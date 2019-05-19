package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
/**
*Class the programs the item
*/
public class Item extends FieldObject {

	private int id;
	private Character obtainer;
	private Character opponent;

	private int duration;
	private int maxDuration;
	private int cooldown;
	private boolean inPlay;
	private boolean queued;
	private final static int MAX_ITEM_IDS = 2;
	
	/**
	*Return the whether or not the item is a character
	*@return false that the item is a character
	*/
	public boolean isCharacter() {
		return false;
	}
	/**
	*Return the whether or not the item is a obstacle
	*@return false that the item is a obstacle
	*/
	public boolean isObstacle() {
		return false;
	}
	
	/**
	*Constructor that initializes the item
	*@param given_id - the id of the item
	*/
	public Item(int given_id) {
		id = given_id;
		inPlay = false;
		direction = 0;
		light = new Color(0,1,0,1);

		maxDuration = 5;
	}
	
	/**
	*Method that implements the effect of the item on whoever got it
	*@param char1 - character 1
	*@param char2 - character 2
	*@param whoGot - whoever got the item
	*/
	public void itemEffect(Character char1, Character char2, int whoGot) {
		if (inPlay) {
			duration = 0;
			switch (whoGot) {
			case 1:
				obtainer = char1;
				opponent = char2;
				break;
			case 2:
				obtainer = char2;
				opponent = char1;
				break;
			}
			switch (id) {
			case 0:
				slowDown(opponent);
				break;
			case 1:
				freeze(opponent);
				break;
			}
		}
		inPlay = false;
	}
	
	/**
	*Slows down the charcter
	*@param character
	*/
	public void slowDown(Character character) {
		character.setNextActionableFrame(2);
	}
	/**
	*Freezes down the charcter
	*@param character
	*/
	public void freeze(Character character) {
		character.setNextActionableFrame(-1);
	}
	/**
	*Returns the character to normal after the item wears off
	*@param char1 - character 1
	*@param char2 - character 2
	*/
	public void returnToNormal(Character char1, Character char2) {
		// Return Characters to their Normal Values
		switch (id) {
		case 0:
			opponent.setNextActionableFrame(1);
			break;
		case 1:
			opponent.setNextActionableFrame(1);
			break;
		}
		inPlay = false;
	}
	
	/**
	*Returns whether or not the item is expired
	*@return true of the duration is greater than or equal to the maximum duration
	*/
	public boolean expired() {
		return duration >= maxDuration;
	}
	
	/**
	*Adds a second the the item duration
	*/
	public void addSecond() {
		duration++;
	}
	
	/**
	*Returns whether or not the item is in play
	*@return whether or not the item is in play
	*/
	public boolean ifInPlay() {
		return inPlay;
	}
	
	/**
	*Activates the item
	*/
	public void activate() {
		queued = false;
		inPlay = true;
	}
	/**
	*Deactivates the item
	*@param char1 - character 1
	*@param char2 - character 2
	*/
	public void deactivate(Character char1, Character char2) {
		returnToNormal(char1, char2);
		inPlay = false;
	}
	
	/**
	*Queues the item
	*/
	public void queueItem() {
		queued = true;
	}
	/**
	*Returns whether or not the item is queued
	*@return whether or not the item is queued
	*/
	public Boolean isQueued() {
		return queued;
	}
	/**
	*Returns whether or not the item is in play
	*@return whether or not the item is in play
	*/
	public Boolean isInPlay() {
		return inPlay;
	}
	
	/**
	*Returns the maximum id of the item
	*return max id of item
	*/
	public static int getMaxID() {
		return MAX_ITEM_IDS;
	}

}
