package org.rloop.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.rloop.Screens.GameScreen;
import org.rloop.Util;

public class DeathStage {
    Stage currentStage;
    public Stage getCurrentStage() {return currentStage;}

    public DeathStage(ScreenAdapter game, Skin skin) {
        currentStage = new Stage(new ScreenViewport());

        Label youDied = new Label("YOU DIED", skin, "title");
        youDied.setColor(1, 0, 0, 1);
        youDied.setWidth(Util.monitorResolutionX((int)youDied.getWidth()));
        youDied.setHeight(Util.monitorResolutionY((int)youDied.getHeight()));
        youDied.setFontScale(Gdx.graphics.getWidth() * 1.0f/1920, Gdx.graphics.getHeight() * 1.0f/1080);
        youDied.setPosition(Gdx.graphics.getWidth() / 2f - youDied.getWidth() / 2, Gdx.graphics.getHeight() / 2f - youDied.getHeight()/2);
        currentStage.addActor(youDied);

        Label pressKey = new Label("press any key, to go to main menu", skin);
        pressKey.setWidth(Util.monitorResolutionX((int)pressKey.getWidth()));
        pressKey.setHeight(Util.monitorResolutionY((int)pressKey.getHeight()));
        pressKey.setFontScale(0.5f, 0.5f);
        pressKey.setFontScale(Gdx.graphics.getWidth() * 1.0f/1920, Gdx.graphics.getHeight() * 1.0f/1080);
        pressKey.setPosition(Gdx.graphics.getWidth() / 2f - pressKey.getWidth() / 2, Gdx.graphics.getHeight() / 2f - pressKey.getHeight()/2 - Util.monitorResolutionY(100));
        currentStage.addActor(pressKey);
    }
}
