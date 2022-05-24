package org.rloop;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class rloop extends Game {
	SpriteBatch batch;

	public OurMusic ourMusic;

	public float GlobalAudioSound = 100;

	GameScreen mainScreen;

	@Override
	public void create () {
		batch = new SpriteBatch();
		ourMusic = new OurMusic();
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
	}
}
