package com.mygdx.game;

public class Item extends FieldObject {
	
	private int id;
	private Character obtainer;
	private Character opponent;
	
	private int duration;
	private int maxDuration;
	private int cooldown;
	private boolean isInPlay;

	@Override
	public boolean isCharacter() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public Item(int given_id, int theRow, int theCol) {
		id = given_id;
		row = theRow;
		col = theCol;
		
		maxDuration = 5;
	}
	
	public void itemEffect(Character char1, Character char2, int whoGot) {
		duration = 0;
		isInPlay = true;
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
				speedUp(obtainer);
				break;
			case 1:
				slowDown(opponent);
				break;
			case 2:
				increaseTrailLength(obtainer);
				break;
			case 3:
				freeze(opponent);
				break;
		}
	}
	
	public void speedUp(Character character) {
		character.setSpeed(character.getSpeed() * 2);
	}
	
	public void slowDown(Character character) {
		character.setNextActionableFrame(character.getNextActionableFrame() * 2);
	}
	
	public void increaseTrailLength(Character character) {
		character.setTrailMultiplier(2);
	}
	
	public void freeze(Character character) {
		character.setNextActionableFrame(-1);
	}
	
	public void returnToNormal(Character char1, Character char2) {
		//Return Characters to their Normal Values
		switch (id) {
		case 0:
			slowDown(obtainer);
			break;
		case 1:
			speedUp(opponent);
			break;
		case 2:
			obtainer.setTrailMultiplier(1);
			break;
		case 3:
			opponent.setNextActionableFrame(1);
			break;
	}
	}
	
	public void incrementDuration() {
		duration++;
	}
	
	public boolean expired() {
		return maxDuration > duration;
	}
	
	public boolean ifInPlay() {
		return isInPlay;
	}
	
	public int getSpawnCooldown() {
		return cooldown;
	}

}
