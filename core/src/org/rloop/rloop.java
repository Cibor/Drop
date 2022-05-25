package org.rloop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class rloop extends Game {
	SpriteBatch batch;

	public OurMusic ourMusic;
	public Resources resources;

	GameScreen mainScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		ourMusic = new OurMusic();
		resources = new Resources();

		this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	public OurMusic getOurMusic() { return ourMusic; }

	@Override
	public void dispose () {
		batch.dispose();
		ourMusic.dispose();

	}
}
