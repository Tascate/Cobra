package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;

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

	public boolean isCharacter() {
		return false;
	}
	
	public boolean isObstacle() {
		return false;
	}

	public Item(int given_id) {
		id = given_id;
		inPlay = false;
		direction = 0;
		light = new Color(0,1,0,1);

		maxDuration = 5;
	}

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

	public void slowDown(Character character) {
		character.setNextActionableFrame(2);
	}

	public void freeze(Character character) {
		character.setNextActionableFrame(-1);
	}

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

	public boolean expired() {
		return duration >= maxDuration;
	}
	
	public void addSecond() {
		duration++;
	}

	public boolean ifInPlay() {
		return inPlay;
	}

	public void activate() {
		queued = false;
		inPlay = true;
	}
	public void deactivate(Character char1, Character char2) {
		inPlay = false;
	}

	public void queueItem() {
		queued = true;
	}
	
	public Boolean isQueued() {
		return queued;
	}

	public Boolean isInPlay() {
		return inPlay;
	}

	public static int getMaxID() {
		return MAX_ITEM_IDS;
	}

}
