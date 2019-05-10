package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Cobra extends ApplicationAdapter {
	ShapeRenderer shapeRender;
	Game round;
	int fps;
	int scale;
	int startX;
	int startY;
	
	public Cobra() {}
	
	public Cobra(int frames) {
		fps = frames;
	}
	
	@Override
	/**
	 * Method to instantiate the game.
	 */
	public void create () {
		shapeRender = new ShapeRenderer();
		round = new Game(300,225);
		scale = 2;
		startX = 10;
		startY = 10;
	}

	@Override
	//Runs this method every frame
	public void render () {
		Gdx.gl.glClearColor(0.96f, 0.96f, 0.86f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		round.progressGame();
		int length = round.getGrid().length;
		int width = round.getGrid()[0].length;
		
		//Draw Grid Box
		shapeRender.begin(ShapeType.Line);
		shapeRender.setColor(Color.GRAY);
		shapeRender.rect(startX, startY, length*scale, width*scale);
		shapeRender.end();
		
		//Draw Light Trails
		shapeRender.begin(ShapeType.Filled);
		for (int i = 0; i < round.getGrid().length; i++) {
			for(int j = 0; j <round.getGrid()[i].length; j++) {
				if(round.getGrid()[i][j] != null && round.getGrid()[i][j].isCharacter() == false) {
					Color trail = round.getGrid()[i][j].getColor();
					shapeRender.setColor(trail);
					shapeRender.rect(startX+(i*scale), startY+(j*scale), scale, scale);
				}
			}
		}
		shapeRender.end();
		
		//Draw Player One
		shapeRender.begin(ShapeType.Filled);
		shapeRender.setColor(Color.BLUE);
		shapeRender.circle(startX+(round.getChar1Row()*scale)+1, (startY+round.getChar1Col()*scale)+1, scale);
		shapeRender.end();
		
		//Draw Player Two
		shapeRender.begin(ShapeType.Filled);
		shapeRender.setColor(Color.RED);
		shapeRender.circle(startX+(round.getChar2Row()*scale), (startY+round.getChar2Col()*scale), scale);
		shapeRender.end();
	}
	
	@Override
	/**
	 * Method to dispose of objects after quitting game.
	 */
	public void dispose () {
		shapeRender.dispose();
	}
	
	
}
