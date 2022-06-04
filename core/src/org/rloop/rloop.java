package org.rloop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.rloop.Screens.GameScreen;
import org.rloop.Screens.MainMenuScreen;

public class rloop extends Game {
	SpriteBatch batch;

	public OurMusic ourMusic;
	public Resources resources;

	public GameScreen mainScreen;
	public MainMenuScreen mainMenu;

	@Override
	public void create () {
		batch = new SpriteBatch();
		ourMusic = new OurMusic();
		resources = new Resources();
		mainMenu = new MainMenuScreen(this);
		this.setScreen(mainMenu);
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	public OurMusic getOurMusic() { return ourMusic; }
	public GameScreen getMainScreen() { return mainScreen; }

	@Override
	public void dispose () {
		batch.dispose();
		ourMusic.dispose();

	}
}
