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
	int scale;
	
	@Override
	//Instantiates game
	public void create () {
		shapeRender = new ShapeRenderer();
		round = new Game(150,150);
		scale = 2;
	}

	@Override
	//Runs this method every frame
	public void render () {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.4f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		round.progressGame();
		int length = round.getGrid().length;
		int width = round.getGrid()[0].length;
		
		//Draw Grid Box
		shapeRender.begin(ShapeType.Line);
		shapeRender.setColor(Color.GRAY);
		shapeRender.rect(50, 50, length*scale, width*scale);
		shapeRender.end();
		
		//Draw Light Trails
		shapeRender.begin(ShapeType.Filled);
		shapeRender.setColor(Color.GREEN);
		for (int i = 0; i < round.getGrid().length; i++) {
			for(int j = 0; j <round.getGrid()[i].length; j++) {
				if(round.getGrid()[i][j] != null && round.getGrid()[i][j].isCharacter() == false) {
					shapeRender.rect(50+(i*scale), 50+(j*scale), scale, scale);
				}
			}
		}
		shapeRender.end();
		
		//Draw Player One
		shapeRender.begin(ShapeType.Filled);
		shapeRender.setColor(Color.BLUE);
		shapeRender.circle(50+(round.getChar1Row()*scale), (50+round.getChar1Col()*scale), scale);
		shapeRender.end();
		
		//Draw Player Two
		shapeRender.begin(ShapeType.Filled);
		shapeRender.setColor(Color.BLUE);
		shapeRender.circle(50+(round.getChar2Row()*scale), (50+round.getChar2Col()*scale), scale);
		shapeRender.end();
	}
	
	@Override
	//Disposes of objects after Quitting
	public void dispose () {
		shapeRender.dispose();
	}
	
	
}
