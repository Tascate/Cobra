package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * Cobra class to make/draw the game and makes the players for the game. It basically makes the game.
 */
public class Cobra extends ApplicationAdapter {
	ShapeRenderer shapeRender;
	Texture pauseTexture;
	Texture tieTexture;
	SpriteBatch spriteBatch;
	BitmapFont font;
	
	Game round;
	int fps;
	float brightness;

	float scale;
	int startX;
	int startY;
	boolean secondPlayer;
	
	int state;
	// What state is the Game in
	// state = 0 -> Menus
	// state = 1 -> Game
	
	/**
	 * Default cobra constructor.
	 */
	public Cobra() {
		
	}
	
	/**
	 * Parameter Cobra constructor to get the fps(frames per second).
	 * @param frames - frames per second
	 */
	public Cobra(int frames) {
		fps = frames;
	}
	
	@Override
	/**
	 * Method to instantiate the game.
	 */
	public void create() {
		shapeRender = new ShapeRenderer();
		spriteBatch = new SpriteBatch();
		font = new BitmapFont();
		pauseTexture = new Texture(Gdx.files.internal("paused.png"));
		tieTexture = new Texture(Gdx.files.internal("tie.png"));
		
		brightness = 1;
		secondPlayer = false;
		round = new Game(310,225, secondPlayer);
		scale = 2.0f;
		startX = 10;
		startY = 10;
	}
	
	public void reset() {
		round = new Game(300,225, secondPlayer);
	}

	@Override
	/**
	 * Runs this method every frame while the game is running
	 */
	public void render() {
		Gdx.gl.glClearColor(0.96f, 0.96f, 0.86f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (!round.isGameEnded()) {
			round.progressGame();
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
			reset();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			reset();
		}
		drawGame();
	}
	
	private void drawGame() {
		int length = round.getGrid().length;
		int width = round.getGrid()[0].length;
		
		//Draws Grid Box for game
		shapeRender.begin(ShapeType.Line);
		shapeRender.setColor(Color.GRAY);
		shapeRender.rect(startX, startY, length*scale, width*scale);
		shapeRender.end();
		
		//Draw Light Trails for character in game
		shapeRender.begin(ShapeType.Filled);
		for (int i = 0; i < round.getGrid().length; i++) {
			for(int j = 0; j <round.getGrid()[i].length; j++) {
				if(round.getGrid()[i][j] != null && !round.getGrid()[i][j].isCharacter() ) {
					Color trail = round.getGrid()[i][j].getColor();
					shapeRender.setColor(trail);
					shapeRender.rect(startX+(i*scale), startY+(j*scale), scale, scale);
				}
			}
		}
		
		
		//Draw Player One
		shapeRender.setColor(Color.BLUE);
		shapeRender.circle(startX+(round.getChar1Row()*scale)+1, (startY+round.getChar1Col()*scale)+1, scale);
		//Draw Player Two
		shapeRender.setColor(Color.RED);
		shapeRender.circle(startX+(round.getChar2Row()*scale)+1, (startY+round.getChar2Col()*scale)+1, scale);
		shapeRender.end();
		
		
		//Draw Items
		for (int i = 0; i < round.getItemPool().length; i++) {
			if (round.getItemPool()[i].isQueued()) {
				shapeRender.begin(ShapeType.Line);
				shapeRender.setColor(Color.GREEN);
				int x = round.getItemPool()[i].getRow();
				int y = round.getItemPool()[i].getCol();
				shapeRender.triangle(startX+(x-5)*scale, startY+(y-5)*scale, startX+(x)*scale, startY+(y+5)*scale, startX+(x+5)*scale, startY+(y-5)*scale);
				shapeRender.end();
			}
			else if (round.getItemPool()[i].isInPlay()) {
				shapeRender.begin(ShapeType.Filled);
				shapeRender.setColor(Color.GREEN);
				int x = round.getItemPool()[i].getRow();
				int y = round.getItemPool()[i].getCol();
				shapeRender.triangle(startX+(x-5)*scale, startY+(y-5)*scale, startX+(x)*scale, startY+(y+5)*scale, startX+(x+5)*scale, startY+(y-5)*scale);
				shapeRender.end();
			}
		}
		shapeRender.end();
		
		if (round.isPaused()) {
			spriteBatch.begin();
			spriteBatch.draw(pauseTexture, Gdx.graphics.getWidth()/2 - pauseTexture.getWidth()/2, Gdx.graphics.getHeight()/2 - pauseTexture.getHeight()/2);
			spriteBatch.end();
		}
		if (round.isGameEnded()) {
			switch (round.findWinner()) {
			case 0:
				spriteBatch.begin();
				spriteBatch.draw(tieTexture, Gdx.graphics.getWidth()/2 - tieTexture.getWidth()/2, Gdx.graphics.getHeight()/2 - tieTexture.getHeight()/2);
				spriteBatch.end();
				break;
			}
		}
	}
	
	@Override
	/**
	 * Method to dispose of objects after quitting game.
	 */
	public void dispose () {
		shapeRender.dispose();
		spriteBatch.dispose();
		pauseTexture.dispose();
		tieTexture.dispose();
		font.dispose();
	}
	
	
}
