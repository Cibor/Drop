package org.rloop.Stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.rloop.Screens.GameScreen;
import org.rloop.Screens.MainMenuScreen;
import org.rloop.Util;

public class PauseGUI extends Stage {
    Stage currentStage;
    public Stage getCurrentStage() {return currentStage; }

    public PauseGUI(final GameScreen gameScreenSuper, Skin skin) {
        currentStage = new Stage(new ScreenViewport());

        Window pauseBlock = new Window("", skin);
        pauseBlock.setMovable(false);
        pauseBlock.setResizable(false);
        pauseBlock.setTouchable(null);
        pauseBlock.setBounds(Util.monitorResolutionX(750), Util.monitorResolutionY(200), Util.monitorResolutionX(420), Util.monitorResolutionY(680));

        TextButton backBut = new TextButton("Back", skin);
        backBut.setWidth(Util.monitorResolutionX(380));
        backBut.setHeight(Util.monitorResolutionY(500/4 - 20));
        backBut.getLabel().setFontScale(Gdx.graphics.getWidth() * 1.0f/ 1920.0f, Gdx.graphics.getHeight() * 1.0f/ 1080.0f);
        backBut.setPosition(Gdx.graphics.getWidth()/2 - backBut.getWidth()/2, Util.monitorResolutionY(240));

        backBut.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreenSuper.paused = false;
            }
        });

        TextButton exitBut = new TextButton("Main Menu", skin);
        exitBut.setWidth(Util.monitorResolutionX(380));
        exitBut.setHeight(Util.monitorResolutionY(500/4 - 20));
        exitBut.getLabel().setFontScale(Gdx.graphics.getWidth() * 1.0f/ 1920.0f, Gdx.graphics.getHeight() * 1.0f/ 1080.0f);
        exitBut.setPosition(Gdx.graphics.getWidth()/2 - exitBut.getWidth()/2, Util.monitorResolutionY(250) + backBut.getHeight());


        exitBut.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreenSuper.dispose();
                gameScreenSuper.getGame().setScreen(new MainMenuScreen(gameScreenSuper.getGame()));
            }
        });


        TextButton settingBut = new TextButton("Settings", skin);
        settingBut.setWidth(Util.monitorResolutionX(380));
        settingBut.setHeight(Util.monitorResolutionY(500/4 - 20));
        settingBut.getLabel().setFontScale(Gdx.graphics.getWidth() * 1.0f/ 1920.0f, Gdx.graphics.getHeight() * 1.0f/ 1080.0f);
        settingBut.setPosition(Gdx.graphics.getWidth()/2 - settingBut.getWidth()/2, Util.monitorResolutionY(260) + exitBut.getHeight() + backBut.getHeight());

        exitBut.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });


        Label pauseLab = new Label("PAUSE", skin , "title");
        pauseLab.setFontScale(Gdx.graphics.getWidth() * 1.0f/ 1920.0f, Gdx.graphics.getHeight() * 1.0f/ 1080.0f);
        pauseLab.setPosition(Gdx.graphics.getWidth()/2 - Util.monitorResolutionX(Math.round(pauseLab.getWidth()/2)), Util.monitorResolutionY(820) - pauseLab.getHeight());

        currentStage.addActor(pauseBlock);
        currentStage.addActor(backBut);
        currentStage.addActor(pauseLab);
        currentStage.addActor(settingBut);
        currentStage.addActor(exitBut);

    }
}
